package asteroids.objects;

import asteroids.Game;
import asteroids.subobjects.VariableDistancePolygon;
import asteroids.subobjects.Vector2d;

/**
 * @author Dominik
 *
 */
public class Asteroid {
	private static final int SHAPE_SIDES = 6;
	private static final int SMALLEST_SIZE = 8;

	/**
	 * @return new random location away from player
	 */
	public static Vector2d getRandomLocation() {
		while (true) {
			Vector2d loc = new Vector2d(Math.random() * Game.width, Math.random() * Game.height);
			if (Vector2d.distance(loc, Game.getInstance().getWorld().getPlayer().getLocation()) > (Game.width / 6)) return loc;
		}
	}

	private boolean destroyed;
	private Vector2d location;
	private double rotation;
	private double rotationSpeed;
	private VariableDistancePolygon shape;
	private int size = 32;
	private Vector2d velocity;

	/**
	 * @param location
	 * Creates Asteroid at location
	 */
	public Asteroid(Vector2d location) {
		this.location = location;
		rotation = 0;
		rotationSpeed = 0;
		shape = new VariableDistancePolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	/**
	 * @param location
	 * @param rotation
	 * Creates asteroid at location & rotation
	 */
	public Asteroid(Vector2d location, double rotation) {
		this.location = location;
		this.rotation = rotation;
		rotationSpeed = 0;
		shape = new VariableDistancePolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	/**
	 * @param location
	 * @param rotation
	 * @param rotationSpeed
	 * Creates asteroid at location & rotation that rotates at rotationSpeed
	 */
	public Asteroid(Vector2d location, double rotation, double rotationSpeed) {
		this.location = location;
		this.rotation = rotation;
		this.rotationSpeed = rotationSpeed;
		shape = new VariableDistancePolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	/**
	 * @param location
	 * @param rotation
	 * @param rotationSpeed
	 * @param size
	 * Creates asteroid at location & rotation that rotates at rotationSpeed & size
	 */
	public Asteroid(Vector2d location, double rotation, double rotationSpeed, int size) {
		this.location = location;
		this.rotation = rotation;
		this.rotationSpeed = rotationSpeed;
		this.size = size;
		shape = new VariableDistancePolygon(location, Asteroid.SHAPE_SIDES, size, rotation, .5, 2);
		setVelocity(getRandosmVelocity());
		destroyed = false;
	}

	public Vector2d getLocation() {
		return location;
	}

	/**
	 * @return random Vector2d for velocity between -5 - 5
	 */
	private Vector2d getRandosmVelocity() {
		return new Vector2d((Math.random() * 10d) - 5d, (Math.random() * 10d) - 5d);
	}

	public double getRotation() {
		return rotation;
	}

	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public VariableDistancePolygon getShape() {
		return shape;
	}

	public int getSize() {
		return size;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	/**
	 * Handles actions if asteroid hit
	 */
	public void hit() {
		destroyed = true;
		if (size <= Asteroid.SMALLEST_SIZE) return;
		//Add two smaller asteroids at location
		Game.getInstance().getWorld().addAsteroid(new Asteroid(new Vector2d(location.getX(), location.getY()), Math.random() * 360, rotationSpeed, size / 2));
		Game.getInstance().getWorld().addAsteroid(new Asteroid(new Vector2d(location.getX(), location.getY()), Math.random() * 360, rotationSpeed, size / 2));
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

	public void setShape(VariableDistancePolygon shape) {
		this.shape = shape;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}

	/**
	 * Updates asteroid
	 */
	public void update() {
		//Keeps asteroid in screen
		while (location.getX() < 0) location.setX(location.getX() + Game.width);
		while (location.getX() > Game.width) location.setX(location.getX() - Game.width);
		while (location.getY() < 0) location.setY(location.getY() + Game.height);
		while (location.getY() > Game.height) location.setY(location.getY() - Game.height);
		rotation += rotationSpeed; //Rotate asteroid
		location.add(getVelocity()); //Move
		shape.setRotation(rotation); //Update location
		shape.updatePoints();
	}
}
