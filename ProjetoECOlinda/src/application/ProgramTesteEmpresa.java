package application;

import model.dao.DaoFactory;
import model.dao.EmpresaDao;

public class ProgramTesteEmpresa {
	
	public static void main(String[] args) {
		
		EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
		System.out.println("=============================== TESTE 1: listar todas as empresas ============================");
		empresaDao.listarEmpresas().forEach(System.out::println);
	}
	
}
