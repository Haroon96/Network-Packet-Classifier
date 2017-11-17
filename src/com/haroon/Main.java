package com.haroon;

import com.haroon.config.Configuration;
import com.haroon.container.Interface;
import com.haroon.container.Packet;
import com.haroon.container.Protocol;
import com.haroon.packetdump.PacketDump;
import com.haroon.packetdump.WinDump;
import com.haroon.ui.SetupWindow;
import com.haroon.ui.MainWindow;
import com.haroon.ui.interfaces.MessageInterface;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Main implements Runnable, PacketDump.CallbackInterface, Configuration.ProtocolLoadInterface, MessageInterface {
	
	private static SetupWindow setupWindow;
	private static MainWindow mainWindow;
	
	LinkedBlockingQueue<String> queue;
	ArrayList<Packet> packets;
	PacketDump dump;
	
	static {
		setupWindow = new SetupWindow();
		mainWindow = new MainWindow();
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setupWindow.show();
		new Thread(new Main()).start();
	}
	
	@Override
	public void run() {
		try {
			ArrayList<Interface> interfaces = new WinDump().listInterfaces();
			
			for (Interface i : interfaces) {
				mainWindow.addInterface(i);
			}
			
		} catch(Exception e) {
			report("Failed to read network interfaces.");
			System.exit(1);
		}
		Configuration.load(Main.this, Main.this);
	}
	
	@Override
	public void protocolsLoaded(ArrayList<Protocol> protocols) {
		setupWindow.hide();
		mainWindow.show();
	}
	
	@Override
	public void report(String message) {
		JFrame frame = null;
		if (setupWindow.isVisible()) {
			frame = setupWindow.getFrame();
		} else if (mainWindow.isVisible()) {
			frame = mainWindow.getFrame();
		}
		
		if (frame != null) {
			JOptionPane.showMessageDialog(frame, message);
		}
	}
	
	@Override
	public void dump(String s) {
		queue.add(s);
	}
}
