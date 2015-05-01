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
import com.thiefg.asteroids.userinterface.Background;
import com.thiefg.asteroids.userinterface.UserInterface;

public class Game {

    public enum GameState {
	DEATH, GAMEPLAY, MAIN_MENU, PAUSED;
    }

    private static Background background;
    private static GameState currentGameState = GameState.MAIN_MENU;
    public static boolean devMode = false;
    public static int FRAME_CAP = Display.getDesktopDisplayMode()
	    .getFrequency();
    private static boolean FULLSCREEN = true;
    private static Game game;
    public static int HEIGHT = Display.getDesktopDisplayMode().getHeight();
    private static Progress progress;
    private static UserInterface ui;
    private static boolean VSYNC = true;
    public static int WIDTH = Display.getDesktopDisplayMode().getWidth();
    private static World world;

    public static void exit() {
	System.exit(0);
    }

    public static void gameStart() {
	progress.init();
    }

    public static Background getBackground() {
	return background;
    }

    public static GameState getCurrentGameState() {
	return currentGameState;
    }

    private static Game getInstance() {
	if (game == null)
	    game = new Game();
	return game;
    }

    public static Progress getProgress() {
	return progress;
    }

    public static UserInterface getUi() {
	return ui;
    }

    public static World getWorld() {
	return world;
    }

    public static void main(String[] args) throws LWJGLException {
	Game.getInstance();
    }

    public static void pause() {
	Game.setCurrentGameState(GameState.PAUSED);
    }

    public static void playerDied() {
	currentGameState = GameState.DEATH;
    }

    public static void resetGame() {
	currentGameState = GameState.MAIN_MENU;
	setWorld(new World());
	progress = new Progress();
    }

    public static void setBackground(Background background) {
	Game.background = background;
    }

    public static void setCurrentGameState(GameState currentGameState) {
	Game.currentGameState = currentGameState;
	Game.gameStart();
    }

    public static void setProgress(Progress progress) {
	Game.progress = progress;
    }

    public static void setUi(UserInterface ui) {
	Game.ui = ui;
    }

    public static void setWorld(World world) {
	Game.world = world;
    }

    public static void unpause() {
	Game.setCurrentGameState(GameState.GAMEPLAY);
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

    private void init() {
	GL11.glEnable(GL11.GL_TEXTURE_2D);
	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
	glMatrixMode(GL_MODELVIEW);
	Input.update();
	setWorld(new World());
	ui = new UserInterface();
	progress = new Progress();
	background = new Background();
	Input.showMouse(false);
    }

    private void render() {
	background.render();
	if (currentGameState == GameState.GAMEPLAY)
	    world.render();
	ui.render();
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
