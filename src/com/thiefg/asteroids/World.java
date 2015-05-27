package com.thiefg.asteroids;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.objects.Asteroid;
import com.thiefg.asteroids.objects.Bullet;
import com.thiefg.asteroids.objects.ParticleEffect;
import com.thiefg.asteroids.objects.Player;
import com.thiefg.asteroids.subobjects.Vector2d;

public class World {
	private static final double MIN_ASTEROID_DISTANCE_TO_PLAYER = 100;
	public double asteroidAddChance = .0075d;
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<ParticleEffect> particleEffects = new ArrayList<ParticleEffect>();
	private Player player;

	public World() {
		player = new Player(new Vector2d(Game.WIDTH / 2, Game.HEIGHT / 2), 0);
		asteroids = new ArrayList<Asteroid>();
		bullets = new ArrayList<Bullet>();
	}

	public void addAsteroid(Asteroid asteroid) {
		asteroids.add(asteroid);
	}

	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}

	public void addParticleEffect(ParticleEffect p) {
		particleEffects.add(p);
	}

	public double getAsteroidAddChance() {
		return asteroidAddChance;
	}

	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public Vector2d getLocationAwayFromPlayer() {
		while (true) {
			Vector2d loc = new Vector2d(Math.random() * Game.WIDTH, Math.random() * Game.HEIGHT);
			if (Vector2d.distance(loc, getPlayer().getLocation()) < World.MIN_ASTEROID_DISTANCE_TO_PLAYER) return loc;
		}
	}

	public ArrayList<ParticleEffect> getParticleEffects() {
		return particleEffects;
	}

	public Player getPlayer() {
		return player;
	}

	private void removeDestroyedAsteroids() {
		asteroids.removeIf(t -> t.isDestroyed());
	}

	private void removeDestroyedBullets() {
		bullets.removeIf(t -> t.isDestroyed());
	}

	private void removeFinishedParticleEffects() {
		particleEffects.removeIf(p -> {
			return p.getParticles().isEmpty();
		});
	}

	private void removeOffScreenBullets() {
		bullets.removeIf(t -> {
			Vector2d loc = t.getLocation();
			return (loc.getX() < 0) || (loc.getX() > Game.WIDTH) || (loc.getY() < 0) || (loc.getY() > Game.HEIGHT);
		});
	}

	public void render() {
		GL11.glColor3d(1, 1, 1);
		for (Asteroid a : asteroids)
			a.render();
		for (Bullet b : bullets)
			b.render();
		// long time = System.nanoTime();
		for (ParticleEffect p : particleEffects)
			p.render();
		// System.out.println("  |  Render: " + (System.nanoTime() - time));
		player.render();
	}

	public void setAsteroidAddChance(double asteroidAddChance) {
		this.asteroidAddChance = asteroidAddChance;
	}

	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public void setParticleEffects(ArrayList<ParticleEffect> particleEffects) {
		this.particleEffects = particleEffects;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update() {
		removeDestroyedAsteroids();
		for (Asteroid a : asteroids)
			a.update();
		for (Bullet b : bullets)
			b.update();
		removeFinishedParticleEffects();
		// long time = System.nanoTime();
		for (ParticleEffect p : particleEffects)
			p.update();
		// System.out.print("Update: " + (System.nanoTime() - time));
		removeOffScreenBullets();
		removeDestroyedBullets();
		player.update();
	}
}
