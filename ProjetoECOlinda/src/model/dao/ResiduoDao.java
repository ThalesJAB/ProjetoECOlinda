package model.dao;

import java.util.List;

import model.entities.Empresa;
import model.entities.Residuo;

public interface ResiduoDao {
	
	void cadastrar(Residuo residuo);

	void alterar(Residuo residuo);

	void deletar(Residuo residuo, Empresa empresa);

	Residuo procurar(Residuo residuo);
	
	List<Residuo> residuosEmpresa(Empresa empresa);

	List<Residuo> listar();

}
