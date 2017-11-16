package com.haroon.chart;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class PieChart {
	
	private static javafx.scene.chart.PieChart pieChart;
	private static Scene scene;
	
	static {
		pieChart = new javafx.scene.chart.PieChart();
		scene = new Scene(pieChart);
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static void init() {
		pieChart.getData().clear();
	}
	
	public static void addCategory(String name) {
		Platform.runLater(()->{
			pieChart.getData().add(new javafx.scene.chart.PieChart.Data(name, 0));
		});
	}
	
	public static void addData(String name) {
		addData(name, 1);
	}
	public static void addData(String name, int value) {
		if (value == 0) {
			return;
		}
		for (javafx.scene.chart.PieChart.Data d : pieChart.getData()) {
			if (d.getName().equals(name)) {
				d.setPieValue(d.getPieValue() + value);
				return;
			}
		}
	}
	
}
