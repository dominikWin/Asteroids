package com.thiefg.asteroids.subobjects;

import java.util.ArrayList;

/**
 * @author Dominik
 * Polygon with custom distances from center
 */
public class VariableDistancePolygon extends Polygon {
	ArrayList<Double> distances;

	/**
	 * @param location
	 * @param sides
	 * @param radius
	 * Creates VariableDistancePolygon at location, sides and radius
	 */
	public VariableDistancePolygon(Vector2d location, int sides, double radius) {
		super(location, sides, radius);
		distances = new ArrayList<>();
		for (int i = 0; i < sides; i++)
			distances.add(new Double((radius * Math.random() * (2 - .5)) + .5));
	}

	/**
	 * @param location
	 * @param sides
	 * @param radius
	 * @param rotation
	 * Creates VariableDistancePolygon at location, sides, radius and rotation
	 */
	public VariableDistancePolygon(Vector2d location, int sides, int radius, double rotation) {
		super(location, sides, radius, rotation);
		distances = new ArrayList<>();
		for (int i = 0; i < sides; i++)
			distances.add(new Double((radius * Math.random() * (2 - .5)) + .5));
	}

	/**
	 * @param location
	 * @param sides
	 * @param radius
	 * @param rotation
	 * @param minChange
	 * @param maxChange
	 * Creates VariableDistancePolygon at location, sides, radius and rotation
	 * Distances are set at random between minChange & maxChange
	 */
	public VariableDistancePolygon(Vector2d location, int sides, int radius, double rotation, double minChange, double maxChange) {
		super(location, sides, radius, rotation);
		distances = new ArrayList<>();
		for (int i = 0; i < sides; i++)
			distances.add(new Double((radius * Math.random() * (maxChange - minChange)) + minChange));
	}

	@Override
	public void updatePoints() {
		setPoints(new ArrayList<Vector2d>()); //Clear points
		//Add points at proper locations
		double innerAngle = 360d / super.getSides();
		for (int i = 0; i < super.getSides(); i++)
			getPoints().add(new Vector2d(super.getLocation(), (innerAngle * i) + (innerAngle / 2d) + super.getRotation(), distances.get(i).doubleValue()));
	}
}
