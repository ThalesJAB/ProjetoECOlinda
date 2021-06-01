package model.services;

import java.util.List;
import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.EmpresaDao;
import model.entities.Empresa;
import model.entities.Residuo;
import model.exceptions.ValorInvalidoException;

public class EmpresaService {

	private static EmpresaService empresaService;

	private EmpresaDao empresaDao = DaoFactory.createEmpresaDao();

	private EmpresaService() {

	}

	public static EmpresaService getInstance() {
		if (Objects.isNull(empresaService)) {
			empresaService = new EmpresaService();

			return empresaService;
		} else {
			return empresaService;
		}
	}

	public boolean cadastrar(Empresa empresa) throws ValorInvalidoException {

		if (empresa.getNome().trim().equals("") || Objects.isNull(empresa.getNome())) {
			throw new ValorInvalidoException("Nome Inválido!");
			
		}
		if (empresa.getEmail().trim().equals("") || Objects.isNull(empresa.getEmail())) {
			throw new ValorInvalidoException("Email Inválido!");
			
		} else if (empresaDao.existeEmail(empresa)) {
			throw new ValorInvalidoException("Email já existente, tente novamente!");
			
		}

		if (empresa.getLogin().trim().equals("") || Objects.isNull(empresa.getLogin())) {
			throw new ValorInvalidoException("Login Inválido");
			
		} else if (empresaDao.existeLogin(empresa)) {
			throw new ValorInvalidoException("Login ja existente, tente novamente!");
			
		}

		empresaDao.cadastrar(empresa);		
		return true;
	
	}

	public void alterar(Empresa empresa) {
		empresaDao.alterar(empresa);		
	}

	public void deletar(Empresa empresa) {
		empresaDao.deletar(empresa);
	}

	public Empresa procurar(Empresa empresa) {
		return empresaDao.procurar(empresa);
	}

	public Empresa login(Empresa empresa) {
		return empresaDao.login(empresa);
	}
	
	public List<Empresa> listarEmpresaResiduo(Residuo residuo){
		return empresaDao.listarEmpresaResiduo(residuo);
		
	}

	public List<Empresa> listarEmpresas() {
		return empresaDao.listarEmpresas();

	}

}
