package model.services;

import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioService {

	private static UsuarioService usuarioService;

	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

	private UsuarioService() {

	}

	public static UsuarioService getInstance() {
		if (Objects.isNull(usuarioService)) {
			usuarioService = new UsuarioService();
			return usuarioService;
		} else {
			return usuarioService;
		}
	}

	public void cadastrar(Usuario usuario) {
		usuarioDao.cadastrar(usuario);
	}

	public void alterar(Usuario usuario) {
		usuarioDao.alterar(usuario);
	}

	public void deletar(Usuario usuario) {
		usuarioDao.deletar(usuario);
	}

	public Usuario login(Usuario usuario) {
		return usuarioDao.login(usuario);
	}

}
