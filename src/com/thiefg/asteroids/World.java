package com.thiefg.asteroids;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.objects.Asteroid;
import com.thiefg.asteroids.objects.Bullet;
import com.thiefg.asteroids.objects.Enemy;
import com.thiefg.asteroids.objects.Player;
import com.thiefg.asteroids.subobjects.Vector2d;

public class World {
	private static final int MAX_ASTEROID_COUNT = 80;

	public double asteroidAddChance = .0075d;

	private static final double MIN_ASTEROID_DISTANCE_TO_PLAYER = 100;
	private Player player;
	private ArrayList<Enemy> enemys = new ArrayList<Enemy>();
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public World() {
		player = new Player(new Vector2d(Game.WIDTH / 2, Game.HEIGHT / 2), 0);
		enemys = new ArrayList<Enemy>();
		asteroids = new ArrayList<Asteroid>();
		bullets = new ArrayList<Bullet>();
	}

	public void render() {
		GL11.glColor3d(1, 1, 1);
		for (Asteroid a : asteroids) {
			a.render();
		}
		for (Bullet b : bullets)
			b.render();
		for (Enemy e : enemys)
			e.render();
		player.render();
	}

	public void update() {
		if (Math.random() < getAsteroidAddChance()
				&& asteroids.size() < MAX_ASTEROID_COUNT)
			addAsteroid(new Asteroid(new Vector2d(Math.random() * Game.WIDTH,
					Math.random() * Game.HEIGHT)));
		removeDestroyedAsteroids();
		for (Asteroid a : asteroids)
			a.update();
		for (Bullet b : bullets)
			b.update();
		removeOffScreenBullets();
		removeDestroyedBullets();
		for (Enemy e : enemys)
			e.update();
		player.update();
	}

	public Vector2d getLocationAwayFromPlayer() {
		while (true) {
			Vector2d loc = new Vector2d(Math.random() * (double) Game.WIDTH,
					Math.random() * (double) Game.HEIGHT);
			if (Vector2d.distance(loc, getPlayer().getLocation()) < MIN_ASTEROID_DISTANCE_TO_PLAYER)
				return loc;
		}
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

	private void removeDestroyedBullets() {
		bullets.removeIf(new Predicate<Bullet>() {

			@Override
			public boolean test(Bullet t) {
				return t.isDestroyed();
			}
		});
	}

	private void removeDestroyedAsteroids() {
		asteroids.removeIf(new Predicate<Asteroid>() {

			@Override
			public boolean test(Asteroid t) {
				return t.isDestroyed();
			}
		});
	}

	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}

	public void addAsteroid(Asteroid asteroid) {
		asteroids.add(asteroid);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Enemy> getEnemys() {
		return enemys;
	}

	public void setEnemys(ArrayList<Enemy> enemys) {
		this.enemys = enemys;
	}

	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}

	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public double getAsteroidAddChance() {
		return asteroidAddChance;
	}

	public void setAsteroidAddChance(double asteroidAddChance) {
		this.asteroidAddChance = asteroidAddChance;
	}

}
