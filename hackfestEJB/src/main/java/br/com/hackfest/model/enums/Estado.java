package br.com.hackfest.model.enums;



public enum Estado {
	AC("AC", "Acre"), 
	AL("AL", "Alagoas"), 
	AP("AP", "Amapa"), 
	AM("AM", "Amazonas"), 
	BA("BA", "Bahia"), 
	CE("CE", "Ceará"), 
	DF("DF", "Distrito Federal"), 
	ES("ES", "Espirito Santo"), 
	GO("GO", "Goiás"), 
	MA("MA", "Maranhão"), 
	MT("MT", "Mato Grosso"), 
	MS("MS", "Mato Grosso do Sul"), 
	MG("MG", "Minas Gerais"), 
	PA("PA", "Pará"), 
	PB("PB", "Paraíba"), 
	PR("PA", "Paraná"), 
	PE("PE", "Pernambuco"), 
	PI("PI","Piauí"), RJ("RJ", "Rio de Janeiro"), 
	RN("RN", "Rio Grande do Norte"), 
	RS("RS","Rio Grande do Sul"), 
	RO("RO", "Rondonia"), 
	RR("RR","Roraima"), 
	SC("SC", "Santa Catarina"), 
	SP("SP","São Paulo"), 
	SE("SE", "Sergipe"), 
	TO("TO", "Tocantins");

	private String sigla;
	private String nome;

	private Estado(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
