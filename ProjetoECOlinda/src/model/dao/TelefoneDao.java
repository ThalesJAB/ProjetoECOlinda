package model.dao;

import java.util.List;

import model.entities.Empresa;
import model.entities.Telefone;
import model.entities.Usuario;

public interface TelefoneDao {

	void cadastrar(Telefone telefone);

	void alterar(Telefone telefone);

	void deletar(Telefone telefone);

	List<Telefone> procurarTelUsuario(Usuario usuario);
	
	List<Telefone> procurarTelEmpresa(Empresa empresa);

	List<Telefone> listar();

	

}
