package model.entities;

import java.io.Serializable;

public class PontoFavorito implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer idUsuario;
	private Empresa empresa;
	private Boolean status;

	public PontoFavorito() {

	}

	public PontoFavorito(Integer id, Integer idUsuario, Empresa empresa, Boolean status) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.empresa = empresa;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PontoFavorito [id=" + id + ", idUsuario=" + idUsuario +  ", status=" + status
				+ "]";
	}

}
