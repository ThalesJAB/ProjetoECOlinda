package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import db.DB;
import db.DbException;
import model.dao.EmpresaDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.Residuo;
import model.entities.Telefone;

public class EmpresaDaoJDBC implements EmpresaDao {

	private Connection connection;

	public EmpresaDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"INSERT INTO EMPRESA(nome_empresa, email_empresa, login_empresa, senha_empresa, status) VALUES\r\n"
							+ "(?, ?, ?, ?, true)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, empresa.getNome());
			st.setString(2, empresa.getEmail());

			st.setString(3, empresa.getLogin());

			st.setString(4, empresa.getSenha());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					empresa.setId(id);
					System.out.println("Empresa cadastrada com sucesso");
				}
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public void alterar(Empresa empresa) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement("UPDATE EMPRESA " + "SET " + "	nome_empresa = ?, " + "	email_empresa = ?, "
					+ "	login_empresa = ?, " + "	senha_empresa = ? "

					+ "WHERE id_empresa = ?");

			st.setString(1, empresa.getNome());
			st.setString(2, empresa.getEmail());
			st.setString(3, empresa.getLogin());
			st.setString(4, empresa.getSenha());
			st.setInt(5, empresa.getId());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Empresa alterada com sucesso !");
			} else {
				throw new DbException("Nenhuma empresa foi alterada !");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletar(Empresa empresa) {
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		PreparedStatement st4 = null;

		try {
			st1 = connection.prepareStatement("UPDATE EMPRESA " + "SET status = false " + "WHERE id_empresa = ?;");

			st2 = connection
					.prepareStatement("UPDATE ENDERECO " + "SET status = false " + "WHERE empresa_id_empresa = ?;");

			st3 = connection
					.prepareStatement("UPDATE TELEFONE " + "SET status = false " + "WHERE empresa_id_empresa = ?;");

			st4 = connection.prepareStatement(
					"UPDATE RESIDUO_EMPRESA " + "SET status = false " + "WHERE empresa_id_empresa = ?;");

			st1.setInt(1, empresa.getId());
			st2.setInt(1, empresa.getId());
			st3.setInt(1, empresa.getId());
			st4.setInt(1, empresa.getId());

			int linhasAf = st1.executeUpdate();
			st2.execute();
			st3.execute();
			st4.execute();

			if (linhasAf > 0) {
				System.out.println("Empresa deletada com sucesso");
			} else {
				throw new DbException("Empresa n?o foi deletada");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st4);
			DB.closeStatement(st3);
			DB.closeStatement(st2);
			DB.closeStatement(st1);
			
		}

	}

	@Override
	public Empresa procurar(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Empresa empresaRetorno = null;

		try {

			st = connection.prepareStatement("SELECT  " + "	EMPRESA.*, " + "	TELEFONE.id_telefone, "
					+ "	TELEFONE.telefone, " + "	TELEFONE.empresa_id_empresa, " + "	ENDERECO.id_endereco, "
					+ "	ENDERECO.cep, " + "	ENDERECO.logradouro, " + "	ENDERECO.numero, " + "	ENDERECO.complemento, "
					+ "	ENDERECO.bairro, " + "	ENDERECO.cidade, " + "	ENDERECO.estado, "
					+ "	ENDERECO.empresa_id_empresa, " + "	RESIDUO.id_residuo, " + "	RESIDUO.tipo_residuo, "
					+ "	RESIDUO.descricao_residuo " + "FROM EMPRESA " + "INNER JOIN TELEFONE "
					+ "ON EMPRESA.id_empresa = TELEFONE.empresa_id_empresa " + "INNER JOIN ENDERECO "
					+ "ON EMPRESA.id_empresa = ENDERECO.empresa_id_empresa " + "INNER JOIN RESIDUO_EMPRESA "
					+ "ON EMPRESA.id_empresa = RESIDUO_EMPRESA.empresa_id_empresa " + "INNER JOIN RESIDUO "
					+ "ON RESIDUO_EMPRESA.residuo_id_residuo = RESIDUO.id_residuo "
					+ "WHERE upper(EMPRESA.nome_empresa) = ? AND EMPRESA.status = true AND TELEFONE.status = true AND ENDERECO.status = true AND RESIDUO_EMPRESA.status = true;");

			String nomeMaiusculo = empresa.getNome().toUpperCase();

			st.setString(1, nomeMaiusculo);

			rs = st.executeQuery();

			Map<Integer, Telefone> telefoneMap = new HashMap<>();
			Map<Integer, Residuo> residuoMap = new HashMap<>();
			Map<Integer, Endereco> enderecoMap = new HashMap<>();
			Map<Integer, Empresa> empresaMap = new HashMap<>();

			while (rs.next()) {
				Telefone telefone = telefoneMap.get(rs.getInt("id_telefone"));
				Residuo residuo = residuoMap.get(rs.getInt("id_residuo"));
				Endereco endereco = enderecoMap.get(rs.getInt("id_endereco"));
				Empresa empresaAnalise = empresaMap.get(rs.getInt("id_empresa"));

				if (Objects.isNull(telefone)) {
					telefone = instanciarTelefone(rs);
					telefoneMap.put(rs.getInt("id_telefone"), telefone);

				}

				if (Objects.isNull(residuo)) {
					residuo = instanciarResiduo(rs);
					residuoMap.put(rs.getInt("id_residuo"), residuo);
				}

				if (Objects.isNull(endereco)) {
					endereco = instanciarEndereco(rs);
					enderecoMap.put(rs.getInt("id_endereco"), endereco);
				}

				if (Objects.isNull(empresaAnalise)) {
					empresaAnalise = instanciarEmpresa(rs);
					empresaMap.put(rs.getInt("id_empresa"), empresaAnalise);
				}

				if (!empresaAnalise.getTelefones().contains(telefone)) {
					empresaAnalise.addTelefone(telefone);

				}

				if (!empresaAnalise.getEnderecos().contains(endereco)) {
					empresaAnalise.addEndereco(endereco);
				}

				if (!empresaAnalise.getResiduos().contains(residuo)) {
					empresaAnalise.addResiduo(residuo);
				}

				empresaRetorno = empresaAnalise;

			}

			return empresaRetorno;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public Empresa login(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Empresa empresaRetorno = null;

		try {
			st = connection.prepareStatement("SELECT * FROM EMPRESA "
											+ "WHERE EMPRESA.login_empresa = ? AND EMPRESA.senha_empresa = ? AND EMPRESA.status = true;");

			st.setString(1, empresa.getLogin());
			st.setString(2, empresa.getSenha());

			rs = st.executeQuery();
			
			if(rs.next()) {
				empresaRetorno = instanciarEmpresa(rs);
			}
			
			return empresaRetorno;
			
			


		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Empresa> listarEmpresaResiduo(Residuo residuo) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"SELECT EMPRESA.id_empresa, EMPRESA.nome_empresa, EMPRESA.email_empresa, TELEFONE.*, RESIDUO.*, ENDERECO.* FROM RESIDUO "
							+ "INNER JOIN RESIDUO_EMPRESA "
							+ "ON RESIDUO_EMPRESA.residuo_id_residuo = RESIDUO.id_residuo " + "INNER JOIN EMPRESA "
							+ "ON EMPRESA.id_empresa = RESIDUO_EMPRESA.empresa_id_empresa " + "INNER JOIN TELEFONE "
							+ "ON TELEFONE.empresa_id_empresa = EMPRESA.id_empresa " + "INNER JOIN ENDERECO "
							+ "ON ENDERECO.empresa_id_empresa = EMPRESA.id_empresa "
							+ "WHERE RESIDUO.tipo_residuo = ? and RESIDUO_EMPRESA.status = true  and EMPRESA.status = true AND TELEFONE.status = true AND ENDERECO.status = true "
							+ "AND RESIDUO_EMPRESA.status = true");

			st.setString(1, residuo.getTipoResiduo());
			rs = st.executeQuery();

			List<Empresa> empresas = new ArrayList<>();

			Map<Integer, Telefone> telefoneMap = new HashMap<>();
			Map<Integer, Residuo> residuoMap = new HashMap<>();
			Map<Integer, Endereco> enderecoMap = new HashMap<>();
			Map<Integer, Empresa> empresaMap = new HashMap<>();

			while (rs.next()) {
				Telefone telefone = telefoneMap.get(rs.getInt("id_telefone"));
				Residuo residuo1 = residuoMap.get(rs.getInt("id_residuo"));
				Endereco endereco = enderecoMap.get(rs.getInt("id_endereco"));
				Empresa empresa = empresaMap.get(rs.getInt("id_empresa"));

				if (Objects.isNull(telefone)) {
					telefone = instanciarTelefone(rs);
					telefoneMap.put(rs.getInt("id_telefone"), telefone);

				}

				if (Objects.isNull(residuo1)) {
					residuo1 = instanciarResiduo(rs);
					residuoMap.put(rs.getInt("id_residuo"), residuo1);
				}

				if (Objects.isNull(endereco)) {
					endereco = instanciarEndereco(rs);
					enderecoMap.put(rs.getInt("id_endereco"), endereco);
				}

				if (Objects.isNull(empresa)) {
					empresa = instanciarEmpresaEsp(rs);
					empresaMap.put(rs.getInt("id_empresa"), empresa);
				}

				if (!empresa.getTelefones().contains(telefone)) {
					empresa.addTelefone(telefone);

				}

				if (!empresa.getEnderecos().contains(endereco)) {
					empresa.addEndereco(endereco);
				}

				if (!empresa.getResiduos().contains(residuo1)) {
					empresa.addResiduo(residuo1);
				}

				if (!empresas.contains(empresa)) {
					empresas.add(empresa);
				}

			}

			return empresas;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public List<Empresa> listarEmpresas() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT " + "	EMPRESA.*, " + "	TELEFONE.id_telefone, "
					+ "	TELEFONE.telefone, " + "	TELEFONE.empresa_id_empresa, " + "	ENDERECO.id_endereco, "
					+ "	ENDERECO.cep, " + "	ENDERECO.logradouro, " + "	ENDERECO.numero, " + "	ENDERECO.complemento, "
					+ "	ENDERECO.bairro, " + "	ENDERECO.cidade, " + "	ENDERECO.estado, "
					+ "	ENDERECO.empresa_id_empresa, " + "	RESIDUO.id_residuo, " + "	RESIDUO.tipo_residuo, "
					+ "	RESIDUO.descricao_residuo " + "FROM EMPRESA " + "INNER JOIN TELEFONE "
					+ "ON EMPRESA.id_empresa = TELEFONE.empresa_id_empresa " + "INNER JOIN ENDERECO "
					+ "ON EMPRESA.id_empresa = ENDERECO.empresa_id_empresa " + "INNER JOIN RESIDUO_EMPRESA "
					+ "ON EMPRESA.id_empresa = RESIDUO_EMPRESA.empresa_id_empresa " + "INNER JOIN RESIDUO "
					+ "ON RESIDUO_EMPRESA.residuo_id_residuo = RESIDUO.id_residuo "
					+ "WHERE EMPRESA.status = true AND TELEFONE.status = true AND ENDERECO.status = true AND RESIDUO_EMPRESA.status = true;");

			rs = st.executeQuery();
			List<Empresa> empresas = new ArrayList<>();

			Map<Integer, Telefone> telefoneMap = new HashMap<>();
			Map<Integer, Residuo> residuoMap = new HashMap<>();
			Map<Integer, Endereco> enderecoMap = new HashMap<>();
			Map<Integer, Empresa> empresaMap = new HashMap<>();

			while (rs.next()) {
				Telefone telefone = telefoneMap.get(rs.getInt("id_telefone"));
				Residuo residuo = residuoMap.get(rs.getInt("id_residuo"));
				Endereco endereco = enderecoMap.get(rs.getInt("id_endereco"));
				Empresa empresa = empresaMap.get(rs.getInt("id_empresa"));

				if (Objects.isNull(telefone)) {
					telefone = instanciarTelefone(rs);
					telefoneMap.put(rs.getInt("id_telefone"), telefone);

				}

				if (Objects.isNull(residuo)) {
					residuo = instanciarResiduo(rs);
					residuoMap.put(rs.getInt("id_residuo"), residuo);
				}

				if (Objects.isNull(endereco)) {
					endereco = instanciarEndereco(rs);
					enderecoMap.put(rs.getInt("id_endereco"), endereco);
				}

				if (Objects.isNull(empresa)) {
					empresa = instanciarEmpresa(rs);
					empresaMap.put(rs.getInt("id_empresa"), empresa);
				}

				if (!empresa.getTelefones().contains(telefone)) {
					empresa.addTelefone(telefone);

				}

				if (!empresa.getEnderecos().contains(endereco)) {
					empresa.addEndereco(endereco);
				}

				if (!empresa.getResiduos().contains(residuo)) {
					empresa.addResiduo(residuo);
				}

				if (!empresas.contains(empresa)) {
					empresas.add(empresa);
				}

			}

			return empresas;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public boolean existeLogin(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM EMPRESA " + "WHERE UPPER(login_empresa) = ?;");

			String loginProc = empresa.getLogin().toUpperCase();
			st.setString(1, loginProc);
			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public boolean existeEmail(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM EMPRESA " + "WHERE UPPER(email_empresa) = ?;");

			String emailProc = empresa.getEmail().toUpperCase();

			st.setString(1, emailProc);
			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	public Telefone instanciarTelefone(ResultSet rs) throws SQLException {
		Telefone telefone = new Telefone();
		telefone.setId(rs.getInt("id_telefone"));
		telefone.setNumTelefone(rs.getString("telefone"));
		telefone.setIdEmpresa(rs.getInt("empresa_id_empresa"));
		telefone.setStatus(true);
		return telefone;
	}

	public Residuo instanciarResiduo(ResultSet rs) throws SQLException {
		Residuo residuo = new Residuo();
		residuo.setId(rs.getInt("id_residuo"));
		residuo.setTipoResiduo(rs.getString("tipo_residuo"));
		residuo.setDescricaoResiduo(rs.getString("descricao_residuo"));
		residuo.setStatus(true);

		return residuo;

	}

	public Endereco instanciarEndereco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getInt("id_endereco"));
		endereco.setCep(rs.getString("cep"));
		endereco.setLogradouro(rs.getString("logradouro"));
		endereco.setNumero(rs.getInt("numero"));
		endereco.setComplemento(rs.getString("complemento"));
		endereco.setBairro(rs.getString("bairro"));
		endereco.setCidade(rs.getString("cidade"));
		endereco.setEstado(rs.getString("estado"));
		endereco.setIdEmpresa(rs.getInt("empresa_id_empresa"));
		endereco.setStatus(true);

		return endereco;

	}

	public Empresa instanciarEmpresa(ResultSet rs) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setId(rs.getInt("id_empresa"));
		empresa.setNome(rs.getString("nome_empresa"));
		empresa.setEmail(rs.getString("email_empresa"));
		empresa.setLogin(rs.getString("login_empresa"));
		empresa.setSenha(rs.getString("senha_empresa"));
		empresa.setStatus(rs.getBoolean("status"));

		return empresa;

	}

	public Empresa instanciarEmpresaEsp(ResultSet rs) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setId(rs.getInt("id_empresa"));
		empresa.setNome(rs.getString("nome_empresa"));
		empresa.setEmail(rs.getString("email_empresa"));
		empresa.setStatus(true);

		return empresa;

	}

}
