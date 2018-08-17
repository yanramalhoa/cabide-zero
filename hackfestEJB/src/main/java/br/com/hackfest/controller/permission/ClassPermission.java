package br.com.hackfest.controller.permission;

import java.io.Serializable;

import javax.persistence.Id;


public class ClassPermission implements Serializable{

	private static final long serialVersionUID = 8434567133219060751L;

	@Id
	private String nameClass;
	
	private String descricao;

	public String getNameClass() {
		return nameClass;
	}

	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nameClass == null) ? 0 : nameClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ClassPermission))
			return false;
		ClassPermission other = (ClassPermission) obj;
		if (nameClass == null) {
			if (other.nameClass != null)
				return false;
		} else if (!nameClass.equals(other.nameClass))
			return false;
		return true;
	}

	

}
