package model.dao;

import java.util.List;

import model.entities.PontoFavorito;
import model.entities.Usuario;


public interface UsuarioDao {
	
	void cadastrar(Usuario Usuario);

	void alterar(Usuario Usuario);

	void deletar(Integer id);

	Usuario procurar(Integer id);

	List<Usuario> listar();
	
	List<PontoFavorito> listarPontosFav(Integer id);


}
