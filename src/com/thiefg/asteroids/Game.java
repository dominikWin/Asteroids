package com.thiefg.asteroids;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.userinterface.UserInterface;

public class Game {
	private static boolean VSYNC = true;
	private static boolean FULLSCREEN = true;
	public static int FRAME_CAP = Display.getDesktopDisplayMode()
			.getFrequency();
	public static int HEIGHT = Display.getDesktopDisplayMode().getHeight();
	public static int WIDTH = Display.getDesktopDisplayMode().getWidth();
	private static World world;
	private static UserInterface ui;

	public static void main(String[] args) throws LWJGLException {
		new Game();
	}

	public Game() {
		try {
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setFullscreen(FULLSCREEN);
			Display.setVSyncEnabled(VSYNC);
			Display.setResizable(false);
			WIDTH = Display.getDisplayMode().getWidth();
			HEIGHT = Display.getDisplayMode().getHeight();
			FRAME_CAP = Display.getDisplayMode().getFrequency();
			System.out.println(Display.getDisplayMode());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		;
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		init();
		start();
	}

	private void start() {
		while (!Display.isCloseRequested()) {
			update();
			render();

			Display.update();
			glClear(GL_COLOR_BUFFER_BIT);
			Display.sync(FRAME_CAP);
		}
		Display.destroy();
		System.exit(0);
	}

	private void render() {
		world.render();
		ui.render();
	}

	private void update() {
		Input.update();
		world.update();
		ui.update();
	}

	private void init() {
//		GL11.glEnable(GL11.GL_TEXTURE_2D);
//		GL11.glShadeModel(GL11.GL_SMOOTH);
//		GL11.glDisable(GL11.GL_DEPTH_TEST);
//		GL11.glDisable(GL11.GL_LIGHTING);
//
//		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//		GL11.glClearDepth(1);

		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		Input.update();
		setWorld(new World());
		ui = new UserInterface();
		Input.showMouse(false);
	}

	public static World getWorld() {
		return world;
	}

	public static void setWorld(World world) {
		Game.world = world;
	}

	public static UserInterface getUi() {
		return ui;
	}

	public static void setUi(UserInterface ui) {
		Game.ui = ui;
	}
}
