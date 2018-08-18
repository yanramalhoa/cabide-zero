package br.com.hackfest.pojo;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

public class ChartPojo {
  
  private BarChartModel barChartModel;
  private HorizontalBarChartModel horizontalBarChartModel;
  
  public BarChartModel getBarChartModel() {
	return barChartModel;
}

public void setBarChartModel(BarChartModel barChartModel) {
	this.barChartModel = barChartModel;
}

public HorizontalBarChartModel getHorizontalBarChartModel() {
	return horizontalBarChartModel;
}

public void setHorizontalBarChartModel(HorizontalBarChartModel horizontalBarChartModel) {
	this.horizontalBarChartModel = horizontalBarChartModel;
}

public ChartPojo() {
	if(barChartModel == null) {
		barChartModel = new BarChartModel();
	}
	
	if(horizontalBarChartModel == null) {
		horizontalBarChartModel = new HorizontalBarChartModel();
	}
  }

public ChartPojo CriarGrafico(String tipoGrafico, ChartSeries info1, ChartSeries info2) {		
	ChartPojo model = new ChartPojo();
	
	if(tipoGrafico.equals("BARRAS")){
		model.getBarChartModel().addSeries(info1);
		if(info2 != null){
			model.getBarChartModel().addSeries(info2);
		}
	}
	
	return model;
}
}


