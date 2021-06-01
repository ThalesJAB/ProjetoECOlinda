package telas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.PontoFavorito;
import model.entities.Residuo;
import model.entities.Telefone;
import model.exceptions.ValorInvalidoException;
import model.services.EmpresaService;
import model.services.EnderecoService;
import model.services.PontoFavoritoService;
import model.services.ResiduoService;
import model.services.TelefoneService;

public class TelaEmpresa {

	private static EmpresaService empresaService = EmpresaService.getInstance();

	private static TelefoneService telefoneService = TelefoneService.getInstance();

	private static EnderecoService enderecoService = EnderecoService.getInstance();

	private static ResiduoService residuoService = ResiduoService.getInstance();

	private static PontoFavoritoService pontoFavoritoService = PontoFavoritoService.getInstance();

	private static TelaAplicacao telaAplicacao = TelaAplicacao.getInstance();

	private static TelaEmpresa telaEmpresa;

	private TelaEmpresa() {

	}

	public static TelaEmpresa getInstance() {
		if (Objects.isNull(telaEmpresa)) {
			telaEmpresa = new TelaEmpresa();
			return telaEmpresa;
		}
		return telaEmpresa;

	}

	public void telaCadastroEmpresa() {

		// Cadastro de Empresa
		// ------------------------------------------------------------------

		Scanner sc = new Scanner(System.in);
		boolean confirmar = false;

		while (!confirmar) {

			try {
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

				confirmar = empresaService.cadastrar(empresa);

				// Cadastro de Endereço
				// -------------------------------------------------------------------
				telaAdicionarEndereco(empresa);

				// Cadastro de Telefone
				// --------------------------------------------------------------------

				telaAdicionarTelefone(empresa);

				// Cadastro de Residuo Empresa
				// --------------------------------
				telaAdicionarResiduo(empresa);

				// Adicionando Empresa a Ponto Favorito
				PontoFavorito pontoFavEmpresa = new PontoFavorito(null, null, empresa, null);
				pontoFavoritoService.cadastrarPontoFavorito(pontoFavEmpresa);

			} catch (ValorInvalidoException e) {
				System.err.println("ERRO: " + e.getMessage());
			}
		}
	}

	private Residuo criarResiduo() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o tipo do residuo: ");
		String tipoResiduo = scanner.nextLine();
		System.out.println("Digite uma descrição para esse residuo: ");
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
			System.out.println("\nSelecione o tipo do Resíduo: ");
			System.out.println("1 - Plástico");
			System.out.println("2 - Metal");
			System.out.println("3 - Vidro");
			System.out.println("4 - Papel");
			System.out.println("5 - Orgânico");
			System.out.println("6 - Resíduos Perigosos");
			System.out.println("7 - Outros");
			System.out.println("0 - Finalizar cadastrado e ir para o menu:");
			opcResiduo = sc.nextLine();

			switch (opcResiduo) {

			case "1":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Plástico")) {
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
					if (residuo.getTipoResiduo().equals("Orgânico")) {
						residuoService.cadastrarResiduoEmp(residuo, empresa);
					}
				}
				break;
			case "6":
				for (Residuo residuo : residuos) {
					if (residuo.getTipoResiduo().equals("Resíduos Perigosos")) {
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
				telaEmpresaLogado(empresa);
				break;
			default:
				System.err.println("ERRO: Opção Inválida\n");
				break;
			}

		} while (!opcResiduo.equals("0"));

	}

	public void telaAdicionarEndereco(Empresa empresa) {
		Scanner sc = new Scanner(System.in);

		String aux = null;

		boolean confirmar = true;

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
			System.out.println("Digite o Numero de Residência da sua Empresa: ");
			int numero = sc.nextInt();
			sc.nextLine();
			System.out.println("Digite o Complemento da sua empresa");
			String complemento = sc.nextLine();
			boolean statusEndereco = true;

			Endereco endereco = new Endereco(null, cep, logradouro, numero, complemento, bairro, cidade, estado,
					empresa.getId(), statusEndereco);
			
			enderecos.add(endereco);

			while (confirmar) {

				System.out.println("Deseja cadastrar mais um endereco?:");
				System.out.println("1 - SIM\n0 - NÃO\n:");
				aux = sc.nextLine();

				if (aux.equals("0")) {
					confirmar = false;
					empresa.addEndereco(endereco);
					
				} else if (aux.equals("1")) {
					confirmar = false;
				} else {
					System.err.println("ERRO: Opção Inválida\n");
				}

			}

		} while (!aux.equals("0"));

		for (Endereco endereco : enderecos) {
			enderecoService.cadastrarEndEmpresa(endereco, empresa);
			empresa.addEndereco(endereco);
			
		}
		

		System.out.println("Endereços cadastrados com sucesso");
		telaEmpresaLogado(empresa);

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
			System.out.println("1 - SIM\n0 - NÃO\n:");
			aux = sc.nextInt();
			sc.nextLine();

		} while (aux != 0);

		for (Telefone telefone : telefones) {
			telefoneService.cadastrar(telefone);
			empresa.addTelefone(telefone);
		}

		System.out.println("Telefones cadastrados com sucesso");
		telaEmpresaLogado(empresa);

	}

	// Tela de Editar Informações da Empresa
	public void telaEditarEmpresa(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		String opc = null;
		boolean menu = true;

		while (menu) {
			System.out.println("O que deseja alterar: ");
			System.out.println("1 - Dados da Empresa");
			System.out.println("2 - Endereco da Empresa");
			System.out.println("3 - Telefone");
			System.out.println("4 - Residuo");
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
						System.out.println("0 - Concluir e voltar ao menu");
						opc = sc.nextLine();

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
							System.out.println("Digite o novo login da Empresa:");
							String novoLogin = sc.nextLine();
							empresa.setLogin(novoLogin);
							break;
						case "4":
							System.out.println("Digite a nova senha da Empresa:");
							String novaSenha = sc.nextLine();
							empresa.setSenha(novaSenha);
						case "0":
							empresaService.alterar(empresa);
							break;
						default:
							System.err.println("ERRO: Opção Inválida\n");
							break;
						}

					} catch (ValorInvalidoException e) {
						System.err.println("ERRO: Alterações desfeitas, motivo: " + e.getMessage());
					}
				} while (!opc.equals("0"));

			} else if (opc.equals("2")) {
				List<Endereco> enderecos = enderecoService.procurarEndEmpresa(empresa);
				int i = 1;

				for (Endereco endereco : enderecos) {

					System.out.println("OPÇÃO " + i + " - " + "========== ENDEREÇO ==========");
					System.out.println("CEP: " + endereco.getCep() + ";\nLogradouro: " + endereco.getLogradouro()
							+ ";\nNúmero " + endereco.getNumero() + ";\nComplemento: " + endereco.getComplemento()
							+ ";\nBairro: " + endereco.getBairro() + ";\nCidade: " + endereco.getCidade()
							+ ";\nEstado: " + endereco.getEstado());
					i++;
				}
				System.out.println("===================================================================");
				System.out.println(
						"Escolha o endereco que você quer alterar, a partir da numeração ao lado ou 0 para voltar ao menu: ");
				int aux = sc.nextInt();
				sc.nextLine();

				if (aux > enderecos.size() || aux < 0) {
					System.err.println("ERRO: Opção Inválida\n");

				} else if (aux == 0) {

					System.out.println("Voltando ao menu...");
				}

				else {
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
						System.out.println("6 - Numero de Residência");
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
							System.out.println("Digite o seu novo Numero de Residência: ");
							Integer novoNumero = sc.nextInt();
							enderecoEditar.setNumero(novoNumero);
							break;
						case "7":
							System.out.println("Digite o seu novo Complemento:");
							String novoComplemento = sc.nextLine();
							enderecoEditar.setComplemento(novoComplemento);
							break;
						case "8":
							enderecoService.deletarEndEmpresa(enderecoEditar, empresa);
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
				List<Telefone> telefones = telefoneService.procurarTelEmpresa(empresa);
				int i = 1;

				for (Telefone telefone : telefones) {
					System.out.println("OPÇÃO " + i + " - " + "============ TELEFONE =========");
					System.out.println(telefone.getNumTelefone());
					i++;
				}

				System.out.println("=======================================================================");

				System.out.println(
						"Escolha o telefone que você quer alterar, a partir da numeração ao lado ou 0 para voltar ao menu: ");
				int aux = sc.nextInt();
				sc.nextLine();

				if (aux > telefones.size() || aux < 0) {
					System.err.println("ERRO: Opção Inválida\n");

				} else if (aux == 0) {

					System.out.println("Voltando ao menu...");
				}

				else {
					aux = aux - 1;
					Telefone telefoneEditar = telefones.get(aux);

					String opcTel = null;

					do {
						System.out.println("O que deseja alterar de telefone: ");
						System.out.println("1 - Número");
						System.out.println("2 - Deletar");
						System.out.println("0 - Concluir e voltar ao menu");
						opcTel = sc.nextLine();

						switch (opcTel) {
						case "1":
							System.out.println("Digite o novo numero da Empresa:");
							String novoNome = sc.nextLine();
							telefoneEditar.setNumTelefone(novoNome);
							break;

						case "2":
							telefoneService.deletar(telefoneEditar);
							break;
						case "0":
							telefoneService.alterar(telefoneEditar);
							break;

						default:
							System.err.println("ERRO: Opção Inválida\n");
							break;

						}

					} while (!opcTel.equals("0"));
				}

			} else if (opc.equals("4")) {
				List<Residuo> residuos = residuoService.residuosEmpresa(empresa);
				int i = 1;

				for (Residuo residuo : residuos) {
					System.out.println("OPÇÃO " + i + " - " + "============ RESIDUO =========");
					System.out.println(residuo.getTipoResiduo() + " - " + residuo.getDescricaoResiduo());
					i++;
				}

				System.out.println(
						"Escolha o residuo que você quer alterar, a partir da numeração ao lado ou 0 para voltar ao menu: ");
				int aux = sc.nextInt();
				sc.nextLine();

				if (aux > residuos.size() || aux < 0) {
					System.err.println("ERRO: Opção Inválida\n");

				} else if (aux == 0) {
					System.out.println("Voltando ao menu...");
				}

				else {
					aux = aux - 1;
					Residuo residuoEditar = residuos.get(aux);

					String opcTel = null;

					do {
						System.out.println("O que deseja alterar de residuo: ");
						System.out.println("1 - Excluir");
						System.out.println("0 - Concluir e voltar ao menu");
						opcTel = sc.nextLine();

						switch (opcTel) {
						case "1":
							System.out.println("Residuo excluido de seu catálogo");
							residuoService.deletar(residuoEditar, empresa);
							break;

						case "0":
							break;
						default:
							System.err.println("ERRO: Opção Inválida\n");
							break;

						}

					} while (!opcTel.equals("0"));

				}

			} else if (opc.equals("0")) {
				menu = false;
				telaEmpresaLogado(empresa);
				break;
			}
		}

	}

	public void telaDesativarConta(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		String opc = " ";

		while (!opc.equals("1") || !opc.equals("2")) {

			System.out.println("Deseja mesmo excluir sua conta?");
			System.out.println("1- Sim");
			System.out.println("2- Não");
			opc = sc.nextLine();

			switch (opc) {
			case "1":
				empresaService.deletar(empresa);
				System.out.println("Conta excluida com sucesso.");
				telaAplicacao.Menu();
				break;
			case "2":
				telaLoginEmpresa();
				break;
			default:
				System.err.println("ERRO: Opção Inválida\n");
				break;
			}
		}
	}

	// Tela da Empresa Logado
	public void telaEmpresaLogado(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		String opc = "99";

		while (!opc.equals("3")) {
			System.out.println("Bem vindo ao Sistema " + empresa.getNome());
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Editar Informações");
			System.out.println("2 - Adicionar Resíduo ao Catálogo");
			System.out.println("3 - Adicionar Telefone");
			System.out.println("4 - Adicionar Endereco");
			System.out.println("5 - Desativar Conta");
			System.out.println("6 - Fazer Logoff");
			opc = sc.nextLine();

			if (opc.equals("1")) {
				telaEditarEmpresa(empresa);
				break;
			}

			else if (opc.equals("2")) {
				telaAdicionarResiduo(empresa);
				break;
			}
			
			else if(opc.equals("3")) {
				telaAdicionarTelefone(empresa);
				break;
			}
			
			else if(opc.equals("4")) {
				telaAdicionarEndereco(empresa);
				break;
			}

			else if (opc.equals("5")) {
				telaDesativarConta(empresa);
				break;
			}

			else if (opc.equals("6")) {
				System.out.println("Fazendo Logoff");
				telaAplicacao.Menu();
				break;

			} else {
				System.err.println("ERRO: Opção Inválida\n");

			}

		}

	}

	// Metodo de Login de Empresa
	public void telaLoginEmpresa() {
		Scanner sc = new Scanner(System.in);
		String opc = "3";
		Empresa empresa = new Empresa();

		while (!opc.equals("2")) {

			System.out.println("Digite seu login: ");
			String login = sc.nextLine();
			System.out.println("Digite sua senha: ");
			String senha = sc.nextLine();

			empresa.setLogin(login);
			empresa.setSenha(senha);

			Empresa empresaRetorno = empresaService.login(empresa);
			if (Objects.nonNull(empresaRetorno)) {
				List<Telefone> telefones = telefoneService.procurarTelEmpresa(empresaRetorno);
				List<Endereco> enderecos = enderecoService.procurarEndEmpresa(empresaRetorno);
				List<Residuo> residuos = residuoService.residuosEmpresa(empresaRetorno);

				empresaRetorno.setTelefones(telefones);
				empresaRetorno.setEnderecos(enderecos);
				empresaRetorno.setResiduos(residuos);

				telaEmpresaLogado(empresaRetorno);

			} else {
				System.err.println("Login ou Senha Incorretos.");

				System.out.println("");
				System.out.println("Deseja tentar de novo?");
				System.out.println("1 - Sim");
				System.out.println("2 - Não");
				opc = sc.nextLine();

				if (opc.equals("1")) {

				} else if (opc.equals("2")) {
					telaAplicacao.Menu();
					break;

				} else {
					System.err.println("ERRO: Opção Inválida\n");
				}

			}
		}

	}
}
