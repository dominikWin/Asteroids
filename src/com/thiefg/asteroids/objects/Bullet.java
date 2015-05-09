package com.thiefg.asteroids.objects;

import java.util.ArrayList;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Polygon;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Bullet {
	private static final int RADIUS = 4;
	private static final int SIDES = 8;
	private boolean destroyed;
	Vector2d location;
	double rotation;
	Polygon shape;
	int speed;

	public Bullet(Vector2d location, double rotation, int speed) {
		this.location = new Vector2d(location.getX(), location.getY());
		this.rotation = rotation;
		this.speed = speed;
		setDestroyed(false);
		shape = new Polygon(location, Bullet.SIDES, Bullet.RADIUS);
	}

	private void collosionTest() {
		ArrayList<Asteroid> asteroids = Game.getInstance().getWorld().getAsteroids();
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid a = asteroids.get(i);
			if (Vector2d.distance(a.getLocation(), location) < a.getSize()) {
				a.hit();
				setDestroyed(true);
				Game.getInstance().getWorld().getPlayer().addScore(a.getSize());
			}
		}
	}

	public Vector2d getLocation() {
		return location;
	}

	public double getRotation() {
		return rotation;
	}

	public Polygon getShape() {
		return shape;
	}

	public int getSpeed() {
		return speed;
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

	public void setShape(Polygon shape) {
		this.shape = shape;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void update() {
		location = new Vector2d(location, rotation, speed);
		shape.setLocation(location);
		shape.update();
		collosionTest();
	}
}
