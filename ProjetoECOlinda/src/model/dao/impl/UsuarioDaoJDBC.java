package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Telefone;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {

	private Connection connection;

	public UsuarioDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Usuario usuario) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement(
					"INSERT USUARIO(nome_usuario, email_usuario, login_usuario, senha_usuario, data_nasc, status) VALUES\r\n"
							+ "(?, ?, ?, ?, ?, true);");
			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getLogin());
			st.setString(4, usuario.getSenha());

			if (Objects.isNull(usuario.getDataNasc())) {
				st.setNull(5, Types.DATE);

			} else {
				st.setDate(5, new java.sql.Date(usuario.getDataNasc().getTime()));
			}
			
			int linhasAf = st.executeUpdate();
			
			if(linhasAf > 0) {
				System.out.println("Usuario cadastrado com sucesso");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void alterar(Usuario usuario) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement("UPDATE USUARIO " + "SET " + "	nome_usuario = ?, " + "	email_usuario = ?, "
					+ "	login_usuario = ?, " + "	senha_usuario = ?, " + "	data_nasc = ? "
					+ "WHERE id_usuario = ?");

			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getLogin());
			st.setString(4, usuario.getSenha());
			st.setDate(5, new java.sql.Date(usuario.getDataNasc().getTime()));

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Usuario alterado com sucesso");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletar(Usuario usuario) {
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		PreparedStatement st4 = null;

		try {
			st1 = connection.prepareStatement("UPDATE USUARIO " + "SET status = false " + "WHERE id_usuario = ?;");
			st1.setInt(1, usuario.getId());

			st2 = connection.prepareStatement(
					"UPDATE USUARIO_PONTO_FAVORITO " + "SET status = false " + "WHERE usuario_id_usuario = ?;");
			st2.setInt(1, usuario.getId());

			st3 = connection
					.prepareStatement("UPDATE TELEFONE " + "SET status = false " + "WHERE usuario_id_usuario = ?;");
			st3.setInt(1, usuario.getId());

			st4 = connection.prepareStatement(
					"UPDATE USUARIO_ENDERECO " + "SET status = false " + "WHERE usuario_id_usuario = ?;");
			st4.setInt(1, usuario.getId());
			
			int linhasAf = st1.executeUpdate();
			st2.execute();
			st3.execute();
			st4.execute();
			
			if(linhasAf > 0) {
				System.out.println("Usuario excluido com sucesso");
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
	public Usuario login(Usuario usuario) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Usuario usuarioRetorno = null;

		try {
			st = connection.prepareStatement("SELECT  " + "	USUARIO.*, " + "	TELEFONE.id_telefone, "
					+ "	TELEFONE.telefone, " + "	TELEFONE.usuario_id_usuario, " + "	ENDERECO.id_endereco, "
					+ "	ENDERECO.cep, " + "	ENDERECO.logradouro, " + "	ENDERECO.numero, " + "	ENDERECO.complemento, "
					+ "	ENDERECO.bairro, " + "	ENDERECO.cidade, " + "	ENDERECO.estado " + "FROM USUARIO "
					+ "INNER JOIN TELEFONE " + "ON USUARIO.id_usuario = TELEFONE.usuario_id_usuario "
					+ "INNER JOIN USUARIO_ENDERECO " + "ON USUARIO.id_usuario = USUARIO_ENDERECO.usuario_id_usuario "
					+ "INNER JOIN ENDERECO " + "ON USUARIO_ENDERECO.endereco_id_endereco = ENDERECO.id_endereco "
					+ "WHERE USUARIO.login_usuario = ? AND USUARIO.senha_usuario = ? AND USUARIO.status = true AND TELEFONE.status = true AND ENDERECO.status = true;");

			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());

			rs = st.executeQuery();

			Map<Integer, Telefone> telefoneMap = new HashMap<>();
			Map<Integer, Endereco> enderecoMap = new HashMap<>();
			Map<Integer, Usuario> usuarioMap = new HashMap<>();

			while (rs.next()) {
				Telefone telefone = telefoneMap.get(rs.getInt("id_telefone"));
				Endereco endereco = enderecoMap.get(rs.getInt("id_endereco"));
				Usuario usuarioAnalise = usuarioMap.get(rs.getInt("id_usuario"));

				if (Objects.isNull(telefone)) {
					telefone = instanciarTelefone(rs);
					telefoneMap.put(rs.getInt("id_telefone"), telefone);

				}

				if (Objects.isNull(endereco)) {
					endereco = instanciarEndereco(rs);
					enderecoMap.put(rs.getInt("id_endereco"), endereco);
				}

				if (Objects.isNull(usuarioAnalise)) {
					usuarioAnalise = instanciarUsuario(rs);
					usuarioMap.put(rs.getInt("id_usuario"), usuarioAnalise);
				}

				if (!usuarioAnalise.getTelefones().contains(telefone)) {
					usuarioAnalise.addTelefone(telefone);

				}

				if (!usuarioAnalise.getEnderecos().contains(endereco)) {
					usuarioAnalise.addEndereco(endereco);
				}

				usuarioRetorno = usuarioAnalise;
			}

			st2 = connection.prepareStatement("SELECT " + "	PONTO_FAVORITO.id_ponto_favorito, " + "	EMPRESA.*, "
					+ "	ENDERECO.*, " + "	TELEFONE.* " + "FROM PONTO_FAVORITO " + "INNER JOIN USUARIO_PONTO_FAVORITO "
					+ "ON PONTO_FAVORITO.id_ponto_favorito = USUARIO_PONTO_FAVORITO.ponto_favorito_id_ponto_favorito "
					+ "INNER JOIN USUARIO " + "ON USUARIO_PONTO_FAVORITO.usuario_id_usuario = USUARIO.id_usuario "
					+ "INNER JOIN EMPRESA " + "ON PONTO_FAVORITO.empresa_id_empresa = EMPRESA.id_empresa "
					+ "INNER JOIN ENDERECO " + "ON EMPRESA.id_empresa = ENDERECO.empresa_id_empresa "
					+ "INNER JOIN TELEFONE " + "ON EMPRESA.id_empresa = TELEFONE.empresa_id_empresa "
					+ "WHERE  USUARIO.login_usuario = ? AND USUARIO.senha_usuario = ? AND PONTO_FAVORITO.status = true AND USUARIO_PONTO_FAVORITO.status = true AND ENDERECO.status = true "
					+ "AND EMPRESA.status = true AND TELEFONE.status = true;");

			st2.setString(1, usuario.getLogin());
			st2.setString(2, usuario.getSenha());

			rs2 = st2.executeQuery();

			Map<Integer, Empresa> empresaMap = new HashMap<>();
			Map<Integer, Telefone> telefoneEmpMap = new HashMap<>();
			Map<Integer, Endereco> enderecoEmpMap = new HashMap<>();
			Map<Integer, PontoFavorito> pontoFavMap = new HashMap<>();
			List<PontoFavorito> pontosUsuario = new ArrayList<>();

			while (rs2.next()) {
				Empresa empresaAux = empresaMap.get(rs2.getInt("id_empresa"));
				Telefone telefoneEmp = telefoneEmpMap.get(rs2.getInt("id_telefone"));
				Endereco enderecoEmp = enderecoEmpMap.get(rs2.getInt("id_endereco"));
				PontoFavorito pontoFav = pontoFavMap.get(rs2.getInt("id_ponto_favorito"));

				if (Objects.isNull(telefoneEmp)) {
					telefoneEmp = instanciarTelefoneEmpresa(rs2);
					telefoneMap.put(rs2.getInt("id_telefone"), telefoneEmp);

				}

				if (Objects.isNull(enderecoEmp)) {
					enderecoEmp = instanciarEnderecoEmpresa(rs2);
					enderecoMap.put(rs2.getInt("id_endereco"), enderecoEmp);
				}

				if (Objects.isNull(empresaAux)) {
					empresaAux = instanciarEmpresa(rs2);
					empresaMap.put(rs2.getInt("id_empresa"), empresaAux);
				}

				if (Objects.isNull(pontoFav)) {
					pontoFav = instanciarPontoFavorito(rs2);
					pontoFavMap.put(rs2.getInt("id_ponto_favorito"), pontoFav);
				}

				if (!empresaAux.getTelefones().contains(telefoneEmp)) {
					empresaAux.addTelefone(telefoneEmp);
				}

				if (!empresaAux.getEnderecos().contains(enderecoEmp)) {
					empresaAux.addEndereco(enderecoEmp);
				}

				if (pontoFav.getEmpresa() == null) {
					pontoFav.setEmpresa(empresaAux);
				}

				if (pontoFav.getEmpresa().getId() == empresaAux.getId()) {
					pontoFav.setEmpresa(empresaAux);
					pontoFav.setIdUsuario(usuarioRetorno.getId());
				}

				pontosUsuario.add(pontoFav);

			}

			usuarioRetorno.setPontosFav(pontosUsuario);

			return usuarioRetorno;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeResultSet(rs2);
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	private Telefone instanciarTelefoneEmpresa(ResultSet rs2) throws SQLException {
		Telefone telefone = new Telefone();
		telefone.setId(rs2.getInt("id_telefone"));
		telefone.setNumTelefone(rs2.getString("telefone"));
		telefone.setIdEmpresa(rs2.getInt("empresa_id_empresa"));
		telefone.setStatus(true);

		return telefone;
	}

	private Endereco instanciarEnderecoEmpresa(ResultSet rs2) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs2.getInt("id_endereco"));
		endereco.setCep(rs2.getString("cep"));
		endereco.setLogradouro(rs2.getString("logradouro"));
		endereco.setNumero(rs2.getInt("numero"));
		endereco.setComplemento(rs2.getString("complemento"));
		endereco.setBairro(rs2.getString("bairro"));
		endereco.setCidade(rs2.getString("cidade"));
		endereco.setEstado(rs2.getString("estado"));
		endereco.setIdEmpresa(rs2.getInt("empresa_id_empresa"));
		endereco.setStatus(true);

		return endereco;
	}

	private PontoFavorito instanciarPontoFavorito(ResultSet rs2) throws SQLException {
		PontoFavorito pontoFavorito = new PontoFavorito();
		pontoFavorito.setId(rs2.getInt("id_ponto_favorito"));
		pontoFavorito.setStatus(true);

		return pontoFavorito;

	}

	private Empresa instanciarEmpresa(ResultSet rs2) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setId(rs2.getInt("id_empresa"));
		empresa.setNome(rs2.getString("nome_empresa"));
		empresa.setEmail(rs2.getString("email_empresa"));
		empresa.setLogin(rs2.getString("login_empresa"));
		empresa.setSenha(rs2.getString("senha_empresa"));
		empresa.setStatus(true);

		return empresa;

	}

	private Usuario instanciarUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("id_usuario"));
		usuario.setNome(rs.getString("nome_usuario"));
		usuario.setEmail(rs.getString("email_usuario"));
		usuario.setLogin(rs.getString("login_usuario"));
		usuario.setSenha(rs.getString("senha_usuario"));
		usuario.setDataNasc(rs.getDate("data_nasc"));
		usuario.setStatus(rs.getBoolean("status"));

		return usuario;
	}

	private Endereco instanciarEndereco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getInt("id_endereco"));
		endereco.setCep(rs.getString("cep"));
		endereco.setLogradouro(rs.getString("logradouro"));
		endereco.setNumero(rs.getInt("numero"));
		endereco.setComplemento(rs.getString("complemento"));
		endereco.setBairro(rs.getString("bairro"));
		endereco.setCidade(rs.getString("cidade"));
		endereco.setEstado(rs.getString("estado"));
		endereco.setStatus(true);

		return endereco;
	}

	private Telefone instanciarTelefone(ResultSet rs) throws SQLException {
		Telefone telefone = new Telefone();
		telefone.setId(rs.getInt("id_telefone"));
		telefone.setNumTelefone(rs.getString("telefone"));
		telefone.setIdUsuario(rs.getInt("usuario_id_usuario"));
		telefone.setStatus(true);

		return telefone;

	}

}
