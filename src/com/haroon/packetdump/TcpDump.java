package com.haroon.packetdump;

import com.haroon.container.Interface;

import java.util.ArrayList;

public class TcpDump extends PacketDump {
	
	public TcpDump(ArrayList<String> params) {
		super(params);
		setProcessName("tcpdump");
	}
	
	public TcpDump() {
		setProcessName("tcpdump");
	}
	
	@Override
	public ArrayList<Interface> listInterfaces() throws Exception {
		ArrayList<Interface> interfaces = super.listInterfaces();
		return interfaces;
	}
}
