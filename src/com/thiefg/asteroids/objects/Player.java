package com.thiefg.asteroids.objects;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.objects.modifiers.GunModifier;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Player {

    private static final int BACK_POINT_DISTANCE = 2;
    private static final int BULLET_SPEED = 15;
    private static final double COLOR_SWEEP_SPEED = 3d;
    private static final double DUAL_GUN_ANGLE = 4;
    private static final int FIRE_DELAY_FAST_MILS = 20;
    private static final int FIRE_DELAY_MILS = 80;
    private static final int FRONT_POINT_DISTANCE = 5;
    private static final double HORIZONTAL_MOVEMENT_SPEED = .2;
    private static final int INITIAL_LIVES = 3;
    private static final double MAX_MOVEMENT_SPEED = 8;
    private static final double MOVEMENT_SPEED = .4;
    private static final int OUT_POINT_ANGLE = 135;
    private static final int OUT_POINT_DISTANCE = 4;
    private static final double QUAD_GUN_ANGLE = 4;
    private static final int SCALE_MULTIPLYER = 5;
    private static final double TRIPLE_GUN_ANGLE = 10;
    private static final double VELOCITY_DRAG_MULTIPLYER = .98;

    private GunModifier gunModifier;
    long lastFire = 0;
    private int livesLeft;
    Vector2d location, velocity;
    ArrayList<Vector2d> points;
    double rotation;
    private int score;

    public Player(Vector2d location, double rotation) {
	this.location = location;
	velocity = new Vector2d(0, 0);
	this.rotation = rotation;
	points = new ArrayList<Vector2d>();
	gunModifier = GunModifier.SINGLE;
	setLivesLeft(INITIAL_LIVES);
	updatePoints();
    }

    public void addScore(int asteroidSize) {
	score += asteroidSize * Vector2d.distance(new Vector2d(0, 0), velocity)
		/ 8;
    }

    private void collosionTest() {
	ArrayList<Asteroid> asteroids = Game.getInstance().getWorld()
		.getAsteroids();
	for (int i = 0; i < asteroids.size(); i++) {
	    Asteroid a = asteroids.get(i);
	    if (Vector2d.distance(a.getLocation(), location) < a.getSize()
		    + SCALE_MULTIPLYER
		    && !a.isDestroyed()) {
		livesLeft--;
		a.setDestroyed(true);
		if (livesLeft < 0)
		    Game.getInstance().playerDied();
	    }
	}
    }

    private void fire() {
	switch (gunModifier) {
	case SINGLE:
	case FAST:
	    Game.getInstance().getWorld()
		    .addBullet(new Bullet(location, rotation, BULLET_SPEED));
	    break;
	case DUAL:
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + DUAL_GUN_ANGLE,
				    BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation - DUAL_GUN_ANGLE,
				    BULLET_SPEED));
	    break;
	case OCT:
	    Game.getInstance().getWorld()
		    .addBullet(new Bullet(location, rotation, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 45, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 90, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 135, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 180, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 225, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 270, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 315, BULLET_SPEED));
	    break;
	case QUAD:
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + 2 * QUAD_GUN_ANGLE,
				    BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + QUAD_GUN_ANGLE,
				    BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation - QUAD_GUN_ANGLE,
				    BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation - 2 * QUAD_GUN_ANGLE,
				    BULLET_SPEED));
	    break;
	case TRIPLE:
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation + TRIPLE_GUN_ANGLE,
				    BULLET_SPEED));
	    Game.getInstance().getWorld()
		    .addBullet(new Bullet(location, rotation, BULLET_SPEED));
	    Game.getInstance()
		    .getWorld()
		    .addBullet(
			    new Bullet(location, rotation - TRIPLE_GUN_ANGLE,
				    BULLET_SPEED));
	    break;
	default:
	    break;
	}
    }

    private void fireRequest() {
	long current = System.currentTimeMillis();
	if (lastFire < current
		- (getGunModifier() == GunModifier.FAST ? FIRE_DELAY_FAST_MILS
			: FIRE_DELAY_MILS)) {
	    lastFire = current;
	    fire();
	}
    }

    public GunModifier getGunModifier() {
	return gunModifier;
    }

    public long getLastFire() {
	return lastFire;
    }

    public int getLivesLeft() {
	return livesLeft;
    }

    public Vector2d getLocation() {
	return location;
    }

    public ArrayList<Vector2d> getPoints() {
	return points;
    }

    public double getRotation() {
	return rotation;
    }

    public int getScore() {
	return score;
    }

    public void render() {
	GL11.glColor3d(
		(Math.sin(System.currentTimeMillis()
			/ (1000d / COLOR_SWEEP_SPEED)) + 1d) / 2d,
		(Math.sin(System.currentTimeMillis()
			/ (1000d / COLOR_SWEEP_SPEED) + (Math.PI * (2d / 3d))) + 1d) / 2d,
		(Math.sin(System.currentTimeMillis()
			/ (1000d / COLOR_SWEEP_SPEED) + (Math.PI * (4d / 3d))) + 1d) / 2d);
	glBegin(GL11.GL_LINES);
	for (int i = 0; i < points.size() - 1; i++) {
	    glVertex2i((int) (points.get(i).getX()),
		    (int) (points.get(i).getY()));
	    glVertex2i((int) (points.get(i + 1).getX()),
		    (int) (points.get(i + 1).getY()));
	}
	glVertex2i((int) (points.get(0).getX()), (int) (points.get(0).getY()));
	glVertex2i((int) (points.get(points.size() - 1).getX()),
		(int) (points.get(points.size() - 1).getY()));
	glEnd();
	GL11.glColor3d(1d, 1d, 1d);
    }

    public void renderPoint() {
	glBegin(GL11.GL_POINTS);
	glVertex2i((int) (location.getX()), (int) (location.getY()));
	glEnd();
    }

    public void setGunModifier(GunModifier gunModifier) {
	this.gunModifier = gunModifier;
    }

    public void setLastFire(long lastFire) {
	this.lastFire = lastFire;
    }

    public void setLivesLeft(int livesLeft) {
	this.livesLeft = livesLeft;
    }

    public void setLocation(Vector2d location) {
	this.location = location;
    }

    public void setPoints(ArrayList<Vector2d> points) {
	this.points = points;
    }

    public void setRotation(double rotation) {
	this.rotation = rotation;
    }

    public void setScore(int score) {
	this.score = score;
    }

    public void update() {
	if (Game.devMode) {
	    if (Input.getKeyDown(Keyboard.KEY_1))
		gunModifier = GunModifier.SINGLE;
	    if (Input.getKeyDown(Keyboard.KEY_2))
		gunModifier = GunModifier.DUAL;
	    if (Input.getKeyDown(Keyboard.KEY_3))
		gunModifier = GunModifier.TRIPLE;
	    if (Input.getKeyDown(Keyboard.KEY_4))
		gunModifier = GunModifier.QUAD;
	    if (Input.getKeyDown(Keyboard.KEY_8))
		gunModifier = GunModifier.OCT;
	    if (Input.getKeyDown(Keyboard.KEY_EQUALS))
		gunModifier = GunModifier.FAST;
	}
	if (Input.getKey(Keyboard.KEY_LEFT))
	    rotation -= 5;
	if (Input.getKey(Keyboard.KEY_RIGHT))
	    rotation += 5;
	if (Input.getKey(Keyboard.KEY_W))
	    // location = new Vector2d(location, rotation, MOVEMENT_SPEED);
	    velocity.add(new Vector2d(new Vector2d(0, 0), rotation,
		    MOVEMENT_SPEED));
	else if (Input.getKey(Keyboard.KEY_S))
	    // location = new Vector2d(location, rotation + 180,
	    // MOVEMENT_SPEED);
	    velocity.add(new Vector2d(new Vector2d(0, 0), rotation + 180,
		    MOVEMENT_SPEED));
	else if (Input.getKey(Keyboard.KEY_D))
	    // location = new Vector2d(location, rotation + 90, MOVEMENT_SPEED);
	    velocity.add(new Vector2d(new Vector2d(0, 0), rotation + 90,
		    HORIZONTAL_MOVEMENT_SPEED));
	else if (Input.getKey(Keyboard.KEY_A))
	    // location = new Vector2d(location, rotation + 270,
	    // MOVEMENT_SPEED);
	    velocity.add(new Vector2d(new Vector2d(0, 0), rotation + 270,
		    HORIZONTAL_MOVEMENT_SPEED));
	if (Input.getKey(Keyboard.KEY_SPACE))
	    fireRequest();

	velocity.multiply(VELOCITY_DRAG_MULTIPLYER);

	if (Vector2d.distance(velocity, new Vector2d(0, 0)) > MAX_MOVEMENT_SPEED) {
	    double multiplyer = MAX_MOVEMENT_SPEED
		    / Vector2d.distance(velocity, new Vector2d(0, 0));
	    velocity.multiply(multiplyer);
	}

	location.add(velocity);

	while (location.getX() < 0)
	    location.setX(location.getX() + Game.WIDTH);
	while (location.getX() > Game.WIDTH)
	    location.setX(location.getX() - Game.WIDTH);
	while (location.getY() < 0)
	    location.setY(location.getY() + Game.HEIGHT);
	while (location.getY() > Game.HEIGHT)
	    location.setY(location.getY() - Game.HEIGHT);
	collosionTest();
	updatePoints();
    }

    public void updatePoints() {
	points.clear();
	points.add(new Vector2d(location, rotation, FRONT_POINT_DISTANCE
		* SCALE_MULTIPLYER));
	points.add(new Vector2d(location, rotation + OUT_POINT_ANGLE,
		OUT_POINT_DISTANCE * SCALE_MULTIPLYER));
	points.add(new Vector2d(location, rotation + 180, BACK_POINT_DISTANCE
		* SCALE_MULTIPLYER));
	points.add(new Vector2d(location, rotation - OUT_POINT_ANGLE,
		OUT_POINT_DISTANCE * SCALE_MULTIPLYER));
    }

}
