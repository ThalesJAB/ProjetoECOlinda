package model.services;

import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;
import model.exceptions.ValorInvalidoException;

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

	public boolean cadastrar(Usuario usuario) throws ValorInvalidoException {

		if (usuario.getNome().trim().equals("") || Objects.isNull(usuario.getNome())) {
			throw new ValorInvalidoException("Nome Inválido!");
			
		}
		if (usuario.getEmail().trim().equals("") || Objects.isNull(usuario.getEmail())) {
			throw new ValorInvalidoException("Email Inválido!");
			
		} else if (usuarioDao.existeEmail(usuario)) {
			throw new ValorInvalidoException("Email já existente, tente novamente!");
			
		}

		if (usuario.getLogin().trim().equals("") || Objects.isNull(usuario.getLogin())) {
			throw new ValorInvalidoException("Login Inválido");
			
		} else if (usuarioDao.existeLogin(usuario)) {
			throw new ValorInvalidoException("Login ja existente, tente novamente!");
			
		}

		usuarioDao.cadastrar(usuario);
		return true;
	}

	public boolean alterar(Usuario usuario) {

		if (usuario.getNome().trim().equals("") || Objects.isNull(usuario.getNome())) {
			throw new ValorInvalidoException("Nome Inválido!");
			
		}
		if (usuario.getEmail().trim().equals("") || Objects.isNull(usuario.getEmail())) {
			throw new ValorInvalidoException("Email Inválido!");
			
		} else if (usuarioDao.existeEmail(usuario)) {
			throw new ValorInvalidoException("Email já existente, tente novamente!");
			
		}

		if (usuario.getLogin().trim().equals("") || Objects.isNull(usuario.getLogin())) {
			throw new ValorInvalidoException("Login Inválido");
			
		} else if (usuarioDao.existeLogin(usuario)) {
			throw new ValorInvalidoException("Login ja existente, tente novamente!");
			
		}

		usuarioDao.alterar(usuario);
		return true;
		
	}

	public void deletar(Usuario usuario) {
		usuarioDao.deletar(usuario);
	}

	public Usuario login(Usuario usuario) {
		return usuarioDao.login(usuario);
	}

}
