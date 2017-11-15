package com.haroon.chart;

import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChart {
	
	private static javafx.scene.chart.LineChart lineChart;
	private static NumberAxis xAxis;
	private static NumberAxis yAxis;
	private static Scene scene;
	
	static {
		xAxis = new NumberAxis(-10,  0,  10);
		yAxis = new NumberAxis(0, 0, 1);
		lineChart = new javafx.scene.chart.LineChart(xAxis, yAxis);
		scene = new Scene(lineChart);
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public void init() {
		lineChart.getData().clear();
		xAxis.setLowerBound(-10);
		xAxis.setUpperBound(0);
	}
	
	public void addSeries(XYChart.Series series) {
		lineChart.getData().add(series);
	}
	
}
