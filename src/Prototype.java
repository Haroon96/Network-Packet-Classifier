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
	
	private static PieChart pc;
	private static Random random = new Random();
	private static int i = 0;
	
	public static void main(String[] args) throws Exception {
		
		JFrame jframe = new JFrame();
		jframe.setSize(new Dimension(500,500));
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JFXPanel jfxpanel = new JFXPanel();
		
		Scene scene = getScene();
		
		jfxpanel.setScene(scene);
		
		
		
		jframe.add(jfxpanel);
		jframe.setVisible(true);
	}
	
	static Scene getScene() {
		
		pc = new PieChart();
		pc.getData().add(new PieChart.Data("HELLO", 1));
		pc.getData().add(new PieChart.Data("BYE", 2));
		
		return new Scene(pc);
	}
	
}
