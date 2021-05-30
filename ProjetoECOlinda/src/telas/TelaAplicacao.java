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
		Scanner sc = new Scanner(System.in);
		String opc = new String();
		boolean confirmar = true;
		
		while(confirmar) {
			System.out.println("--------- Menu --------");
			System.out.println("1 - Fazer Login");
			System.out.println("2 - Cadastrar Usuario");
			System.out.println("3 - Cadastrar Empresa");
			System.out.println("0 - Sair");
			opc = sc.nextLine();

			if(opc.equals("1")) {
				TelaOpcaoLogin();
				confirmar = false;
			}
			
			else if(opc.equals("2")) {
				telaUsuario.cadastroUsuario();
				confirmar = false;
			}
			
			else if(opc.equals("3")) {
				telaEmpresa.cadastroEmpresa();
				confirmar = false;
				
			}else if(opc.equals("0")) {
				confirmar = false;
				DB.closeConnection();
				System.out.println("Fim do Programa.");
				System.exit(0);
				
				
			}else {
				System.out.println("Digite uma opção valida.");
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
