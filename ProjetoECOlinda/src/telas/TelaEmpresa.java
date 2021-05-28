package telas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.dao.impl.EmpresaDaoJDBC;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Residuo;
import model.entities.Telefone;
import model.services.EmpresaService;
import model.services.EnderecoService;
import model.services.ResiduoService;
import model.services.TelefoneService;

public class TelaEmpresa {

	private static EmpresaService empresaService = EmpresaService.getInstance();

	private static TelefoneService telefoneService = TelefoneService.getInstance();

	private static EnderecoService enderecoService = EnderecoService.getInstance();

	private static ResiduoService residuoService = ResiduoService.getInstance();

	private static TelaAplicacao telaAplicacao = TelaAplicacao.getInstance();

	private static TelaEmpresa telaEmpresa;

	private static Empresa empresa;

	private TelaEmpresa() {

	}

	public static TelaEmpresa getInstance() {
		if (Objects.isNull(telaEmpresa)) {
			telaEmpresa = new TelaEmpresa();
			return telaEmpresa;
		}
		return telaEmpresa;

	}

	public void CadastroEmpresa() {

		// Cadastro de Empresa
		// ------------------------------------------------------------------

		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o Nome da Empresa: ");
		String nome = sc.nextLine();
		System.out.println("Digite o Email da Empresa: ");
		String email = sc.nextLine();
		System.out.println("Digite o Login da Empresa: ");
		String login = sc.nextLine();
		System.out.println("Digite a Senha da Empresa: ");
		String senha = sc.nextLine();
		boolean statusEmpresa = true;

		Empresa empresa = new Empresa(null, nome, login, senha, email, statusEmpresa, null, null, null);

		empresaService.cadastrar(empresa);

		// Cadastro de Endere�o
		// -------------------------------------------------------------------
		telaAdicionarEndereco(empresa);

		// Cadastro de Telefone
		// --------------------------------------------------------------------

		telaAdicionarTelefone(empresa);

		// Cadastro de Residuo Empresa
		// --------------------------------
		telaAdicionarResiduo(empresa);

	}

	private Residuo criarResiduo() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o tipo do residuo: ");
		String tipoResiduo = scanner.nextLine();
		System.out.println("Digite uma descri��o para esse residuo: ");
		String descricaoResiduo = scanner.nextLine();

		Residuo residuo = new Residuo(null, tipoResiduo, descricaoResiduo, true);

		return residuo;

	}

	public void telaAdicionarResiduo(Empresa empresa) {
		Scanner sc = new Scanner(System.in);

		String opcResiduo;

		System.out.println("Residuos:\n");

		List<Residuo> residuos = residuoService.listar();
		for (Residuo residuo : residuos) {
			System.out.println(residuo.getTipoResiduo() + " - " + residuo.getDescricaoResiduo());
		}

		do {
			System.out.println("\nSelecione o tipo do Res�duo: ");
			System.out.println("1 - Pl�stico");
			System.out.println("2 - Metal");
			System.out.println("3 - Vidro");
			System.out.println("4 - Papel");
			System.out.println("5 - Org�nico");
			System.out.println("6 - Res�duos Perigosos");
			System.out.println("7 - Outros");
			System.out.println("0 - Finalizar cadastrado e ir para o menu:");
			opcResiduo = sc.next();

			switch (opcResiduo) {

			case "1":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Pl�stico")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;
			case "2":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Metal")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;
			case "3":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Vidro")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;

			case "4":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Papel")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;
			case "5":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Org�nico")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;
			case "6":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Res�duos Perigosos")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;
			case "7":
				Residuo residuo7 = criarResiduo();
				residuoService.cadastrar(residuo7);
				residuoService.cadastrarResiduoEmp(residuo7, empresa);
				break;

			case "0":
				telaAplicacao.Menu();
				break;
			default:
				System.out.println("Digite uma op��o valida.");
				break;
			}

		} while (!opcResiduo.equals("0"));

	}

	public void telaAdicionarEndereco(Empresa empresa) {
		Scanner sc = new Scanner(System.in);

		int aux = 0;

		List<Endereco> enderecos = new ArrayList<>();

		do {

			System.out.println("Digite o Estado da sua Empresa: ");
			String estado = sc.nextLine();
			System.out.println("Digite a Cidade da sua Empresa: ");
			String cidade = sc.nextLine();
			System.out.println("Digite o Bairro da sua Empresa: ");
			String bairro = sc.nextLine();
			System.out.println("Digite o logradouro da sua Empresa: ");
			String logradouro = sc.nextLine();
			System.out.println("Digite o Cep da sua Empresa: ");
			String cep = sc.nextLine();
			System.out.println("Digite o Numero de Resid�ncia da sua Empresa: ");
			int numero = sc.nextInt();
			sc.nextLine();
			System.out.println("Digite o Complemento da sua empresa");
			String complemento = sc.nextLine();
			boolean statusEndereco = true;

			Endereco endereco = new Endereco(null, cep, logradouro, numero, complemento, bairro, cidade, estado,
					empresa.getId(), statusEndereco);

			enderecos.add(endereco);

			System.out.println("Deseja cadastrar mais um endereco?:");
			System.out.println("1 - SIM\n0 - N�O\n:");
			aux = sc.nextInt();
			sc.nextLine();

		} while (aux != 0);

		for (Endereco endereco : enderecos) {
			enderecoService.cadastrarEndEmpresa(endereco, empresa);

		}

	}

	public void telaAdicionarTelefone(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		List<Telefone> telefones = new ArrayList<>();
		int aux = 0;
		do {

			System.out.println("Digite o seu Telefone: ");
			String numtelefone = sc.nextLine();
			boolean statusTelefone = true;

			Telefone telefone = new Telefone(null, numtelefone, null, empresa.getId(), statusTelefone);
			telefones.add(telefone);

			System.out.println("Deseja cadastrar mais um telefone?:");
			System.out.println("1 - SIM\n0 - N�O\n:");
			aux = sc.nextInt();
			sc.nextLine();

		} while (aux != 0);

		empresa.setTelefones(telefones);

		for (Telefone telefone : telefones) {
			telefoneService.cadastrar(telefone);
		}

	}

	// Tela e metodo para buscar empresa por nome
	public void TelaBuscarEmpresaNome() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o nome da Empresa que deseja buscar: ");
		String nome = sc.nextLine();
		BuscarEmpresaNome(nome);
	}

	// Metodo de Buscar Empresa por Nome
	public void BuscarEmpresaNome(String nomeEmpresa) {
		EmpresaService empre = EmpresaService.getInstance();
		Empresa empresa = new Empresa(null, nomeEmpresa, nomeEmpresa, nomeEmpresa, nomeEmpresa, null, null, null, null);

		empre.procurar(empresa);

	}

	// Metodo de Login de Empresa
	public void telaLoginEmpresa() {
		Scanner sc = new Scanner(System.in);

		Empresa empresa = new Empresa();

		System.out.println("Digite seu login: ");
		String login = sc.nextLine();
		System.out.println("Digite sua senha: ");
		String senha = sc.nextLine();

		empresa.setLogin(login);
		empresa.setSenha(senha);

		Empresa empresaRetorno = empresaService.login(empresa);
		if (Objects.nonNull(empresaRetorno)) {
			telaEmpresaLogado(empresa);

		} else {
			System.out.println("Login ou Senha Incorretos.");
			System.out.println("");
			System.out.println("Deseja tentar de novo?");
			System.out.println("1 - Sim");
			System.out.println("2 - N�o");
			String opc = sc.next();
			if (!opc.equals("1")) {
				telaLoginEmpresa();
			} else {
				telaAplicacao.Menu();
			}
		}
	}

	// Tela de Editar Informa��es da Empresa
	public void telaEditarEmpresa(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		String opc = null;
		boolean menu = true;

		while (menu) {
			System.out.println("O que deseja alterar: ");
			System.out.println("1 - Dados da Empresa");
			System.out.println("2 - Endereco da Empresa");
			System.out.println("3 - Telefone");
			System.out.println("0 - Voltar ao menu login");
			opc = sc.nextLine();

			if (opc.equals("1")) {
				do {
					System.out.println("O que deseja alterar: ");
					System.out.println("1 - Nome");
					System.out.println("2 - Email");
					System.out.println("3 - Login");
					System.out.println("4 - Senha");
					System.out.println("0 - Concluir e voltar ao menu");
					opc = sc.next();

					switch (opc) {
					case "1":
						System.out.println("Digite o novo nome da Empresa:");
						String novoNome = sc.nextLine();
						empresa.setNome(novoNome);
						break;
					case "2":
						System.out.println("Digite o novo email da Empresa:");
						String novoEmail = sc.nextLine();
						empresa.setEmail(novoEmail);
						break;
					case "3":
						System.out.println("Digite o novo Login da Empresa:");
						String novoLogin = sc.nextLine();
						empresa.setLogin(novoLogin);
						break;
					case "4":
						System.out.println("Digite a nova Senha da Empresa:");
						String novaSenha = sc.nextLine();
						empresa.setSenha(novaSenha);
					case "0":
						empresaService.alterar(empresa);
						break;
					default:
						System.out.println("Digite uma op��o valida");
						break;
					}
				} while (!opc.equals("0"));

			} else if (opc.equals("2")) {
				List<Endereco> enderecos = enderecoService.procurarEndEmpresa(empresa);
				int i = 1;

				for (Endereco endereco : enderecos) {
					System.out.println(i + " : " + endereco);
				}

				System.out.println("Escolha o endereco que quer alterar ");
				int aux = sc.nextInt();
				sc.nextLine();
				aux = aux - 1;
				Endereco enderecoEditar = enderecos.get(aux);

				String opcEnd = null;

				do {

					System.out.println("O que deseja alterar no endereco:");
					System.out.println("1 - Estado");
					System.out.println("2 - Cidade");
					System.out.println("3 - Bairro");
					System.out.println("4 - Logradouro");
					System.out.println("5 - Cep");
					System.out.println("6 - Numero de Resid�ncia");
					System.out.println("7 - Complemento");
					System.out.println("0 - Concluir e voltar ao menu");
					opcEnd = sc.nextLine();

					switch (opcEnd) {
					case "1":
						System.out.println("Digite o seu novo Estado: ");
						String novoEstado = sc.nextLine();
						enderecoEditar.setBairro(novoEstado);
						break;
					case "2":
						System.out.println("Digite a sua nova Cidade: ");
						String novoCidade = sc.nextLine();
						enderecoEditar.setCidade(novoCidade);
						break;
					case "3":
						System.out.println("Digite o seu novo Bairro:");
						String novoBairro = sc.nextLine();
						enderecoEditar.setBairro(novoBairro);

						break;
					case "4":
						System.out.println("Digite o seu novo Logradouro: ");
						String novoLogradouro = sc.nextLine();
						enderecoEditar.setLogradouro(novoLogradouro);
						break;
					case "5":
						System.out.println("Digite o seu novo Cep: ");
						String novoCep = sc.nextLine();
						enderecoEditar.setCep(novoCep);
						break;
					case "6":
						System.out.println("Digite o seu novo Numero de Resid�ncia");
						Integer novoNumero = sc.nextInt();
						enderecoEditar.setNumero(novoNumero);
						break;
					case "7":
						System.out.println("Digite o seu novo complemento");
						String novoComplemento = sc.nextLine();
						enderecoEditar.setComplemento(novoComplemento);
						break;
					case "0":
						enderecoService.alterar(enderecoEditar);
						break;
					default:
						System.out.println("Digite uma op��o valida.");
						break;
					}

				} while (!opcEnd.equals("0"));

			} else if (opc.equals("3")) {
				List<Telefone> telefones = telefoneService.procurarTelEmpresa(empresa);
				int i = 1;

				for (Telefone telefone : telefones) {
					System.out.println(i + " : " + telefone);
				}

				System.out.println("Escolha o telefone que quer alterar ");
				int aux = sc.nextInt();
				sc.nextLine();
				aux = aux - 1;
				Telefone telefoneEditar = telefones.get(aux);

				String opcTel = null;

				do {
					System.out.println("O que deseja alterar de telefone: ");
					System.out.println("1 - N�mero");
					System.out.println("0 - Concluir e voltar ao menu");
					opcTel = sc.nextLine();

					switch (opcTel) {
					case "1":
						System.out.println("Digite o novo numero da Empresa:");
						String novoNome = sc.nextLine();
						telefoneEditar.setNumTelefone(novoNome);
						break;
					case "0":
						telefoneService.alterar(telefoneEditar);
						break;

					default:
						System.out.println("Digite uma op��o valida.");
						break;

					}

				} while (!opcTel.equals("0"));

			} else if (opc.equals("0")) {
				menu = false;
				telaEmpresaLogado(empresa);
			}
		}

	}

	public void TelaDesativarConta() {
		Scanner sc = new Scanner(System.in);
		// EmpresaDaoJDBC empresadao = new EmpresaDaoJDBC();
		String opc = sc.next();
		System.out.println("Deseja mesmo excluir sua conta?");
		System.out.println("1- Sim");
		System.out.println("2- N�o");
		opc = sc.next();

		switch (opc) {
		case "1":
			// empresadao.deletar(id); Possivel metodo para excluir a propria conta pegando
			// o id do usuario logado.
			System.out.println("Conta excluida com sucesso.");
			telaAplicacao.Menu();
			break;
		case "2":
			telaLoginEmpresa();
			;
			break;
		default:
			System.out.println("Digite uma op��o valida.");
			break;
		}
	}

	// Tela da Empresa Logado
	public void telaEmpresaLogado(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		String opc = "99";

		while (!opc.equals("3")) {
			System.out.println("Bem vindo ao Sistema.");
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Editar Informa��es");
			System.out.println("2 - Desativar Conta");
			System.out.println("3 - Fazer Logoff");
			opc = sc.next();

			switch (opc) {
			case "1":
				telaEditarEmpresa(empresa);
				break;
			case "2":
				TelaDesativarConta();
				break;
			case "3":
				System.out.println("Fazendo Logoff");
				telaAplicacao.Menu();
				break;
			default:
				System.out.println("Digite uma op��o valida.");
				break;
			}
		}
	}

}