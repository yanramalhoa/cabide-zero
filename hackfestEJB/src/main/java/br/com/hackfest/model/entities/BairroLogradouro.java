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






@Entity
@Table(name = "bairro_logradouro")
@SequenceGenerator(name = "seqBairroLogradouro", sequenceName = "seq_Bairro_Logradouro", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "BairroLogradouro.unicidade", query = "SELECT COUNT(bl) FROM BairroLogradouro bl WHERE (bl.cep=:cep)")
})
public class BairroLogradouro   implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -1026389414803111980L;

	@Id
	@Column(name = "ID_BAIRRO_LOGRADOURO")
	@GeneratedValue(generator = "seqBairroLogradouro", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "CEP", unique=true, nullable = false)
	private String cep;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_LOGRADOURO", referencedColumnName = "ID_LOGRADOURO", nullable=false)
	private Logradouro logradouro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BAIRRO", referencedColumnName = "ID_BAIRRO", nullable=false)
	private Bairro bairro;
	
	public BairroLogradouro(){
		if(bairro == null){
			bairro = new Bairro();
		}
		if(logradouro == null){
			logradouro = new Logradouro();
		}
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
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
		if (!(obj instanceof BairroLogradouro))
			return false;
		BairroLogradouro other = (BairroLogradouro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
