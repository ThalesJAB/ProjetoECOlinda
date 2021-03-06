package telas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Residuo;
import model.entities.Telefone;
import model.entities.Usuario;
import model.exceptions.ValorInvalidoException;
import model.services.EmpresaService;
import model.services.EnderecoService;
import model.services.PontoFavoritoService;
import model.services.TelefoneService;
import model.services.UsuarioService;

public class TelaUsuario {

	private static TelaUsuario telaUsuario;

	private static TelaAplicacao telaAplicacao = TelaAplicacao.getInstance();

	private static UsuarioService usuarioService = UsuarioService.getInstance();

	private static EnderecoService enderecoService = EnderecoService.getInstance();

	private static TelefoneService telefoneService = TelefoneService.getInstance();

	private static EmpresaService empresaService = EmpresaService.getInstance();

	private static PontoFavoritoService pontoFavoritoService = PontoFavoritoService.getInstance();

	private TelaUsuario() {

	}

	public static TelaUsuario getInstance() {
		if (Objects.isNull(telaUsuario)) {
			telaUsuario = new TelaUsuario();
			return telaUsuario;
		}

		return telaUsuario;
	}

	// Cadastro de Usu?rio
	// -----------------------------------------------------------------
	public void telaCadastroUsuario() {
		Scanner sc = new Scanner(System.in);
		boolean confirmar = false;

		while (!confirmar) {
			try {
				Date dataNascimento = null;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				boolean statusUsuario = true;

				System.out.println("\nDigite o seu Nome: ");
				String nome = sc.nextLine();
				System.out.println("Digite o seu Email: ");
				String email = sc.nextLine();
				System.out.println("Digite o seu Login: ");
				String login = sc.nextLine();
				System.out.println("Digite a sua Senha: ");
				String senha = sc.nextLine();
				System.out.println("Digite a sua Data de Nascimento: dia/mes/ano: ");
				dataNascimento = sdf.parse(sc.nextLine());

				Usuario usuario = new Usuario(null, nome, login, senha, email, dataNascimento, statusUsuario, null,
						null, null);

				confirmar = usuarioService.cadastrar(usuario);

			} catch (ParseException e) {
				System.err.println("ERRO: Data de nascimento inv?lida");
				
			} catch (ValorInvalidoException e) {
				System.err.println("ERRO: " + e.getMessage());
			}
		}

		telaLoginUsuario();

	}

	// Cadastro de Endere?o
	// -------------------------------------------------------------------

	public void telaCadastrarEndereco(Usuario usuario) {
		Scanner sc = new Scanner(System.in);

		List<Endereco> enderecos = new ArrayList<>();

		String aux = null;

		

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
			System.out.println("Digite o seu N?mero de Resid?ncia: ");
			int numero = sc.nextInt();
			sc.nextLine();
			System.out.println("Digite o seu Complemento: ");
			String complemento = sc.nextLine();
			boolean statusEndereco = true;

			Endereco endereco = new Endereco(null, cep, logradouro, numero, complemento, bairro, cidade, estado, null,
					statusEndereco);

			enderecos.add(endereco);
			
			boolean confirmar = true;

			while (confirmar) {

				System.out.println("Deseja cadastrar mais um endereco?:");
				System.out.println("1 - SIM\n0 - N?O\n:");
				aux = sc.nextLine();

				if (aux.equals("0")) {
					confirmar = false;
				} else if (aux.equals("1")) {
					confirmar = false;
				} else {
					System.err.println("ERRO: Op??o Inv?lida\n");

				}

			}

		} while (!aux.equals("0"));

		for (Endereco endereco : enderecos) {
			enderecoService.cadastrarEndUsuario(endereco, usuario);
			usuario.addEndereco(endereco);
			

		}
		
		System.out.println("Endere?os cadastrados com sucesso");
		telaUsuarioLogado(usuario);

	}

	public void telaCadastrarTelefone(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		List<Telefone> telefones = new ArrayList<>();
		int aux = 0;
		do {

			System.out.println("Digite o seu Telefone: ");
			String numtelefone = sc.nextLine();
			boolean statusTelefone = true;

			Telefone telefone = new Telefone(null, numtelefone, usuario.getId(), null, statusTelefone);
			telefones.add(telefone);

			System.out.println("Deseja cadastrar mais um telefone?:");
			System.out.println("1 - SIM\n0 - N?O\n:");
			aux = sc.nextInt();
			sc.nextLine();

		} while (aux != 0);

		for (Telefone telefone : telefones) {
			telefoneService.cadastrar(telefone);
			usuario.addTelefone(telefone);
		}

		System.out.println("Telefones cadastrados com sucesso");
		telaUsuarioLogado(usuario);

	}

	// Tela de desativar a conta do Usuario
	public void telaDesativarConta(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		String opc = null;

		// ---------------------------------------
		do {
			System.out.println("Deseja mesmo excluir sua conta?");
			System.out.println("1- Sim");
			System.out.println("2- N?o");
			opc = sc.nextLine();

			switch (opc) {
			case "1":
				usuarioService.deletar(usuario);
				System.out.println("Conta excluida com sucesso.");
				telaAplicacao.Menu();
				break;
			case "2":
				telaUsuarioLogado(usuario);
				break;
			default:
				System.err.println("ERRO: Op??o Inv?lida\n");

				break;
			}

		} while (!opc.equals("1"));

	}

	public void telaBuscaEmpPorResiduo(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		String opc = null;
		Residuo residuo = new Residuo();
		int i = 0;
		List<Empresa> empresas = new ArrayList<>();
		do {
			System.out.println("Selecione o tipo do Res?duo: ");
			System.out.println("1 - Pl?stico");
			System.out.println("2 - Vidro");
			System.out.println("3 - Papel");
			System.out.println("4 - Metal");
			System.out.println("5 - Org?nico");
			System.out.println("6 - Res?duos Perigosos");
			System.out.println("7 - Res?duos Eletr?nicos");
			System.out.println("8 - Outros");
			System.out.println("0 - Voltar");
			opc = sc.next();

			switch (opc) {
			case "1":
				residuo.setTipoResiduo("Pl?stico");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "2":
				residuo.setTipoResiduo("Vidro");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "3":
				residuo.setTipoResiduo("Papel");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "4":
				residuo.setTipoResiduo("Metal");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "5":
				residuo.setTipoResiduo("Org?nico");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "6":
				residuo.setTipoResiduo("Res?duos Perigosos");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;
				
			case "7":
				residuo.setTipoResiduo("Res?duos Eletr?nicos");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "8":
				System.out.println("Digite o residuo: ");
				String aux = sc.nextLine();
				residuo.setTipoResiduo(aux);
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "0":
				telaUsuarioLogado(usuario);
				break;

			default:
				System.err.println("ERRO: Op??o Inv?lida\n");

			}

			i = 0;
			for (Empresa empresa : empresas) {
				System.out.println(i + 1 + " - " + empresa.getNome() + ", " + empresa.getEmail());
				i++;
			}
			int aux = 0;

			if (empresas.size() > 0) {
				System.out
						.print("\nSelecione a empresa pela n?mera??o para mais informa??es ou 0 para voltar ao menu: ");
				aux = sc.nextInt();
				sc.nextLine();
				if (aux > empresas.size() || aux < 0) {
					System.err.println("ERRO: Op??o Inv?lida\n");
				}

				else if (aux == 0) {

					System.out.println("\nVoltando ao menu...");

				} else {
					aux = aux - 1;
					Empresa empresa = empresas.get(aux);
					System.out.println("\n========== EMPRESA ==========");
					System.out.println("Nome: " + empresa.getNome() + "\n" + "Email: " + empresa.getEmail() + "\n");
					System.out.println("============ TELEFONE =========");
					for (Telefone telefone : empresa.getTelefones()) {
						System.out.println(telefone.getNumTelefone());
					}

					for (Endereco endereco : empresa.getEnderecos()) {
						System.out.println("========== ENDERE?O ==========");
						System.out.println("CEP: " + endereco.getCep() + ";\nLogradouro: " + endereco.getLogradouro()
								+ ";\nN?mero " + endereco.getNumero() + ";\nComplemento: " + endereco.getComplemento()
								+ ";\nBairro: " + endereco.getBairro() + ";\nCidade: " + endereco.getCidade()
								+ ";\nEstado: " + endereco.getEstado());
					}

					String opc1 = null;
					do {
						System.out.println("\nQuer adicionar essa empresa como seu Ponto favorito");
						System.out.println("1 - SIM");
						System.out.println("2 - N?O");
						opc1 = sc.nextLine();

						if (opc1.equals("1")) {
							telaFavoritarEmpresa(usuario, empresa);
							break;

						}

						else if (opc1.equals("2")) {
							telaUsuarioLogado(usuario);
							break;
						} else {
							System.err.println("ERRO: Op??o Inv?lida\n");

						}

					} while (!opc1.equals("2") || !opc1.equals("1"));
				}
			} else {
				System.err.println("Nenhuma empresa trabalha com esse tipo de res?duo\n");
			}

		} while (!opc.equals("0"));

	}

	public void telaBuscarEmpresaNome(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		Empresa empresa = new Empresa();
		System.out.print("Digite a empresa que voc? procura: ");
		String nomeEmpresa = sc.nextLine();
		empresa.setNome(nomeEmpresa);

		Empresa empresaRetorno = empresaService.procurar(empresa);

		if (Objects.isNull(empresaRetorno)) {
			System.err.println("Empresa procurada n?o existe");
		} else {
			empresaRetorno.setSenha(null);
			empresaRetorno.setLogin(null);
			System.out.println("\n========== EMPRESA ==========");
			System.out
					.println("Nome: " + empresaRetorno.getNome() + "\n" + "Email: " + empresaRetorno.getEmail() + "\n");
			System.out.println("============ TELEFONE =========");
			for (Telefone telefone : empresaRetorno.getTelefones()) {
				System.out.println(telefone.getNumTelefone());
			}

			for (Endereco endereco : empresaRetorno.getEnderecos()) {
				System.out.println("========== ENDERE?O ==========");
				System.out.println("CEP: " + endereco.getCep() + ";\nLogradouro: " + endereco.getLogradouro()
						+ ";\nN?mero " + endereco.getNumero() + ";\nComplemento: " + endereco.getComplemento()
						+ ";\nBairro: " + endereco.getBairro() + ";\nCidade: " + endereco.getCidade() + ";\nEstado: "
						+ endereco.getEstado());
			}

			String opc = null;
			do {
				System.out.println("\nQuer adicionar essa empresa como seu Ponto favorito");
				System.out.println("1 - SIM");
				System.out.println("2 - N?O");
				opc = sc.nextLine();

				if (opc.equals("1")) {
					telaFavoritarEmpresa(usuario, empresaRetorno);
					telaUsuarioLogado(usuario);
					break;

				}

				else if (opc.equals("2")) {
					telaUsuarioLogado(usuario);
					break;
				} else {
					System.err.println("ERRO: Op??o Inv?lida\n");

				}

			} while (!opc.equals("2") || !opc.equals("1"));

		}

	}

	// Tela que o usuario vai favoritar a empresa
	public void telaFavoritarEmpresa(Usuario usuario, Empresa empresa) {
		PontoFavorito pontoFavorito = new PontoFavorito(null, null, empresa, true);
		PontoFavorito pontoFavoritoRetorno = pontoFavoritoService.procurarPontoFavorito(pontoFavorito);

		if (Objects.nonNull(pontoFavoritoRetorno)) {
			pontoFavoritoRetorno.setIdUsuario(usuario.getId());
			pontoFavoritoRetorno.setEmpresa(empresa);

			if (pontoFavoritoService.procurarPontoFavoritoUsu(pontoFavoritoRetorno) == false) {

				pontoFavoritoService.cadastrarPontoFvUsuario(pontoFavoritoRetorno);
			} else {
				System.err.println(
						"Usuario ja cadastrou essa empresa como ponto favorito, n?o pode cadastrar novamente\n");
			}

		} else {
			System.err.println("Empresa ainda n?o pode ser adicionada como ponto favorito\n");

		}
	}

	// Tela que o Usuario lista as empresas favoritas por ele
	public void telaListarEmpresasFavoritas(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		String opc = " ";
		int i = 0, aux = 0;
		List<PontoFavorito> pontosFavoritosUsuario = pontoFavoritoService.listarPontosFav(usuario);

		if (pontosFavoritosUsuario.isEmpty()) {
			System.err.println("O Usuario n?o possui pontos favoritos");
			telaUsuarioLogado(usuario);
		} else {

			for (PontoFavorito pontoFavorito : pontosFavoritosUsuario) {
				System.out.println("\n========== EMPRESA ==========");
				System.out.println("Nome: " + pontoFavorito.getEmpresa().getNome() + "\n" + "Email: "
						+ pontoFavorito.getEmpresa().getEmail() + "\n");
				System.out.println("============ TELEFONE =========");

				for (Telefone telefone : pontoFavorito.getEmpresa().getTelefones()) {
					System.out.println(telefone.getNumTelefone());
				}

				for (Endereco endereco : pontoFavorito.getEmpresa().getEnderecos()) {
					System.out.println("========== ENDERE?O ==========");
					System.out.println("CEP: " + endereco.getCep() + ";\nLogradouro: " + endereco.getLogradouro()
							+ ";\nN?mero " + endereco.getNumero() + ";\nComplemento: " + endereco.getComplemento()
							+ ";\nBairro: " + endereco.getBairro() + ";\nCidade: " + endereco.getCidade()
							+ ";\nEstado: " + endereco.getEstado());
				}

				System.out.println("========================================================================");

			}

			while (!opc.equals("2") && !opc.equals("1")) {
				System.out.println("Deseja deletar algum Ponto Favorito ?");
				System.out.println("1 - SIM");
				System.out.println("2 - N?O");
				opc = sc.nextLine();

				if (opc.equals("1")) {
					for (PontoFavorito pontoFavorito : pontosFavoritosUsuario) {
						System.out.println(i + 1 + "-" + "========== EMPRESA ==========");
						System.out.println("Nome: " + pontoFavorito.getEmpresa().getNome() + "\n" + "Email: "
								+ pontoFavorito.getEmpresa().getEmail() + "\n");
						i++;
					}

					System.out.println(
							"\nDigite um que voc? queira deletar, a partir da numera??o ao lado ou 0 para voltar ao menu: ");
					aux = sc.nextInt();
					sc.nextLine();

					if (aux > pontosFavoritosUsuario.size() || aux < 0) {
						System.err.println("ERRO: Op??o Inv?lida\n");

					} else if (aux == 0) {
						System.out.println("\nVoltando ao menu...");
					} else {
						aux = aux - 1;
						PontoFavorito pontoFavoritoRet = pontosFavoritosUsuario.get(aux);

						pontoFavoritoService.deletarPontoFvUsuario(pontoFavoritoRet);
						
						telaUsuarioLogado(usuario);
					}

				} else if (opc.equals("2")) {
					telaUsuarioLogado(usuario);
				} else {
					System.err.println("ERRO: Op??o Inv?lida\n");

				}
			}
		}

	}

	public void telaEditarUsuario(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		String opc = null;
		boolean menu = true;

		while (menu) {
			System.out.println("O que deseja alterar: ");
			System.out.println("1 - Dados");
			System.out.println("2 - Endereco");
			System.out.println("3 - Telefone");
			System.out.println("0 - Voltar ao menu login");
			opc = sc.nextLine();

			if (opc.equals("1")) {
				do {
					try {
						System.out.println("O que deseja alterar: ");
						System.out.println("1 - Nome");
						System.out.println("2 - Email");
						System.out.println("3 - Login");
						System.out.println("4 - Senha");
						System.out.println("0 - Salvar altera??es e voltar ao menu");
						opc = sc.nextLine();

						switch (opc) {
						case "1":
							System.out.println("Digite o novo nome do Usuario:");
							String novoNome = sc.nextLine();
							usuario.setNome(novoNome);
							break;
						case "2":
							System.out.println("Digite o novo email do Usuario:");
							String novoEmail = sc.nextLine();
							usuario.setEmail(novoEmail);
							break;
						case "3":
							System.out.println("Digite o novo Login do Usuario:");
							String novoLogin = sc.nextLine();
							usuario.setLogin(novoLogin);
							break;
						case "4":
							System.out.println("Digite a nova senha do Usuario:");
							String novaSenha = sc.nextLine();
							usuario.setSenha(novaSenha);
							break;
						case "0":
							usuarioService.alterar(usuario);
							break;
						default:
							System.err.println("ERRO: Op??o Inv?lida\n");
							break;
						}

					} catch (ValorInvalidoException e) {
						System.err.println("ERRO: Altera??es desfeitas, motivo: " + e.getMessage());
					}
				} while (!opc.equals("0"));

			} else if (opc.equals("2")) {
				List<Endereco> enderecos = enderecoService.procurarEndUsuario(usuario);
				int i = 1;

				for (Endereco endereco : enderecos) {
					System.out.println("OP??O " + i + " - " + "========== ENDERE?O ==========");
					System.out.println("CEP: " + endereco.getCep() + ";\nLogradouro: " + endereco.getLogradouro()
							+ ";\nN?mero " + endereco.getNumero() + ";\nComplemento: " + endereco.getComplemento()
							+ ";\nBairro: " + endereco.getBairro() + ";\nCidade: " + endereco.getCidade()
							+ ";\nEstado: " + endereco.getEstado());
					i++;
				}
				int aux = 0;
				System.out.println("=========================================================");
				System.out.println(
						"\nEscolha o endereco que voc? quer alterar, a partir da numera??o ao lado ou 0 para voltar ao menu: ");
				aux = sc.nextInt();
				sc.nextLine();

				if (aux > enderecos.size() || aux < 0) {
					System.err.println("ERRO: Op??o Inv?lida\n");
				} else if (aux == 0) {
					System.out.println("Voltando ao menu...");
				} else {
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
						System.out.println("6 - Numero de Resid?ncia");
						System.out.println("7 - Complemento");
						System.out.println("8 - Deletar");
						System.out.println("0 - Concluir e voltar ao menu");
						opcEnd = sc.nextLine();

						switch (opcEnd) {
						case "1":
							System.out.println("Digite o seu novo Estado: ");
							String novoEstado = sc.nextLine();
							enderecoEditar.setEstado(novoEstado);
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
							System.out.println("Digite o seu novo Numero de Resid?ncia");
							Integer novoNumero = sc.nextInt();
							sc.nextLine();
							enderecoEditar.setNumero(novoNumero);
							break;
						case "7":
							System.out.println("Digite o seu novo Complemento");
							String novoComplemento = sc.nextLine();
							enderecoEditar.setComplemento(novoComplemento);
							break;
						case "8":
							enderecoService.deletarEndUsuario(enderecoEditar, usuario);
							System.out.println("Endere?o deletado");
							break;
						case "0":
							enderecoService.alterar(enderecoEditar);
							break;
						default:
							System.err.println("ERRO: Op??o Inv?lida\n");
							break;
						}

					} while (!opcEnd.equals("0"));
				}
			} else if (opc.equals("3")) {
				List<Telefone> telefones = telefoneService.procurarTelUsuario(usuario);
				int i = 1;

				for (Telefone telefone : telefones) {
					System.out.println("OP??O " + i + " - " + "============ TELEFONE =========");

					System.out.println(telefone.getNumTelefone());
					i++;
				}

				System.out.println("=======================================================================");

				System.out.println(
						"\nEscolha o telefone que voc? quer alterar, a partir da numera??o ou 0 para voltar ao menu:");
				int aux = sc.nextInt();
				sc.nextLine();
				if (aux > telefones.size()  || aux < 0) {
					System.err.println("ERRO: Op??o Inv?lida\n");
				}

				else if (aux == 0) {

					System.out.println("Voltando ao menu...");

				} else {
					aux = aux - 1;

					Telefone telefoneEditar = telefones.get(aux);

					String opcTel = null;

					do {
						System.out.println("O que deseja alterar de telefone: ");
						System.out.println("1 - N?mero");
						System.out.println("2 - Excluir");
						System.out.println("0 - Concluir e voltar ao menu");
						opcTel = sc.nextLine();

						switch (opcTel) {
						case "1":
							System.out.println("Digite seu novo n?mero:");
							String novoNome = sc.nextLine();
							telefoneEditar.setNumTelefone(novoNome);
							telefoneService.alterar(telefoneEditar);
							break;

						case "2":
							telefoneService.deletar(telefoneEditar);
							System.out.println("");
							break;

						case "0":
							System.out.println("");
							break;

						default:
							System.err.println("ERRO: Op??o Inv?lida\n");
							break;

						}

					} while (!opcTel.equals("0"));
				}

			} else if (opc.equals("0")) {
				menu = false;
				telaUsuarioLogado(usuario);
				break;
			}

		}

	}

	// Tela do Usuario quando estiver logado no sistema.
	public void telaUsuarioLogado(Usuario usuario) {
		Scanner sc = new Scanner(System.in);

		String opc = "99";

		while (!opc.equals("7")) {
			System.out.println("\nBem vindo ao Sistema " + usuario.getNome());
			System.out.println("\nO que deseja fazer?");
			System.out.println("1 - Buscar Empresa por Nome");
			System.out.println("2 - Buscar Empresa por Residuo");
			System.out.println("3 - Editar Informa??es");
			System.out.println("4 - Adicionar Telefone");
			System.out.println("5 - Adicionar Endere?o");
			System.out.println("6 - Desativar Conta");
			System.out.println("7 - Listar Minhas Empresas Favoritas");
			System.out.println("8 - Fazer Logoff");
			opc = sc.next();

			switch (opc) {
			case "1":
				telaBuscarEmpresaNome(usuario);
				break;
			case "2":
				telaBuscaEmpPorResiduo(usuario);
				break;
			case "3":
				telaEditarUsuario(usuario);
				break;

			case "4":
				telaCadastrarTelefone(usuario);
				break;
			case "5":
				telaCadastrarEndereco(usuario);
				break;
			case "6":
				telaDesativarConta(usuario);
				break;
			case "7":
				telaListarEmpresasFavoritas(usuario);
				break;
			case "8":
				System.out.println("Fazendo Logoff.");
				telaAplicacao.Menu();
				break;
			default:
				System.err.println("ERRO: Op??o Inv?lida\n");
				break;
			}
		}

	}

	// Metodo de Login de Usuario

	public void telaLoginUsuario() {
		Scanner sc = new Scanner(System.in);
		String opc = null;
		Usuario usuario = new Usuario();

		System.out.println("\n======LOGIN======");
		System.out.println("Digite seu login: ");
		String login = sc.nextLine();
		System.out.println("Digite sua senha: ");
		String senha = sc.nextLine();

		usuario.setLogin(login);
		usuario.setSenha(senha);

		Usuario usuarioRetorno = usuarioService.login(usuario);

		if (Objects.nonNull(usuarioRetorno)) {

			List<Telefone> telefones = telefoneService.procurarTelUsuario(usuarioRetorno);
			List<PontoFavorito> pontosFav = pontoFavoritoService.listarPontosFav(usuarioRetorno);
			List<Endereco> enderecos = enderecoService.procurarEndUsuario(usuarioRetorno);

			usuarioRetorno.setTelefones(telefones);
			usuarioRetorno.setPontosFav(pontosFav);
			usuarioRetorno.setEnderecos(enderecos);

			telaUsuarioLogado(usuarioRetorno);

		} else {
			System.err.println("Login ou Senha Incorretos.");

			do {
				System.out.println("");
				System.out.println("Deseja tentar de novo?");
				System.out.println("1 - Sim");
				System.out.println("2 - N?o");
				opc = sc.nextLine();
				if (opc.equals("1")) {
					telaLoginUsuario();
					break;

				} else if (opc.equals("2")) {
					telaAplicacao.Menu();
					break;
				} else {
					System.err.println("ERRO: Op??o Inv?lida\n");

				}

			} while (!opc.equals("2"));
		}

	}

}
