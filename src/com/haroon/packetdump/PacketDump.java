package com.haroon.packetdump;

import com.haroon.container.Interface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class PacketDump extends Thread {
	
	protected ArrayList<String> command;
	private Process process;
	private String process_name;
	private CallbackInterface callback;
	
	protected PacketDump(ArrayList<String> command) {
		this.command = command;
		this.process_name = null;
		this.callback = null;
		this.process = null;
	}
	
	protected void setProcessName(String process_name) {
		this.process_name = process_name;
		command.add(0, process_name);
	}
	
	public void setCallback(CallbackInterface callback) {
		this.callback = callback;
	}
	
	public ArrayList<Interface> listInterfaces() throws Exception {
		
		ArrayList<Interface> interfaces = new ArrayList<>();
		
		ProcessBuilder processBuilder = new ProcessBuilder(process_name, "-D");
		Process p = processBuilder.start();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		int id = 1;
		String s;
		while ((s = br.readLine()) != null) {
			interfaces.add(new Interface(Integer.toString(id), s));
			++id;
		}
		return interfaces;
	}
	
	@Override
	public void run() {
		if (process_name == null) {
			return;
		}
		if (this.callback == null) {
			return;
		}
		
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		
		String s;
		
		try {
			
			process = processBuilder.start();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			while ((s = br.readLine()) != null) {
				callback.dump(s);
			}
			
		} catch (Exception e) {
			process.destroy();
		}
		
	}
	
	public void exitProcess() {
		if (process.isAlive()) {
			process.destroy();
		}
	}
	
	public interface CallbackInterface {
		void dump(String s);
	}
	
}
