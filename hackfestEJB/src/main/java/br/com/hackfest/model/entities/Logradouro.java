package br.com.hackfest.model.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "logradouro")
@SequenceGenerator(name = "seqLogradouro", sequenceName = "seq_Logradouro", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Logradouro.unicidade", query = "SELECT COUNT(l) FROM Logradouro l WHERE l.nome =:nome AND l.tipoLogradouro =:tipoLogradouro")
})
public class Logradouro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3395203169572503462L;

	@Id
	@Column(name = "ID_LOGRADOURO", nullable = false)
	@GeneratedValue(generator = "seqLogradouro", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_LOGRADOURO", referencedColumnName = "ID_TIPO_LOGRADOURO", nullable=false)
	private TipoLogradouro tipoLogradouro;
	
	public Logradouro(){
		if(tipoLogradouro  == null){
			tipoLogradouro = new TipoLogradouro();
		}
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
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
		if (!(obj instanceof Logradouro))
			return false;
		Logradouro other = (Logradouro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
