package br.com.hackfest.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.hackfest.controller.utilitarios.Util;

/**
 * Servlet implementation class ServletImage
 */
@WebServlet("/ServletImage")
public class ServletImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		
		if(request.getParameter("id").equals("")){
			return;
		}
		Long id = Long.parseLong(request.getParameter("id"));
		String tipoFoto = request.getParameter("tipoFoto");
		
		if(id==null || tipoFoto==null || tipoFoto.equals("")){
			return;
		}
		
		String folder_file = Util.getPastaComprovantes() + File.separator
				+ id + File.separator +"comprovante."+tipoFoto;

		File foto = new File(folder_file);
		
		byte[] imageBytes = new byte[(int) foto.length()];

		try {
			inputStream = new FileInputStream(foto);
			inputStream.read(imageBytes);
			
			
			inputStream.close();
			
		    response.setContentType("image/jpeg");

		    response.setContentLength(imageBytes.length);

		    response.getOutputStream().write(imageBytes);		
		    
//		    response.sendRedirect(location);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
