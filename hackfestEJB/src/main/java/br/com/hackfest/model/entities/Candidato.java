package br.com.hackfest.model.entities;


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
import javax.persistence.Transient;

import br.com.hackfest.model.enums.CargoEnum;

@Entity
@Table(name= "candidato")
@SequenceGenerator(name = "seqCandidato", sequenceName = "seq_Candidato", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Candidato.unicidade", query = "SELECT COUNT(c) FROM Candidato c WHERE c.nome =:nome AND c.nomeUrna =:nomeUrna "
			+ "AND c.partido =:partido ")
})
public class Candidato extends EntidadeGenerica {
	
	private static final long serialVersionUID = -8617709936543423705L;


	@Id
	@Column(name = "ID_CANDIDATO")
	@GeneratedValue(generator = "seqCandidato", strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="ID_DEPUTADO_FEDERAL")
    private Long deputadoFederal;
    
    @Column(name="ID_DEPUTADO_ESTADUAL")
    private Long deputadoEstadual;
    
    @Column(name="ID_ULTIMO_VOTO_GOVERNADOR")
    private Long ultimoVotoGovernador;
    
    @Column(name="ID_ULTIMO_VOTO_SENADOR")
    private Long ultimoVotoSenador;
	
	
	@Column(name="NOME")
    private String nome;

    @Column(name = "NOME_URNA")
    private String nomeUrna;
    
    @Column(name="CPF")
    private String cpf;
    
    @Column(name="TELEFONE")
    private String telefone;
    
	@Column(name="EMAIL")
	private String email;
    
    @Column(name = "seq_candidato")
    private String seqCandidato;

    @Column(name = "numero_candidato")
    private Integer numeroCandidato;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName="ID_ELEICAO", name="ID_ELEICAO")
    private Eleicao eleicao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName="ID_PARTIDO", name="ID_PARTIDO")
    private Partido partido;
    

    
    @Column(name = "OBSERVACAO", columnDefinition="TEXT")
    private String observacao;
    
    @Transient
    private CargoEnum cargo;
    
    public Candidato() {
		// TODO Auto-generated constructor stub
    	if(partido == null) {
    		partido = new Partido();
    	}
    
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
		if (!(obj instanceof Candidato))
			return false;
		Candidato other = (Candidato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public void setNomeUrna(String nomeUrna) {
        this.nomeUrna = nomeUrna;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSeqCandidato() {
        return seqCandidato;
    }

    public void setSeqCandidato(String seqCandidato) {
        this.seqCandidato = seqCandidato;
    }

    public Integer getNumeroCandidato() {
        return numeroCandidato;
    }

    public void setNumeroCandidato(Integer numeroCandidato) {
        this.numeroCandidato = numeroCandidato;
    }

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}




	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public Eleicao getEleicao() {
		return eleicao;
	}


	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}


	public CargoEnum getCargo() {
		return cargo;
	}


	public void setCargo(CargoEnum cargo) {
		this.cargo = cargo;
	}


	public Long getUltimoVotoSenador() {
		return ultimoVotoSenador;
	}


	public void setUltimoVotoSenador(Long ultimoVotoSenador) {
		this.ultimoVotoSenador = ultimoVotoSenador;
	}


	public Long getUltimoVotoGovernador() {
		return ultimoVotoGovernador;
	}


	public void setUltimoVotoGovernador(Long ultimoVotoGovernador) {
		this.ultimoVotoGovernador = ultimoVotoGovernador;
	}


	public Long getDeputadoEstadual() {
		return deputadoEstadual;
	}


	public void setDeputadoEstadual(Long deputadoEstadual) {
		this.deputadoEstadual = deputadoEstadual;
	}


	public Long getDeputadoFederal() {
		return deputadoFederal;
	}


	public void setDeputadoFederal(Long deputadoFederal) {
		this.deputadoFederal = deputadoFederal;
	}



	

}
