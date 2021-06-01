package model.dao;



import model.entities.Usuario;


public interface UsuarioDao {
	
	void cadastrar(Usuario usuario);

	void alterar(Usuario usuario);

	void deletar(Usuario usuario);

	Usuario login(Usuario usuario);
	
	boolean existeLogin(Usuario usuario);
	
	boolean existeEmail(Usuario usuario);




}
