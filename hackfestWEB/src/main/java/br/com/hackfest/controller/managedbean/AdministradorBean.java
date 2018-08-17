package br.com.hackfest.controller.managedbean;

import java.io.Serializable;
import java.net.UnknownHostException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.hackfest.controller.utilitarios.FacesUtil;
import br.com.hackfest.model.entities.EntidadeGenerica;
import br.com.hackfest.model.entities.Usuario;

@SessionScoped
@ManagedBean
public class AdministradorBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6101873444327970251L;
	
	private String url;
	private String mensagemGeralInformacao;
	private Usuario usuario;
	private boolean logged;
	
	private EntidadeGenerica entidadeGenerica;
	
	public AdministradorBean(){
		this.usuario = new Usuario();
		logged = false;
	}
	
	public String getMensagemGeralInformacao() {
		return mensagemGeralInformacao;
	}

	public void setMensagemGeralInformacao(String mensagemGeralInformacao) {
		this.mensagemGeralInformacao = mensagemGeralInformacao;
	}
	
	public void abrirDialogMensagemGeralInformacao(String mensagemGeralInformacao){
		this.mensagemGeralInformacao = mensagemGeralInformacao;
		FacesUtil.updateRequestContext("pgDialogGeralMensagemInformacao");
		FacesUtil.executeRequestContext("PF('dialogMensagemGeralInformacao').show()");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogged() {
		if(usuario!=null && usuario.getId()!=null && !usuario.getForcarTrocaSenha()){
			logged = true;
		}
		return logged;
	}
	public void redirecionar(){
		FacesUtil.redirectTo("animal.jsf");
	}
	
	
	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public String getUrl() {
		if(url==null){
			url = "/pages/index.xhtml";
		}
		return url;
	}
	
	
	public String paginaComObject(String url, Object T){
		this.url = "pages" + url;
		return "/pages/common/index.xtml";
		
	}

	public String inputUrl(String url) {
		this.url = "/pages/" + url;
		return "/pages/common/index.xhtml";
	}

	public String capturarIpLocal() throws UnknownHostException{
		
		return FacesUtil.obterIp();
	}

	public EntidadeGenerica getEntidadeGenerica() {
		return entidadeGenerica;
	}

	public void setEntidadeGenerica(EntidadeGenerica entidadeGenerica) {
		this.entidadeGenerica = entidadeGenerica;
	}
	
}
