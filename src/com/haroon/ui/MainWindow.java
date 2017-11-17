package com.haroon.ui;

import com.haroon.chart.AreaChart;
import com.haroon.chart.LineChart;
import com.haroon.chart.PieChart;
import com.haroon.config.Configuration;
import com.haroon.container.*;
import com.haroon.packetdump.PacketDump;
import com.haroon.packetdump.WinDump;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.haroon.config.Configuration.LINUX;
import static com.haroon.config.Configuration.WINDOWS;
import static com.haroon.config.Configuration.getSelectedProtocols;

public class MainWindow {
	
	private JPanel panel1;
	private JPanel graphPanel;
	private JComboBox interfaceComboBox;
	private JButton start_stop_btn;
	private JButton line_btn;
	private JButton area_btn;
	private JButton pie_btn;
	private JButton settings_btn;
	private JButton logBtn;
	private JFrame frame;
	private JFXPanel jfxPanel;
	
	private LogWindow logWindow;
	
	private int interval = 0;
	
	private ArrayList<Packet> packets;
	private PacketDump packetDump;
	
	private boolean started;
	private boolean continue_running;
	
	private HashMap<String, Series> hashmap;
	
	public JFrame getFrame() {
		return frame;
	}
	
	public MainWindow() {
		hashmap = new HashMap<>();
		jfxPanel = new JFXPanel();
		packets = new ArrayList<>();
		started = false;
		
		start_stop_btn.setFocusPainted(false);
		line_btn.setFocusPainted(false);
		area_btn.setFocusPainted(false);
		pie_btn.setFocusPainted(false);
		settings_btn.setFocusPainted(false);
		
		logWindow = new LogWindow();
	}
	
	public void show() {
		
		setListeners();
		
		frame = new JFrame("Network Packet Classifier");
		frame.setMinimumSize(new Dimension(800, 500));
		frame.setContentPane(panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfxPanel.setScene(AreaChart.getScene());
		graphPanel.add(jfxPanel);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private void setListeners() {
		settings_btn.addActionListener((ActionEvent e) -> {
			new ConfigurationWindow().show();
		});
		line_btn.addActionListener((ActionEvent e) -> {
			jfxPanel.setScene(LineChart.getScene());
		});
		area_btn.addActionListener((ActionEvent e) -> {
			jfxPanel.setScene(AreaChart.getScene());
		});
		pie_btn.addActionListener((ActionEvent e) -> {
			jfxPanel.setScene(PieChart.getScene());
		});
		start_stop_btn.addActionListener((ActionEvent e) -> {
			if (started) {
				// stop
				continue_running = false;
				start_stop_btn.setIcon(new ImageIcon("resources/start_icon.png"));
				interfaceComboBox.setEnabled(true);
				settings_btn.setEnabled(true);
				packetDump.exitProcess();
				hashmap.clear();
			} else {
				// start
				start_stop_btn.setIcon(new ImageIcon("resources/stop_icon.png"));
				interfaceComboBox.setEnabled(false);
				settings_btn.setEnabled(false);
				continue_running = true;
				if (Configuration.getOS() == WINDOWS) {
					packetDump = new WinDump(new ArrayList<>(Arrays.asList("-i", Integer.toString(interfaceComboBox.getSelectedIndex() + 1), "-n", "-l", "-q", "tcp", "or", "udp")));
				} else if (Configuration.getOS() == LINUX) {
					//TODO: add linux tcpdump
				}
				
				LineChart.init();
				AreaChart.init();
				PieChart.init();
				packets.clear();
				
				for (Protocol p : Configuration.getSelectedProtocols()) {
					Series series = new Series();
					series.setName(p.getName());
					hashmap.put(Integer.toString(p.getPort()), series);
					LineChart.addSeries(series.lineChartSeries);
					AreaChart.addSeries(series.areaChartSeries);
				}
				
				new Thread(() -> {
					int total_packets;
					interval = 1;
					ArrayList<Packet> dump = new ArrayList<>();
					ArrayList<Counter> counters = new ArrayList<>();
					
					PieChart.addCategory("Unclassified");
					for (Protocol proto : getSelectedProtocols()) {
						counters.add(new Counter(proto.getPort()));
						PieChart.addCategory(proto.getName());
					}
					
					while (continue_running) {
						try {
							Thread.sleep(1000);
							if (continue_running == false) {
								continue;
							}
							synchronized (packets) {
								dump.addAll(packets);
								packets.clear();
							}
							
							total_packets = dump.size();
							
							if (total_packets == 0) {
								continue;
							}
							
							for (Packet p : dump) {
								for (Counter c : counters) {
									if (p.getDstPort() == c.getPort() || p.getSrcPort() == c.getPort()) {
										c.increment();
									}
								}
							}
							
							AreaChart.move();
							LineChart.move();
							int sum_for_pie = 0;
							for (Counter c : counters) {
								sum_for_pie += c.getCount();
								float percent = ((float) c.getCount() / total_packets) * 100f;
								Series series = hashmap.get(Integer.toString(c.getPort()));
								PieChart.addData(series.getName(), c.getCount());
								Platform.runLater(() -> {
									series.lineChartSeries.getData().add(new XYChart.Data(interval, percent));
									series.areaChartSeries.getData().add(new XYChart.Data(interval, percent));
								});
								c.reset();
							}
							PieChart.addData("Unclassified", total_packets - sum_for_pie);
							++interval;
						} catch (Exception e2) {
						
						}
					}
				}).start();
				
				
				packetDump.setCallback((String s) -> {
					Packet p = Packet.parse(s);
					synchronized (packets) {
						packets.add(p);
					}
					logWindow.addEntry(p);
				});
				
				packetDump.start();
			}
			started = !started;
		});
		logBtn.addActionListener((ActionEvent e) -> {
			logWindow.show();
		});
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	public boolean isVisible() {
		return frame.isVisible();
	}
	
	public void addInterface(Interface i) {
		interfaceComboBox.addItem(i.getName());
	}
	
	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}
	
	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
		panel1.setBackground(new Color(-723724));
		graphPanel = new JPanel();
		graphPanel.setLayout(new BorderLayout(0, 0));
		graphPanel.setBackground(new Color(-723724));
		panel1.add(graphPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel2.setBackground(new Color(-723724));
		panel1.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
		panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		pie_btn = new JButton();
		pie_btn.setIcon(new ImageIcon(getClass().getResource("/pie_chart.png")));
		pie_btn.setText("");
		panel3.add(pie_btn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		line_btn = new JButton();
		line_btn.setIcon(new ImageIcon(getClass().getResource("/line_chart.png")));
		line_btn.setText("");
		panel3.add(line_btn, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		area_btn = new JButton();
		area_btn.setIcon(new ImageIcon(getClass().getResource("/area_chart.png")));
		area_btn.setText("");
		panel3.add(area_btn, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel2.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		settings_btn = new JButton();
		settings_btn.setIcon(new ImageIcon(getClass().getResource("/settings_icon.png")));
		settings_btn.setText("");
		panel4.add(settings_btn, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		logBtn = new JButton();
		logBtn.setIcon(new ImageIcon(getClass().getResource("/log_icon.png")));
		logBtn.setText("");
		panel4.add(logBtn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel5.setBackground(new Color(-723724));
		panel1.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JPanel panel6 = new JPanel();
		panel6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel6.setBackground(new Color(-723724));
		panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		start_stop_btn = new JButton();
		start_stop_btn.setHorizontalAlignment(0);
		start_stop_btn.setIcon(new ImageIcon(getClass().getResource("/start_icon.png")));
		start_stop_btn.setText("");
		panel6.add(start_stop_btn);
		final JPanel panel7 = new JPanel();
		panel7.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panel7.setBackground(new Color(-723724));
		panel5.add(panel7, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setText("Interface");
		panel7.add(label1);
		interfaceComboBox = new JComboBox();
		panel7.add(interfaceComboBox);
	}
	
	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return panel1;
	}
}
