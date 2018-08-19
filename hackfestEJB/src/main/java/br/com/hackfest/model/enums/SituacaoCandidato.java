package br.com.hackfest.model.enums;


public enum SituacaoCandidato {

	
	SEGUNDO_TURNO("2º Turno"),
	ELEITO("Eleito"),
	NAO_ELEITO("NÃO Eleito"),
	ELEITO_POR_MEDIA("Eleito por Média"),
	SUPLENTE("Suplente"),
	ELEITO_POR_QP("Eleito por QP");
	
	private String descricao;
	
	private SituacaoCandidato(String descricao) {
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
