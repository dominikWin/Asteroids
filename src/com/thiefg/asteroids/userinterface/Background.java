package com.thiefg.asteroids.userinterface;

import java.util.ArrayList;

public class Background {
	private static final int PARTICAL_COUNT = 500;
	ArrayList<Particle> particals;
	public Background() {
		while(particals.size() < PARTICAL_COUNT) particals.add(new Particle());
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
	
	private class Particle {
		public Particle() {
			
		}
	}
}
