package br.com.hackfest.model.entities;



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
@Table(name = "municipio")
@SequenceGenerator(name = "seqMunicipio", sequenceName = "seq_Municipio", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Municipio.unicidade", query = "SELECT COUNT(c) FROM Municipio c WHERE c.nome =:nome AND c.estado =:estado")
})
public class Municipio extends EntidadeGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5780370121962605277L;

	@Id
	@Column(name = "ID_MUNICIPIO")
	@GeneratedValue(generator = "seqMunicipio", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "ESTADO")
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	@Column(name = "CODIGO")
    private Integer codigo;
    
    @Column(name = "ESP_VIDA")
    private Double esperancaVida;
    
    @Column(name = "TOTAL_HOMEM")
    private Double homemTotal;
    
    @Column(name = "TOTAL_MULHER")
    private Double mulherTotal;
    
    @Column(name = "IDHM")
    private Double idhm;
    
    @Column(name = "IDHM_E")
    private Double idhmE;
    
    @Column(name = "IDHM_L")
    private Double idhmL;
    
    @Column(name = "IDHM_R")
    private Double idhmR;
    
    @Column(name = "TOTAL_PESO")
    private Double pesoTotal;

    @Column(name = "LATITUDE")
    private String latitude;
    
    @Column(name="LONGITUDE")
    private String longitude;
    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName="ID_ZONA", name="ID_ZONA")
//    private Zona zona;
	
    public Municipio() {
		// TODO Auto-generated constructor stub
//    	if(zona == null) {
//    		zona = new Zona();
//    	}
	}
	

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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Double getEsperancaVida() {
		return esperancaVida;
	}

	public void setEsperancaVida(Double esperancaVida) {
		this.esperancaVida = esperancaVida;
	}

	public Double getHomemTotal() {
		return homemTotal;
	}

	public void setHomemTotal(Double homemTotal) {
		this.homemTotal = homemTotal;
	}

	public Double getMulherTotal() {
		return mulherTotal;
	}

	public void setMulherTotal(Double mulherTotal) {
		this.mulherTotal = mulherTotal;
	}

	public Double getIdhm() {
		return idhm;
	}

	public void setIdhm(Double idhm) {
		this.idhm = idhm;
	}

	public Double getIdhmE() {
		return idhmE;
	}

	public void setIdhmE(Double idhmE) {
		this.idhmE = idhmE;
	}

	public Double getIdhmL() {
		return idhmL;
	}

	public void setIdhmL(Double idhmL) {
		this.idhmL = idhmL;
	}

	public Double getIdhmR() {
		return idhmR;
	}

	public void setIdhmR(Double idhmR) {
		this.idhmR = idhmR;
	}

	public Double getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(Double pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

//	public Zona getZona() {
//		return zona;
//	}
//
//	public void setZona(Zona zona) {
//		this.zona = zona;
//	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Municipio))
			return false;
		Municipio other = (Municipio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
