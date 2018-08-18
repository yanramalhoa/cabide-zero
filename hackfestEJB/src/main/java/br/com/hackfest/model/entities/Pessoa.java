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

}
