package com.thiefg.asteroids.userinterface;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.subobjects.Vector2d;

public class PlayerModel {
	private static final int OUT_POINT_ANGLE = 135;
	private static final int BACK_POINT_DISTANCE = 2;
	private static final int FRONT_POINT_DISTANCE = 5;
	private static final int OUT_POINT_DISTANCE = 4;
	private static final int SCALE_MULTIPLYER = 3;
	double rotation;
	Vector2d location;
	ArrayList<Vector2d> points;

	public PlayerModel(Vector2d location, double rotation) {
		this.location = location;
		this.rotation = rotation;
		points = new ArrayList<Vector2d>();
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

	public void render() {
		GL11.glColor3d(1, 1, 1);
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

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public Vector2d getLocation() {
		return location;
	}

	public void setLocation(Vector2d location) {
		this.location = location;
	}

	public ArrayList<Vector2d> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Vector2d> points) {
		this.points = points;
	}
}
