package com.haroon;

import com.haroon.container.Packet;
import com.haroon.packetdump.PacketDump;
import com.haroon.packetdump.WinDump;
import com.haroon.ui.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class Main implements PacketDump.CallbackInterface {
	
	// Vector is synchronized
	LinkedBlockingQueue<String> queue;
	ArrayList<Packet> packets;
	PacketDump dump;
	
	public static void main(String[] args) throws Exception {
		//new Main().run();
		new MainWindow().launch();
	}
	
	Main() {
		queue = new LinkedBlockingQueue<>();
		packets = new ArrayList<>();
		dump = new WinDump(new ArrayList<>(Arrays.asList("-i", "2", "-n", "-l", "-q", "tcp", "or", "udp")));
		dump.setCallback(this);
	}
	
	public void run() throws Exception {
		dump.start();
		String str;
		while (true) {
			
			str = queue.take();
			packets.add(Packet.parse(str));
			
			System.out.println(str);
			System.out.println(packets.get(packets.size() - 1));
			System.out.println("--------------------------------");
			
		}
	}
	
	@Override
	public void dump(String s) {
		queue.add(s);
	}
}
