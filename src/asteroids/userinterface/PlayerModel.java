package asteroids.userinterface;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import asteroids.subobjects.Vector2d;

/**
 * @author Dominik
 * Empty player shape for life bar, only for player HUD
 */
public class PlayerModel {
	private static final int BACK_POINT_DISTANCE = 2;
	private static final int FRONT_POINT_DISTANCE = 5;
	private static final int OUT_POINT_ANGLE = 135;
	private static final int OUT_POINT_DISTANCE = 4;
	private static final int SCALE_MULTIPLYER = 3;
	Vector2d location;
	ArrayList<Vector2d> points;
	double rotation;

	/**
	 * Creates PlayerModel at location & rotation
	 * @param location
	 * @param rotation
	 */
	public PlayerModel(Vector2d location, double rotation) {
		this.location = location;
		this.rotation = rotation;
		points = new ArrayList<Vector2d>();
		updatePoints();
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

	/**
	 * Renders model
	 */
	public void render() {
		GL11.glColor3d(1, 1, 1);
		GL11.glBegin(GL11.GL_LINES);
		//Connect lines
		for (int i = 0; i < (points.size() - 1); i++) {
			GL11.glVertex2i((int) (points.get(i).getX()), (int) (points.get(i).getY()));
			GL11.glVertex2i((int) (points.get(i + 1).getX()), (int) (points.get(i + 1).getY()));
		}
		//Connect first & last point
		GL11.glVertex2i((int) (points.get(0).getX()), (int) (points.get(0).getY()));
		GL11.glVertex2i((int) (points.get(points.size() - 1).getX()), (int) (points.get(points.size() - 1).getY()));
		
		GL11.glEnd();
		GL11.glColor3d(1d, 1d, 1d);
	}

	/**
	 * Renders point at center of model
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

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	/**
	 * Recalculates the point locations
	 */
	public void updatePoints() {
		points.clear();
		points.add(new Vector2d(location, rotation, PlayerModel.FRONT_POINT_DISTANCE * PlayerModel.SCALE_MULTIPLYER));
		points.add(new Vector2d(location, rotation + PlayerModel.OUT_POINT_ANGLE, PlayerModel.OUT_POINT_DISTANCE * PlayerModel.SCALE_MULTIPLYER));
		points.add(new Vector2d(location, rotation + 180, PlayerModel.BACK_POINT_DISTANCE * PlayerModel.SCALE_MULTIPLYER));
		points.add(new Vector2d(location, rotation - PlayerModel.OUT_POINT_ANGLE, PlayerModel.OUT_POINT_DISTANCE * PlayerModel.SCALE_MULTIPLYER));
	}
}
