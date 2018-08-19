package br.com.hackfest.model.enums;

public enum ActionAuthentication {
	
	SALVAR("Salvar"),
	ALTERAR("Alterar"),
	DELETAR("Deletar"),
	PESQUISAR("Pesquisar"),
	IMPRIMIR("Imprimir");
	
	private String action;
	
	private ActionAuthentication(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Override
	public String toString() {
		return action;
	}
	
	
}
