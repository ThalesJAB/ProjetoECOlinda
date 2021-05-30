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
import model.entities.Residuo;
import model.entities.Telefone;
import model.entities.Usuario;
import model.exceptions.ValorInvalidoException;
import model.services.EmpresaService;
import model.services.EnderecoService;
import model.services.PontoFavoritoService;
import model.services.ResiduoService;
import model.services.TelefoneService;
import model.services.UsuarioService;

public class TelaUsuario {

	private static TelaUsuario telaUsuario;

	private static TelaAplicacao telaAplicacao = TelaAplicacao.getInstance();

	private static TelaEmpresa telaEmpresa = TelaEmpresa.getInstance();

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

	// Cadastro de Usuário
	// -----------------------------------------------------------------
	public void cadastroUsuario() {
		Scanner sc = new Scanner(System.in);
		boolean confirmar = false;

		while (!confirmar) {
			try {
				Date dataNascimento = null;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				boolean statusUsuario = true;
				
				System.out.println("\nDigite o seu nome: ");
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
				System.err.println("ERRO: Data de nascimento inválida");;
			} catch (ValorInvalidoException e) {
				System.err.println("ERRO: "+ e.getMessage());
			}
		}
		
		telaLoginUsuario();

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
					System.err.println("ERRO: Opção Inválida\n");

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

					System.err.println("ERRO: Opção Inválida\n");

				}

			}

		} while (!aux.equals("0"));

		for (Telefone telefone : telefones) {
			telefoneService.cadastrar(telefone);
		}
		usuario.setTelefones(telefones);
		System.out.println("Telefones cadastrados com sucesso");

	}

	// Tela de desativar a conta do Usuario
	public void TelaDesativarConta(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		String opc = null;

		// ---------------------------------------
		do {
			System.out.println("Deseja mesmo excluir sua conta?");
			System.out.println("1- Sim");
			System.out.println("2- Não");
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
				System.err.println("ERRO: Opção Inválida\n");

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
			System.out.println("Selecione o tipo do Resíduo: ");
			System.out.println("1 - Plástico");
			System.out.println("2 - Vidro");
			System.out.println("3 - Papel");
			System.out.println("4 - Metal");
			System.out.println("5 - Orgânico");
			System.out.println("6 - Resíduos Perigosos");
			System.out.println("7 - Outros");
			System.out.println("0 - Voltar");
			opc = sc.next();

			switch (opc) {
			case "1":
				residuo.setTipoResiduo("Plástico");
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
				residuo.setTipoResiduo("Orgânico");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "6":
				residuo.setTipoResiduo("Resíduos Perigosos");
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "7":
				System.out.println("Digite o residuo: ");
				String aux = sc.nextLine();
				residuo.setTipoResiduo(aux);
				empresas = empresaService.listarEmpresaResiduo(residuo);
				break;

			case "0":
				telaUsuarioLogado(usuario);
				break;

			default:
				System.err.println("ERRO: Opção Inválida\n");

			}

			for (Empresa empresa : empresas) {
				System.out.println(i + 1 + " - " + empresa);
				i++;
			}
			int aux = 0;

			if (empresas.size() > 0) {
				System.out.print("\nSelecione a empresa pela númeração para mais informações: ");
				aux = sc.nextInt();
				sc.nextLine();
				aux = aux - 1;
				if (aux > empresas.size() - 1 || aux < 0) {
					System.err.println("ERRO: Opção Inválida\n");

				} else {
					Empresa empresa = empresas.get(aux);
					System.out.println("");
					System.out.println(empresa);

					String opc1 = null;
					do {
						System.out.println("\nQuer adicionar essa empresa como seu Ponto favorito");
						System.out.println("1 - SIM");
						System.out.println("2 - NÃO");
						opc1 = sc.nextLine();

						if (opc1.equals("1")) {
							telaFavoritarEmpresa(usuario, empresa);
							break;

						}

						else if (opc1.equals("2")) {
							telaUsuarioLogado(usuario);
							break;
						} else {
							System.err.println("ERRO: Opção Inválida\n");

						}

					} while (!opc1.equals("2") || !opc1.equals("1"));
				}
			} else {
				System.err.println("Nenhuma empresa trabalha com esse tipo de resíduo\n");
			}

		} while (!opc.equals("0"));

	}

	public void TelaBuscarEmpresaNome(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		Empresa empresa = new Empresa();
		System.out.print("Digite a empresa que você procura: ");
		String nomeEmpresa = sc.nextLine();
		empresa.setNome(nomeEmpresa);

		Empresa empresaRetorno = empresaService.procurar(empresa);

		if (Objects.isNull(empresaRetorno)) {
			System.err.println("Empresa procurada não existe");
		} else {
			empresaRetorno.setSenha(null);
			empresaRetorno.setLogin(null);
			System.out.println(empresaRetorno);

			String opc = null;
			do {
				System.out.println("Quer adicionar essa empresa como seu Ponto favorito");
				System.out.println("1 - SIM");
				System.out.println("2 - NÃO");
				opc = sc.nextLine();

				if (opc.equals("1")) {
					telaFavoritarEmpresa(usuario, empresa);
					telaUsuarioLogado(usuario);
					break;

				}

				else if (opc.equals("2")) {
					telaUsuarioLogado(usuario);
					break;
				} else {
					System.err.println("ERRO: Opção Inválida\n");

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
						"Usuario ja cadastrou essa empresa como ponto favorito, não pode cadastrar novamente\n");
			}

		} else {
			System.err.println("Empresa ainda não pode ser adicionada como ponto favorito\n");

		}
	}

	// Tela que o Usuario lista as empresas favoritas por ele
	public void telaListarEmpresasFavoritas(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		String opc = " ";
		int i = 0, aux = 0;
		List<PontoFavorito> pontosFavoritosUsuario = pontoFavoritoService.listarPontosFav(usuario);

		if (pontosFavoritosUsuario.isEmpty()) {
			System.err.println("O Usuario não possui pontos favoritos");
		} else {
			pontosFavoritosUsuario.forEach(System.out::println);

			while (!opc.equals("2") && !opc.equals("1")) {
				System.out.println("Deseja deletar algum Ponto Favorito ?");
				System.out.println("1 - SIM");
				System.out.println("2 - NÃO");
				opc = sc.nextLine();

				if (opc.equals("1")) {
					for (PontoFavorito pontoFavorito : pontosFavoritosUsuario) {
						System.out.println(i + 1 + " - " + pontoFavorito);
						i++;
					}

					System.out.println("\nDigite um que você queira deletar, a partir da numeração ao lado: ");
					aux = sc.nextInt();
					sc.nextLine();

					aux = aux - 1;
					PontoFavorito pontoFavoritoRet = pontosFavoritosUsuario.get(aux);

					pontoFavoritoService.deletarPontoFvUsuario(pontoFavoritoRet);

				} else if (opc.equals("2")) {
					telaUsuarioLogado(usuario);
				} else {
					System.err.println("ERRO: Opção Inválida\n");

				}
			}
		}

	}

	public void TelaEditarUsuario(Usuario usuario) {
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
					System.out.println("O que deseja alterar: ");
					System.out.println("1 - Nome");
					System.out.println("2 - Email");
					System.out.println("3 - Login");
					System.out.println("4 - Senha");
					System.out.println("0 - Concluir e voltar ao menu");
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
						System.out.println("Digite a nova senha da Usuario:");
						String novaSenha = sc.nextLine();
						usuario.setSenha(novaSenha);
						break;
					case "0":
						usuarioService.alterar(usuario);
						break;
					default:
						System.err.println("ERRO: Opção Inválida\n");
						break;
					}
				} while (!opc.equals("0"));

			} else if (opc.equals("2")) {
				List<Endereco> enderecos = enderecoService.procurarEndUsuario(usuario);
				int i = 1;

				for (Endereco endereco : enderecos) {
					System.out.println(i + " - " + endereco);
					i++;
				}

				System.out.println("Escolha o endereco que você quer alterar: ");
				int aux = sc.nextInt();
				sc.nextLine();
				aux = aux - 1;
				if (aux > enderecos.size() - 1 || aux < 0) {
					System.err.println("ERRO: Opção Inválida\n");
				} else {
					Endereco enderecoEditar = enderecos.get(aux);

					String opcEnd = null;

					do {

						System.out.println("O que deseja alterar no endereco:");
						System.out.println("1 - Estado");
						System.out.println("2 - Cidade");
						System.out.println("3 - Bairro");
						System.out.println("4 - Logradouro");
						System.out.println("5 - Cep");
						System.out.println("6 - Numero de Residência");
						System.out.println("7 - Complemento");
						System.out.println("8 - Deletar");
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
							System.out.println("Digite o seu novo Numero de Residência");
							Integer novoNumero = sc.nextInt();
							enderecoEditar.setNumero(novoNumero);
							break;
						case "7":
							System.out.println("Digite o seu novo complemento");
							String novoComplemento = sc.nextLine();
							enderecoEditar.setComplemento(novoComplemento);
							break;
						case "8":
							enderecoService.deletarEndUsuario(enderecoEditar, usuario);
							System.out.println("Endereço deletado");
							break;
						case "0":
							enderecoService.alterar(enderecoEditar);
							break;
						default:
							System.err.println("ERRO: Opção Inválida\n");
							break;
						}

					} while (!opcEnd.equals("0"));
				}
			} else if (opc.equals("3")) {
				List<Telefone> telefones = telefoneService.procurarTelUsuario(usuario);
				int i = 1;

				for (Telefone telefone : telefones) {
					System.out.println(i + " - " + telefone);
					i++;
				}

				System.out.println("\nEscolha o telefone que você quer alterar, apartir da numeração:");
				int aux = sc.nextInt();
				sc.nextLine();
				aux = aux - 1;
				if (aux > telefones.size() - 1 || aux < 0) {
					System.err.println("ERRO: Opção Inválida\n");
				} else {

					Telefone telefoneEditar = telefones.get(aux);

					String opcTel = null;

					do {
						System.out.println("O que deseja alterar de telefone: ");
						System.out.println("1 - Número");
						System.out.println("2 - Excluir");
						System.out.println("0 - Concluir e voltar ao menu");
						opcTel = sc.nextLine();

						switch (opcTel) {
						case "1":
							System.out.println("Digite seu novo número:");
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
							System.err.println("ERRO: Opção Inválida\n");
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
			System.out.println("3 - Editar Informações");
			System.out.println("4 - Desativar Conta");
			System.out.println("5 - Listar Minhas Empresas Favoritas");
			System.out.println("6 - Fazer Logoff");
			opc = sc.next();

			switch (opc) {
			case "1":
				TelaBuscarEmpresaNome(usuario);
				break;
			case "2":
				telaBuscaEmpPorResiduo(usuario);
				break;
			case "3":
				TelaEditarUsuario(usuario);
				break;
			case "4":
				TelaDesativarConta(usuario);
				break;
			case "5":
				telaListarEmpresasFavoritas(usuario);
				break;
			case "6":
				System.out.println("Fazendo Logoff.");
				telaAplicacao.Menu();
				break;
			default:
				System.err.println("ERRO: Opção Inválida\n");
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
				System.out.println("2 - Não");
				opc = sc.nextLine();
				if (opc.equals("1")) {
					telaLoginUsuario();
					break;

				} else if (opc.equals("2")) {
					telaAplicacao.Menu();
					break;
				} else {
					System.err.println("ERRO: Opção Inválida\n");

				}

			} while (!opc.equals("2"));
		}

	}

}
