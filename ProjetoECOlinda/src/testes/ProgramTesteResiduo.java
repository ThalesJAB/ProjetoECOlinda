package testes;

import model.dao.DaoFactory;
import model.dao.ResiduoDao;
import model.entities.Residuo;

public class ProgramTesteResiduo {

	public static void main(String[] args) {
		
		ResiduoDao residuoDao = DaoFactory.createResiduoDao();
	
		System.out.println("========================= TESTE 1: LISTAR TODOS OS RESIDUOS ==========================");
		residuoDao.listar().forEach(System.out::println);
		System.out.println();
		
		System.out.println("========================= TESTE 2: PROCURAR DETERMINADO RESIDUO ==========================");
		Residuo residuo = new Residuo(2, null, null, true);
		//System.out.println(residuoDao.procurar(residuo));
		System.out.println();
		
		System.out.println("========================= TESTE 3: DELETAR DETERMINADO RESIDUO POR ID E ID EMPRESA ==========================");
		//residuoDao.deletar(1,1);
		
		System.out.println("========================= TESTE 4: ALTERANDO DETERMINADO RESIDUO POR UM OBJETO DO MSM TIPO ===================");
		Residuo residuo2 = new Residuo(1,"Plastico", "Garrafa Pet, Baldes, etc.", true);
		residuoDao.alterar(residuo2);
		System.out.println("========================= TESTE 5: INSERINDO RESIDUO ======================================");
		//Residuo residuo3 = new Residuo(null, "Organico", "Restos de Alimentos, etc.", true);
		//residuoDao.cadastrar(residuo3);
	}

}
