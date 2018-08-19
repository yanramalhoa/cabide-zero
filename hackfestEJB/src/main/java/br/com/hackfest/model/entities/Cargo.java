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
@Table(name = "cargo")
@SequenceGenerator(name = "seqCargo", sequenceName = "seq_cargo", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Cargo.unicidade", query = "SELECT COUNT(c) FROM Cargo c WHERE c.unidadeGestora =:unidadeGestora and c.tipoRegime=:tipoRegime and c.nome =:nome ")
})
public class Cargo implements Serializable{

	private static final long serialVersionUID = -6153473565775605439L;

	@Id
	@Column(name = "ID_CARGO", nullable = false)
	@GeneratedValue(generator = "seqCargo", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME", nullable = false, length = 255)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_UNIDADE_GESTORA", referencedColumnName = "ID_UNIDADE_GESTORA", nullable=false)
	private UnidadeGestora unidadeGestora;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_REGIME", referencedColumnName = "ID_TIPO_REGIME", nullable=false)
	private TipoRegime tipoRegime;
	
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

	public UnidadeGestora getUnidadeGestora() {
		return unidadeGestora;
	}

	public void setUnidadeGestora(UnidadeGestora unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}

	public TipoRegime getTipoRegime() {
		return tipoRegime;
	}

	public void setTipoRegime(TipoRegime tipoRegime) {
		this.tipoRegime = tipoRegime;
	}
	
	
}
