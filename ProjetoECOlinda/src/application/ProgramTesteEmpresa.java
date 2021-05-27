package application;

import model.dao.DaoFactory;
import model.dao.EmpresaDao;
import model.entities.Empresa;

public class ProgramTesteEmpresa {
	
	public static void main(String[] args) {
		
		EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
		System.out.println("=============================== TESTE 1: LISTAR TODAS AS EMPRESAS ============================");
		//empresaDao.listarEmpresas().forEach(System.out::println);
		
		System.out.println("================================TESTE 2: PROCURAR EMPRESA POR NOME ====================================");
		//Empresa empresa = empresaDao.procurar(new Empresa(null, "machadosEco", null, null, null, null, null, null, null));
		//System.out.println(empresa);
		
		System.out.println("================================TESTE 3: LOGIN EMPRESA ==================================================");
		Empresa empresa2 = empresaDao.login(new Empresa(null, null, "machados_eco", "78292", null, null, null, null, null));
		if(empresa2 != null) {
			System.out.println("logado com sucesso\n"+empresa2);
		}else {
			System.out.println("login ou senha incorreta");
		}
	}
	
}
