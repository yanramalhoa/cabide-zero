package br.com.hackfest.model.entities;


import java.math.BigDecimal;

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
@Table(name= "partido")
@SequenceGenerator(name = "seqPartido", sequenceName = "seq_Partido", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Partido.unicidade", query = "SELECT COUNT(p) FROM Partido p WHERE p.nome =:nome AND p.sigla =:sigla AND p.numero =:numero ")
})
public class Partido extends EntidadeGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7077280524134441780L;


	/**
	 * 
	 */


	@Id
	@Column(name = "ID_PARTIDO")
	@GeneratedValue(generator = "seqPartido", strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="NOME")
    private String nome;

    @Column(name = "SIGLA")
    private String sigla;
    
    @Column(name="NUMERO")
    private String numero;
    
    @Transient
	private Integer quantFiliados;
    
    @Transient
  	private Integer quantServidoresEmpregados;
    
    @Transient
  	private Integer quantiServidoresDoisEmpregos;
    
    @Transient
  	private BigDecimal financeiro;
    
	
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
		if(obj instanceof Partido)
			if(((Partido)obj).getId().equals(id))
				return true;
		return false;
	}


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getQuantServidoresEmpregados() {
		return quantServidoresEmpregados;
	}

	public void setQuantServidoresEmpregados(Integer quantServidoresEmpregados) {
		this.quantServidoresEmpregados = quantServidoresEmpregados;
	}

	public Integer getQuantiServidoresDoisEmpregos() {
		return quantiServidoresDoisEmpregos;
	}

	public void setQuantiServidoresDoisEmpregos(Integer quantiServidoresDoisEmpregos) {
		this.quantiServidoresDoisEmpregos = quantiServidoresDoisEmpregos;
	}

	public Integer getQuantFiliados() {
		return quantFiliados;
	}

	public void setQuantFiliados(Integer quantFiliados) {
		this.quantFiliados = quantFiliados;
	}

	public BigDecimal getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(BigDecimal financeiro) {
		this.financeiro = financeiro;
	}
    


}
