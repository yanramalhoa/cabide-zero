package br.com.hackfest.view;

public enum Mensagem implements MensagemGenerica {

	//Comum
	NENHUM_REGISTRO_ENCONTRADO("msg.comum.registroNaoEncontrado"),
	ATUALIZAR_SUCESSO("msg.comum.alterar.sucesso"),
	SALVAR_SUCESSO("msg.comum.salvar.sucesso"),
	EXLCUIR_SUCESSO("msg.comum.excluir.sucesso"),
	REGISTRO_JA_EXISTE("msg.comum.registroJaExiste"),
	USUARIO_SENHA_NAO_EXISTE("msg.comum.usuario.senha.nao.existe");

	
	private String codigo;

	private Mensagem(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getCodigo() {
		return this.codigo;
	}
}