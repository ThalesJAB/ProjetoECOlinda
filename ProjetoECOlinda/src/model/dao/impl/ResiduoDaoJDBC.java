package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ResiduoDao;
import model.entities.Empresa;
import model.entities.Residuo;

public class ResiduoDaoJDBC implements ResiduoDao {

	private Connection connection;

	public ResiduoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Residuo residuo) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement(
					"INSERT INTO RESIDUO(tipo_residuo, descricao_residuo, status) " + "VALUES " + "	(?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, residuo.getTipoResiduo());
			st.setString(2, residuo.getDescricaoResiduo());
			st.setBoolean(3, residuo.getStatus());

			int linhasAf = st.executeUpdate();

			if (linhasAf > 0) {

				System.out.println("Linhas Afetadas: " + linhasAf);
				rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					residuo.setId(id);
				}
				DB.closeResultSet(rs);

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
	public void cadastrarResiduoEmp(Residuo residuo, Empresa empresa) {
		PreparedStatement st = null;

		try {

			st = connection.prepareStatement(
					"INSERT INTO RESIDUO_EMPRESA(residuo_id_residuo, empresa_id_empresa, status) VALUES "
							+ "(?, ?, true);");

			st.setInt(1, residuo.getId());
			st.setInt(2, empresa.getId());

			int linhasAf = st.executeUpdate();
			
			if(linhasAf > 0) {
				System.out.println("Residuo cadastrado com sucesso");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(null);
		}

	}

	@Override
	public void alterar(Residuo residuo) {
		PreparedStatement st = null;

		try {

			st = connection.prepareStatement("UPDATE RESIDUO " + "SET " + "tipo_residuo = ?, "
					+ "descricao_residuo = ?, " + "status = ? " + "WHERE id_residuo = ?;");

			st.setString(1, residuo.getTipoResiduo());
			st.setString(2, residuo.getDescricaoResiduo());
			st.setBoolean(3, residuo.getStatus());
			st.setInt(4, residuo.getId());

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
	public void deletar(Residuo residuo, Empresa empresa) {
		PreparedStatement st = null;

		try {

			st = connection.prepareStatement("UPDATE Residuo_empresa " + "SET " + "status = false " + "WHERE "
					+ "	residuo_id_residuo = ? and empresa_id_empresa = ?;");

			st.setInt(1, residuo.getId());
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
	public Residuo procurar(Residuo residuo) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"SELECT id_residuo AS id, tipo_residuo AS tipoResiduo, descricao_residuo AS descricaoResiduo, status FROM residuo "
							+ "WHERE status = ? AND id_residuo = ? " + "ORDER BY id_residuo; ");

			st.setBoolean(1, residuo.getStatus());
			st.setInt(2, residuo.getId());
			rs = st.executeQuery();

			if (rs.next()) {
				Residuo residuoProcurado = instanciarResiduo(rs);
				return residuoProcurado;

			}

			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Residuo> residuosEmpresa(Empresa empresa) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement(
					"SELECT id_residuo as id, tipo_residuo as tipoResiduo, descricao_residuo as descricaoResiduo, residuo.status as status FROM residuo "
							+ "INNER JOIN RESIDUO_EMPRESA "
							+ "ON RESIDUO_EMPRESA.residuo_id_residuo = RESIDUO.id_residuo " + "INNER JOIN EMPRESA  "
							+ "ON EMPRESA.id_empresa = RESIDUO_EMPRESA.empresa_id_empresa "
							+ "WHERE EMPRESA.id_empresa = ? and RESIDUO_EMPRESA.status = true ");

			st.setInt(1, empresa.getId());

			rs = st.executeQuery();

			List<Residuo> residuos = new ArrayList<>();
			while (rs.next()) {
				Residuo residuo = instanciarResiduo(rs);
				residuos.add(residuo);
			}

			return residuos;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Residuo> listar() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement(
					"select id_residuo as id, tipo_residuo as tipoResiduo, descricao_residuo as descricaoResiduo, status from residuo "
							+ "where status = true " + "order by id_residuo;");

			rs = st.executeQuery();

			List<Residuo> residuos = new ArrayList<>();

			while (rs.next()) {
				Residuo residuo = instanciarResiduo(rs);
				residuos.add(residuo);
			}

			return residuos;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	public Residuo instanciarResiduo(ResultSet rs) throws SQLException {
		Residuo residuo = new Residuo();
		residuo.setId(rs.getInt("id"));
		residuo.setTipoResiduo(rs.getString("tipoResiduo"));
		residuo.setDescricaoResiduo(rs.getString("descricaoResiduo"));
		residuo.setStatus(rs.getBoolean("status"));

		return residuo;

	}

}
