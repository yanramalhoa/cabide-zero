package br.com.hackfest.model.enums;



public enum SituacaoFiliacao {

    REGULAR("Regular"),
    DESFILIADO("Desfiliado"),
    CANCELADO("Cancelado");

    private String descricao;

    private SituacaoFiliacao(String descricao) {
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
