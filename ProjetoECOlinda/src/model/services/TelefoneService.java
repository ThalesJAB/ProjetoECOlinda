package model.services;

import java.util.List;
import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.TelefoneDao;
import model.entities.Empresa;
import model.entities.Telefone;
import model.entities.Usuario;

public class TelefoneService {

	private static TelefoneService telefoneService;

	private TelefoneDao telefoneDao = DaoFactory.createTelefoneDao();

	private TelefoneService() {

	}

	public static TelefoneService getInstance() {
		if (Objects.isNull(telefoneService)) {
			telefoneService = new TelefoneService();
			return telefoneService;
		} else {
			return telefoneService;
		}
	}

	public void cadastrar(Telefone telefone) {
		telefoneDao.cadastrar(telefone);
	}

	public void alterar(Telefone telefone) {
		telefoneDao.alterar(telefone);
	}

	public void deletar(Telefone telefone) {
		telefoneDao.deletar(telefone);
	}

	public List<Telefone> procurarTelUsuario(Usuario usuario) {
		return telefoneDao.procurarTelUsuario(usuario);
	}

	public List<Telefone> procurarTelEmpresa(Empresa empresa) {
		return telefoneDao.procurarTelEmpresa(empresa);
	}

	public List<Telefone> listar() {
		return telefoneDao.listar();
	}

}
