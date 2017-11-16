package com.haroon.config;

import com.haroon.container.Protocol;
import com.haroon.ui.interfaces.MessageInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Configuration {
	
	static {
		selectedProtocols = new ArrayList<>();
	}
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static final int WINDOWS = 1;
	public static final int LINUX = 2;
	
	public static int getOS() {
		if (OS.indexOf("win") >= 0) {
			return WINDOWS;
		} else if (OS.indexOf("nix") >= 0) {
			return LINUX;
		} else {
			return 0;
		}
	}
	
	public static void save() {
	}
	
	
	public static void load(ProtocolLoadInterface pli, MessageInterface msgi) {
		
		ArrayList<Protocol> protocols = new ArrayList<>();
		
		File protocol_file = new File("data/protocols.xml");
		
		try {
			
			if (!protocol_file.isFile()) {
				// if file does not exist, create new empty file
				protocol_file.createNewFile();
				msgi.report("The protocol data set was not found. A new one has been created.");
				return;
			}
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(protocol_file);
			
			NodeList nodes = doc.getElementsByTagName("protocol");
			
			int length = nodes.getLength();
			
			Element elem;
			
			for (int i = 0; i < length; ++i) {
				elem = (Element)nodes.item(i);
				String name = elem.getElementsByTagName("name").item(0).getTextContent();
				int port = Integer.parseInt(elem.getElementsByTagName("port").item(0).getTextContent());
				boolean selected = Boolean.parseBoolean(elem.getElementsByTagName("selected").item(0).getTextContent());
				
				protocols.add(new Protocol(name, port, selected));
			}
		} catch (Exception e) {
			// if an error occured while parsing the file, delete the malformed file
			protocol_file.delete();
			msgi.report("The protocol data set was malformed and has been deleted");
		}
		synchronized (selectedProtocols) {
			selectedProtocols.clear();
			for (Protocol p : protocols) {
				if (p.isSelected()) {
					selectedProtocols.add(p);
				}
			}
		}
		pli.protocolsLoaded(protocols);
	}
	
	public interface ProtocolLoadInterface {
		void protocolsLoaded(ArrayList<Protocol> protocols);
	}
	
	public static ArrayList<Protocol> getSelectedProtocols() {
		return selectedProtocols;
	}
	
	
	private static ArrayList<Protocol> selectedProtocols;
	
	
}
