package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.Objects;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;

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
					"INSERT INTO USUARIO(nome_usuario, email_usuario, login_usuario, senha_usuario, data_nasc, status) VALUES "
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

			if (linhasAf > 0) {
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

			if (linhasAf > 0) {
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
		ResultSet rs = null;
		Usuario usuarioRetorno = null;

		try {
			st = connection.prepareStatement("SELECT * FROM USUARIO "
					+ "WHERE USUARIO.login_usuario = ? AND USUARIO.senha_usuario = ? AND USUARIO.status = true");

			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());

			rs = st.executeQuery();

			if (rs.next()) {

				usuarioRetorno = instanciarUsuario(rs);

			}

			return usuarioRetorno;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	public boolean existeLogin(Usuario usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM USUARIO " + "WHERE UPPER(login_usuario) = ?;");

			String loginProc = usuario.getLogin().toUpperCase();
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

	public boolean existeEmail(Usuario usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM USUARIO " + "WHERE UPPER(email_usuario) = ?;");

			String emailProc = usuario.getEmail().toUpperCase();

			st.setString(1, emailProc);
			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

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

}
