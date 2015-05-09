package com.thiefg.asteroids.userinterface;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Background {
	private class Particle {
		Vector2d location, velocity;

		public Particle() {
			location = Background.getRandomLocation();
			velocity = Background.getRandomVelocity();
		}

		public void render() {
			GL11.glVertex2i((int) location.getX(), (int) location.getY());
		}

		public void update() {
			location.add(velocity);
			if ((location.getX() < 0) || (location.getX() > Game.WIDTH) || (location.getY() < 0) || (location.getY() > Game.HEIGHT)) {
				location = Background.getRandomLocation();
				velocity = Background.getRandomVelocity();
			}
		}
	}

	private static final int PARTICAL_COUNT = 500;

	public static Vector2d getRandomLocation() {
		return new Vector2d(Game.WIDTH * Math.random(), Game.HEIGHT * Math.random());
	}

	public static Vector2d getRandomVelocity() {
		return new Vector2d((Math.random() - .5d) * 3, (Math.random() - .5d) * 3);
	}

	ArrayList<Particle> particals;

	public Background() {
		particals = new ArrayList<Background.Particle>();
		while (particals.size() < Background.PARTICAL_COUNT)
			particals.add(new Particle());
	}

	public void render() {
		GL11.glColor3d(.5, .5, .5);
		GL11.glBegin(GL11.GL_POINTS);
		for (Particle p : particals)
			p.render();
		GL11.glEnd();
		GL11.glColor3d(1, 1, 1);
	}

	public void update() {
		for (Particle p : particals)
			p.update();
	}
}
