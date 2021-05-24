package model.dao;

import java.util.List;

import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.Usuario;


public interface EnderecoDao {
	
	void cadastrarEndEmpresa(Endereco endereco, Empresa empresa);
	
	void cadastrarEndUsuario(Endereco endereco, Usuario usuario);

	void alterar(Endereco endereco);

	void deletarEndEmpresa(Endereco endereco, Empresa empresa);
	
	void deletarEndUsuario(Endereco endereco, Usuario usuario);

	List<Endereco> procurarEndEmpresa(Empresa empresa);
	
	List<Endereco> procurarEndUsuario(Usuario usuario);


}
