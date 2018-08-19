package br.com.hackfest.model.util;

import java.io.File;

public class Path {

	public static String getPathServer(){
		StringBuffer caminho = new StringBuffer();
		return caminho.append(System.getProperty("jboss.server.base.dir")).toString();
	}
	
	public static String getDigitalCertificate(){
		StringBuffer caminho = new StringBuffer();
		caminho.append(getPathServer());
		
		caminho.append(File.separator);
		caminho.append("configuration");
		caminho.append(File.separator);
		caminho.append("certificado");
		caminho.append(File.separator);
		
		
		return caminho.toString();
	}
	
}
