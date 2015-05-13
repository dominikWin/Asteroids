package com.thiefg.asteroids;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.userinterface.Background;
import com.thiefg.asteroids.userinterface.UserInterface;

public class Game implements Runnable {
	public enum GameState {
		DEATH, GAMEPLAY, MAIN_MENU, PAUSED;
	}

	public static boolean devMode = false;
	public static int FRAME_CAP = Display.getDesktopDisplayMode().getFrequency();
	private static boolean FULLSCREEN = true;
	public static int HEIGHT = Display.getDesktopDisplayMode().getHeight();
	private static Game instance;
	private static boolean VSYNC = true;
	public static int WIDTH = Display.getDesktopDisplayMode().getWidth();
	public static Game getInstance() {
		if (Game.instance == null) Game.instance = new Game();
		return Game.instance;
	}

	public static void main(String[] args) throws LWJGLException {
		new Thread(Game.getInstance()).start();
	}

	private Background background;

	private GameState currentGameState = GameState.MAIN_MENU;
	private Progress progress;
	private UserInterface ui;
	private World world;

	public Game() {
	}

	public void exit() {
		System.exit(0);
	}

	public void gameStart() {
		progress.init();
	}

	public Background getBackground() {
		return background;
	}

	public GameState getCurrentGameState() {
		return currentGameState;
	}

	public Progress getProgress() {
		return progress;
	}

	public UserInterface getUi() {
		return ui;
	}

	public World getWorld() {
		return world;
	}

	private void init() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Game.WIDTH, Game.HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		Input.update();
		world = new World();
		ui = new UserInterface();
		progress = new Progress();
		background = new Background();
		Input.showMouse(false);
	}

	public void pause() {
		setCurrentGameState(GameState.PAUSED);
	}

	public void playerDied() {
		currentGameState = GameState.DEATH;
	}

	private void render() {
		background.render();
		if (currentGameState == GameState.GAMEPLAY) world.render();
		ui.render();
	}

	public void resetGame() {
		currentGameState = GameState.MAIN_MENU;
		setWorld(new World());
		progress = new Progress();
	}

	@Override
	public void run() {
		try {
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setFullscreen(Game.FULLSCREEN);
			Display.setVSyncEnabled(Game.VSYNC);
			Display.setResizable(false);
			Game.WIDTH = Display.getDisplayMode().getWidth();
			Game.HEIGHT = Display.getDisplayMode().getHeight();
			Game.FRAME_CAP = Display.getDisplayMode().getFrequency();
			System.out.println(Display.getDisplayMode());
		} catch (LWJGLException e) {
			e.printStackTrace();
		};
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		init();
		while (!Display.isCloseRequested()) {
			update();
			render();
			Display.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			Display.sync(Game.FRAME_CAP);
		}
		Display.destroy();
	}

	public void setBackground(Background background) {
		this.background = background;
	}

	public void setCurrentGameState(GameState currentGameState) {
		this.currentGameState = currentGameState;
		gameStart();
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	public void setUi(UserInterface ui) {
		this.ui = ui;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void unpause() {
		setCurrentGameState(GameState.GAMEPLAY);
	}

	private void update() {
		Input.update();
		background.update();
		if (currentGameState == GameState.GAMEPLAY) {
			progress.update();
			world.update();
		}
		ui.update();
	}
}
