package com.thiefg.asteroids.development;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConsoleThread extends Thread implements Runnable {
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public void run() {
		new BufferedReader(new InputStreamReader(System.in));
	}

	public void runCommand(String command) {
		String comm = command.trim().split(" ")[0];
		String[] args = command.trim().split(" ");
		List<String> tmp = Arrays.asList(args);
		tmp.remove(0);
		tmp.toArray(args);
		if (comm.equalsIgnoreCase("colorsweep")) {
		}
	}
}
