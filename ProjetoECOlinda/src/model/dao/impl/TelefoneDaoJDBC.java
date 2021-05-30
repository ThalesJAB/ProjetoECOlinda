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
import model.dao.TelefoneDao;
import model.entities.Empresa;
import model.entities.Telefone;
import model.entities.Usuario;

public class TelefoneDaoJDBC implements TelefoneDao {

	private Connection connection;

	public TelefoneDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Telefone telefone) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement(
					"insert into TELEFONE(telefone, USUARIO_id_usuario, EMPRESA_id_empresa, status) VALUES "
							+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			if (Objects.isNull(telefone.getIdUsuario())) {
				st.setString(1, telefone.getNumTelefone());
				st.setNull(2, Types.INTEGER);
				st.setInt(3, telefone.getIdEmpresa());
				st.setBoolean(4, telefone.getStatus());
			} else {
				st.setString(1, telefone.getNumTelefone());
				st.setInt(2, telefone.getIdUsuario());
				st.setNull(3, Types.INTEGER);
				st.setBoolean(4, telefone.getStatus());

			}

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					telefone.setId(id);
				}

			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public void alterar(Telefone telefone) {
		PreparedStatement st = null;

		try {

			st = connection
					.prepareStatement("UPDATE TELEFONE  " + "SET  " + "	telefone = ?, " + "	usuario_id_usuario = ?, "
							+ " 	empresa_id_empresa = ?, " + "	status = ? " + "WHERE " + " id_telefone = ?;");

			if (Objects.isNull(telefone.getIdUsuario())) {
				st.setString(1, telefone.getNumTelefone());
				st.setNull(2, Types.INTEGER);
				st.setInt(3, telefone.getIdEmpresa());
				st.setBoolean(4, telefone.getStatus());
				st.setInt(5, telefone.getId());

			} else {
				st.setString(1, telefone.getNumTelefone());
				st.setInt(2, telefone.getIdUsuario());
				st.setNull(3, Types.INTEGER);
				st.setBoolean(4, telefone.getStatus());
				st.setInt(5, telefone.getId());

			}

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Número alterado com sucesso");

			} else {
				throw new DbException("Número não foi alterado!");

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletar(Telefone telefone) {

		PreparedStatement st = null;

		try {

			st = connection.prepareStatement("UPDATE telefone " + "SET status = false " + "WHERE id_telefone = ?;");
			st.setInt(1, telefone.getId());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Número deletado");

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
	public List<Telefone> procurarTelUsuario(Usuario usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement(
					"select id_telefone as id, telefone as numTelefone, usuario_id_usuario as idUsuario, empresa_id_empresa as idEmpresa, status from telefone "
							+ "where usuario_id_usuario = ? and status = ?;");

			st.setInt(1, usuario.getId());
			st.setBoolean(2, usuario.getStatus());

			rs = st.executeQuery();

			List<Telefone> telefonesUsuario = new ArrayList<>();
			while (rs.next()) {
				Telefone telefoneUsuario = instanciarTelefone(rs);
				telefonesUsuario.add(telefoneUsuario);

			}

			return telefonesUsuario;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Telefone> procurarTelEmpresa(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement(
					"select id_telefone as id, telefone as numTelefone, usuario_id_usuario as idUsuario, empresa_id_empresa as idEmpresa, status from telefone "
							+ "where empresa_id_empresa = ? and status = ?;");

			st.setInt(1, empresa.getId());
			st.setBoolean(2, empresa.getStatus());

			rs = st.executeQuery();

			List<Telefone> telefonesEmpresa = new ArrayList<>();
			while (rs.next()) {
				Telefone TelefoneEmpresa = instanciarTelefone(rs);
				telefonesEmpresa.add(TelefoneEmpresa);

			}

			return telefonesEmpresa;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Telefone> listar() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"select id_telefone as id, telefone as numTelefone, usuario_id_usuario as idUsuario, empresa_id_empresa as idEmpresa, status from telefone "
							+ "where status = true " + "order by id_telefone;");

			rs = st.executeQuery();

			List<Telefone> telefones = new ArrayList<>();

			while (rs.next()) {
				Telefone telefone = instanciarTelefone(rs);
				telefones.add(telefone);
			}

			return telefones;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	public Telefone instanciarTelefone(ResultSet rs) throws SQLException {
		Telefone telefone = new Telefone();

		telefone.setId(rs.getInt("id"));
		telefone.setNumTelefone(rs.getString("numTelefone"));
		rs.getObject("idUsuario");
		if (rs.wasNull()) {
			telefone.setIdUsuario(null);
			telefone.setIdEmpresa(rs.getInt("idEmpresa"));
		} else {
			telefone.setIdEmpresa(null);
			telefone.setIdUsuario(rs.getInt("idUsuario"));
		}

		telefone.setStatus(rs.getBoolean("status"));

		return telefone;

	}

}
