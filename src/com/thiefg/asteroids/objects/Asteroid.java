package com.thiefg.asteroids.objects;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.DistortedPolygon;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Asteroid {
    private static final int SMALLEST_SIZE = 8;
    private static final int SHAPE_SIDES = 6;
    private int size = 32;
    private DistortedPolygon shape;
    private double rotation;
    private double rotationSpeed;
    private Vector2d location;
    private Vector2d velocity;
    private boolean destroyed;
    


    public Asteroid(Vector2d location) {
	this.location = location;
	rotation = 0;
	rotationSpeed = 0;
	shape = new DistortedPolygon(location, SHAPE_SIDES, size, rotation, .5,
		2);
	setVelocity(getRandosmVelocity());
	destroyed = false;
    }

    public Asteroid(Vector2d location, double rotation) {
	this.location = location;
	this.rotation = rotation;
	rotationSpeed = 0;
	shape = new DistortedPolygon(location, SHAPE_SIDES, size, rotation, .5,
		2);
	setVelocity(getRandosmVelocity());
	destroyed = false;
    }

    public Asteroid(Vector2d location, double rotation, double rotationSpeed) {
	this.location = location;
	this.rotation = rotation;
	this.rotationSpeed = rotationSpeed;
	shape = new DistortedPolygon(location, SHAPE_SIDES, size, rotation, .5,
		2);
	setVelocity(getRandosmVelocity());
	destroyed = false;
    }

    public Asteroid(Vector2d location, double rotation, double rotationSpeed,
	    int size) {
	this.location = location;
	this.rotation = rotation;
	this.rotationSpeed = rotationSpeed;
	this.size = size;
	shape = new DistortedPolygon(location, SHAPE_SIDES, size, rotation, .5,
		2);
	setVelocity(getRandosmVelocity());
	destroyed = false;
    }

    public void hit() {
	destroyed = true;
	if (size <= SMALLEST_SIZE)
	    return;
	Game.getWorld().addAsteroid(
		new Asteroid(new Vector2d(location.getX(), location.getY()),
			Math.random() * 360, rotationSpeed, size / 2));
	Game.getWorld().addAsteroid(
		new Asteroid(new Vector2d(location.getX(), location.getY()),
			Math.random() * 360, rotationSpeed, size / 2));
	Game.getWorld().asteroidAddChance += .0002;
    }

    private Vector2d getRandosmVelocity() {
	return new Vector2d(Math.random() * 10d - 5d, Math.random() * 10d - 5d);
    }

    public void update() {
	if (location.getX() < 0)
	    location.setX(location.getX() + Game.WIDTH);
	if (location.getX() > Game.WIDTH)
	    location.setX(location.getX() - Game.WIDTH);
	if (location.getY() < 0)
	    location.setY(location.getY() + Game.HEIGHT);
	if (location.getY() > Game.HEIGHT)
	    location.setY(location.getY() - Game.HEIGHT);
	rotation += rotationSpeed;
	location.add(getVelocity());
	shape.setRotation(rotation);
	shape.update();
    }

    public void render() {
	shape.render();
    }

    public DistortedPolygon getShape() {
	return shape;
    }

    public void setShape(DistortedPolygon shape) {
	this.shape = shape;
    }

    public double getRotation() {
	return rotation;
    }

    public void setRotation(double rotation) {
	this.rotation = rotation;
    }

    public double getRotationSpeed() {
	return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
	this.rotationSpeed = rotationSpeed;
    }

    public Vector2d getLocation() {
	return location;
    }

    public void setLocation(Vector2d location) {
	this.location = location;
    }

    public Vector2d getVelocity() {
	return velocity;
    }

    public void setVelocity(Vector2d velocity) {
	this.velocity = velocity;
    }

    public boolean isDestroyed() {
	return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
	this.destroyed = destroyed;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

}
