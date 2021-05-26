package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String login;
	private String senha;
	private String email;
	private Date dataNasc;
	private Boolean status;

	private List<PontoFavorito> pontosFav = new ArrayList<>();
	private List<Telefone> telefones = new ArrayList<>();
	private List<Endereco> enderecos = new ArrayList<>();

	public Usuario() {

	}

	public Usuario(Integer id, String nome, String login, String senha, String email, Date dataNasc, Boolean status,
			List<PontoFavorito> pontosFav, List<Telefone> telefones, List<Endereco> enderecos) {

		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.dataNasc = dataNasc;
		this.status = status;
		this.pontosFav = pontosFav;
		this.telefones = telefones;
		this.enderecos = enderecos;
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

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<PontoFavorito> getPontosFav() {
		return pontosFav;
	}

	public void setPontosFav(List<PontoFavorito> pontosFav) {
		this.pontosFav = pontosFav;
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

	public void addPontoFavorito(PontoFavorito pontoFavorito) {
		pontosFav.add(pontoFavorito);
	}

	public void addTelefone(Telefone telefone) {
		telefones.add(telefone);
	}

	public void addEndereco(Endereco endereco) {
		enderecos.add(endereco);
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", email=" + email
				+ ", dataNasc=" + dataNasc + ", pontosFav=" + pontosFav + ", telefones=" + telefones + ", enderecos="
				+ enderecos + "]";
	}

}
