package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import db.DB;
import db.DbException;
import model.dao.EnderecoDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.Usuario;

public class EnderecoDaoJDBC implements EnderecoDao {

	private Connection connection;

	public EnderecoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrarEndEmpresa(Endereco endereco, Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"insert into ENDERECO(cep, logradouro, numero, complemento, bairro, cidade, estado, empresa_id_empresa, status) values "
							+ "(?, ?, ?, ?, ?, ?, ?, ?, true);",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, endereco.getCep());
			st.setString(2, endereco.getLogradouro());
			st.setInt(3, endereco.getNumero());

			if (Objects.isNull(endereco.getComplemento())) {
				st.setNull(4, Types.NVARCHAR);
			} else {
				st.setString(4, endereco.getComplemento());
			}

			st.setString(5, endereco.getBairro());
			st.setString(6, endereco.getCidade());
			st.setString(7, endereco.getEstado());
			st.setInt(8, empresa.getId());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					endereco.setId(id);
				}
			} else {
				throw new DbException("Endereco não foi cadastrado");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void cadastrarEndUsuario(Endereco endereco, Usuario usuario) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;

		try {

			connection.setAutoCommit(false);
			st = connection.prepareStatement(
					"insert into ENDERECO(cep, logradouro, numero, complemento, bairro, cidade, estado, status) values "
							+ "(?, ?, ?, ?, ?, ?, ?, true);",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, endereco.getCep());
			st.setString(2, endereco.getLogradouro());
			st.setInt(3, endereco.getNumero());

			if (Objects.isNull(endereco.getComplemento())) {
				st.setNull(4, Types.NVARCHAR);
			} else {
				st.setString(4, endereco.getComplemento());
			}

			st.setString(5, endereco.getBairro());
			st.setString(6, endereco.getCidade());
			st.setString(7, endereco.getEstado());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				rs = st.getGeneratedKeys();

				st2 = connection.prepareStatement(
						"INSERT INTO USUARIO_ENDERECO(usuario_id_usuario, endereco_id_endereco, status) VALUES " + "(?, ?, true);");

				if (rs.next()) {

					st2.setInt(1, usuario.getId());
					st2.setInt(2, rs.getInt(1));

					int linhasAf2 = st2.executeUpdate();

					connection.commit();

					if (linhasAf2 > 0) {

						int id = rs.getInt(1);
						endereco.setId(id);
						System.out.println("Endereço cadastrado com sucesso !");
					}
				}
			} else {
				throw new DbException("Endereço não foi cadastrado");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
				throw new DbException(
						"Erro inesperado! Endereço não foi cadastrado!, Ação foi desfeita, motivo: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("A ação não foi possivel ser desfeita, motivo: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void alterar(Endereco endereco) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement("UPDATE ENDERECO " + "SET " + "	cep = ?, " + "	logradouro = ?, "
					+ "	numero = ?, " + "	complemento = ?, " + "	bairro = ?, " + "	cidade = ?, "
					+ "	estado = ?, " + "	empresa_id_empresa = ? " + "WHERE id_endereco = ?;");

			st.setString(1, endereco.getCep());
			st.setString(2, endereco.getLogradouro());
			st.setInt(3, endereco.getNumero());
			st.setString(5, endereco.getBairro());
			st.setString(6, endereco.getCidade());
			st.setString(7, endereco.getEstado());

			if (Objects.isNull(endereco.getIdEmpresa())) {
				st.setNull(8, Types.INTEGER);

			} else {
				st.setInt(8, endereco.getIdEmpresa());
			}

			if (Objects.isNull(endereco.getComplemento())) {
				st.setNull(4, Types.NVARCHAR);
			} else {
				st.setString(4, endereco.getComplemento());
			}

			st.setInt(9, endereco.getId());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Endereço alterado com sucesso !");
			} else {
				throw new DbException("Nenhum endereço foi alterado !");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarEndEmpresa(Endereco endereco, Empresa empresa) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement(
					"UPDATE ENDERECO " + "SET status = false " + "WHERE id_endereco = ? and empresa_id_empresa = ?;");

			st.setInt(1, endereco.getId());
			st.setInt(2, empresa.getId());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Linhas Afetadas: " + linhasAf);

			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");

			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarEndUsuario(Endereco endereco, Usuario usuario) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			connection.setAutoCommit(false);

			st = connection.prepareStatement("UPDATE USUARIO_ENDERECO " + "SET status = false "
					+ "WHERE endereco_id_endereco = ? and usuario_id_usuario = ?;");

			st.setInt(1, endereco.getId());
			st.setInt(2, usuario.getId());

			st2 = connection.prepareStatement("UPDATE ENDERECO " + "SET status = false " + "WHERE id_endereco = ?");

			st2.setInt(1, endereco.getId());

			int linhasAf1 = st.executeUpdate();
			int linhasAf2 = st2.executeUpdate();

			connection.commit();

			if (linhasAf1 > 0 && linhasAf2 > 0) {
				System.out.println("Endereco deletado com sucesso");

			}

		} catch (SQLException e) {
			try {
				connection.rollback();
				throw new DbException(
						"Erro inesperado! Endereco não foi deletado!, Ação foi desfeita, motivo:" + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("A ação não foi possivel ser desfeita, motivo: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public List<Endereco> procurarEndEmpresa(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"SELECT id_endereco as id, cep, logradouro, numero, complemento, bairro, cidade, estado, empresa_id_empresa, status FROM ENDERECO "
							+ "WHERE empresa_id_empresa = ? and status = ?;");

			st.setInt(1, empresa.getId());
			st.setBoolean(2, empresa.getStatus());

			rs = st.executeQuery();

			List<Endereco> enderecos = new ArrayList<>();
			while (rs.next()) {
				Endereco endereco = instanciarEndereco(rs);
				enderecos.add(endereco);
			}

			return enderecos;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Endereco> procurarEndUsuario(Usuario usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"SELECT id_endereco as id, cep, logradouro, numero, complemento, bairro, cidade, estado, empresa_id_empresa, endereco.status "
							+ "FROM ENDERECO INNER JOIN USUARIO_ENDERECO " + "ON id_endereco = endereco_id_endereco "
							+ "INNER JOIN USUARIO " + "ON id_usuario = usuario_id_usuario "
							+ "WHERE id_usuario = ? and USUARIO.status =  true and USUARIO_ENDERECO.status =  true;");

			st.setInt(1, usuario.getId());

			rs = st.executeQuery();

			List<Endereco> enderecos = new ArrayList<>();
			while (rs.next()) {
				Endereco endereco = instanciarEndereco(rs);
				enderecos.add(endereco);
			}

			return enderecos;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeResultSet(rs);
		}
	}

	public Endereco instanciarEndereco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getInt("id"));
		endereco.setCep(rs.getString("cep"));
		endereco.setLogradouro(rs.getString("logradouro"));
		endereco.setNumero(rs.getInt("numero"));
		endereco.setComplemento(rs.getString("complemento"));
		endereco.setBairro(rs.getString("bairro"));
		endereco.setCidade(rs.getString("cidade"));
		endereco.setEstado(rs.getString("estado"));
		rs.getObject("empresa_id_empresa");
		if (rs.wasNull()) {
			endereco.setIdEmpresa(null);
		} else {
			endereco.setIdEmpresa(rs.getInt("empresa_id_empresa"));
		}

		endereco.setStatus(rs.getBoolean("status"));

		return endereco;

	}

}
