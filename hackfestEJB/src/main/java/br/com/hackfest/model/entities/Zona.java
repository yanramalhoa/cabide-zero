package br.com.hackfest.model.entities;



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
@Table(name = "zona")
@SequenceGenerator(name = "seqZona", sequenceName = "seq_Zona", allocationSize = 1)
@NamedQueries({
	@NamedQuery(name = "Zona.unicidade", query = "SELECT COUNT(z) FROM Zona z WHERE z.numeroZona =:numeroZona ")
})
public class Zona extends EntidadeGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -720108030563142701L;


	/**
	 * 
	 */
	@Id
	@Column(name = "ID_ZONA")
	@GeneratedValue(generator = "seqZona", strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="numero_zona")
	private String numeroZona;

	public String getNumeroZona() {
		return numeroZona;
	}

	public void setNumeroZona(String numeroZona) {
		this.numeroZona = numeroZona;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
