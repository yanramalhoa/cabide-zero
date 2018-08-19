package br.com.hackfest.model.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/**
 * Classe para controle das requisições http/https
 * 
 * @author Rodrigo Aurélio
 * @since 2014-12
 * */

public class HttpUtil {


	//private static final String PASSWD = "@dmin123";
	private static final String PASSWD = "JK5St0r@";

	private HttpsURLConnection connection;
	
	public BufferedReader httpsResponse(String url) throws Exception{

		url = url.replace("+", "%20");
		BufferedReader br = null;
		URL httpsUrl = new URL(url);
		connection = (HttpsURLConnection) httpsUrl.openConnection();
		connection.setSSLSocketFactory(configureClient());
		connection.setHostnameVerifier(new HostnameVerifier() {
			
			@Override
			public boolean verify(String hostname, SSLSession session) {
				if (hostname.equals(Host.getCommonServiceHost())) {
					return true;
				}
				return false;
			}
		});
		
		br = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		
		//connection.disconnect();
		
		return br;
	}
	
	public BufferedReader httpsPostPut(String url, String data, String method) throws Exception{
		
		String [] methods = {"POST", "PUT", "DELETE"};
		data = data.replace("+", "%20");
		if (method == null || !Arrays.asList(methods).contains(method))
			throw new Exception();
		
		BufferedReader br = null;
		URL httpsUrl = new URL(url);	
		connection = (HttpsURLConnection) httpsUrl.openConnection();
		connection.setSSLSocketFactory(configureClient());
		connection.setHostnameVerifier(new HostnameVerifier() {
			
			@Override
			public boolean verify(String hostname, SSLSession session) {
				if (hostname.equals(Host.getCommonServiceHost())) {
					return true;
				}
				return false;
			}
		});
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connection.setRequestProperty("charset", "UTF-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
//		DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
//		//stream.writeBytes(URLEncoder.encode(data,"UTF-8"));
//		stream.writeBytes(data);
//		stream.flush();
//		stream.close();
		
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
		writer.write(data);
		writer.close();
		wr.close();		
 		
		InputStream is = connection.getInputStream();
		br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		return br;
	}
	
	
	
	public void disconnect(){
		
		if(connection != null)
			connection.disconnect();
	}
	
	private SSLSocketFactory configureClient() throws Exception{

		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = new FileInputStream(new File(Path.getDigitalCertificate()));
		keyStore.load(is, PASSWD.toCharArray());
		is.close();
		SSLContext sslContext = SSLContext.getInstance("TLS");
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(keyStore);
		sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
		return sslContext.getSocketFactory();
	}
	
}
