package br.com.hackfest.controller.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hackfest.view.MensagemGenerica;

public class CabideException extends Exception {

	private static final long serialVersionUID = -134850192939618379L;

	private MensagemGenerica mensagem;
	private List<String> parametros;

	public CabideException(MensagemGenerica mensagem) {
		super(mensagem.getCodigo());
		this.mensagem = mensagem;
		this.parametros = new ArrayList<>();
	}
	

	public CabideException(MensagemGenerica mensagem, Exception e) {
		super(mensagem.getCodigo(), e);
		this.mensagem = mensagem;
		this.parametros = new ArrayList<>();
	}
	
	public CabideException(MensagemGenerica mensagem, CabideException e) {
		super(mensagem.getCodigo().concat(" ").concat(e.getMensagem().getCodigo()), e);
		this.mensagem = mensagem;
		this.parametros = new ArrayList<>();
	}
	
	public CabideException(MensagemGenerica mensagem, String ... parametros) {
		super(mensagem.getCodigo());
		this.mensagem = mensagem;
		this.parametros = Arrays.asList(parametros);
	}

	public CabideException(MensagemGenerica mensagem, Exception e, String ... parametros) {
		super(mensagem.getCodigo(), e);
		this.mensagem = mensagem;
		this.parametros = Arrays.asList(parametros);
	}
	
	public CabideException(MensagemGenerica mensagem, CabideException e, String ... parametros) {
		super(mensagem.getCodigo().concat(" ").concat(e.getMensagem().getCodigo()), e);
		this.mensagem = mensagem;
		this.parametros = Arrays.asList(parametros);
	}
	
	public static final String getPilhaErro(Throwable t) {
		StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        t.printStackTrace(printWriter);
        return writer.toString();
	}
	
	@Override
    public String toString() {
        StringBuilder str = new StringBuilder(CabideException.class.getSimpleName());
        str.append(" [");
        if(mensagem != null) str.append("error=").append(mensagem).append(", ");
        if(parametros != null) str.append("parameters=").append(parametros);
        str.append("]");
        return str.toString();
    }
	
	public MensagemGenerica getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemGenerica mensagem) {
		this.mensagem = mensagem;
	}

	public List<String> getParametros() {
		return parametros;
	}

	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}
}