package br.com.hackfest.controller.utilitarios;



public class NavigationUtil {
	public static final String TO_HOME = "/cabide-zero/login";
	public static final String TO_PAGES_HOME = "/cabide-zero/pages/index";
	public static final String TO_LOGIN = "/cabide-zero/login.jsf";
	public static final String TO_PAGES_PRIMEIRO_ACESSO = "/cabide-zero/pages/primeiroAcesso.jsf";
	public static final String TO_ALTERAR_SENHA = "/cabide-zero/alterarSenha.jsf";

	private NavigationUtil() { 
		
	}
	
	public static void toHome() {
		FacesUtil.redirectTo(TO_HOME);
	}

	public static void toPagesHome() {
		FacesUtil.redirectTo(TO_PAGES_HOME);
	}
	
	public static void toPrimeiroAcesso() {
		FacesUtil.redirectTo(TO_PAGES_PRIMEIRO_ACESSO);
	}
	
	public static void toLogin() {
		FacesUtil.redirectTo(getToLogin());
	}

	public static String getToLogin() {
		return TO_LOGIN;
	}

	
	


}
