package br.com.hackfest.controller.utilitarios;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.util.Base64;

import br.com.hackfest.model.util.SHA;


public class Util {
	public static final String NOME_SISTEMA = "Betan365";
	
	public static Object getManagedBean(String managedBean){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = (HttpSession) request.getSession();
		return session.getAttribute(managedBean);
	}
	
	public static String obterCaminhoReal(String caminho) {
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        return sc.getRealPath(caminho);
    }
	
	public static String formatarCpfCnpj(String cpfCnpj){
		if(cpfCnpj == null || cpfCnpj.isEmpty()){
			return cpfCnpj;
		}
			//CNPJ
		if(cpfCnpj.length() > 11){
			cpfCnpj = cpfCnpj.substring(0,2).concat(".").concat(cpfCnpj.substring(2,5).concat(".")).concat(cpfCnpj.substring(5,8).concat("/")).concat(cpfCnpj.substring(8,12).concat("-")).concat(cpfCnpj.substring(12,14));
			}
			//CPF
		if(cpfCnpj.length() == 11){
			cpfCnpj = cpfCnpj.substring(0,3).concat(".").concat(cpfCnpj.substring(3,6).concat(".")).concat(cpfCnpj.substring(6,9).concat("-")).concat(cpfCnpj.substring(9,11));
		}
		return cpfCnpj;
			
	}
	
	
	public static String getPastaBilhetes(){
		StringBuffer caminho = new StringBuffer();
		caminho.append("/home/ubuntu/" + NOME_SISTEMA);
//		caminho.append("C:/"+NOME_SISTEMA);
		caminho.append(File.separator);
		caminho.append("imagens_bilhetes");
		return caminho.toString();
	}
	
	public static String getPastaComprovantes(){
		StringBuffer caminho = new StringBuffer();
		caminho.append("/home/ubuntu/"+NOME_SISTEMA);
//		caminho.append("C:/" + NOME_SISTEMA);
		caminho.append(File.separator);
		caminho.append("comprovantes");
		return caminho.toString();
	}
	
	public static String chartToFileImageTemp(String base64, String nameFile) throws IOException{
		Date hoje = new Date();
    	String nomeArquivo = SHA.bytesToHex(SHA.hash256(nameFile + hoje.getTime())) + 
							 ".png";
    	String caminho = Util.getPastaBilhetes() + File.separator + nomeArquivo;
		if(base64.split(",").length > 1){
	        String encoded = base64.split(",")[1];
	        byte[] decoded = Base64.decode(encoded);

            RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
            ImageIO.write(renderedImage, "png", new File(caminho)); // use a proper path & file name here.
	    }else{
	    	caminho = "";
	    }
		return caminho;
	}
}
