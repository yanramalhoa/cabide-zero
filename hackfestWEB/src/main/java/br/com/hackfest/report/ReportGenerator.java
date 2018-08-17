package br.com.hackfest.report;


import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.hackfest.controller.utilitarios.FacesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class ReportGenerator {


	public static void print(GenericReport relatorio)
			throws Exception {
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorio.getCaminhoRelatorioCompilado(),
		relatorio.getParametros(), getConnection());
		
		JasperExportManager
				.exportReportToPdfStream(impressoraJasper,getOut(relatorio));
		
		FacesUtil.obterFacesContext().responseComplete();
		FacesUtil.obterFacesContext().renderResponse();
	}

	public static void print(GenericReport relatorio, JRBeanCollectionDataSource dataSource)
			throws Exception {

		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorio.getCaminhoRelatorioCompilado(),
		relatorio.getParametros(), dataSource);
		
		JasperExportManager
				.exportReportToPdfStream(impressoraJasper,getOut(relatorio));
		
		
		FacesUtil.obterFacesContext().responseComplete();
		FacesUtil.obterFacesContext().renderResponse();
	}
	
	private static OutputStream getOut(GenericReport relatorio) throws IOException{
		HttpServletResponse response = FacesUtil.obterResponse();
		response.setContentType("application/pdf");

		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setHeader(
				"Content-Disposition",
				"attachment;filename=\""
						+ relatorio
								.getNomeDoRelatorio()
								.toUpperCase()
								.concat("_")
								.concat(new SimpleDateFormat(
										"dd_MM_yyyy_hh_mm_ss")
										.format(new Date())) + ".pdf");
		OutputStream out = response.getOutputStream();		
		return out;
	}
	
	public static Connection getConnection() throws NamingException, SQLException{
		InitialContext cxt = new InitialContext();
		Connection conexao = ((DataSource) cxt.lookup("java:jboss/datasources/hackfestDS")).getConnection();
		return conexao; 
	}
	
}