package asteroids;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import asteroids.objects.Asteroid;
import asteroids.objects.Bullet;
import asteroids.objects.ParticleEffect;
import asteroids.objects.Player;
import asteroids.subobjects.Vector2d;

/**
 * @author Dominik
 * World object, holds all game components
 */
public class World {
	private static final double MIN_ASTEROID_DISTANCE_TO_PLAYER = 100;
	public double asteroidAddChance = .0075d;
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<ParticleEffect> particleEffects = new ArrayList<ParticleEffect>();
	private Player player;

	/**
	 * Creates new World
	 */
	public World() {
		player = new Player(new Vector2d(Game.width / 2, Game.height / 2), 0);
		asteroids = new ArrayList<Asteroid>();
		bullets = new ArrayList<Bullet>();
	}

	/**
	 * @param asteroid
	 * Adds asteroid to asteroids
	 */
	public void addAsteroid(Asteroid asteroid) {
		asteroids.add(asteroid);
	}

	/**
	 * @param bullet
	 * Add bullet to bullets
	 */
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}

	/**
	 * @param particleEffect
	 * Adds particleEffect to particleEffects
	 */
	public void addParticleEffect(ParticleEffect particleEffect) {
		particleEffects.add(particleEffect);
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

	/**
	 * @return new Vector2d in screen away from player
	 */
	public Vector2d getLocationAwayFromPlayer() {
		while (true) {
			Vector2d loc = new Vector2d(Math.random() * Game.width, Math.random() * Game.height);
			if (Vector2d.distance(loc, getPlayer().getLocation()) < World.MIN_ASTEROID_DISTANCE_TO_PLAYER) return loc;
		}
	}

	public ArrayList<ParticleEffect> getParticleEffects() {
		return particleEffects;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Removes destroyed asteroids
	 */
	private void removeDestroyedAsteroids() {
		asteroids.removeIf(t -> t.isDestroyed());
	}

	/**
	 * Removes destroyed bullets
	 */
	private void removeDestroyedBullets() {
		bullets.removeIf(t -> t.isDestroyed());
	}

	/**
	 * Removes particleEffects with no particles
	 */
	private void removeFinishedParticleEffects() {
		particleEffects.removeIf(p -> {
			return p.getParticles().isEmpty();
		});
	}

	/**
	 * Removes off-screen bullets
	 */
	private void removeOffScreenBullets() {
		bullets.removeIf(t -> {
			Vector2d loc = t.getLocation();
			return (loc.getX() < 0) || (loc.getX() > Game.width) || (loc.getY() < 0) || (loc.getY() > Game.height);
		});
	}

	/**
	 * Renders world
	 */
	public void render() {
		GL11.glColor3d(1, 1, 1);
		asteroids.forEach(a -> a.render()); //Renders asteroids
		bullets.forEach(b -> b.render()); //Renders bullets
		particleEffects.forEach(e -> e.render()); //Renders particleEffects
		player.render(); //Renders player
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

	/**
	 * Updates world
	 */
	public void update() {
		removeDestroyedAsteroids();
		asteroids.forEach(a -> a.update()); //Updates asteroids
		bullets.forEach(b -> b.update()); //Updates bullets
		removeFinishedParticleEffects();
		particleEffects.forEach(e -> e.update()); //Updates particleEffects
		removeOffScreenBullets();
		removeDestroyedBullets();
		player.update(); //Updates player
	}
}
