package com.thiefg.asteroids;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.objects.Asteroid;
import com.thiefg.asteroids.objects.Bullet;
import com.thiefg.asteroids.objects.Player;
import com.thiefg.asteroids.subobjects.Vector2d;

public class World {
	private static final double MIN_ASTEROID_DISTANCE_TO_PLAYER = 100;

	public double asteroidAddChance = .0075d;
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
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
			Vector2d loc = new Vector2d(Math.random() * Game.WIDTH,
					Math.random() * Game.HEIGHT);
			if (Vector2d.distance(loc, getPlayer().getLocation()) < MIN_ASTEROID_DISTANCE_TO_PLAYER)
				return loc;
		}
	}

	public Player getPlayer() {
		return player;
	}

	private void removeDestroyedAsteroids() {
		asteroids.removeIf(new Predicate<Asteroid>() {

			@Override
			public boolean test(Asteroid t) {
				return t.isDestroyed();
			}
		});
	}

	private void removeDestroyedBullets() {
		bullets.removeIf(new Predicate<Bullet>() {

			@Override
			public boolean test(Bullet t) {
				return t.isDestroyed();
			}
		});
	}

	private void removeOffScreenBullets() {
		bullets.removeIf(new Predicate<Bullet>() {

			@Override
			public boolean test(Bullet t) {
				Vector2d loc = t.getLocation();
				return loc.getX() < 0 || loc.getX() > Game.WIDTH
						|| loc.getY() < 0 || loc.getY() > Game.HEIGHT;
			}
		});
	}

	public void render() {
		GL11.glColor3d(1, 1, 1);
		for (Asteroid a : asteroids) {
			a.render();
		}
		for (Bullet b : bullets)
			b.render();
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

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update() {
		removeDestroyedAsteroids();
		for (Asteroid a : asteroids)
			a.update();
		for (Bullet b : bullets)
			b.update();
		removeOffScreenBullets();
		removeDestroyedBullets();
		player.update();
	}

}
