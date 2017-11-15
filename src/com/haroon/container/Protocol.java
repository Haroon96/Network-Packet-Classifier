package com.haroon.container;

public class Protocol {
	
	public Protocol(String name, int port) {
		this.name = name;
		this.port = port;
	}
	
	public Protocol(String name, int port, boolean selected) {
		this(name, port);
		this.selected = selected;
	}
	
	private String name;
	private int port;
	private boolean selected;
	
	public void toggleSelect() {
		selected = !selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
	
	@Override
	public String toString() {
		return name + " " + port + " " + selected;
	}
}
