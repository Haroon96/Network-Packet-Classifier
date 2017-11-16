package com.haroon.chart;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class AreaChart {
	
	private static javafx.scene.chart.AreaChart areaChart;
	private static NumberAxis xAxis;
	private static NumberAxis yAxis;
	private static Scene scene;
	
	static {
		xAxis = new NumberAxis(-10,  0,  1);
		yAxis = new NumberAxis(0, 100, 10);
		areaChart = new javafx.scene.chart.AreaChart<>(xAxis, yAxis);
		scene = new Scene(areaChart);
		areaChart.setAnimated(false);
		areaChart.setCreateSymbols(false);
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static void init() {
		Platform.runLater(()->{
			areaChart.getData().clear();
		});
		xAxis.setLowerBound(-10);
		xAxis.setUpperBound(0);
	}
	
	public static void move() {
		xAxis.setLowerBound(xAxis.getLowerBound() + 1);
		xAxis.setUpperBound(xAxis.getUpperBound() + 1);
	}
	
	public static void addSeries(XYChart.Series series) {
		Platform.runLater(()->{
			series.getData().add(new XYChart.Data<>(0, 0));
			areaChart.getData().add(series);
		});
	}
	
}
