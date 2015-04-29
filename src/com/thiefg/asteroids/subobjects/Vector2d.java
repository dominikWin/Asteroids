package com.thiefg.asteroids.subobjects;

public class Vector2d {
    public static Vector2d add(Vector2d obj1, Vector2d obj2) {
	return new Vector2d(obj1.x + obj2.x, obj1.y + obj2.y);
    }

    public static double distance(Vector2d obj1, Vector2d obj2) {
	return Math.sqrt(Math.pow(obj1.getX() - obj2.getX(), 2)
		+ Math.pow(obj1.y - obj2.y, 2));
    }

    public static Vector2d rotate(Vector2d obj, Vector2d base, double angle) {
	if (angle == 0)
	    return obj;
	angle = Math.toRadians(angle);
	double sin = Math.sin(angle);
	double cos = Math.cos(angle);

	obj.x -= base.x;
	obj.y -= base.y;
	double x = (obj.x * cos) + (obj.y * sin);
	double y = (obj.x * sin) + (obj.y * cos);
	x += base.x;
	y += base.y;
	return new Vector2d((double) x, (double) y);
    }

    public static Vector2d avarage(Vector2d obj1, Vector2d obj2) {
	return new Vector2d((obj1.x + obj2.x) / 2, (obj1.x + obj2.x) / 2);
    }

    public static Vector2d between(Vector2d obj1, Vector2d obj2,
	    double object1Bias) {
	return new Vector2d((obj1.x * object1Bias)
		+ (obj2.x * (1 - object1Bias)) / 2, (obj1.y * object1Bias)
		+ (obj2.y * (1 - object1Bias)) / 2);
    }

    private double x, y;

    public Vector2d(double x, double y) {
	this.x = x;
	this.y = y;
    }

    public Vector2d(Vector2d base, double angle, double distance) {
	this.x = Vector2d.rotate(new Vector2d(base.x + distance, base.y), base,
		angle).x;
	this.y = Vector2d.rotate(new Vector2d(base.x + distance, base.y), base,
		angle).y;
    }

    public void add(Vector2d other) {
	this.x += other.x;
	this.y += other.y;
    }

    public double getX() {
	return this.x;
    }

    public double getY() {
	return this.y;
    }

    public void rotate(double angle) {
	double _x = Vector2d.rotate(this, new Vector2d(0, 0), angle).x;
	double _y = Vector2d.rotate(this, new Vector2d(0, 0), angle).y;
	this.x = _x;
	this.y = _y;
    }

    public void rotate(Vector2d base, double angle) {
	double _x = Vector2d.rotate(this, base, angle).x;
	double _y = Vector2d.rotate(this, base, angle).y;
	this.x = _x;
	this.y = _y;
    }

    public void multiply(double amount) {
	x *= amount;
	y *= amount;
    }

    public void setX(double x) {
	this.x = x;
    }

    public void setY(double y) {
	this.y = y;
    }

    @Override
    public String toString() {
	return "Vector2d[" + x + ", " + y + "]";
    }
}
