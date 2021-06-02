package testes;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class ProgramTesteUsuario {

	public static void main(String[] args) {
		
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
		
		System.out.println("===========================TESTE 1: LOGIN USUARIO ==============================");
		Usuario usuario = usuarioDao.login(new Usuario(null, null, "thalespro", "dale123", null, null, null, null, null, null));
		if(usuario != null) {
			System.out.println("logado com sucesso");
			System.out.println(usuario.getDataNasc());
			
		}else {
			System.out.println("login ou senha incorreta");
		}
		

	}

}
