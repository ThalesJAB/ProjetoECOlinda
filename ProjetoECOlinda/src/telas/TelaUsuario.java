package telas;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Telefone;
import model.entities.Usuario;
import model.services.EnderecoService;
import model.services.TelefoneService;
import model.services.UsuarioService;

public class TelaUsuario {

	private static TelaUsuario telaUsuario;

	private static TelaAplicacao telaAplicacao = TelaAplicacao.getInstance();

	private static TelaEmpresa telaEmpresa = TelaEmpresa.getInstance();

	private static UsuarioService usuarioService = UsuarioService.getInstance();

	private static EnderecoService enderecoService = EnderecoService.getInstance();

	private static TelefoneService telefoneService = TelefoneService.getInstance();

	private TelaUsuario() {

	}

	public static TelaUsuario getInstance() {
		if (Objects.isNull(telaUsuario)) {
			telaUsuario = new TelaUsuario();
			return telaUsuario;
		}

		return telaUsuario;
	}

	// Cadastro de Usuário
	// -----------------------------------------------------------------
	public void cadastroUsuario() {
		Scanner sc = new Scanner(System.in);

		Date dataNascimento = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean statusUsuario = true;
		System.out.println("Digite o seu nome: ");
		String nome = sc.nextLine();
		System.out.println("Digite o seu Email: ");
		String email = sc.nextLine();
		System.out.println("Digite o seu Login: ");
		String login = sc.nextLine();
		System.out.println("Digite a sua Senha: ");
		String senha = sc.nextLine();
		System.out.println("Digite a sua Data de Nascimento: dia/mes/ano ");
		try {
			dataNascimento = sdf.parse(sc.next());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Usuario usuario = new Usuario(null, nome, login, senha, email, dataNascimento, null, null, null, null);

		usuarioService.cadastrar(usuario);
	}

	// Cadastro de Endereço
	// -------------------------------------------------------------------

	public void cadastrarEndereco(Usuario usuario) {
		Scanner sc = new Scanner(System.in);

		List<Endereco> enderecos = new ArrayList<>();

		String aux = null;

		boolean confirmar = true;

		do {

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

			Endereco endereco = new Endereco(null, cep, logradouro, numero, complemento, bairro, cidade, estado, null,
					statusEndereco);

			enderecos.add(endereco);

			while (confirmar) {

				System.out.println("Deseja cadastrar mais um endereco?:");
				System.out.println("1 - SIM\n0 - NÃO\n:");
				aux = sc.nextLine();

				if (aux.equals("0")) {
					confirmar = false;
				} else if (aux.equals("1")) {
					confirmar = false;
				} else {
					System.out.println("Digite uma opção valida");
				}

			}

		} while (!aux.equals("0"));

		for (Endereco endereco : enderecos) {
			enderecoService.cadastrarEndUsuario(endereco, usuario);

		}
		usuario.setEnderecos(enderecos);
		System.out.println("Endereços cadastrados com sucesso");

	}

	public void cadastrarTelefone(Usuario usuario) {

		Scanner sc = new Scanner(System.in);
		List<Telefone> telefones = new ArrayList<>();
		boolean confirmar = true;
		String aux = null;
		do {

			System.out.println("Digite o seu Telefone: ");
			String numtelefone = sc.nextLine();
			boolean statusTelefone = true;

			Telefone telefone = new Telefone(null, numtelefone, null, usuario.getId(), statusTelefone);
			telefones.add(telefone);

			while (confirmar) {

				System.out.println("Deseja cadastrar mais um telefone?:");
				System.out.println("1 - SIM\n0 - NÃO\n:");
				aux = sc.nextLine();

				if (aux.equals("0")) {
					confirmar = false;
				} else if (aux.equals("1")) {
					confirmar = false;
				} else {
					System.out.println("Digite uma opção valida");
				}

			}

		} while (!aux.equals("0"));

		for (Telefone telefone : telefones) {
			telefoneService.cadastrar(telefone);
		}
		usuario.setTelefones(telefones);
		System.out.println("Telefones cadastrados com sucesso");

	}

	// Metodo de Login de Usuario

	public void telaLoginUsuario() {
		Scanner sc = new Scanner(System.in);
		String opc = null;
		Usuario usuario = new Usuario();
		
		System.out.println("Digite seu login: ");
		String login = sc.nextLine();
		System.out.println("Digite sua senha: ");
		String senha = sc.nextLine();

		usuario.setLogin(login);
		usuario.setSenha(senha);

		Usuario usuarioRetorno = usuarioService.login(usuario);

		if (Objects.nonNull(usuarioRetorno)) {
			telaUsuarioLogado(usuarioRetorno);

		} else {
			System.out.println("Login ou Senha Incorretos.");

			do {
				System.out.println("");
				System.out.println("Deseja tentar de novo?");
				System.out.println("1 - Sim");
				System.out.println("2 - Não");
				opc = sc.nextLine();
				if (opc.equals("1")) {
					telaLoginUsuario();

				} else if (opc.equals("2")) {
					telaAplicacao.Menu();
				} else {
					System.out.println("Digite uma opcão valida\n");
				}

			} while (!opc.equals("2"));
		}

	}

	// Tela de desativar a conta do Usuario
	public void TelaDesativarConta(Usuario usuario) {
		Scanner sc = new Scanner(System.in);

		// ---------------------------------------
		System.out.println("Deseja mesmo excluir sua conta?");
		System.out.println("1- Sim");
		System.out.println("2- Não");
		int opc = sc.nextInt();

		switch (opc) {
		case 1:
			usuarioService.deletar(usuario);
			System.out.println("Conta excluida com sucesso.");
			telaAplicacao.Menu();
			break;
		case 2:
			telaUsuarioLogado(usuario);
			break;
		default:
			System.out.println("Digite uma opção valida.");
			break;
		}

	}

	// Tela que o usuario vai favoritar a empresa
	public void TelaFavoritarEmpresa() {
		

	}

	// Tela que o Usuario lista as empresas favoritas por ele
	public void TelaListarEmpresasFavoritas() {
		
	}

	// Tela do Usuario quando estiver logado no sistema.
	public void telaUsuarioLogado(Usuario usuario) {
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
				TelaDesativarConta(usuario);
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

	public void TelaEditarUsuario() {
		// TODO Auto-generated method stub

	}

}
