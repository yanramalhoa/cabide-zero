package br.com.hackfest.model.enums;


public enum TipoComplemento {
	
	INDEFINIDO("Indefinido"),
	APARTAMENTO("Apartamento"),
	LOJA("Loja"),
	SALA("Sala"),
	CASA_VILA("Casa de Vila"),
	QUADRA_LOTEAMENTO("Quadra Loteamento"),
	BOX("Box");
	
	
	private String tipoComplemento;
	
	TipoComplemento(String tipoComplemento){
		this.tipoComplemento = tipoComplemento;
	}

	public String getTipoComplemento() {
		return tipoComplemento;
	}

	public void setTipoComplemento(String tipoComplemento) {
		this.tipoComplemento = tipoComplemento;
	}

}
