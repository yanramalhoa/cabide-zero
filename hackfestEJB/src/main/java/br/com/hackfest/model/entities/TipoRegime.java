package br.com.hackfest.model.entities;

import java.io.Serializable;

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

import br.com.hackfest.model.enums.Estado;

@Entity
@Table(name = "tipo_regime")
@SequenceGenerator(name = "seqTipoRegime", sequenceName = "seq_tipo_regime", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "TipoRegime.unicidade", query = "SELECT COUNT(tr) FROM TipoRegime tr WHERE tr.unidadeFederativa=:unidadeFederativa AND tr.descricao =:descricao ")
})
public class TipoRegime implements Serializable{

	private static final long serialVersionUID = 6066192582367337352L;

	@Id
	@Column(name = "ID_TIPO_REGIME", nullable = false)
	@GeneratedValue(generator = "seqTipoRegime", strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "UF", nullable = false, length = 2)
	private Estado unidadeFederativa;

	@Column(name = "DESCRICAO", nullable = false, length = 255)
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estado getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(Estado unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
