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
		PontoFavorito other = (PontoFavorito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PontoFavorito [id=" + id + ", idUsuario=" + idUsuario + ", empresa=" + empresa + ", status=" + status
				+ "]";
	}
}

