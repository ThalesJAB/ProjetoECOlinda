package telas;

import java.util.Objects;
import java.util.Scanner;

import db.DB;

public class TelaAplicacao {
	
	private static TelaAplicacao telaAplicacao;
	
	private static TelaEmpresa telaEmpresa = TelaEmpresa.getInstance();
	
	private static TelaUsuario telaUsuario = TelaUsuario.getInstance();
	
	private TelaAplicacao() {
		
	}
	
	public static TelaAplicacao getInstance() {
		if(Objects.isNull(telaAplicacao)) {
			telaAplicacao = new TelaAplicacao();
			return telaAplicacao;
		}else {
			return telaAplicacao;
		}
		
	}

	// Metodo do Menu Inicial.
	public void Menu() {
		TelaResiduo tr = new TelaResiduo();
		Scanner sc = new Scanner(System.in);
		String opc = "99";
		while (!opc.equals("0")) {
			System.out.println("--------- Menu --------");
			System.out.println("1- Fazer Login");
			System.out.println("2- Cadastrar Usuario");
			System.out.println("3- Cadastrar Empresa");
			System.out.println("4- Cadastrar Residuo");
			System.out.println("0 - Sair");
			opc = sc.next();

			switch (opc) {
			case "1":
				TelaOpcaoLogin();
				break;
			case "2":
				telaUsuario.cadastroUsuario();
				break;
			case "3":
				telaEmpresa.cadastroEmpresa();
				break;
			case "4":
				tr.CadastroResiduo();
				break;
			case "0":
				System.out.println("Fim do Programa.");
				DB.closeConnection();
				break;
			default:
				System.out.println("Digite uma opção valida.");
				break;
			}
		}
	}

	// Tela de Opção de Login
	public void TelaOpcaoLogin() {
		Scanner sc = new Scanner(System.in);
		String opc = "99";
		System.out.println("Você é um Usuario ou Empresa: ");
		System.out.println("1- Usuario:");
		System.out.println("2- Empresa:");
		opc = sc.next();

		switch (opc) {
		case "1":
			telaUsuario.telaLoginUsuario();
			break;
		case "2":
			telaEmpresa.telaLoginEmpresa();
			break;
		default:
			System.out.println("Digite uma opção valida.");
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TelaAplicacao ta = getInstance();
		ta.Menu();
	}

}
