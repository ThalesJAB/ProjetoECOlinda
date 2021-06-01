package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String login;
	private String senha;
	private String email;
	private Boolean status;

	private List<Telefone> telefones = new ArrayList<>();
	private List<Endereco> enderecos = new ArrayList<>();
	private List<Residuo> residuos = new ArrayList<>();

	public Empresa() {

	}

	public Empresa(Integer id, String nome, String login, String senha, String email, Boolean status,
			List<Telefone> telefones, List<Endereco> enderecos, List<Residuo> residuos) {

		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.status = status;
		this.telefones = telefones;
		this.enderecos = enderecos;
		this.residuos = residuos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Residuo> getResiduos() {
		return residuos;
	}

	public void setResiduos(List<Residuo> residuos) {
		this.residuos = residuos;
	}

	public void addTelefone(Telefone telefone) {
		if (Objects.isNull(telefones)) {
			this.telefones = new ArrayList<>();
			telefones.add(telefone);
		}
		telefones.add(telefone);
	}

	public void addEndereco(Endereco endereco) {
		if (Objects.isNull(enderecos)) {
			this.enderecos = new ArrayList<>();
			enderecos.add(endereco);
		}
		enderecos.add(endereco);
	}

	public void addResiduo(Residuo residuo) {
		if (Objects.isNull(residuos)) {
			this.residuos = new ArrayList<>();
			residuos.add(residuo);
		}
		residuos.add(residuo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Empresa [id=" + id + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", email=" + email
				+ ", status=" + status + ", telefones=" + telefones + ", enderecos=" + enderecos + ", residuos="
				+ residuos + "]";
	}

}
