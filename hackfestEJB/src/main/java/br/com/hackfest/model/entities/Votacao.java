package br.com.hackfest.model.entities;


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

import br.com.hackfest.model.enums.CargoEnum;
import br.com.hackfest.model.enums.SituacaoCandidato;
import br.com.hackfest.model.enums.Turno;


@Entity
@Table(name= "votacao")
@SequenceGenerator(name = "seqVotacao", sequenceName = "seq_Votacao", allocationSize = 1)
//@NamedQueries({
//	@NamedQuery(name = "Votacao.unicidade", query = "SELECT COUNT(v) FROM Votacao v WHERE v.ano =:ano AND v.candidato =:candidato "
//			+ "AND v.municipio =:municipio ")
//})
public class Votacao extends EntidadeGenerica {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2533402408428617701L;

	@Id
	@Column(name = "ID_VOTACAO")
	@GeneratedValue(generator = "seqVotacao", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="CONTAGEM")
    private Integer contagem;
    
	@Column(name="TURNO")
	private Integer turno;
	
	@Column(name="TURNO_ENUM")
	@Enumerated(EnumType.STRING)
	private Turno turnoEnum;
   
    @Column(name="CARGO")
    @Enumerated(EnumType.STRING)
    private CargoEnum cargoEnum;
    
    @Column(name="ANO")
    private Integer ano;
    
    @Column(name = "SITUACAO_CANDIDATO")
    @Enumerated(EnumType.STRING)
    private SituacaoCandidato situacaoCandidato;

    @Column(name = "cod_cargo")
    private String codigoCargo;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CANDIDATO", referencedColumnName = "ID_CANDIDATO")
    private Candidato candidato;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
    private Municipio municipio;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ELEICAO", referencedColumnName = "ID_ELEICAO")
    private Eleicao eleicao;
    
    
    public Votacao() {
		// TODO Auto-generated constructor stub
    	if(getMunicipio() == null) {
    		setMunicipio(new Municipio());
    	}
    	if(candidato == null) {
    		candidato = new Candidato();
    	}
    	if(eleicao == null) {
    		eleicao = new Eleicao();
    	}
  
    
    }

    public Integer getContagem() {
        return contagem;
    }

    public void setContagem(Integer contagem) {
        this.contagem = contagem;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }


    public String getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(String codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CargoEnum getCargoEnum() {
		return cargoEnum;
	}

	public void setCargoEnum(CargoEnum cargoEnum) {
		this.cargoEnum = cargoEnum;
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
		if (!(obj instanceof Votacao))
			return false;
		Votacao other = (Votacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	
}
