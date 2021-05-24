package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String numTelefone;
	private Integer idUsuario;
	private Integer idEmpresa;
	private Boolean status;

	public Telefone() {

	}

	public Telefone(Integer id, String numTelefone, Integer idUsuario, Integer idEmpresa, Boolean status) {

		this.id = id;
		this.numTelefone = numTelefone;
		this.idUsuario = idUsuario;
		this.idEmpresa = idEmpresa;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumTelefone() {
		return numTelefone;
	}

	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
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
		result = prime * result + ((numTelefone == null) ? 0 : numTelefone.hashCode());
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
		Telefone other = (Telefone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numTelefone == null) {
			if (other.numTelefone != null)
				return false;
		} else if (!numTelefone.equals(other.numTelefone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (Objects.isNull(this.idUsuario)) {
			return "Telefone [id=" + id + ", numTelefone=" + numTelefone + ", idEmpresa=" + idEmpresa + ", status="
					+ status + "]";
		} else {
			return "Telefone [id=" + id + ", numTelefone=" + numTelefone + ", idUsuario=" + idUsuario + ", status="
					+ status + "]";
		}

	}
}
