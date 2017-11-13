package com.haroon.packetdump;

import com.haroon.container.Interface;

import java.util.ArrayList;

public class WinDump extends PacketDump {
	
	public WinDump(ArrayList<String> params) {
		super(params);
		setProcessName("windump");
	}
	
	@Override
	public ArrayList<Interface> listInterfaces() throws Exception {
		ArrayList<Interface> interfaces = super.listInterfaces();
		
		for (Interface i: interfaces) {
			String s = i.getName();
			int startIndex = s.indexOf('(') + 1;
			int endIndex = s.indexOf(')');
			i.setName(s.substring(startIndex, endIndex));
		}
		
		return interfaces;
	}
}
