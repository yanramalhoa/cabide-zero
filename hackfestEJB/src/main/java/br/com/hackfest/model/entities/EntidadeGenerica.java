package br.com.hackfest.model.entities;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.hackfest.controller.annotation.Status;



@Embeddable
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntidadeGenerica implements Serializable, Cloneable {

	private static final long serialVersionUID = -8517999279663669144L;

	@Status
	@Column(name = "ATIVO")
	protected Boolean ativo;
	
	@Transient
	protected Usuario usuarioAUD;
	
	@Status
	@Column(name = "NOME_USUARIO_AUD")
	private String nomeUsuarioAUD;
	
	@Column(name = "DATA_AUD", nullable=true)
	private Date dataAUD;
	
	@Column(name = "CODIGO_ANTERIOR", length=255, nullable=true)
	private String codigoAnterior;
	
	
	
	public EntidadeGenerica() {
	}
	

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[").append(this.getClass().getSimpleName()).append("] ");
		try{
			Field[] fields = this.getClass().getDeclaredFields();
			for(Field f : fields){
				if(!Modifier.isStatic(f.getModifiers())){
					f.setAccessible(true);
					str.append(f.getName()).append(": ").append(f.get(this)).append(", ");
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public Usuario getUsuarioAUD() {
		return usuarioAUD;
	}

	public void setUsuarioAUD(Usuario usuarioAUD) {
		this.usuarioAUD = usuarioAUD;
		if(this.nomeUsuarioAUD==null){
			this.nomeUsuarioAUD = usuarioAUD.getLogin();
		}
	}

	public String getNomeUsuarioAUD() {
		return nomeUsuarioAUD;
	}

	public void setNomeUsuarioAUD(String nomeUsuarioAUD) {
		this.nomeUsuarioAUD = nomeUsuarioAUD;
	}

	public Date getDataAUD() {
		return dataAUD;
	}

	public void setDataAUD(Date dataAUD) {
		this.dataAUD = dataAUD;
	}

	public String getCodigoAnterior() {
		return codigoAnterior;
	}

	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior;
	}

	/**
	 * @return the ativo
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}


	
	
}
