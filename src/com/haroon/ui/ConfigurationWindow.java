package com.haroon.ui;

import com.haroon.config.Configuration;
import com.haroon.container.Protocol;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ConfigurationWindow {
	private JList allList;
	private JList selectedList;
	private DefaultListModel<Protocol> selectedListModel;
	private DefaultListModel<Protocol> allListModel;
	
	private JButton addBtn;
	private JButton removeButton;
	private JTextField protoField;
	private JTextField portField;
	private JButton protoBtn;
	private JButton saveBtn;
	private JPanel panel;
	
	private JFrame frame;
	
	ConfigurationWindow() {
		selectedListModel = new DefaultListModel<>();
		allListModel = new DefaultListModel<>();
		selectedList.setModel(selectedListModel);
		allList.setModel(allListModel);
		setListeners();
		
		frame = new JFrame("Configuration");
		frame.setMinimumSize(new Dimension(300, 500));
		frame.setContentPane(panel);
		frame.pack();
		
	}
	
	private void setListeners() {
		saveBtn.addActionListener((ActionEvent e) -> {
			ArrayList<Protocol> protocols = new ArrayList<>();
			ArrayList<Protocol> selectedProtocols = Configuration.getSelectedProtocols();
			selectedProtocols.clear();
			Protocol p;
			int size = selectedListModel.size();
			for (int i = 0; i < size; ++i) {
				p = selectedListModel.get(i);
				protocols.add(p);
				selectedProtocols.add(p);
				p.select();
			}
			size = allListModel.size();
			for (int i = 0; i < size; ++i) {
				p = allListModel.get(i);
				protocols.add(p);
				p.deselect();
			}
			Configuration.save(protocols);
			selectedListModel.clear();
			allListModel.clear();
			hide();
		});
		addBtn.addActionListener((ActionEvent e) -> {
			move(allList, allListModel, selectedListModel);
		});
		removeButton.addActionListener((ActionEvent e) -> {
			move(selectedList, selectedListModel, allListModel);
		});
		protoBtn.addActionListener((ActionEvent e) -> {
			String name = protoField.getText();
			int port;
			try {
				port = Integer.parseInt(portField.getText());
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(frame, "Invalid port number");
				return;
			}
			allListModel.addElement(new Protocol(name, port));
		});
	}
	
	private void move(JList src, DefaultListModel<Protocol> srcModel, DefaultListModel<Protocol> dstModel) {
		int index = src.getSelectedIndex();
		Protocol p = srcModel.getElementAt(index);
		srcModel.remove(index);
		dstModel.addElement(p);
	}
	
	public void show() {
		frame.setVisible(true);
		
		Configuration.load((ArrayList<Protocol> protocols) -> {
			for (Protocol p : protocols) {
				if (p.isSelected()) {
					selectedListModel.addElement(p);
				} else {
					allListModel.addElement(p);
				}
			}
		}, (String message) -> {
			JOptionPane.showMessageDialog(frame, message);
		});
		
	}
	
	public void hide() {
		frame.setVisible(false);
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
		panel = new JPanel();
		panel.setLayout(new GridLayoutManager(12, 3, new Insets(10, 10, 10, 10), -1, -1));
		addBtn = new JButton();
		addBtn.setText("Add >>");
		panel.add(addBtn, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		removeButton = new JButton();
		removeButton.setText("<< Remove");
		panel.add(removeButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		panel.add(panel1, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setText("Add new protocol");
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		panel1.add(label1, gbc);
		protoField = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel1.add(protoField, gbc);
		portField = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel1.add(portField, gbc);
		final JLabel label2 = new JLabel();
		label2.setText("Name");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel1.add(label2, gbc);
		final JLabel label3 = new JLabel();
		label3.setText("Port");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panel1.add(label3, gbc);
		protoBtn = new JButton();
		protoBtn.setText("Add");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel1.add(protoBtn, gbc);
		final JPanel spacer1 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel1.add(spacer1, gbc);
		final JLabel label4 = new JLabel();
		label4.setText("List of Protocols");
		panel.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label5 = new JLabel();
		label5.setText("Selected Protocols");
		panel.add(label5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		saveBtn = new JButton();
		saveBtn.setText("Save");
		panel.add(saveBtn, new GridConstraints(11, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer2 = new Spacer();
		panel.add(spacer2, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		panel.add(scrollPane1, new GridConstraints(1, 0, 10, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		allList = new JList();
		scrollPane1.setViewportView(allList);
		final JScrollPane scrollPane2 = new JScrollPane();
		panel.add(scrollPane2, new GridConstraints(1, 2, 10, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		selectedList = new JList();
		final DefaultListModel defaultListModel1 = new DefaultListModel();
		selectedList.setModel(defaultListModel1);
		scrollPane2.setViewportView(selectedList);
	}
	
	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return panel;
	}
}
