package br.com.hackfest.model.enums;


public enum TipoEleicao {

	
	PREFEITOS_VEREADORES("Prefeitos e Vereadores"),
	PRESIDENTE_SENADORES_GOVERNADORES_DEPUTADOS_FEDERAL_DEPUTADOS_ESTUAL("Presidente, Senadores, Governadores, "
			+ "Deputados Federais e Deputados Estaduais");
	
	private String descricao;
	
	private TipoEleicao(String descricao) {
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
