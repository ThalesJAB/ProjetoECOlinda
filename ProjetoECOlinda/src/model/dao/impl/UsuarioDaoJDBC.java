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
import model.dao.UsuarioDao;
import model.entities.PontoFavorito;
import model.entities.Telefone;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao{

	
	private Connection connection;

	public UsuarioDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void cadastrar(Usuario Usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Usuario Usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario procurar(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = connection.prepareStatement("select * from usuario\r\n"
									+ "where id_usuario = ?");
			st.setInt(1, id);		
			
			rs = st.executeQuery();
					
			List<Telefone> telefonesUsuario = new ArrayList<>();
			
			
			
			return null;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PontoFavorito> listarPontosFav(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
