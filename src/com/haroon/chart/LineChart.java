package com.haroon.chart;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChart {
	
	private static javafx.scene.chart.LineChart lineChart;
	private static NumberAxis xAxis;
	private static NumberAxis yAxis;
	private static Scene scene;
	
	static {
		xAxis = new NumberAxis(-100,  0,  1);
		yAxis = new NumberAxis(0, 100, 10);
		lineChart = new javafx.scene.chart.LineChart(xAxis, yAxis);
		scene = new Scene(lineChart);
		lineChart.setAnimated(false);
		lineChart.setCreateSymbols(false);
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static void init() {
		Platform.runLater(()->{
			lineChart.getData().clear();
		});
		xAxis.setLowerBound(-100);
		xAxis.setUpperBound(0);
	}
	
	public static void move() {
		xAxis.setLowerBound(xAxis.getLowerBound() + 1);
		xAxis.setUpperBound(xAxis.getUpperBound() + 1);
	}
	
	public static void addSeries(XYChart.Series series) {
		Platform.runLater(()->{
			series.getData().add(new XYChart.Data<>(0, 0));
			lineChart.getData().add(series);
		});
	}
	
}
