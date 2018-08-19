package br.com.hackfest.controller.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Partido;
import br.com.hackfest.model.interfaces.business.PartidoBusiness;
import br.com.hackfest.pojo.ChartPojo;

@ViewScoped
@ManagedBean
public class PrincipalBean implements Serializable {

	private static final long serialVersionUID = -4117333534500404730L;

	
	@EJB
	private PartidoBusiness partidoBusiness;
	
	
	private ChartPojo chart;
	private ChartPojo pieChart;
	
	private List<Partido> listaPartidos;
	
	private Partido partido;
	
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
		
		if(listaPartidos == null) {
			buscarPartidos();
		}
		ChartSeries empregado = new ChartSeries();
		ChartSeries doisEmpregos = new ChartSeries();		
		for(Partido p : listaPartidos) {
			
			//mudar isso
			
			p.setQuantServidoresEmpregados(1000);
			p.setQuantiServidoresDoisEmpregos(500);
			empregado.setLabel("Partidos");
			empregado.set(p.getSigla(), p.getQuantServidoresEmpregados());
			
			doisEmpregos.setLabel("Partidos");
			doisEmpregos.set(p.getSigla(), p.getQuantServidoresEmpregados());
			
		}
		
		chart = chart.CriarGrafico("BARRAS", empregado, doisEmpregos);
		
		chart.getBarChartModel().setAnimate(true);
		
		Axis xAxis = chart.getBarChartModel().getAxis(AxisType.X);
        xAxis.setLabel("Partidos");
         
        Axis yAxis = chart.getBarChartModel().getAxis(AxisType.Y);
        yAxis.setLabel("Empregos");
        
        chart.getBarChartModel().setSeriesColors("36af0e, eaa228");
        
        pieChart = new ChartPojo();
        
        pieChart = pieChart.CriarGrafico("TORTA", null, null);
        
        pieChart.getPieChartModel().set("Financeiro", 50000);
        pieChart.getPieChartModel().set("Filiados", 10000);
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
	
	public void buscarPartidos() {
		Partido p = new Partido();
		p.setAtivo(true);
		
		try {
			listaPartidos = partidoBusiness.buscarPorExemplo(p);
		} catch (CabideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public List<Partido> listPartidos()   {
		if (listaPartidos == null) {
			listaPartidos = new ArrayList<Partido>();
			try {
				listaPartidos = partidoBusiness.buscarTodos();
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listaPartidos;
	}
	
	
	public boolean isTipoBuscarPartido() {
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

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}
}
