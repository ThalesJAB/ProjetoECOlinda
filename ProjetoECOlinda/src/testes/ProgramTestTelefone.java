package testes;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.TelefoneDao;
import model.entities.Empresa;
import model.entities.Telefone;
import model.entities.Usuario;

public class ProgramTestTelefone {
	
	public static void main(String[] args) {
		
		TelefoneDao telefoneDao = DaoFactory.createTelefoneDao();
		
		Usuario usuario = new Usuario(3, null, null, null, null, null, true, null, null, null);
		
		Empresa empresa = new Empresa(2, null, null, null, null, true, null, null, null);
		
		//telefoneDao.deletar(1);
		
//		List<Telefone> telefonesUsuario = telefoneDao.procurarTelUsuario(usuario);
//		List<Telefone> telefonesEmpresa = telefoneDao.procurarTelEmpresa(empresa);
//		
//		telefonesUsuario.forEach(System.out::println);
//	
//		telefonesEmpresa.forEach(System.out::println);
//		
		Telefone telefone = new Telefone(8, "(81)97307-1315", 3, null, true);
		
		//telefoneDao.cadastrar(telefone);
		
		//telefoneDao.alterar(telefone);
		
		telefoneDao.listar().forEach(System.out::println);
		
		
	}

}
