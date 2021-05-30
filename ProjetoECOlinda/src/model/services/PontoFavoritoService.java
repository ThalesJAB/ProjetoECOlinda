package model.services;

import java.util.List;
import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.PontoFavoritoDao;
import model.entities.PontoFavorito;
import model.entities.Usuario;

public class PontoFavoritoService {

	private static PontoFavoritoService pontoFavoritoService;

	private PontoFavoritoDao pontoFavoritoDao = DaoFactory.createPontoFavoritoDao();

	private PontoFavoritoService() {

	}

	public static PontoFavoritoService getInstance() {
		if (Objects.isNull(pontoFavoritoService)) {
			pontoFavoritoService = new PontoFavoritoService();

			return pontoFavoritoService;
		} else {
			return pontoFavoritoService;
		}
	}

	public void cadastrarPontoFavorito(PontoFavorito pontoFavorito) {
		pontoFavoritoDao.cadastrarPontoFavorito(pontoFavorito);
	}

	public void cadastrarPontoFvUsuario(PontoFavorito pontoFavorito) {
		pontoFavoritoDao.cadastrarPontoFvUsuario(pontoFavorito);
	}

	public void deletarPontoFavorito(PontoFavorito pontoFavorito) {
		pontoFavoritoDao.deletarPontoFavorito(pontoFavorito);
	}

	public void deletarPontoFvUsuario(PontoFavorito pontoFavorito) {
		pontoFavoritoDao.deletarPontoFvUsuario(pontoFavorito);
	}

	public PontoFavorito procurarPontoFavorito(PontoFavorito pontoFavorito) {
		return pontoFavoritoDao.procurarPontoFavorito(pontoFavorito);
	}
	
	public boolean procurarPontoFavoritoUsu(PontoFavorito pontoFavorito) {
		return pontoFavoritoDao.procurarPontoFavoritoUsu(pontoFavorito);
	}

	public List<PontoFavorito> listarPontosFav(Usuario usuario) {
		return pontoFavoritoDao.listarPontosFav(usuario);
	}

}
