package br.com.hackfest.controller.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;

import br.com.hackfest.controller.exception.CabideException;
import br.com.hackfest.model.entities.Partido;
import br.com.hackfest.model.entities.Pessoa;
import br.com.hackfest.model.entities.Votacao;
import br.com.hackfest.model.interfaces.business.PartidoBusiness;
import br.com.hackfest.model.interfaces.business.PessoaBusiness;
import br.com.hackfest.model.interfaces.business.PessoaFolhaBusiness;
import br.com.hackfest.model.interfaces.business.VotacaoBusiness;
import br.com.hackfest.pojo.ChartPojo;

@ViewScoped
@ManagedBean
public class PrincipalBean implements Serializable {

	private static final long serialVersionUID = -4117333534500404730L;

	
	@EJB
	private PartidoBusiness partidoBusiness;
	
	
	@EJB
	private PessoaBusiness pessoaBusiness;
	
	
	@EJB
	private VotacaoBusiness votacaoBusiness;
	
	@EJB
	private PessoaFolhaBusiness pessoaFolhaBusiness;
	
	
	private ChartPojo chart;
	private ChartPojo pieChart;
	
	private List<Partido> listaPartidos;
	
	private Partido partido;
	private Votacao votacao;
	
	private boolean tipoBuscarPartido; 
	private boolean tipoBuscarServidor;
	
	private boolean blocoInformacao;
	private boolean blocoInformacaoServidor;
	private boolean blocoInformacaoPartido;
	
	private int mes;
	private int ano;
	
	private Pessoa servidor;
	
	private int     ultimaEscolha = 2; //Buscar Servidor
	
	public PrincipalBean() {
		// TODO Auto-generated constructor stub
		tipoBuscarServidor = true;
		blocoInformacao = false;
		blocoInformacaoPartido = false;
		blocoInformacaoServidor = false;
		
		votacao = new Votacao();
		servidor = new Pessoa();
	}
	
	
	
	public ChartPojo getChart() {
		return chart;
	}

	public void setChart(ChartPojo chart) {
		this.chart = chart;
	}

	@PostConstruct
	public void init() throws CabideException {
		chart                = new ChartPojo();
		
		if(listaPartidos == null) {
			buscarPartidos();
		}
		ChartSeries empregado = new ChartSeries();
		ChartSeries doisEmpregos = new ChartSeries();		
		for(Partido p : listaPartidos) {
			
			empregado.setLabel("Filiados em Orgãos Públicos");
			empregado.set(p.getSigla(), p.getQtd_filiados_regular());
			
			doisEmpregos.setLabel("Filiados Irregulares em Orgãos Públicos");
			doisEmpregos.set(p.getSigla(), p.getQtd_filiados_irregular());
			
		}
		
		chart = chart.CriarGrafico("BARRAS", empregado, doisEmpregos);
		
		chart.getBarChartModel().setAnimate(true);
		
		Axis xAxis = chart.getBarChartModel().getAxis(AxisType.X);
        xAxis.setLabel("Partidos");
         
        Axis yAxis = chart.getBarChartModel().getAxis(AxisType.Y);
        yAxis.setLabel("Empregos");
        
        chart.getBarChartModel().setSeriesColors("36af0e, eaa228");
        chart.getBarChartModel().setLegendPosition("s");
        chart.getBarChartModel().setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        pieChart = new ChartPojo();
        
        pieChart = pieChart.CriarGrafico("TORTA", null, null);
        
        pieChart.getPieChartModel().set("Financeiro", 50000);
        pieChart.getPieChartModel().set("Filiados", 10000);
        
        
        
        pessoaFolhaBusiness.listaPessoasComVinculo(1, 2017);
        
        
	}
	
	public void alterarBusca(Integer tipoBusca) {
		if(tipoBusca != ultimaEscolha) {
			if(tipoBusca == 1) {
				tipoBuscarPartido  = true;
				tipoBuscarServidor = false;
				blocoInformacaoPartido = false;
				blocoInformacaoServidor = false;
			}else if(tipoBusca == 2) {
				tipoBuscarPartido  = false;
				tipoBuscarServidor = true;
				blocoInformacaoPartido = false;
				blocoInformacaoServidor = false;
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
	
	
	public void buscarPartido() {
		if(partido.getSigla() != null	) {
			try {
				partido = partidoBusiness.buscarPorEntidade(partido);
				if(partido != null) {
					blocoInformacao = true;
					blocoInformacaoPartido = true;
					blocoInformacaoServidor = false;
				}else {
					
				}
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void buscarServidor() {
		if(servidor != null && servidor.getCpf() != null	) {
			try {
				servidor = pessoaBusiness.buscarPorEntidade(servidor);
				
				if(servidor != null) {
					blocoInformacaoPartido = false;
					blocoInformacaoServidor = true;
				}else {
					FacesContext.getCurrentInstance().addMessage(
 							"",
 							new FacesMessage(FacesMessage.SEVERITY_WARN,
 									"Não foi encontrado nenhum servidor com esse CPF", ""));
				}
				
				
			} catch (CabideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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



	public Votacao getVotacao() {
		return votacao;
	}



	public void setVotacao(Votacao votacao) {
		this.votacao = votacao;
	}



	public boolean isBlocoInformacao() {
		return blocoInformacao;
	}



	public void setBlocoInformacao(boolean blocoInformacao) {
		this.blocoInformacao = blocoInformacao;
	}



	public boolean isBlocoInformacaoServidor() {
		return blocoInformacaoServidor;
	}



	public void setBlocoInformacaoServidor(boolean blocoInformacaoServidor) {
		this.blocoInformacaoServidor = blocoInformacaoServidor;
	}



	public boolean isBlocoInformacaoPartido() {
		return blocoInformacaoPartido;
	}



	public void setBlocoInformacaoPartido(boolean blocoInformacaoPartido) {
		this.blocoInformacaoPartido = blocoInformacaoPartido;
	}



	public int getMes() {
		return mes;
	}



	public void setMes(int mes) {
		this.mes = mes;
	}



	public int getAno() {
		return ano;
	}



	public void setAno(int ano) {
		this.ano = ano;
	}



	public Pessoa getServidor() {
		return servidor;
	}



	public void setServidor(Pessoa servidor) {
		this.servidor = servidor;
	}
}
