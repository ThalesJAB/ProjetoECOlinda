package model.services;

import java.util.List;
import java.util.Objects;

import model.dao.DaoFactory;
import model.dao.EnderecoDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.Usuario;

public class EnderecoService {

	private static EnderecoService enderecoService;

	private EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();

	private EnderecoService() {

	}

	public static EnderecoService getInstance() {
		if (Objects.isNull(enderecoService)) {
			enderecoService = new EnderecoService();

			return enderecoService;
		} else {
			return enderecoService;
		}
	}

	public void cadastrarEndEmpresa(Endereco endereco, Empresa empresa) {
		enderecoDao.cadastrarEndEmpresa(endereco, empresa);
	}

	public void cadastrarEndUsuario(Endereco endereco, Usuario usuario) {
		enderecoDao.cadastrarEndUsuario(endereco, usuario);
	}

	public void alterar(Endereco endereco) {
		enderecoDao.alterar(endereco);
	}

	public void deletarEndEmpresa(Endereco endereco, Empresa empresa) {
		enderecoDao.deletarEndEmpresa(endereco, empresa);
	}

	public void deletarEndUsuario(Endereco endereco, Usuario usuario) {
		enderecoDao.cadastrarEndUsuario(endereco, usuario);
	}

	public List<Endereco> procurarEndEmpresa(Empresa empresa) {
		return enderecoDao.procurarEndEmpresa(empresa);
	}

	public List<Endereco> procurarEndUsuario(Usuario usuario) {
		return enderecoDao.procurarEndUsuario(usuario);
	}

}
