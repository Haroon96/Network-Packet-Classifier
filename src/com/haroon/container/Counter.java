package com.haroon.container;

public class Counter {
	int port;
	int count;
	public Counter(int port) {
		this.port = port;
		count = 0;
	}
	public int getPort() {
		return port;
	}
	public void increment() {
		count++;
	}
	public int getCount() {
		return count;
	}
	public void reset() {
		count = 0;
	}
}
