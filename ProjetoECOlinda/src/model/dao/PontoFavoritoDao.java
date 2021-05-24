package model.dao;

import java.util.List;

import model.entities.PontoFavorito;
import model.entities.Usuario;

public interface PontoFavoritoDao {

	void cadastrarPontoFavorito(PontoFavorito pontoFavorito);
	
	void cadastrarPontoFvUsuario(PontoFavorito pontoFavorito);
	
	void deletarPontoFavorito(PontoFavorito pontoFavorito);

	void deletarPontoFvUsuario(PontoFavorito pontoFavorito);
	
	PontoFavorito procurarPontoFavorito(PontoFavorito pontoFavorito);

	List<PontoFavorito> listarPontosFav(Usuario usuario);

}
