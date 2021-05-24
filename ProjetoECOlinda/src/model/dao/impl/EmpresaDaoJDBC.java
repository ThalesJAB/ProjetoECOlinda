package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.EmpresaDao;
import model.entities.Empresa;

public class EmpresaDaoJDBC implements EmpresaDao{

	private Connection conn;

	public EmpresaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public void cadastrar(Empresa empresa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Empresa empresa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Empresa procurar(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Empresa> listar() {
		// TODO Auto-generated method stub
		return null;
	}

}
