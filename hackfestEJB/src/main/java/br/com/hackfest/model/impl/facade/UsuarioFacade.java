package br.com.hackfest.model.impl.facade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Usuario;
import br.com.hackfest.model.interfaces.business.UsuarioBusiness;
import br.com.hackfest.model.interfaces.repository.UsuarioRepository;
import br.com.hackfest.model.util.SHA;
import br.com.hackfest.view.Erro;

@Stateless
@LocalBean
public class UsuarioFacade implements UsuarioBusiness{

	@EJB
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario salvar(Usuario t) throws CabideException, EmailException {
		// TODO Auto-generated method stub
		if(t != null) {
			if (t.getCpf() !=null && !t.getCpf().isEmpty()) {
				t.setCpf(t.getCpf().replaceAll("\\.", "").replaceAll("\\/", "").replace("-", ""));	
			}
			if(t.getId()==null){
				t.setLogin(t.getLogin().toLowerCase());
				t.setNumeroVitorias(0);
				t.setCotacaoTotalVitorias(new BigDecimal(0));
				t.setForcarTrocaSenha(false);
				t.setEmailVerificado(false);
				t.setSenha(this.generateHash(t.getSenha()));
				t.setCelular(t.getCelular().replace("(", "").replace(")", "").replace(" ", "").replace("-", "").trim());
				t.setAtivo(true);
				t.setDataCadastro(new Date(System.currentTimeMillis()));
				t = usuarioRepository.criar(t);
				if(t != null && t.getId() != null ) {
					enviarEmailUsuario(t);
				}
			} else {
				t = usuarioRepository.atualizar(t);
			}
		}
		return t;
	}
	
	@Override
	public Usuario atualizar(Usuario t) throws CabideException{
		if(t != null && t.getId() != null){
			usuarioRepository.atualizar(t);
		}
		return t;
	}
	
	@Override
	public String buscarEmail(String email) throws CabideException{
		if(email != null && !email.isEmpty()){
			Usuario t  = new Usuario();
			t.setEmail(email);
		   t = usuarioRepository.buscarPorEntidade(t);
		 if(t != null){
			 return email;
		 }
	}
		return null;
	}
	
	public void enviarEmailUsuario(Usuario t) throws EmailException, CabideException{
		  try{	
			  if(t != null && t.getId() != null) {
				  String hash = getHash(t);
				  HtmlEmail email = new HtmlEmail();
//				email.setDebug(true);
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(465);
				email.setSslSmtpPort("465");
				email.setSSL(true);
				email.addTo(t.getEmail());
				email.setAuthenticator(new DefaultAuthenticator("betan365desportes@gmail.com", "betan0506"));
				email.setFrom("betan365desportes@gmail.com");
				email.setSubject("Confirme seu cadastro");
				email.setHtmlMsg(
						"<html> <head> <title>E-mail</title> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'> <style> .corpo{ background: #ebebeb; } table{ padding: 40px; } </style> </head> <body> <div class='corpo'> <center> <table bgcolor='white'> <tbody> "
						+ "<tr><td><h1><font>Falta Pouco</font></h1></td></tr> <tr><td><font>Agora s&oacute; precisamos que voc&ecirc; confirme seu cadastro clicando no bot&atilde;o abaixo</font> "
						+ "<br> <br></td></tr> <tr><td ><a style='background:#41aedd; padding: 10px; text-decoration: none;' "
						+ "href='http://www.betan365.com/ServletUsuario?hash="+hash
						+"' target='_blank'><font color='white'>Confirmar cadastro!</font></a> <br> <br></td></tr> <tr><td><font>Em caso de d&uacute;vida, converse com a gente.</font><br> <br></td></tr> <tr><td><font>&#8212; &nbsp; Betan365!</font></td></tr> </tbody> </table> </center> </div> </body></html>"					
					);
				
				email.send();
				t.setHashCadastro(hash);
				usuarioRepository.atualizar(t);
			  }	
		} catch (EmailException e) {
//			e.printStackTrace();
		 }
		}
	
	@Override
	public void emailRecuperarSenha(Usuario t) throws EmailException, CabideException{
		  try{	
			  if(t != null && t.getEmail() != null  && !t.getEmail().isEmpty()) {
				 t = usuarioRepository.buscarPorEntidade(t);
				 if(t != null && t.getId() != null) { 
					 String hash = getHash(t);
					 HtmlEmail email = new HtmlEmail();
	//				email.setDebug(true);
					email.setHostName("smtp.gmail.com");
					email.setSmtpPort(465);
					email.setSslSmtpPort("465");
					email.setSSL(true);
					email.addTo(t.getEmail());
					email.setAuthenticator(new DefaultAuthenticator("betan365desportes@gmail.com", "betan0506"));
					email.setFrom("betan365desportes@gmail.com");
					email.setSubject("Recuperar senha");
					email.setHtmlMsg(
							"<html> <head> <title>E-mail</title> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'> <style> .corpo{ background: #ebebeb; } table{ padding: 40px; } </style> </head> <body> <div class='corpo'> <center> <table bgcolor='white'> <tbody> "
							+ "<tr><td><h1><font>Recuperar senha</font></h1></td></tr> <tr><td><font>Clique  no bot&atilde;o abaixo  para redirecionar a pagina e criar uma nova senha</font> "
							+ "<br> <br></td></tr> <tr><td ><a style='background:#41aedd; padding: 10px; text-decoration: none;' "
							+ "href='http://www.betan365.com/ServletUsuario?hash="+hash
							+"' target='_blank'><font color='white'>Nova senha</font></a> <br> <br></td></tr> <tr><td><font>Em caso de d&uacute;vida, converse com a gente.</font><br> <br></td></tr> <tr><td><font>&#8212; &nbsp; Betan365!</font></td></tr> </tbody> </table> </center> </div> </body></html>"					
						);
					
					email.send();
					t.setEsqueceuSenha(true);
					t.setHashCadastro(hash);
					usuarioRepository.atualizar(t);
				}
			  }	
		} catch (EmailException e) {
//			e.printStackTrace();
		 }
		}
	
	public String getHash(Usuario t) {
		String hash = null;
		hash = String.valueOf(t.getId()).concat("777");
	     MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(hash.getBytes(),0,hash.length());
			hash = ""+new BigInteger(1,m.digest()).toString(16);
		

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		return hash;
	}
	
	@Override
	public List<Usuario> buscarRanking() throws CabideException{
		Usuario t = new Usuario();
		List<Usuario> listaUsuariosRankeados = usuarioRepository.buscarRanking(t); 
		if(listaUsuariosRankeados != null && listaUsuariosRankeados.size() > 0) {
			int posicao = 0;
			for(Usuario u : listaUsuariosRankeados) {
				if (u.getNumeroVitorias() > 0) {
					u.setPosicao(posicao + 1);
					posicao++;
				}
			}
			if(posicao != 0) {
				posicao = posicao + 1;
			}
			for(Usuario u : listaUsuariosRankeados) {
				if (u.getNumeroVitorias() == 0) {
					u.setPosicao(posicao);
				}
			}
		}
		return listaUsuariosRankeados; 
	}

	

	@Override
	public void excluir(Usuario t) throws CabideException {
		usuarioRepository.excluir(t);
	}

	@Override
	public List<Usuario> buscarTodos() throws CabideException {
		// TODO Auto-generated method stub
		return usuarioRepository.buscarTodos();
	}

	@Override
	public Usuario buscarPorId(Long id) throws CabideException {
		// TODO Auto-generated method stub
		return usuarioRepository.buscarPorID(id);
	}

	@Override
	public Usuario login(Usuario usuario) throws Exception {
		// TODO Auto-generated method stub
		usuario.setLogin(usuario.getLogin().toLowerCase());
		return usuarioRepository.login(usuario);
	}

	@Override
	public Usuario buscarPorEntidade(Usuario entidade) throws CabideException {
		// TODO Auto-generated method stub
		return usuarioRepository.buscarPorEntidade(entidade);
	}

	@Override
	public Number contaResultados(Usuario entidade) throws CabideException {
		// TODO Auto-generated method stub
		return usuarioRepository.contaResultados(entidade);
	}
	
	public List<Usuario> buscarPorExemplo(Usuario usuario) throws CabideException{
		return usuarioRepository.buscarPorExemplo(usuario);
	}

	public boolean verifyPasswordModify(Usuario usuario) throws Exception{
		this.requiredField(usuario);
		if(usuario.getSenhaAnterior()==null || usuario.getSenhaAnterior().isEmpty()){
			throw new CabideException(Erro.NULL_USER_PASSWORD_OLD);
		}
		
		String senhaNova = this.generateHash(usuario.getSenha());
		Usuario usrFind = usuario;
		usrFind.setSenha(usuario.getSenhaAnterior());
		
		Usuario usr = usuarioRepository.login(usrFind);
		usuario.setSenha(senhaNova); 
		if(usr==null || usr.getId()==null){
			throw new CabideException(Erro.NULL_USER_FIND);
		}else if(!usr.getSenha().equals(usuario.getSenhaAnterior())){
			throw new CabideException(Erro.PASSWORD_OLD_NOT_EQUALS_PASSWORD_NEW);
		}
		
		return true;
	}
	
	public String generateHash(String str){
		return SHA.bytesToHex(SHA.hash256(str));
	}

	private void requiredField(Usuario usuario) throws CabideException{
		if(usuario==null){
			throw new CabideException(Erro.NULL_POINTER_EXCEPTION);
		}else if(usuario.getLogin()==null || usuario.getLogin().isEmpty()){
			throw new CabideException(Erro.NULL_USER_LOGIN);
		}else if(usuario.getSenha()==null || usuario.getSenha().isEmpty()){
			throw new CabideException(Erro.NULL_USER_PASSWORD);
		}
	}

	@Override
	public List<Usuario> loadPaginated(Usuario entidade, int inicio, int tamanho, String campoOrdenacao,
			String tipoOrdenacao, Map<String, Object> parametros, StringBuilder where) throws CabideException {
		// TODO Auto-generated method stub
		return usuarioRepository.loadPaginated(entidade, inicio, tamanho, campoOrdenacao, tipoOrdenacao, parametros, where);
	}

}
