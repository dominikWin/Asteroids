package com.thiefg.asteroids.userinterface;

import java.util.ArrayList;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Vector2d;

import static org.lwjgl.opengl.GL11.*;

public class Background {
	private static final int PARTICAL_COUNT = 500;
	ArrayList<Particle> particals;

	public Background() {
		particals = new ArrayList<Background.Particle>();
		while (particals.size() < PARTICAL_COUNT)
			particals.add(new Particle());
	}

	public void update() {
		for (Particle p : particals)
			p.update();
	}

	public void render() {
		glColor3d(.5, .5, .5);
		glBegin(GL_POINTS);
		for (Particle p : particals)
			p.render();
		glEnd();
		glColor3d(1, 1, 1);
	}

	public static Vector2d getRandomLocation() {
		return new Vector2d(Game.WIDTH * Math.random(), Game.HEIGHT
				* Math.random());
	}

	public static Vector2d getRandomVelocity() {
		return new Vector2d((Math.random() - .5d) * 3,
				(Math.random() - .5d) * 3);
	}

	private class Particle {
		Vector2d location, velocity;

		public Particle() {
			location = getRandomLocation();
			velocity = getRandomVelocity();
		}

		public void render() {
			glVertex2i((int) location.getX(), (int) location.getY());
		}

		public void update() {
			location.add(velocity);
			if (location.getX() < 0 || location.getX() > Game.WIDTH
					|| location.getY() < 0 || location.getY() > Game.HEIGHT) {
				location = getRandomLocation();
				velocity = getRandomVelocity();
			}
		}

	}
}
