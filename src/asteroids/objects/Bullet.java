package asteroids.objects;

import java.util.ArrayList;

import asteroids.Game;
import asteroids.subobjects.Polygon;
import asteroids.subobjects.Vector2d;

/**
 * @author Dominik
 *
 */
public class Bullet {
	private static final int RADIUS = 4;
	private static final boolean RELATIVE_BULLET_SPEED = false;
	private static final int SIDES = 8;
	private boolean destroyed;
	Vector2d location;
	double rotation;
	Polygon shape;
	int speed;

	/**
	 * @param location
	 * @param rotation
	 * @param speed
	 * Creates bullet at location with rotation & speed
	 */
	public Bullet(Vector2d location, double rotation, int speed) {
		this.location = new Vector2d(location.getX(), location.getY());
		this.rotation = rotation;
		this.speed = speed;
		setDestroyed(false);
		shape = new Polygon(location, Bullet.SIDES, Bullet.RADIUS);
		if (Bullet.RELATIVE_BULLET_SPEED)
			//Add player speed to bullet speed
			//WARNING!!! Non-directional
			speed += Vector2d.distance(Game.getInstance().getWorld().getPlayer().getVelocity(), new Vector2d(0, 0));
	}

	/**
	 * Test for collisions with asteroids
	 */
	private void collisionTest() {
		//For each asteroid
		ArrayList<Asteroid> asteroids = Game.getInstance().getWorld().getAsteroids();
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid a = asteroids.get(i);
			if (Vector2d.distance(a.getLocation(), location) < a.getSize()) {
				//Bullet his asteroid
				a.hit(); //Hit asteroid
				setDestroyed(true); //Destroy Bullet
				Game.getInstance().getWorld().getPlayer().addScore(a.getSize()); //Update player score
				Game.getInstance()
						.getWorld()
						.addParticleEffect(
								new ParticleEffect(location, a.getSize() * 4, a.getSize() / 6, new Vector2d(new Vector2d(0, 0), rotation, speed), .5)); //Add new explosion
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

	/**
	 * Updates bullet
	 * @param time 
	 */
	public void update(double time) {
		location = new Vector2d(location, rotation, (double) speed * time);
		shape.setLocation(location);
		shape.updatePoints();
		collisionTest();
	}
}
