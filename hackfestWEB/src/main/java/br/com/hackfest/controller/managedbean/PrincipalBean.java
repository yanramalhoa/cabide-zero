package br.com.hackfest.controller.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;

import br.com.hackfest.pojo.ChartPojo;

@ViewScoped
@ManagedBean
public class PrincipalBean implements Serializable {

	private static final long serialVersionUID = -4117333534500404730L;

	private ChartPojo chart;
	
	private boolean tipoBuscarPartido; 
	private boolean tipoBuscarServidor = true;
	private int     ultimaEscolha = 2; //Buscar Servidor
	
	public ChartPojo getChart() {
		return chart;
	}

	public void setChart(ChartPojo chart) {
		this.chart = chart;
	}

	@PostConstruct
	public void init() {
		chart                = new ChartPojo();
		ChartSeries partidos = new ChartSeries();		
		partidos.setLabel("Partidos");
		partidos.set("PT", 100);
		partidos.set("PA", 150);
		partidos.set("PB", 170);
		partidos.set("PC", 180);
		partidos.set("PD", 220);
		partidos.set("PT", 100);
		partidos.set("AA", 150);
		partidos.set("CB", 170);
		partidos.set("DC", 180);
		partidos.set("ED", 220);
		partidos.set("QPT", 100);
		partidos.set("WA", 150);
		partidos.set("EB", 170);
		partidos.set("1C", 180);
		partidos.set("2D", 220);
		partidos.set("CPT", 100);
		partidos.set("NA", 150);
		partidos.set("MB", 170);
		partidos.set("KC", 180);
		partidos.set("LD", 220);
		partidos.set("LPT", 100);
		partidos.set("PA", 150);
		partidos.set("OB", 170);
		partidos.set("IC", 180);
		partidos.set("UD", 220);
		partidos.set("PT", 100);
		partidos.set("UA", 150);
		partidos.set("YB", 170);
		partidos.set("RC", 180);
		partidos.set("WD", 220);
		partidos.set("PT", 100);
		partidos.set("QA", 150);
		partidos.set("QB", 170);
		partidos.set("QC", 180);
		partidos.set("QD", 220);
		
		
		ChartSeries partidos2 = new ChartSeries();		
		partidos2.setLabel("Partidos");
		partidos2.set("<strong>PT</strong>", 100);
		partidos2.set("PA", 150);
		partidos2.set("PB", 170);
		partidos2.set("PC", 180);
		partidos2.set("PD", 220);
		partidos2.set("PT", 100);
		partidos2.set("AA", 150);
		partidos2.set("CB", 170);
		partidos2.set("DC", 180);
		partidos2.set("ED", 220);
		partidos2.set("QPT", 100);
		partidos2.set("WA", 150);
		partidos2.set("EB", 170);
		partidos2.set("1C", 180);
		partidos2.set("2D", 220);
		partidos2.set("CPT", 100);
		partidos2.set("NA", 150);
		partidos2.set("MB", 170);
		partidos2.set("KC", 180);
		partidos2.set("LD", 220);
		partidos2.set("LPT", 100);
		partidos2.set("PA", 150);
		partidos2.set("OB", 170);
		partidos2.set("IC", 180);
		partidos2.set("UD", 220);
		partidos2.set("PT", 100);
		partidos2.set("UA", 150);
		partidos2.set("YB", 170);
		partidos2.set("RC", 180);
		partidos2.set("WD", 220);
		partidos2.set("PT", 100);
		partidos2.set("QA", 150);
		partidos2.set("QB", 170);
		partidos2.set("QC", 180);
		partidos2.set("QD", 220);
		
		chart = chart.CriarGrafico("BARRAS", partidos, partidos2);
		
		chart.getBarChartModel().setAnimate(true);
		
		Axis xAxis = chart.getBarChartModel().getAxis(AxisType.X);
        xAxis.setLabel("Partidos");
         
        Axis yAxis = chart.getBarChartModel().getAxis(AxisType.Y);
        yAxis.setLabel("Empregos");
	}
	
	public void alterarBusca(Integer tipoBusca) {
		if(tipoBusca != ultimaEscolha) {
			if(tipoBusca == 1) {
				tipoBuscarPartido  = true;
				tipoBuscarServidor = false;
			}else if(tipoBusca == 2) {
				tipoBuscarPartido  = false;
				tipoBuscarServidor = true;
			}
		}
		ultimaEscolha = tipoBusca;
	}

	public boolean tipoBuscarPartido() {
		return tipoBuscarPartido;
	}

	public void setTipoBuscarPartido(boolean tipoBuscarPartido) {
		this.tipoBuscarPartido = tipoBuscarPartido;
	}

	public boolean isTipoBuscarServidor() {
		return tipoBuscarServidor;
	}

	public void setTipoBuscarServidor(boolean tipoBuscarServidor) {
		this.tipoBuscarServidor = tipoBuscarServidor;
	}
	
}
