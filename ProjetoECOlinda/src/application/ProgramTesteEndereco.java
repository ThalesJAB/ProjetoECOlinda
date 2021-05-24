package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EnderecoDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.Usuario;

public class ProgramTesteEndereco {
	
	public static void main(String[] args) {
		
		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
		
		System.out.println("============================= TESTE 1: LISTAR ENDERECOS DE UM USUARIO ============================");
		Usuario usuario = new Usuario(1, null, null, null, null, null, true, null, null, null);
		enderecoDao.procurarEndUsuario(usuario).forEach(System.out::println);
		
		System.out.println("============================= TESTE 2: LISTAR  ENDERECO DE UMA EMPRESA ===============================");
		Empresa empresa = new Empresa(1, null, null, null, null, true, null, null, null);
		Empresa empresa2 =  new Empresa(2, null, null, null, null, true, null, null, null);
		enderecoDao.procurarEndEmpresa(empresa).forEach(System.out::println);
		enderecoDao.procurarEndEmpresa(empresa2).forEach(System.out::println);
		
		System.out.println("============================== TESTE 3: DELETAR ENDERECO DE UM USUARIO ==================================");
		
		//List<Endereco> teste = enderecoDao.procurarEndUsuario(usuario);
		//enderecoDao.deletarEndUsuario(teste.get(0), usuario);
		//enderecoDao.procurarEndUsuario(usuario).forEach(System.out::println);
		
		System.out.println("================================ TESTE 4: DELETAR ENDERECO DE UMA EMPRESA ===================================");
		//List<Endereco> teste2 = enderecoDao.procurarEndEmpresa(empresa);
		//enderecoDao.deletarEndEmpresa(teste2.get(0), empresa);
		//enderecoDao.procurarEndEmpresa(empresa);
		
		System.out.println("================================ TESTE 5: ALTERAR ENDERECO ==============================");
		//List<Endereco> teste3 = enderecoDao.procurarEndUsuario(usuario);
		//Endereco endereco = teste3.get(0);
		//endereco.setLogradouro("Rua 3");
		//enderecoDao.alterar(endereco);
		System.out.println("==================================TESTE 6: INSERIR ENDERECO USUARIO ==========================");
		//enderecoDao.cadastrarEndUsuario(new Endereco(null, "53260-380", "Rua Debóra Regis de Carvalho", 160, "ao lado da Ciretran", "Peixinhos", "Olinda", "Pernambuco", null, true), usuario);
		//enderecoDao.procurarEndUsuario(usuario).forEach(System.out::println);
		System.out.println("==================================TESTE 7: INSERIR ENDERECO EMPRESA ============================");
		//enderecoDao.cadastrarEndEmpresa(new Endereco(null, "53456-213", "Rua A", 1876, "do lado da Rua B", "Jardim Brasil", "Olinda", "Pernambuco", null, true), empresa2);
		//enderecoDao.procurarEndEmpresa(empresa2).forEach(System.out::println);
	}

}
