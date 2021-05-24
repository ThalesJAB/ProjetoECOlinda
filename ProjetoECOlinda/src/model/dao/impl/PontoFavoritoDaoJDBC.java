package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.EnderecoDao;
import model.dao.PontoFavoritoDao;
import model.dao.ResiduoDao;
import model.dao.TelefoneDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Residuo;
import model.entities.Telefone;
import model.entities.Usuario;

public class PontoFavoritoDaoJDBC implements PontoFavoritoDao {

	private Connection connection;

	public PontoFavoritoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrarPontoFvUsuario(PontoFavorito pontoFavorito) {
		PreparedStatement st = null;
		PontoFavorito pontoFavExiste = procurarPontoFavorito(pontoFavorito);

		try {
			if (Objects.nonNull(pontoFavExiste)) {

				st = connection.prepareStatement(
						"INSERT INTO USUARIO_PONTO_FAVORITO(usuario_id_usuario, ponto_favorito_id_ponto_favorito, status) values "
								+ "(?, ?, true)",
						Statement.RETURN_GENERATED_KEYS);

				st.setInt(1, pontoFavorito.getUsuario().getId());
				st.setInt(2, pontoFavExiste.getId());

				int linhasAf = st.executeUpdate();

				if (linhasAf > 0) {
					System.out.println("Ponto Favorito do Usuario foi cadastrado com sucesso !");

				} else {
					throw new DbException("Nenhum Ponto Favorito foi cadastrado !");
				}
			}else {
				System.out.println("Essa empresa ainda não pode ser ponto favorito de usuario");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public void cadastrarPontoFavorito(PontoFavorito pontoFavorito) {
		PreparedStatement st = null;
		ResultSet rs = null;
		PontoFavorito pontoFavExiste = procurarPontoFavorito(pontoFavorito);
		try {

			if (Objects.isNull(pontoFavExiste)) {
				st = connection.prepareStatement(
						"INSERT INTO PONTO_FAVORITO(empresa_id_empresa, status) values " + "(?, true)",
						Statement.RETURN_GENERATED_KEYS);

				st.setInt(1, pontoFavorito.getEmpresa().getId());

				int linhasAf = st.executeUpdate();

				if (linhasAf > 0) {
					rs = st.getGeneratedKeys();
					if (rs.next()) {
						int id = rs.getInt(1);
						pontoFavorito.setId(id);
						System.out.println("Ponto Favorito cadastrado com sucesso !");
					}

				} else {
					throw new DbException("Nenhum Ponto Favorito foi cadastrado !");
				}
			} else {
				System.out.println("Falha!, Ponto Favorito ja existente");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);

		}

	}
	
	@Override
	public void deletarPontoFavorito(PontoFavorito pontoFavorito) {
		PreparedStatement st = null;
		
		try {
			st = connection.prepareStatement("UPDATE PONTO_FAVORITO "
											+ "SET status = false "
											+ "WHERE id_ponto_favorito = ?");
			
			st.setInt(1, pontoFavorito.getId());
			
			int linhasAf = st.executeUpdate();
			
			if(linhasAf > 0) {
				System.out.println("Ponto Favorito deletado com sucesso !");
			}else {
				throw new DbException("Nenhum Ponto Favorito foi deletado !");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void deletarPontoFvUsuario(PontoFavorito pontoFavorito) {
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement("UPDATE USUARIO_PONTO_FAVORITO " + "SET status = false "
					+ "WHERE ponto_favorito_id_ponto_favorito = ?");

			st.setInt(1, pontoFavorito.getId());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {
				System.out.println("Ponto Favorito deletado com sucesso !");
			} else {
				throw new DbException("Nenhum Ponto Favorito foi deletado !");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public PontoFavorito procurarPontoFavorito(PontoFavorito pontoFavorito) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT * FROM PONTO_FAVORITO " + "INNER JOIN EMPRESA "
					+ "ON PONTO_FAVORITO.empresa_id_empresa = empresa.id_empresa "
					+ "WHERE PONTO_FAVORITO.empresa_id_empresa = ? AND EMPRESA.status = true");

			st.setInt(1, pontoFavorito.getEmpresa().getId());

			rs = st.executeQuery();

			if (rs.next()) {
				PontoFavorito pontoFav = new PontoFavorito();
				int id = rs.getInt(1);
				pontoFav.setId(id);
				return pontoFav;

			} else {

				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<PontoFavorito> listarPontosFav(Usuario usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement("SELECT " + "	EMPRESA.id_empresa, " + "	EMPRESA.nome_empresa, "
					+ "	EMPRESA.email_empresa, " + "	EMPRESA.status AS statusEmpresa, "
					+ " PONTO_FAVORITO.id_ponto_favorito, " + "	PONTO_FAVORITO.status AS statusPontoFv "
					+ "FROM USUARIO " + "INNER JOIN USUARIO_PONTO_FAVORITO "
					+ "ON USUARIO.id_usuario = USUARIO_PONTO_FAVORITO.usuario_id_usuario "
					+ "INNER JOIN PONTO_FAVORITO "
					+ "ON PONTO_FAVORITO.id_ponto_favorito = USUARIO_PONTO_FAVORITO.ponto_favorito_id_ponto_favorito "
					+ "INNER JOIN EMPRESA " + "ON EMPRESA.id_empresa = PONTO_FAVORITO.empresa_id_empresa "
					+ "WHERE USUARIO.id_usuario = ? AND USUARIO_PONTO_FAVORITO.status = true AND PONTO_FAVORITO.status = true;");

			st.setInt(1, usuario.getId());

			rs = st.executeQuery();

			List<PontoFavorito> pontosFavoritos = new ArrayList<>();
			while (rs.next()) {
				Empresa empresa = instanciarEmpresa(rs);
				/*
				 * GAMBIARRA DO BEM, VOU CHAMAR CADA IMPLEMENTAÇÃO DE ACORDO COM MINHAS
				 * NECESSIDADES. PRECISO DE TELEFONES, ENDERECOS E RESIDUOS TUDO PARA COMPLETAR
				 * A ENTIDADE EMPRESA
				 * 
				 * EVITANDO ASSIM REPETICAO DE CODIGO DESNECESSARIA, JA QUE EU TENHO
				 * IMPLEMENTAÇÕES ESPECIFICAS PARA DETERMINADAS NECESSIDADES
				 */
				// TELEFONES:
				TelefoneDao telefoneDao = DaoFactory.createTelefoneDao();
				List<Telefone> telefonesEmpresa = telefoneDao.procurarTelEmpresa(empresa); // Me retorna uma lista de
																							// telefones
				empresa.setTelefones(telefonesEmpresa);

				// ENDERECOS:
				EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
				List<Endereco> enderecoEmpresa = enderecoDao.procurarEndEmpresa(empresa);
				empresa.setEnderecos(enderecoEmpresa);

				// RESIDUOS:
				ResiduoDao residuoDao = DaoFactory.createResiduoDao();
				List<Residuo> residuosEmpresa = residuoDao.residuosEmpresa(empresa);
				empresa.setResiduos(residuosEmpresa);

				// AGORA POSSO INSTANCIAR MEU PONTO FAVORITO

				PontoFavorito pontoFavorito = instanciarPontoFavorito(empresa, usuario, rs);
				pontosFavoritos.add(pontoFavorito);

			}
			return pontosFavoritos;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	public Empresa instanciarEmpresa(ResultSet rs) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setId(rs.getInt("id_empresa"));
		empresa.setNome(rs.getString("nome_empresa"));
		empresa.setEmail(rs.getString("email_empresa"));
		empresa.setStatus(rs.getBoolean("statusEmpresa"));

		return empresa;

	}

	public PontoFavorito instanciarPontoFavorito(Empresa empresa, Usuario usuario, ResultSet rs) throws SQLException {
		PontoFavorito pontoFavorito = new PontoFavorito();
		pontoFavorito.setId(rs.getInt("id_ponto_favorito"));
		pontoFavorito.setStatus(rs.getBoolean("statusPontoFv"));
		pontoFavorito.setEmpresa(empresa);
		pontoFavorito.setUsuario(usuario);
		return pontoFavorito;
	}

	

}
