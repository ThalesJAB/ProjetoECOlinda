package model.entities;

import java.io.Serializable;


public class Residuo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tipoResiduo;
	private String descricaoResiduo;
	private Boolean status;
	

	public Residuo() {

	}

	public Residuo(Integer id, String tipoResiduo, String descricaoResiduo, Boolean status) {

		this.id = id;
		this.tipoResiduo = tipoResiduo;
		this.descricaoResiduo = descricaoResiduo;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoResiduo() {
		return tipoResiduo;
	}

	public void setTipoResiduo(String tipoResiduo) {
		this.tipoResiduo = tipoResiduo;
	}

	public String getDescricaoResiduo() {
		return descricaoResiduo;
	}

	public void setDescricaoResiduo(String descricaoResiduo) {
		this.descricaoResiduo = descricaoResiduo;
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
		result = prime * result + ((tipoResiduo == null) ? 0 : tipoResiduo.hashCode());
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
		Residuo other = (Residuo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoResiduo == null) {
			if (other.tipoResiduo != null)
				return false;
		} else if (!tipoResiduo.equals(other.tipoResiduo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Residuo [id=" + id + ", tipoResiduo=" + tipoResiduo + ", nomeResiduo=" + descricaoResiduo + ", status="
				+ status + "]";
	}

	

}
