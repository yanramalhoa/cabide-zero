package br.com.hackfest.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "pessoa_folha")
@SequenceGenerator(name = "seqPessoaFolha", sequenceName = "seq_pessoa_folha", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "PessoaFolha.unicidade", query = "SELECT COUNT(pf) FROM PessoaFolha pf WHERE pf.pessoa=:pessoa and pf.cargo=:cargo and pf.ano=:ano and pf.mes=:mes ")
})
public class PessoaFolha implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 518993912719222571L;

	@Id
	@Column(name = "ID_PESSOA_FOLHA", nullable = false)
	@GeneratedValue(generator = "seqPessoaFolha", strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", nullable=false)
	private Pessoa pessoa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO", nullable=false)
	private Cargo cargo;

	private Integer mes;
	
	private Integer ano;
	
	private BigDecimal vantagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getVantagem() {
		return vantagem;
	}

	public void setVantagem(BigDecimal vantagem) {
		this.vantagem = vantagem;
	}
	
}
