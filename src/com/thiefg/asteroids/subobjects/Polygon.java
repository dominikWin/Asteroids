package com.thiefg.asteroids.subobjects;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class Polygon {
    private Vector2d location;
    private ArrayList<Vector2d> points;
    private double raduis;
    private double rotation;
    private int sides;

    public Polygon(Vector2d location, int sides, double radius) {
	this.setLocation(location);
	this.setSides(sides);
	this.raduis = radius;
	setPoints(new ArrayList<Vector2d>());
    }

    public Polygon(Vector2d location, int sides, int radius, double rotation) {
	this.setLocation(location);
	this.setSides(sides);
	this.rotation = rotation;
	this.raduis = radius;
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
	if (getPoints().isEmpty())
	    update();
	glBegin(GL11.GL_LINES);
	for (int i = 0; i < getPoints().size() - 1; i++) {
	    glVertex2i((int) (getPoints().get(i).getX()), (int) (getPoints()
		    .get(i).getY()));
	    glVertex2i((int) (getPoints().get(i + 1).getX()),
		    (int) (getPoints().get(i + 1).getY()));
	}
	glVertex2i((int) (getPoints().get(0).getX()),
		(int) (getPoints().get(0).getY()));
	glVertex2i((int) (getPoints().get(getPoints().size() - 1).getX()),
		(int) (getPoints().get(getPoints().size() - 1).getY()));
	glEnd();
    }

    public void renderPoint() {
	glBegin(GL11.GL_POINTS);
	glVertex2i((int) (location.getX()), (int) (location.getY()));
	glEnd();
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

    public void update() {
	setPoints(new ArrayList<Vector2d>());
	double innerAngle = 360d / sides;
	for (int i = 0; i < sides; i++) {
	    getPoints().add(
		    new Vector2d(location, innerAngle * i + (innerAngle / 2d)
			    + rotation, raduis));
	}
    }
}
