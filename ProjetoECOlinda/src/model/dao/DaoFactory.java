package model.dao;

import db.DB;
import model.dao.impl.EmpresaDaoJDBC;
import model.dao.impl.EnderecoDaoJDBC;
import model.dao.impl.PontoFavoritoDaoJDBC;
import model.dao.impl.ResiduoDaoJDBC;
import model.dao.impl.TelefoneDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {
	
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}
	
	public static EmpresaDao createEmpresaDao() {
		return new EmpresaDaoJDBC(DB.getConnection());
	}
	
	public static EnderecoDao createEnderecoDao() {
		return new EnderecoDaoJDBC(DB.getConnection());
	}
	
	public static PontoFavoritoDao createPontoFavoritoDao() {
		return new PontoFavoritoDaoJDBC(DB.getConnection());
	}
	
	public static ResiduoDao createResiduoDao() {
		return new ResiduoDaoJDBC(DB.getConnection());
	}
	
	public static TelefoneDao createTelefoneDao() {
		return new TelefoneDaoJDBC(DB.getConnection());
	}

}
