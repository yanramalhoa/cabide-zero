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
@Table(name = "bairro")
@SequenceGenerator(name = "seqBairro", sequenceName = "seq_Bairro", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Bairro.unicidade", query = "SELECT COUNT(b) FROM Bairro b WHERE b.nome =:nome AND b.municipio =:municipio")
})
public class Bairro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2844595244797826766L;

	@Id
	@Column(name = "ID_BAIRRO", nullable = false)
	@GeneratedValue(generator = "seqBairro", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
	private Municipio municipio;
	
	public Bairro(){
		if(municipio == null){
			municipio = new Municipio();
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
		if (!(obj instanceof Bairro))
			return false;
		Bairro other = (Bairro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public Municipio getMunicipio() {
		return municipio;
	}



	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
}
