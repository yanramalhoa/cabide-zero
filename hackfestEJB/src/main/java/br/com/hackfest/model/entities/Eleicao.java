package br.com.hackfest.model.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.hackfest.model.enums.TipoEleicao;

@Entity
@Table(name= "eleicao")
@SequenceGenerator(name = "seqEleicao", sequenceName = "seq_Eleicao", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Eleicao.unicidade", query = "SELECT COUNT(e) FROM Eleicao e WHERE e.ano =:ano ")
})
//@EntityForm(descricao="GRUPO ANIMAL")
public class Eleicao extends EntidadeGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8379653468667764459L;

	/**
	 * 
	 */

	@Id
	@Column(name = "ID_ELEICAO")
	@GeneratedValue(generator = "seqEleicao", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="TIPO_ELEICAO")
	@Enumerated(EnumType.STRING)
	private TipoEleicao tipoEleicao;
	
	@Column(name = "ANO")
	private Integer ano;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Eleicao)
			if(((Eleicao)obj).getId().equals(id))
				return true;
		return false;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoEleicao getTipoEleicao() {
		return tipoEleicao;
	}

	public void setTipoEleicao(TipoEleicao tipoEleicao) {
		this.tipoEleicao = tipoEleicao;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}


	
	
	

}
