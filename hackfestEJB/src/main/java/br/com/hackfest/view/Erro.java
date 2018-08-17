package br.com.hackfest.view;

public enum Erro implements MensagemGenerica {
	
	ERRO_SALVAR("erro.salvar"),
	ERRO_ATUALIZAR("erro.alterar"),
	ERRO_EXCLUIR("erro.excluir"),
	REGISTRO_DUPLICADO("erro.registro.duplicado"),
	RESTRICAO_REFERENCIAL("erro.restricao.referencial"),
	PROPRIEDADE_INDEFINIDA("erro.propriedade.indefinida"),
	BUSCAR_REGISTRO("erro.buscar.registro"),
	INESPERADO("erro.inesperado"),
	ACESSO_ILEGAL("erro.acesso.ilegal"),
	ARGUMENTO_ILEGAL("erro.argumento.ilegal"),
	INSTANCIACAO("erro.instanciacao"),
	HTTP_400("erro.http.400"),
	HTTP_500("erro.http.500"),
	HTTP_404("erro.http.404"),
	TIPO_PESSOA_INVALIDO("erro.tipo.pessoa.invalido"),
	CPFCNPJ_INVALIDO("erro.cpf.cnpj.invalido"),
	PERMISSAO_SALVAR("erro.permissao.salvar"),
	PERMISSAO_ALTERAR("erro.permissao.alterar"),
	PERMISSAO_EXCLUIR("erro.permissao.excluir"),
	PERMISSAO_PESQUISAR("erro.permissao.pesquisar"),
	NULL_USER_PASSWORD_OLD("erro.null.user.password.old"),
	NULL_USER_FIND("erro.null.user.find"),
	PASSWORD_OLD_NOT_EQUALS_PASSWORD_NEW("erro.password.old.not.equals.password.new"),
	NULL_POINTER_EXCEPTION("erro.null.pointer.exception"),
	NULL_USER_LOGIN("erro.null.user.login"),
	NULL_USER_PASSWORD("erro.null.user.password");

	private String codigo;

	private Erro(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getCodigo() {
		return this.codigo;
	}
}