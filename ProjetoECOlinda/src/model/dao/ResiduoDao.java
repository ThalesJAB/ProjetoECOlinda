package model.dao;

import java.util.List;

import model.entities.Empresa;
import model.entities.Residuo;

public interface ResiduoDao {
	
	void cadastrar(Residuo residuo);
	
	void cadastrarResiduoEmp(Residuo residuo, Empresa empresa);

	void alterar(Residuo residuo);

	void deletar(Residuo residuo, Empresa empresa);

	boolean procurar(Residuo residuo, Empresa empresa);
	
	List<Residuo> residuosEmpresa(Empresa empresa);
	
	List<Residuo> listar();

}
