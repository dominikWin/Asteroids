package com.thiefg.asteroids.objects;

import java.util.ArrayList;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Polygon;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Bullet {

    private static final int SIDES = 8;
    private static final int RADIUS = 4;
    Polygon shape;
    Vector2d location;
    double rotation;
    int speed;
    private boolean destroyed;

    public Bullet(Vector2d location, double rotation, int speed) {
	this.location = new Vector2d(location.getX(), location.getY());
	this.rotation = rotation;
	this.speed = speed;
	this.setDestroyed(false);
	shape = new Polygon(location, SIDES, RADIUS);
    }

    public void update() {
	location = new Vector2d(location, rotation, speed);
	shape.setLocation(location);
	shape.update();
	collosionTest();
    }

    private void collosionTest() {
	ArrayList<Asteroid> asteroids = Game.getWorld().getAsteroids();
	for (int i = 0; i < asteroids.size(); i++) {
	    Asteroid a = asteroids.get(i);
	    if (Vector2d.distance(a.getLocation(), location) < a.getSize()) {
		a.hit();
		setDestroyed(true);
	    }
	}
    }

    public void render() {
	shape.render();
    }

    public Polygon getShape() {
	return shape;
    }

    public void setShape(Polygon shape) {
	this.shape = shape;
    }

    public Vector2d getLocation() {
	return location;
    }

    public void setLocation(Vector2d location) {
	this.location = location;
    }

    public double getRotation() {
	return rotation;
    }

    public void setRotation(double rotation) {
	this.rotation = rotation;
    }

    public int getSpeed() {
	return speed;
    }

    public void setSpeed(int speed) {
	this.speed = speed;
    }

    public boolean isDestroyed() {
	return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
	this.destroyed = destroyed;
    }

}
