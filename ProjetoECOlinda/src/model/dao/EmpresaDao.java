package model.dao;

import java.util.List;

import model.entities.Empresa;

public interface EmpresaDao {

	void cadastrar(Empresa empresa);

	void alterar(Empresa empresa);

	void deletar(Integer id);

	Empresa procurar(Integer id);

	List<Empresa> listar();

}
