package model.services;

import java.util.List;
import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.EmpresaDao;
import model.entities.Empresa;
import model.entities.Residuo;

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

	public void cadastrar(Empresa empresa) {
		empresaDao.cadastrar(empresa);
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
