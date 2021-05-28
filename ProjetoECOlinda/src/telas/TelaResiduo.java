package telas;

import java.util.ArrayList;
import java.util.Scanner;

import model.entities.Residuo;
import model.services.ResiduoService;

public class TelaResiduo {
	
	public  void CadastroResiduo() {
		//TelaAplicacao ta = new TelaAplicacao();
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o nome do Residuo: ");
		String descResiduo = sc.nextLine();
		String tipoResiduo = null;
		String opcResiduo = "99";
		
		//while (opcResiduo != 0 || (opcResiduo != 1 || (opcResiduo != 2 || (opcResiduo != 3 || (opcResiduo != 4 || (opcResiduo != 5 || (opcResiduo != 6 || (opcResiduo != 7)))))))) {
		while (!opcResiduo.equals("0")) {
			System.out.println("Selecione o tipo do Resíduo: ");
			System.out.println("1 - Plástico");
			System.out.println("2 - Vidro");
			System.out.println("3 - Papel");
			System.out.println("4 - Metal");
			System.out.println("5 - Orgânico");
			System.out.println("6 - Resíduos Perigosos");
			System.out.println("7 - Outros");
			System.out.println("0 - Voltar");
			opcResiduo = sc.next();
			

			switch(opcResiduo) {
			case "1": 
			
				break;				
			case "2":
				tipoResiduo = "Vidro";
				opcResiduo = "0";
				break;
			case "3":
				tipoResiduo = "Papel";
				opcResiduo = "0";
				break;
			case "4":
				tipoResiduo = "Metal";
				opcResiduo = "0";
				break;
			case "5":
				tipoResiduo = "Orgânico";
				opcResiduo = "0";
				break;
			case "6":
				tipoResiduo = "Resíduos Perigosos";
				opcResiduo = "0";
				break;
			case "7":
				opcResiduo = "0";
				sc.nextLine();
				System.out.println("Digite o tipo do Resíduo:");
				tipoResiduo = sc.nextLine();
				
				break;
			case "0":
				System.out.println("Voltando ao Menu.");
				//ta.Menu();
				break;
			default:
				System.out.println("Digite uma opção valida.");
				break;
			}
		}
		boolean statusResiduo = true;
		Residuo residuo = new Residuo(null, tipoResiduo, descResiduo, statusResiduo);
		
		
	}
	
	public  void TelaBuscaPorResiduo() {
		String opcResiduo = "99";
		Scanner sc = new Scanner(System.in);
		String tipoResiduo = null;
		ArrayList<String> residuos = new ArrayList<String>();
		while (!opcResiduo.equals(0)) {
			if (!residuos.isEmpty()) {
				System.out.println("Atualmente procurando por: ");
				System.out.println(residuos);
			}
		System.out.println("Selecione o tipo do Resíduo: ");
		System.out.println("1 - Plástico");
		System.out.println("2 - Vidro");
		System.out.println("3 - Papel");
		System.out.println("4 - Metal");
		System.out.println("5 - Orgânico");
		System.out.println("6 - Resíduos Perigosos");
		System.out.println("7 - Outros");
		System.out.println("8 - Limpar Buscas");
		System.out.println("0 - Voltar");
		opcResiduo = sc.next();
		
		switch(opcResiduo) {
		case "1": 
			tipoResiduo = "Plástico";
			residuos.add(tipoResiduo);
			break;				
		case "2":
			tipoResiduo = "Vidro";
			residuos.add(tipoResiduo);
			break;
		case "3":
			tipoResiduo = "Papel";
			residuos.add(tipoResiduo);
			break;
		case "4":
			tipoResiduo = "Metal";
			residuos.add(tipoResiduo);
			break;
		case "5":
			tipoResiduo = "Orgânico";
			residuos.add(tipoResiduo);
			break;
		case "6":
			tipoResiduo = "Resíduos Perigosos";
			residuos.add(tipoResiduo);
			break;
		case "7":
			opcResiduo = "0";
			sc.nextLine();
			System.out.println("Digite o tipo do Resíduo:");
			tipoResiduo = sc.nextLine();
			break;
		case "8":
			residuos.removeAll(residuos);
			break;
		case "0":
			System.out.println("Voltando ao Menu.");
			//TelaAplicacao ta = new TelaAplicacao();
			//ta.Menu();
			break;
		default:
			System.out.println("Digite uma opção valida.");
			break;
			
		}
		}
		
		BuscaPorNomeTipoResiduo(tipoResiduo);
	}
	
	public  void BuscaPorNomeTipoResiduo(String tipoResiduo) {
		// aqui tem que haver um metodo pra pegar as empresas com o tipo de residuo que foi enviado da tela.
	}
}
