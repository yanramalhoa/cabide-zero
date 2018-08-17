package br.com.hackfest.model.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "tipo_logradouro")
@SequenceGenerator(name = "seqTipoLogradouro", sequenceName = "seq_Tipo_Logradouro", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "TipoLogradouro.unicidade", query = "SELECT COUNT(tl) FROM TipoLogradouro tl WHERE tl.nome =:nome AND tl.abreviacao =:abreviacao")
})
public class TipoLogradouro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3961881092401229538L;

	@Id
	@Column(name = "ID_TIPO_LOGRADOURO", nullable = false)
	@GeneratedValue(generator = "seqTipoLogradouro", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "ABREVIACAO", nullable = false)
	private String abreviacao;

	
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

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
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
		if (!(obj instanceof TipoLogradouro))
			return false;
		TipoLogradouro other = (TipoLogradouro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
