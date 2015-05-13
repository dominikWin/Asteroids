package com.thiefg.asteroids.development;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConsoleThread extends Thread implements Runnable {
	private boolean extractBoolean(String string) {
		string = string.trim();
		if(string.equalsIgnoreCase("1")) return true;
		if(string.equalsIgnoreCase("0")) return false;
		if(string.equalsIgnoreCase("true")) return true;
		if(string.equalsIgnoreCase("false")) return false;
		if(string.equalsIgnoreCase("on")) return true;
		if(string.equalsIgnoreCase("off")) return false;
		return false;
	}
	
	public void print(String message) {
		System.out.println(message);
	}
	
	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	public void runCommand(String command) {
		String comm = command.trim().split(" ")[0];
		String[] args = command.trim().split(" ");
		List<String> tmp = Arrays.asList(args);
		tmp.remove(0);
		tmp.toArray(args);
		if(comm.equalsIgnoreCase("colorsweep")) {
			
		}
	}
}
