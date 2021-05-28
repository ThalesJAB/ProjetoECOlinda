package telas;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Telefone;
import model.entities.Usuario;

public class TelaUsuario {
	
	private static TelaUsuario telaUsuario;
	
	private static TelaAplicacao telaAplicacao = TelaAplicacao.getInstance();
	
	private static TelaEmpresa telaEmpresa =  TelaEmpresa.getInstance();
	
	private TelaUsuario() {
		
	}
	
	public static TelaUsuario getInstance() {
		if(Objects.isNull(telaUsuario)) {
			telaUsuario = new TelaUsuario();
			return telaUsuario;
		}
		
		return telaUsuario;
	}

	// Cadastro de Usuário
	// -----------------------------------------------------------------
	public void CadastroUsuario() {
		Scanner sc = new Scanner(System.in);
		String dataNasc;
		Date dt = null;
		System.out.println("Digite o seu nome: ");
		String nome = sc.nextLine();
		System.out.println("Digite o seu Email: ");
		String email = sc.nextLine();
		System.out.println("Digite o seu Login: ");
		String login = sc.nextLine();
		System.out.println("Digite a sua Senha: ");
		String senha = sc.nextLine();
		System.out.println("Digite a sua Data de Nascimento: ");
		boolean statusUsuario = true;
		try {
			dataNasc = sc.nextLine();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			dt = df.parse(dataNasc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Cadastro de Endereço
		// -------------------------------------------------------------------
		System.out.println("Digite o seu Estado: ");
		String estado = sc.nextLine();
		System.out.println("Digite a sua Cidade: ");
		String cidade = sc.nextLine();
		System.out.println("Digite o seu Bairro: ");
		String bairro = sc.nextLine();
		System.out.println("Digite o seu Logradouro: ");
		String logradouro = sc.nextLine();
		System.out.println("Digite o seu Cep: ");
		String cep = sc.nextLine();
		System.out.println("Digite o seu Numero de Residencia: ");
		int numero = sc.nextInt();
		System.out.println("Digite o seu Complemento: ");
		String complemento = sc.nextLine();
		boolean statusEndereco = true;
		// Cadastro de Telefone
		// --------------------------------------------------------------------
		System.out.println("Digite o seu Telefone: ");
		String numTelefone = sc.nextLine();
		boolean statusTelefone = true;

		PontoFavorito pf = new PontoFavorito(null, null, null, true);
		ArrayList<PontoFavorito> pontos = new ArrayList<PontoFavorito>();
		pontos.add(pf);

		Telefone telefone = new Telefone(null, numTelefone, null, null, statusTelefone);
		ArrayList<Telefone> telefones = new ArrayList<>();
		telefones.add(telefone);

		Endereco endereco = new Endereco(null, cep, logradouro, numero, complemento, bairro, cidade, estado, null,
				statusTelefone);
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		enderecos.add(endereco);

		//Usuario usuario = new Usuario(null, nome, login, senha, email, dataNasc, statusUsuario, pontos, telefones,
		//		enderecos);
		//UsuarioDaoJDBC usudao = new UsuarioDaoJDBC();
		//usudao.cadastrar(usuario);

	}

	// Metodo de Login de Usuario

	public void LoginUsuario(String login, String senha) {
		Scanner sc = new Scanner(System.in);
		Usuario usu = new Usuario();
		// if (login.equals(usu.getLogin()) && senha.equals(usu.getSenha())) {
		if (login.equals("a") && senha.equals("a")) {
			TelaUsuarioLogado();
		} else {
			String opc = "99";
			System.out.println("Login ou Senha Incorretos.");
			System.out.println("");
			System.out.println("Deseja tentar de novo?");
			System.out.println("1 - Sim");
			System.out.println("2 - Não");
			opc = sc.next();
			if (opc.equals("1")) {
				TelaLoginUsuario();
			} else {
				telaAplicacao.Menu();
			}

		}
	}

	// Tela para Listar Usuarios
	public void TelaListarUsuarios() {
		System.out.println("Listando Usuarios:");
		//UsuarioDaoJDBC us = new UsuarioDaoJDBC();
		//us.listar();
	}

	// Tela para Editar Usuarios
	public void TelaEditarUsuario() {
		Scanner sc = new Scanner(System.in);
		//UsuarioDaoJDBC us = new UsuarioDaoJDBC();
		System.out.println("Digite o id que deseja alterar:");
		//us.listar();
		int id = sc.nextInt();

	}

	// Tela de desativar a conta do Usuario
	public void TelaDesativarConta() {
		//UsuarioDaoJDBC us = new UsuarioDaoJDBC();
		Scanner sc = new Scanner(System.in);
		/*
		 * Simulando que ele tem que pegar o id do usuario que está logado atualmente.
		 * Usuario usu = new Usuario(); //int id = usu.getId();
		 */
		// ---------------------------------------
		System.out.println("Deseja mesmo excluir sua conta?");
		System.out.println("1- Sim");
		System.out.println("2- Não");
		int opc = sc.nextInt();

		switch (opc) {
		case 1:
			// us.deletar(id); Possivel metodo para excluir pegando o id do usuario logado.
			System.out.println("Conta excluida com sucesso.");
			telaAplicacao.Menu();
			break;
		case 2:
			TelaUsuarioLogado();
			break;
		default:
			System.out.println("Digite uma opção valida.");
			break;
		}

	}

	// Tela de Login do Usuario
	public void TelaLoginUsuario() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite seu Login: ");
		String login = sc.nextLine();
		System.out.println("Digite sua Senha:");
		String senha = sc.nextLine();
		LoginUsuario(login, senha);
	}

	// Tela que o usuario vai favoritar a empresa
	public void TelaFavoritarEmpresa() {
		Scanner sc = new Scanner(System.in);

		//telaAplicacao.ListarEmpresas();
		System.out.println("Digite o ID da empresa que deseja favoritar");
		int id = sc.nextInt();

		//UsuarioDaoJDBC usuario = new UsuarioDaoJDBC();
		// usuario.favoritar(id);
		// Possivelmente um metodo que favorita pegando o id da empresa listada

	}

	// Tela que o Usuario lista as empresas favoritas por ele
	public void TelaListarEmpresasFavoritas() {
	//	UsuarioDaoJDBC usuario = new UsuarioDaoJDBC();
		// usuario.listarPontosFav();
	}

	// Tela do Usuario quando estiver logado no sistema.
	public void TelaUsuarioLogado() {
		Scanner sc = new Scanner(System.in);
		TelaResiduo tr = new TelaResiduo();
		
		String opc = "99";

		while (!opc.equals(7)) {
			System.out.println("Bem vindo ao Sistema");
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Buscar Empresa por Nome:");
			System.out.println("2 - Buscar Empresa por Residuo:");
			System.out.println("3 - Editar Informações");
			System.out.println("4 - Desativar Conta");
			System.out.println("5 - Favoritar Empresa");
			System.out.println("6 - Listar Minhas Empresas Favoritas");
			System.out.println("7 - Fazer Logoff");
			opc = sc.next();

			switch (opc) {
			case "1":
				telaEmpresa.TelaBuscarEmpresaNome();
				break;
			case "2":
				tr.TelaBuscaPorResiduo();
				break;
			case "3":
				TelaEditarUsuario();
				break;
			case "4":
				TelaDesativarConta();
				break;
			case "5":
				TelaFavoritarEmpresa();
				break;
			case "6":
				TelaListarEmpresasFavoritas();
				break;
			case "7":
				System.out.println("Fazendo Logoff.");
				telaAplicacao.Menu();
				break;
			default:
				System.out.println("Digite uma opção valida.");
				break;
			}
		}

	}

}
