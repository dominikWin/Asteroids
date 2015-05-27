package com.thiefg.asteroids.objects;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Vector2d;

public class ParticleEffect {
	private ArrayList<Particle> particles;

	public ParticleEffect(Vector2d loc, int size, double force) {
		setParticles(new ArrayList<ParticleEffect.Particle>());
		for (int i = 0; i < size; i++)
			getParticles().add(new Particle(loc, force));
	}

	public ParticleEffect(Vector2d loc, int size, double force, Vector2d initDirection, double initDirectionMultiplyer) {
		setParticles(new ArrayList<ParticleEffect.Particle>());
		for (int i = 0; i < size; i++)
	}

	public void update() {
		removeOffScreenParticles();
		for (Particle p : getParticles())
			p.update();
	}

	public void render() {
		GL11.glBegin(GL11.GL_POINTS);
		for (Particle p : getParticles())
			p.render();
		GL11.glEnd();
		GL11.glColor3d(1, 1, 1);
	}

	private void removeOffScreenParticles() {
		getParticles().removeIf(t -> {
			Vector2d loc = t.location;
			return (loc.getX() < 0) || (loc.getX() > Game.WIDTH) || (loc.getY() < 0) || (loc.getY() > Game.HEIGHT);
		});
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	private class Particle {
		private static final double GRAVITY_MULTIPLYER = .1;
		Vector2d location, velocity;
		double r, g, b;

		public Particle(Vector2d loc, double force) {
			location = new Vector2d(loc.getX(), loc.getY());
			velocity = new Vector2d(new Vector2d(0, 0), Math.random() * 360, force * 2 * Math.random());
			r = .5d + (Math.random() / 2);
			g = .5d + (Math.random() / 2);
			b = .5d + (Math.random() / 2);
		}

		public Particle(Vector2d loc, double force, Vector2d multiply) {
			location = new Vector2d(loc.getX(), loc.getY());
			velocity = new Vector2d(new Vector2d(0, 0), Math.random() * 360, force * 2 * Math.random());
			velocity.add(multiply);
			r = .5d + (Math.random() / 2);
			g = .5d + (Math.random() / 2);
			b = .5d + (Math.random() / 2);
		}

		public void update() {
			location.add(velocity);
			velocity.add(new Vector2d(0, GRAVITY_MULTIPLYER));
		}

		public void render() {
			GL11.glColor3d(r, g, b);
			GL11.glVertex2i((int) location.getX(), (int) location.getY());
		}
	}
}
