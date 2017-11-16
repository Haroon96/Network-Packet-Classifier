package com.haroon.container;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Series {
	public Series() {
		this.lineChartSeries = new XYChart.Series<>();
		this.areaChartSeries = new XYChart.Series<>();
	}
	
	public void setName(String name) {
		this.lineChartSeries.setName(name);
		this.areaChartSeries.setName(name);
	}
	
	public String getName() {
		return lineChartSeries.getName();
	}
	
	public XYChart.Series<NumberAxis, NumberAxis> lineChartSeries;
	public XYChart.Series<NumberAxis, NumberAxis> areaChartSeries;
	
}
