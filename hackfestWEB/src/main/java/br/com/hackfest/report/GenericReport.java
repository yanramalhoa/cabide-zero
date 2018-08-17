package br.com.hackfest.report;


import java.util.HashMap;

import br.com.hackfest.controller.utilitarios.FacesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class GenericReport {

	private String caminhoRelatorioCompilado;

	private String caminhoImagemSistema;

	private String caminhoImagemCABIDE;
	
	private String caminhoImagemBanco;

	private String nomeDoRelatorio;

	private String query;

	private HashMap<String, Object> parametros;

	private JasperReport relatorio;

	private JasperPrint impressao;

	public GenericReport(String nomeDoRelatorio) {
		this.nomeDoRelatorio = nomeDoRelatorio;
		this.caminhoRelatorioCompilado = FacesUtil.obterCaminhoReal("reports/jasper/" + nomeDoRelatorio + ".jasper");
		this.caminhoImagemSistema = FacesUtil.obterCaminhoReal("/imagem/logo.png");
		this.caminhoImagemCABIDE = FacesUtil.obterCaminhoReal("/imagem/logo_nova_cabide.png");
		this.caminhoImagemBanco = FacesUtil.obterCaminhoReal("imagem/bb.jpeg");
		this.parametros = new HashMap<String, Object>();
		montarParametrosGenericosRelatorio();
	}
	
	public GenericReport(String nomeDoRelatorio, boolean caminhoCompleto) {
//		String caminho = "D:\\Desenvolvimento\\wildfly-11.0.0.Final\\standalone\\deployments\\tradebetsEAR-1.0-SNAPSHOT.ear\\tradebetsWEB.war\\";
		
		//linux
		String caminho = "/home/ubuntu/wildfly-11.0.0.Final/standalone/deployments/tradebetsEAR-1.0-SNAPSHOT.ear/tradebetsWEB.war/";
		this.nomeDoRelatorio = nomeDoRelatorio;
		this.caminhoRelatorioCompilado = caminho+"reports/jasper/" + nomeDoRelatorio + ".jasper";
		this.caminhoImagemSistema = caminho+"/imagem/logoumti.png";
		this.caminhoImagemCABIDE = caminho+"/imagem/logo_nova_pmjp.png";
		this.caminhoImagemBanco = caminho+"imagem/bb.jpeg";
		this.parametros = new HashMap<String, Object>();
		montarParametrosGenericosRelatorio();
	}

	public final void montarParametrosGenericosRelatorio() {
		parametros.put("logoSistema", caminhoImagemSistema);
		parametros.put("logoPMJP", caminhoImagemCABIDE);
	}

	public String getCaminhoRelatorioCompilado() {
		return caminhoRelatorioCompilado;
	}

	public void setCaminhoRelatorioCompilado(String caminhoRelatorioCompilado) {
		this.caminhoRelatorioCompilado = caminhoRelatorioCompilado;
	}

	public String getNomeDoRelatorio() {
		return nomeDoRelatorio;
	}

	public void setNomeDoRelatorio(String nomeDoRelatorio) {
		this.nomeDoRelatorio = nomeDoRelatorio;
	}

	public HashMap<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(HashMap<String, Object> parametros) {
		this.parametros = parametros;
		montarParametrosGenericosRelatorio();
	}

	public JasperReport getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(JasperReport relatorio) {
		this.relatorio = relatorio;
	}

	public JasperPrint getImpressao() {
		return impressao;
	}

	public void setImpressao(JasperPrint impressao) {
		this.impressao = impressao;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getCaminhoImagemSistema() {
		return caminhoImagemSistema;
	}

	public void setCaminhoImagemSistema(String caminhoImagemSistema) {
		this.caminhoImagemSistema = caminhoImagemSistema;
	}

	public String getCaminhoImagemCABIDE() {
		return caminhoImagemCABIDE;
	}

	public void setCaminhoImagemPMJP(String caminhoImagemCABIDE) {
		this.caminhoImagemCABIDE = caminhoImagemCABIDE;
	}

	public String getCaminhoImagemBanco() {
		return caminhoImagemBanco;
	}

	public void setCaminhoImagemBanco(String caminhoImagemBanco) {
		this.caminhoImagemBanco = caminhoImagemBanco;
	}
	
}
