package br.com.hackfest.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.hackfest.controller.managedbean.AdministradorBean;
import br.com.hackfest.model.entities.Usuario;
import br.com.hackfest.model.interfaces.business.UsuarioBusiness;

/**
 * Servlet implementation class ServletImage
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioBusiness usuarioBusiness;
	
	@ManagedProperty("#{administradorBean}")
	private AdministradorBean administradorBean;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		
		String hash = request.getParameter("hash");
		String tipoFoto = request.getParameter("tipoFoto");
		
		if(hash==null && tipoFoto.equals("")){
			return;
		}
		Usuario u = new Usuario();
		u.setHashCadastro(hash);
		try {
			u = usuarioBusiness.buscarPorEntidade(u);
			if(u != null && u.getId() != null) {
				if(u.getEsqueceuSenha() != null &&  u.getEsqueceuSenha()) {
					 response.sendRedirect("/novasenha.jsf");
				}else {
					u.setEmailVerificado(true);
					usuarioBusiness.atualizar(u);
					response.sendRedirect("/login.jsf");
//					response.set
				
				}
			}
		
			
		    
		   
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public AdministradorBean getAdministradorBean() {
		return administradorBean;
	}

	public void setAdministradorBean(AdministradorBean administradorBean) {
		this.administradorBean = administradorBean;
	}

}
