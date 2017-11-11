package com.haroon.util;

public class Utilities {
	
	public enum SupportedOS {
		Windows,
		Linux,
		Unsupported
	}
	
	public static SupportedOS getOS() {
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("win")) {
			return SupportedOS.Windows;
		} else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
			return SupportedOS.Linux;
		} else {
			return SupportedOS.Unsupported;
		}
	}
}
