package br.com.hackfest.model.enums;


public enum Turno {

	
	PRIMEIRO_TURNO("Primeiro Turno"),
	SEGUNDO_TURNO("Segundo Turno");
	
	private String descricao;
	
	private Turno(String descricao) {
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}


}
