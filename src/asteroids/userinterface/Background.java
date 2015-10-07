package com.thiefg.asteroids.userinterface;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Vector2d;

/**
 * @author Dominik
 * Basic particle system for game background
 */
public class Background {
	/**
	 * @author Dominik
	 * Background Particle
	 */
	private class Particle {
		Vector2d location, velocity;

		/**
		 * Creates new particle at random location, and random direction
		 */
		public Particle() {
			location = Background.getRandomLocation();
			velocity = Background.getRandomVelocity();
		}

		/**
		 * Renders particle
		 */
		public void render() {
			GL11.glVertex2i((int) location.getX(), (int) location.getY());
		}

		/**
		 * Updates particle
		 */
		public void update() {
			location.add(velocity); //Add velocity
			
			//Reposition off-screen particles
			if ((location.getX() < 0) || (location.getX() > Game.width) || (location.getY() < 0) || (location.getY() > Game.height)) {
				location = Background.getRandomLocation();
				velocity = Background.getRandomVelocity(); //Effectively creates new particle
			}
		}
	}

	private static final int PARTICAL_COUNT = 500;

	/**
	 * @return new Vector2d within screen
	 */
	public static Vector2d getRandomLocation() {
		return new Vector2d(Game.width * Math.random(), Game.height * Math.random());
	}

	
	/**
	 * @return new Vector2d for velocity
	 */
	public static Vector2d getRandomVelocity() {
		return new Vector2d((Math.random() - .5d) * 3, (Math.random() - .5d) * 3);
	}

	ArrayList<Particle> particals;

	/**
	 * Creates new background
	 */
	public Background() {
		particals = new ArrayList<Background.Particle>();

		//Add particles until count reached
		while (particals.size() < Background.PARTICAL_COUNT)
			particals.add(new Particle());
	}

	/**
	 * Renders particles
	 */
	public void render() {
		GL11.glColor3d(.5, .5, .5);
		GL11.glBegin(GL11.GL_POINTS);
		
		particals.forEach(p -> p.render()); //Renders each particle
		
		GL11.glEnd();
		GL11.glColor3d(1, 1, 1);
	}

	/**
	 * Updates particles
	 */
	public void update() {
		particals.forEach(p -> p.update()); //Updates each particle
	}
}
