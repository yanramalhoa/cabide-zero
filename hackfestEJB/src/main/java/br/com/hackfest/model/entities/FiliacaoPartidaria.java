package br.com.hackfest.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.hackfest.model.enums.Estado;
import br.com.hackfest.model.enums.SituacaoFiliacao;

@Entity
@Table(name = "filiacao_partidaria")
@SequenceGenerator(name = "seqFiliacaoPartidaria", sequenceName = "seq_filiacao_partidaria", allocationSize = 1)
//@NamedQueries({
//	@NamedQuery(name = "FiliacaoPartidaria.unicidade", query = "SELECT COUNT(fp) FROM FiliacaoPartidaria fp WHERE c.unidadeGestora =:unidadeGestora and c.tipoRegime=:tipoRegime and c.nome =:nome ")
//})
public class FiliacaoPartidaria implements Serializable{

	private static final long serialVersionUID = 6310504856221670037L;

	@Id
	@Column(name = "ID_FILIACAO_PARTIDARIA", nullable = false)
	@GeneratedValue(generator = "seqFiliacaoPartidaria", strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", nullable=false)
	private Pessoa pessoa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PARTIDO", referencedColumnName = "ID_PARTIDO", nullable=false)
	private Partido partido;

	@Column(name = "DATA_EXTRACAO")
	@Temporal(TemporalType.DATE)
	private Date dataExtracao;
	
	@Column(name = "DATA_FILIACAO")
	@Temporal(TemporalType.DATE)
	private Date dataFiliacao;
	
	@Column(name = "DATA_DESFILIACAO")
	@Temporal(TemporalType.DATE)
	private Date dataDesfiliacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SITUACAO_FILIACAO")
	private SituacaoFiliacao situacaoFiliacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "UF", length=2)
	private Estado unidadeFederativa;

	@Column(name = "MUNICIPIO", length = 255)
	private String municipio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataExtracao() {
		return dataExtracao;
	}

	public void setDataExtracao(Date dataExtracao) {
		this.dataExtracao = dataExtracao;
	}

	public Date getDataFiliacao() {
		return dataFiliacao;
	}

	public void setDataFiliacao(Date dataFiliacao) {
		this.dataFiliacao = dataFiliacao;
	}

	public Date getDataDesfiliacao() {
		return dataDesfiliacao;
	}

	public void setDataDesfiliacao(Date dataDesfiliacao) {
		this.dataDesfiliacao = dataDesfiliacao;
	}

	public SituacaoFiliacao getSituacaoFiliacao() {
		return situacaoFiliacao;
	}

	public void setSituacaoFiliacao(SituacaoFiliacao situacaoFiliacao) {
		this.situacaoFiliacao = situacaoFiliacao;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Estado getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(Estado unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	
	
}
