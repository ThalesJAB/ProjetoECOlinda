package model.services;

import java.util.List;
import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.ResiduoDao;
import model.entities.Empresa;
import model.entities.Residuo;

public class ResiduoService {

	private static ResiduoService residuoService;

	private ResiduoDao residuoDao = DaoFactory.createResiduoDao();

	private ResiduoService() {

	}

	public static ResiduoService getInstance() {
		if (Objects.isNull(residuoService)) {
			residuoService = new ResiduoService();
			
			return residuoService;
		} else {
			return residuoService;
		}
	}

	public void cadastrar(Residuo residuo) {
		residuoDao.cadastrar(residuo);
	}

	public void cadastrarResiduoEmp(Residuo residuo, Empresa empresa) {
		residuoDao.cadastrarResiduoEmp(residuo, empresa);
	}
	
	public void alterar(Residuo residuo) {
		residuoDao.alterar(residuo);
	}

	public void deletar(Residuo residuo, Empresa empresa) {
		residuoDao.deletar(residuo, empresa);
	}

	public boolean procurar(Residuo residuo, Empresa empresa) {
		return residuoDao.procurar(residuo, empresa);
	}

	public List<Residuo> residuosEmpresa(Empresa empresa) {
		return residuoDao.residuosEmpresa(empresa);
	}

	public List<Residuo> listar() {
		return residuoDao.listar();
	}

}
