package com.haroon.container;

import java.util.Scanner;

public class Packet {
	
	private String time;
	private String srcIp;
	private String dstIp;
	private int srcPort;
	private int dstPort;
	
	public static Packet parse(String s) {
		String time, srcIp, dstIp, srcPort, dstPort;
		
		String[] tokens = s.split(" ");
		
		/*
		* Sample format:
		* 14:53:14.761138 IP 52.38.149.111.443 > 192.168.11.117.65367: tcp 31
		*/
		
		time = tokens[0];
		srcIp = tokens[2].substring(0, tokens[2].lastIndexOf("."));
		srcPort = tokens[2].substring(tokens[2].lastIndexOf(".") + 1, tokens[2].length());
		dstIp = tokens[4].substring(0, tokens[4].lastIndexOf("."));
		dstPort = tokens[4].substring(tokens[4].lastIndexOf(".") + 1, tokens[4].length() - 1);
		
		return new Packet(time, srcIp, dstIp, Integer.parseInt(srcPort), Integer.parseInt(dstPort));
	}
	
	@Override
	public String toString() {
		return String.format("%s %s:%s -> %s:%s", time, srcIp, srcPort, dstIp, dstPort);
	}
	
	private Packet(String time, String srcIp, String dstIp, int srcPort, int dstPort) {
		this.time = time;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
	}
	
	
	public String getTime() {
		return time;
	}
	
	public String getSrcIp() {
		return srcIp;
	}
	
	public String getDstIp() {
		return dstIp;
	}
	
	public int getSrcPort() {
		return srcPort;
	}
	
	public int getDstPort() {
		return dstPort;
	}
}
