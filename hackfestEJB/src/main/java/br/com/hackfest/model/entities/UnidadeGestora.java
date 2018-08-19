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
@Table(name = "unidade_gestora")
@SequenceGenerator(name = "seqUnidadeGestora", sequenceName = "seq_unidade_gestora", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "UnidadeGestora.unicidade", query = "SELECT COUNT(ug) FROM UnidadeGestora ug WHERE ug.unidadeFederativa =:unidadeFederativa AND ug.codigoReferencia =:codigoReferencia")
})
public class UnidadeGestora implements Serializable{

	private static final long serialVersionUID = 888133636088586499L;

	@Id
	@Column(name = "ID_UNIDADE_GESTORA", nullable = false)
	@GeneratedValue(generator = "seqUnidadeGestora", strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "UF", nullable = false, length = 2)
	private Estado unidadeFederativa;
	
	@Column(name = "CODIGO_REFERENCIA", nullable = false, length = 100)
	private String codigoReferencia;
	
	@Column(name = "NOME", nullable = false, length = 255)
	private String nome;

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

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
