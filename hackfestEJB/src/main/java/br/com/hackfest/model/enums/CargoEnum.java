package br.com.hackfest.model.enums;



public enum CargoEnum {

    GOVERNADOR("GOVERNADOR"),
    SENADOR("SENADOR"),
    DEPUTADO_FEDERAL("DEPUTADO FEDERAL"),
    DEPUTADO_ESTADUAL("DEPUTADO ESTADUAL"),
    PREFEITO("PREFEITO"),
    VEREADOR("VEREADOR");

    private String descricao;

    private CargoEnum(String descricao) {
        this.setDescricao(descricao);
    }
    
    @Override
	public String toString() {
		return this.getDescricao();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
