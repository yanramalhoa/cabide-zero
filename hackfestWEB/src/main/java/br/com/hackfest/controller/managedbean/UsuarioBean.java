package br.com.hackfest.controller.managedbean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.controller.utilitarios.FacesUtil;
import br.com.hackfest.controller.utilitarios.NavigationUtil;
import br.com.hackfest.model.entities.Usuario;
import br.com.hackfest.model.interfaces.business.GenericoBusiness;
import br.com.hackfest.model.interfaces.business.UsuarioBusiness;
import br.com.hackfest.model.util.SHA;



@ViewScoped
@ManagedBean
public class UsuarioBean extends MBeanGenerico<Usuario>{


	private static final long serialVersionUID = 3520576694877697507L;
	
	@EJB
	private UsuarioBusiness usuarioBusiness;
	
	private String mensagemFormNovo;
	
	private StreamedContent file;
	
	
	private boolean formNovo;
	private boolean statusRegister;
	private boolean abilitaComissoes;
	
	private BigDecimal novoSaldo;
	private BigDecimal creditoASerColocado;
	
	private String contaUsuario;
	
	@Override
	public GenericoBusiness<Usuario> getGenericBusiness() {
		// TODO Auto-generated method stub
		return usuarioBusiness;
	}
	
	@PostConstruct
	public void init() {
		if(getAdministradorBean().getMensagemGeralInformacao() != null && getAdministradorBean().getMensagemGeralInformacao().equals("cadastro")) {
			getAdministradorBean().setMensagemGeralInformacao(null);
			FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Cadastro confirmado com sucesso", ""));
		}
	}
	
	public UsuarioBean() {
		// TODO Auto-generated constructor stub
		mensagemFormNovo = "Cadastra-se";
		contaUsuario = null;
		formNovo = true;
		statusRegister = false;
		abilitaComissoes =  false;
		novoSaldo = new BigDecimal(0.00);
		setCreditoASerColocado(new BigDecimal(0));
		 java.io.InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/app/release/app-release.apk");   
		file = new DefaultStreamedContent(stream, "apk", "onebets.apk");
	
	}
	
	@Override
	public void acaoAlterar(Usuario obj) throws NoSuchFieldException, SecurityException, InstantiationException,
			IllegalAccessException, CabideException {
		// TODO Auto-generated method stub
		statusRegister = false;
		contaUsuario = null;
		super.acaoAlterar(obj);
	}
	
	@Override
	public void salvar() throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(getAdministradorBean() != null && getAdministradorBean().getUsuario() != null && getAdministradorBean().getUsuario().getUsuarioAdministrador() != null 
				&&  getAdministradorBean().getUsuario().getUsuarioAdministrador()) {
			getObjeto().setEmailVerificado(true);
		}else {
			getObjeto().setEmailVerificado(false);
		}
		super.salvar();
		contaUsuario = null;
//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"A senha foi enviada para seu e-mail", null);
//		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	public void salvarCliente() throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(getAdministradorBean() != null && getAdministradorBean().getUsuario() != null && getAdministradorBean().getUsuario().getUsuarioAdministrador() != null 
				&&  getAdministradorBean().getUsuario().getUsuarioAdministrador()) {
			getObjeto().setEmailVerificado(true);
		}else {
			getObjeto().setEmailVerificado(false);
		}
		String senha = getObjeto().getSenha();
		
		super.salvar();
		

		
		if(getObjetoSelecionado() != null && getObjetoSelecionado().getId() != null) {
				getObjeto().setLogin(getObjetoSelecionado().getLogin());
				getObjeto().setSenha(senha);
				login();
		}
		
	}


	public void login() {
		this.getObjeto().setAtivo(true);
		this.getObjeto().setSenha(usuarioBusiness.generateHash(this.getObjeto().getSenha()));
		try {
			getAdministradorBean().setUsuario(usuarioBusiness.login(this.getObjeto()));
		} catch (CabideException e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), ""));
		}
		if (getAdministradorBean().getUsuario() != null && getAdministradorBean().getUsuario().getId() != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String ip = null;
			ip = request.getHeader("x-forwarded-for");
			if (ip == null) {
				ip = request.getHeader("X_FORWARDED_FOR");
				if (ip == null) {
					ip = request.getRemoteAddr();
				}
			}
			if (ip != null) {
				getAdministradorBean().getUsuario().setIpUltimoAcesso(ip);
			}
			getAdministradorBean().getUsuario().setUltimoAcesso(new Date(System.currentTimeMillis()));
			
			try {
				usuarioBusiness.atualizar(getAdministradorBean().getUsuario());
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getAdministradorBean().inputUrl("dashboard.xhtml");

			//NavigationUtil.toHome();
		}
	}
	
	private String generateHash(String str){
		return SHA.bytesToHex(SHA.hash256(str));
	}
	
	public void logout(){
		getAdministradorBean().setUsuario(new Usuario());
		getAdministradorBean().setLogged(false);
		NavigationUtil.toHome();
	}

	public void alterarSenha() throws Exception{
		try {
			if(this.existsUserLogged()){
				this.getObjeto().setId(this.getAdministradorBean().getUsuario().getId());
				this.getObjeto().setLogin(this.getAdministradorBean().getUsuario().getLogin());
				//this.getObjeto().setLogin(this.getAdministradorBean().getUsuario().getLogin());
				this.getObjeto().setSenhaAnterior(this.getAdministradorBean().getUsuario().getSenha());
				//this.getObjeto().setTipoUsuario(this.getAdministradorBean().getUsuario().getTipoUsuario());
			}
			
			if(usuarioBusiness.verifyPasswordModify(this.getObjeto())){
				this.getObjeto().setForcarTrocaSenha(false);
				this.setSalvarSemPermissao(true);
				usuarioBusiness.salvar(this.getObjeto());
				this.setSalvarSemPermissao(false);
				if(this.existsUserLogged()){
					this.getAdministradorBean().setUsuario(this.getObjeto());
				}
				NavigationUtil.toPagesHome();
			}
		} catch (CabideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesUtil.registrarErro(e);
		}
	}
	
	public void enviarEmailRecuperarSenha() throws CabideException{
		if(getObjeto().getEmail() != null && !getObjeto().getEmail().isEmpty()){
			boolean testEmail = emailCadastrado(getObjeto().getEmail());
			if(testEmail){
				try {
					usuarioBusiness.emailRecuperarSenha(getObjeto());
					FacesContext.getCurrentInstance().addMessage(
							"submitEnviarEmail" ,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Verifique seu e-mail", ""));
					
				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else {
				FacesContext.getCurrentInstance().addMessage(
							"submitEnviarEmail" ,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"E-mail "+getObjeto().getEmail()+" não  foi encontrado", ""));
			}
		}
	}
	
	public void verificarSenhaAtual() {
		if(getAdministradorBean().getUsuario()  != null) {
			getObjeto().setSenhaAtual(this.generateHash(getObjeto().getSenhaAtual()));
			if(!getObjeto().getSenhaAtual().equals(getAdministradorBean().getUsuario().getSenha())){
				RequestContext contextRequest = RequestContext.getCurrentInstance();
				contextRequest.update("formUsuarioDialog:senhaAtual");
				getObjeto().setSenhaAtual(null);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Senha atual incorreta", null);
				FacesContext.getCurrentInstance().addMessage("senhaAtual", msg);
			}
		}
	}
	
	public void enviarEmailUsuario() throws EmailException{
		  try{	
			  if(getObjetoSelecionado() != null && getObjetoSelecionado().getId() != null && getObjetoSelecionado().getEmail() != null
				&& !getObjetoSelecionado().getEmail().isEmpty() && !getObjetoSelecionado().getEmailVerificado()) {
				
				  HtmlEmail email = new HtmlEmail();
				email.setDebug(true);
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(465);
				email.setSslSmtpPort("465");
//				email.setSSLOnConnect(true);
				email.addTo(getObjeto().getEmail());
				email.setAuthenticator(new DefaultAuthenticator("sistemaagroa@gmail.com", "mortalstrike05"));
				email.setFrom(getObjetoSelecionado().getEmail());
				email.setSubject("Confirme seu cadastro");
				email.setHtmlMsg(
						"<html> <head> <title>E-mail</title> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'> <style> .corpo{ background: #ebebeb; } table{ padding: 40px; } </style> </head> <body> <div class='corpo'> <center> <table bgcolor='white'> <tbody> <tr><td><h1><font>Falta Pouco</font></h1></td></tr> <tr><td><font>Agora s&oacute; precisamos que voc&ecirc; confirme seu cadastro clicando no bot&atilde;o abaixo</font> <br> <br></td></tr> <tr><td ><a style='background:#41aedd; padding: 10px; text-decoration: none;' href='http://geradormemes.com/media/created/ow9dzo.jpg' target='_blank'><font color='white'>Confirmar cadastro!</font></a> <br> <br></td></tr> <tr><td><font>Em caso de d&uacute;vida, converse com a gente.</font><br> <br></td></tr> <tr><td><font>&#8212; &nbsp; Seu amigão!</font></td></tr> </tbody> </table> </center> </div> </body></html>"					
					);
				
				email.send();
				mensagemFormNovo = "Verifique seu e-mail" ;
				formNovo = false;
			  }	
				
		} catch (EmailException e) {
			e.printStackTrace();
		 }
		}
	
	@Override
	public void acaoCadastrar() throws InstantiationException,
			IllegalAccessException {
		// TODO Auto-generated method stub
		super.acaoCadastrar();
		this.setStatusRegister(true);
	}
	
	
	
	public void resetarSenhaPadrao(Usuario usuario) {
		try {
			if (usuario.getId() != null) {
				String senha = "mudar123";
				usuario.setSenha(SHA.bytesToHex(SHA.hash256(senha)));
				usuarioBusiness.salvar(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"A senha do usuário foi alterada, a senha agora é mudar123", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	public void desabilitaUsuario() {

		try {
			if (getObjetoSelecionado().getId() != null) {
				getObjetoSelecionado().setAtivo(false);
				usuarioBusiness.salvar(getObjetoSelecionado());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário foi excluído", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				FacesUtil.executeRequestContext("startCountdown();");
			}
			setObjetoSelecionado(new Usuario()); 

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void validaIdade() {
		try {
			if (super.getObjeto().getDataNascimento() != null) {
				Calendar dataNascimento = Calendar.getInstance();
				dataNascimento.setTime(super.getObjeto()
						.getDataNascimento());
				Calendar dataAtual = Calendar.getInstance();

				Integer diferencaMes = dataAtual.get(Calendar.MONTH)
						- dataNascimento.get(Calendar.MONTH);
				Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH)
						- dataNascimento.get(Calendar.DAY_OF_MONTH);
				Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento
						.get(Calendar.YEAR));

				if (diferencaMes < 0 || (diferencaMes == 0 && diferencaDia < 0)) {
					idade--;
				}
				if (idade < 18) {
					super.getObjeto().setDataNascimento(null);
					FacesContext
					.getCurrentInstance()
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"O usuário deve ser maior de 18 anos ",
									""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void verificaLogin() {
		if(getObjeto() != null && getObjeto().getLogin() != null) {
			Usuario u = new Usuario();
			u.setLogin(getObjeto().getLogin().toLowerCase());
			try {
				u = usuarioBusiness.buscarPorEntidade(u);
				if(u != null && u.getId() != null) {
					getObjeto().setLogin(null);
					FacesContext
					.getCurrentInstance()
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"O login "+u.getLogin()+" não está disponível",
									""));
				}
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void verificaEmail() {
		if(getObjeto() != null && getObjeto().getEmail() != null) {
			Usuario u = new Usuario();
			u.setEmail(getObjeto().getEmail().toLowerCase());
			try {
				u = usuarioBusiness.buscarPorEntidade(u);
				if(u != null && u.getId() != null) {
					getObjeto().setEmail(null);
					FacesContext
					.getCurrentInstance()
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"O e-mail "+u.getEmail()+" não está disponível",
									""));
				}
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void verificaCpf() {
		if(getObjeto() != null && getObjeto().getCpf() != null) {
			Usuario u = new Usuario();
			u.setCpf(getObjeto().getCpf());
			u.setCpf(u.getCpf().replaceAll("\\.", "").replaceAll("\\/", "").replace("-", ""));	
			try {
				u = usuarioBusiness.buscarPorEntidade(u);
				if(u != null && u.getId() != null) {
					getObjeto().setCpf(null);
					FacesContext
					.getCurrentInstance()
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"O CPF "+u.getCpf()+" já está em uso",
									""));
				}
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void atualizarSenha() {
		if(getObjeto().getSenhaAtual()  != null) {
			String novaSenha = this.generateHash(getObjeto().getSenha());
			setObjetoSelecionado(getAdministradorBean().getUsuario());
			getObjetoSelecionado().setSenha(novaSenha);
			try {
				usuarioBusiness.atualizar(getObjetoSelecionado());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"A senha foi alterada com sucesso", null);
				FacesContext.getCurrentInstance().addMessage("senhaAtual", msg);
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	
	public boolean dataPago(Usuario usuario){
		if(usuario.getCodigoAnterior() == null){
			return false;
		}
		return true;
	}
	
	private boolean existsUserLogged(){
		if(this.getAdministradorBean().getUsuario()!=null && this.getAdministradorBean().getUsuario().getId()!=null){
			return true;
		}
		return false;
	}
	
	public boolean emailCadastrado(String email) throws CabideException{
		if(email != null && !email.isEmpty()){
			email = usuarioBusiness.buscarEmail(email);
			if(email != null){
				return true;
			}
		}
		return false;
	}
	
	public boolean isStatusRegister() {
		return statusRegister;
	}

	public void setStatusRegister(boolean statusRegister) {
		this.statusRegister = statusRegister;
	}

	public boolean isAbilitaComissoes() {
		return abilitaComissoes;
	}

	public void setAbilitaComissoes(boolean abilitaComissoes) {
		this.abilitaComissoes = abilitaComissoes;
	}

	public StreamedContent getFile() {
		return file;
	}

	public String getMensagemFormNovo() {
		return mensagemFormNovo;
	}

	public void setMensagemFormNovo(String mensagemFormNovo) {
		this.mensagemFormNovo = mensagemFormNovo;
	}

	public BigDecimal getNovoSaldo() {
		return novoSaldo;
	}
	
	public String getTotalNovoSaldo() {
		return new DecimalFormat("#,###,##0.00").format(novoSaldo);
	}


	public void setNovoSaldo(BigDecimal novoSaldo) {
		this.novoSaldo = novoSaldo;
	}

	public BigDecimal getCreditoASerColocado() {
		return creditoASerColocado;
	}

	public void setCreditoASerColocado(BigDecimal creditoASerColocado) {
		this.creditoASerColocado = creditoASerColocado;
	}

	public String getContaUsuario() {
		return contaUsuario;
	}

	public void setContaUsuario(String contaUsuario) {
		this.contaUsuario = contaUsuario;
	}
	
}