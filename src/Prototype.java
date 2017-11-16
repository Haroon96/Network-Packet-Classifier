import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Prototype {
	
	private static AreaChart<Number, Number> lc;
	private static XYChart.Series<Number, Number> series, series2;
	private static Random random = new Random();
	private static int i = 0;
	
	public static void main(String[] args) throws Exception {
		
		JFrame jframe = new JFrame();
		jframe.setSize(new Dimension(500,500));
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JFXPanel jfxpanel = new JFXPanel();
		
		Scene scene = getScene();
		
		jfxpanel.setScene(scene);
		
		
		new Thread(()-> {
				try {
					while (true) {
						Thread.sleep(1000);
						Platform.runLater(()->{
							if (series.getData().size() == 10) {
								series.getData().remove(0);
								series2.getData().remove(0);
							}
							double lb = ((NumberAxis)lc.getXAxis()).getLowerBound();
							double ub = ((NumberAxis)lc.getXAxis()).getUpperBound();
							((NumberAxis)lc.getXAxis()).setLowerBound(lb + 1);
							((NumberAxis)lc.getXAxis()).setUpperBound(ub + 1);
							++i;
							series.getData().add(new XYChart.Data<>(i, random.nextInt(100)));
							series2.getData().add(new XYChart.Data<>(i, random.nextInt(100)));
						});
					}
				} catch (Exception e) {
				
				}
		}).start();
		
		jframe.add(jfxpanel);
		jframe.setVisible(true);
	}
	
	static Scene getScene() {
		
		NumberAxis xAxis = new NumberAxis(-10, 0, 1);
		NumberAxis yAxis = new NumberAxis(0,100,10);
		
		
		lc = new AreaChart<>(xAxis, yAxis);
	
		lc.setCreateSymbols(false);
		
	//	xAxis.setGapStartAndEnd(false);
//		xAxis.setStartMargin(0);
		
		Scene scene = new Scene(lc, 500, 500);
		
		series = new XYChart.Series<>();
		series2 = new XYChart.Series<>();
		
		series.getData().add(new XYChart.Data<>(0,0));
		series2.getData().add(new XYChart.Data<>(0,0));
		
		series.setName("HTTP");
		series2.setName("FTP");
		
		lc.getData().add(series);
		lc.getData().add(series2);
		
	//	lc.setAnimated(false);
		
		return scene;
	}
	
}
