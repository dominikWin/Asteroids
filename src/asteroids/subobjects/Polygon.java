package asteroids.subobjects;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

/**
 * @author Dominik
 * Perfect Polygon class
 */
public class Polygon {
	private Vector2d location;
	private ArrayList<Vector2d> points;
	private double raduis;
	private double rotation;
	private int sides;

	/**
	 * @param location
	 * @param sides
	 * @param radius
	 * Creates polygon with size, radius and location
	 */
	public Polygon(Vector2d location, int sides, double radius) {
		setLocation(location);
		setSides(sides);
		raduis = radius;
		setPoints(new ArrayList<Vector2d>());
	}

	/**
	 * @param location
	 * @param sides
	 * @param radius
	 * @param rotation
	 * Creates polygon with size, radius, location and rotation
	 */
	public Polygon(Vector2d location, int sides, int radius, double rotation) {
		setLocation(location);
		setSides(sides);
		this.rotation = rotation;
		raduis = radius;
		setPoints(new ArrayList<Vector2d>());
	}

	public Vector2d getLocation() {
		return location;
	}

	public ArrayList<Vector2d> getPoints() {
		return points;
	}

	public double getRaduis() {
		return raduis;
	}

	public double getRotation() {
		return rotation;
	}

	public int getSides() {
		return sides;
	}

	public void render() {
		if (getPoints().isEmpty()) updatePoints();
		GL11.glBegin(GL11.GL_LINES);
		//Connect points
		for (int i = 0; i < (getPoints().size() - 1); i++) {
			GL11.glVertex2i((int) (getPoints().get(i).getX()), (int) (getPoints().get(i).getY()));
			GL11.glVertex2i((int) (getPoints().get(i + 1).getX()), (int) (getPoints().get(i + 1).getY()));
		}
		//Connect first & last points
		GL11.glVertex2i((int) (getPoints().get(0).getX()), (int) (getPoints().get(0).getY()));
		GL11.glVertex2i((int) (getPoints().get(getPoints().size() - 1).getX()), (int) (getPoints().get(getPoints().size() - 1).getY()));
		
		GL11.glEnd();
	}

	/**
	 * Renders point at center
	 */
	public void renderPoint() {
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glVertex2i((int) (location.getX()), (int) (location.getY()));
		GL11.glEnd();
	}

	public void setLocation(Vector2d location) {
		this.location = location;
	}

	public void setPoints(ArrayList<Vector2d> points) {
		this.points = points;
	}

	public void setRaduis(double raduis) {
		this.raduis = raduis;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	/**
	 * Recalculates points
	 */
	public void updatePoints() {
		setPoints(new ArrayList<Vector2d>()); //Clear points
		
		//Add points at angle
		double innerAngle = 360d / sides;
		for (int i = 0; i < sides; i++)
			getPoints().add(new Vector2d(location, (innerAngle * i) + (innerAngle / 2d) + rotation, raduis));
	}
}
