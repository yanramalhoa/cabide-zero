package br.com.hackfest.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pessoa")
@SequenceGenerator(name = "seqPessoa", sequenceName = "seq_pessoa", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Pessoa.unicidade", query = "SELECT COUNT(p) FROM Pessoa p WHERE p.cpf =:cpf")
})
public class Pessoa implements Serializable{

	private static final long serialVersionUID = 7334851826930236025L;

	@Id
	@Column(name = "ID_PESSOA", nullable = false)
	@GeneratedValue(generator = "seqPessoa", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "CPF", nullable = false, length = 20)
	private String cpf;

	@Column(name = "NOME", nullable = false, length = 255)
	private String nome;

	@Transient
	private Integer qtdTotalPorPessoa;
	
	@Transient
	private BigDecimal totalVantagem;
	
	@Transient
	private List<FiliacaoPartidaria> listaFiliacaoPartidaria;
	
	@Transient
	private List<PessoaFolha> listaPessoaFolha;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdTotalPorPessoa() {
		return qtdTotalPorPessoa;
	}

	public void setQtdTotalPorPessoa(Integer qtdTotalPorPessoa) {
		this.qtdTotalPorPessoa = qtdTotalPorPessoa;
	}

	public BigDecimal getTotalVantagem() {
		return totalVantagem;
	}

	public void setTotalVantagem(BigDecimal totalVantagem) {
		this.totalVantagem = totalVantagem;
	}

	public List<FiliacaoPartidaria> getListaFiliacaoPartidaria() {
		return listaFiliacaoPartidaria;
	}

	public void setListaFiliacaoPartidaria(List<FiliacaoPartidaria> listaFiliacaoPartidaria) {
		this.listaFiliacaoPartidaria = listaFiliacaoPartidaria;
	}

	public List<PessoaFolha> getListaPessoaFolha() {
		return listaPessoaFolha;
	}

	public void setListaPessoaFolha(List<PessoaFolha> listaPessoaFolha) {
		this.listaPessoaFolha = listaPessoaFolha;
	}

}
