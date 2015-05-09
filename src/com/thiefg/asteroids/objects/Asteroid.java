package com.thiefg.asteroids.objects;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.DistortedPolygon;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Asteroid {
	private static final int SHAPE_SIDES = 6;
	private static final int SMALLEST_SIZE = 8;

	public static Vector2d getRandomLocation() {
		while (true) {
			Vector2d loc = new Vector2d(Math.random() * Game.WIDTH, Math.random() * Game.HEIGHT);
			if (Vector2d.distance(loc, Game.getInstance().getWorld().getPlayer().getLocation()) > (Game.WIDTH / 6)) return loc;
		}
	}

	private boolean destroyed;
	private Vector2d location;
	private double rotation;
	private double rotationSpeed;
	private DistortedPolygon shape;
	private int size = 32;
	private Vector2d velocity;

	public Asteroid(Vector2d location) {
		this.location = location;
		rotation = 0;
		rotationSpeed = 0;
		shape = new DistortedPolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	public Asteroid(Vector2d location, double rotation) {
		this.location = location;
		this.rotation = rotation;
		rotationSpeed = 0;
		shape = new DistortedPolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	public Asteroid(Vector2d location, double rotation, double rotationSpeed) {
		this.location = location;
		this.rotation = rotation;
		this.rotationSpeed = rotationSpeed;
		shape = new DistortedPolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	public Asteroid(Vector2d location, double rotation, double rotationSpeed, int size) {
		this.location = location;
		this.rotation = rotation;
		this.rotationSpeed = rotationSpeed;
		this.size = size;
		shape = new DistortedPolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	public Vector2d getLocation() {
		return location;
	}

	private Vector2d getRandosmVelocity() {
		return new Vector2d((Math.random() * 10d) - 5d, (Math.random() * 10d) - 5d);
	}

	public double getRotation() {
		return rotation;
	}

	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public DistortedPolygon getShape() {
		return shape;
	}

	public int getSize() {
		return size;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void hit() {
		destroyed = true;
		if (size <= Asteroid.SMALLEST_SIZE) return;
		Game.getInstance().getWorld().addAsteroid(new Asteroid(new Vector2d(location.getX(), location.getY()), Math.random() * 360, rotationSpeed, size / 2));
		Game.getInstance().getWorld().addAsteroid(new Asteroid(new Vector2d(location.getX(), location.getY()), Math.random() * 360, rotationSpeed, size / 2));
		Game.getInstance().getWorld().asteroidAddChance += .0002;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void render() {
		shape.render();
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public void setLocation(Vector2d location) {
		this.location = location;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setRotationSpeed(double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void setShape(DistortedPolygon shape) {
		this.shape = shape;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}

	public void update() {
		if (location.getX() < 0) location.setX(location.getX() + Game.WIDTH);
		if (location.getX() > Game.WIDTH) location.setX(location.getX() - Game.WIDTH);
		if (location.getY() < 0) location.setY(location.getY() + Game.HEIGHT);
		if (location.getY() > Game.HEIGHT) location.setY(location.getY() - Game.HEIGHT);
		rotation += rotationSpeed;
		location.add(getVelocity());
		shape.setRotation(rotation);
		shape.update();
	}
}
