package com.thiefg.asteroids.subobjects;

import java.util.ArrayList;

public class DistortedPolygon extends Polygon {

    ArrayList<Double> distances;

    public DistortedPolygon(Vector2d location, int sides, double radius) {
	super(location, sides, radius);
	distances = new ArrayList<>();
	for (int i = 0; i < sides; i++) {
	    distances.add(new Double(radius * Math.random() * (2-.5) + .5));
	}
    }

    public DistortedPolygon(Vector2d location, int sides, int radius,
	    double rotation) {
	super(location, sides, radius, rotation);
	distances = new ArrayList<>();
	for (int i = 0; i < sides; i++) {
	    distances.add(new Double(radius * Math.random() * (2-.5) + .5));
	}
    }

    public DistortedPolygon(Vector2d location, int sides, int radius,
	    double rotation, double minChange, double maxChange) {
	super(location, sides, radius, rotation);
	distances = new ArrayList<>();
	for (int i = 0; i < sides; i++) {
	    distances.add(new Double(radius * Math.random() * (maxChange-minChange) + minChange));
	}
    }

    @Override
    public void update() {
	setPoints(new ArrayList<Vector2d>());
	double innerAngle = 360d / super.getSides();
	for (int i = 0; i < super.getSides(); i++) {
	    getPoints().add(
		    new Vector2d(super.getLocation(), innerAngle * i
			    + (innerAngle / 2d) + super.getRotation(), distances.get(i).doubleValue()));
	}
    }

}
