package testes;

import model.dao.DaoFactory;
import model.dao.PontoFavoritoDao;
import model.entities.Empresa;
import model.entities.PontoFavorito;
import model.entities.Usuario;

public class ProgramTestePontoFavorito {
	
	
	public static void main(String[] args) {
		
		PontoFavoritoDao pontoFavoritoDao = DaoFactory.createPontoFavoritoDao();
		
		System.out.println("========================TESTE 1: Listar Pontos Favoritos Usuario =========================");
		Usuario usuario = new Usuario(1, "Thales", null, null, null, null, null, null, null, null);
		pontoFavoritoDao.listarPontosFav(usuario).forEach(System.out::println);
		
		System.out.println("========================TESTE 2: Cadastrar Ponto Favorito =========================");
		//Empresa empresa = new Empresa(2, null, null, null, null, null, null, null, null);
		//pontoFavoritoDao.cadastrarPontoFavorito(new PontoFavorito(null, null, empresa, true));
		
		System.out.println("========================TESTE 3: Cadastrar Ponto Favorito Usuario =========================");
	//	Usuario usuario2 = new Usuario(1, "Thales", null, null, null, null, null, null, null, null);
		//Empresa empresa = new Empresa(2, null, null, null, null, null, null, null, null);
		//pontoFavoritoDao.cadastrarPontoFvUsuario(new PontoFavorito(null, usuario2, empresa, true));
		System.out.println("=========================TESTE 4: Deletar Ponto Favorito ========================");
		//pontoFavoritoDao.deletarPontoFavorito(new PontoFavorito(2, null,  null, null));
		
		System.out.println("=========================TESTE 5: Deletar Ponto Favorito de Usuario ========================");
		//pontoFavoritoDao.deletarPontoFvUsuario(new PontoFavorito(2, null, null, null));
	}

}
