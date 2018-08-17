package br.com.hackfest.model.util;

public class Host {

//	private static final String HOST = "localhost";
	private static final String HOST = "191.252.191.24";
	
	public static String getUrlThread(){
		return "http://" + HOST + ":8080/tradebets/thread/";
	}
	
	public static String getCommonServiceHost(){
		return HOST;
	}
}
