package br.com.hackfest.model.entities;



import java.io.Serializable;

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

import br.com.hackfest.model.enums.TipoComplemento;


@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seqEndereco", sequenceName = "seq_Endereco", allocationSize = 1)
public class Endereco implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7029330931993560547L;

	@Id
	@Column(name = "ID_ENDERECO")
	@GeneratedValue(generator = "seqEndereco", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NUMERO", nullable = false)
	private String numero;
	
	@Column(name = "APARTAMENTO")
	private String apartamento;
	
	@Column(name = "BLOCO")
	private String bloco;
	
	@Column(name = "EDIFICIO")
	private String edificio;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;

	@Column(name = "TIPO_COMPLEMENTO")
	@Enumerated(EnumType.STRING)
	private TipoComplemento tipoComplemento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BAIRRO_LOGRADOURO", referencedColumnName = "ID_BAIRRO_LOGRADOURO", nullable = false)
	private BairroLogradouro bairroLogradouro;
	
	public Endereco(){
		if(bairroLogradouro == null){
			bairroLogradouro = new BairroLogradouro();
		}
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getApartamento() {
		return apartamento;
	}

	public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public TipoComplemento getTipoComplemento() {
		return tipoComplemento;
	}

	public void setTipoComplemento(TipoComplemento tipoComplemento) {
		this.tipoComplemento = tipoComplemento;
	}

	public BairroLogradouro getBairroLogradouro() {
		return bairroLogradouro;
	}

	public void setBairroLogradouro(BairroLogradouro bairroLogradouro) {
		this.bairroLogradouro = bairroLogradouro;
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
		if (!(obj instanceof Endereco))
			return false;
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
