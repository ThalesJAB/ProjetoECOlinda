package model.dao;

import java.util.List;

import model.entities.Empresa;
import model.entities.Residuo;

public interface EmpresaDao {

	void cadastrar(Empresa empresa);

	void alterar(Empresa empresa);

	void deletar(Empresa empresa);

	Empresa procurar(Empresa empresa);
	
	Empresa login(Empresa empresa);
	
	List<Empresa> listarEmpresaResiduo(Residuo residuo);

	List<Empresa> listarEmpresas();
	
	boolean existeLogin(Empresa empresa);
	
	boolean existeEmail(Empresa empresa);

}
