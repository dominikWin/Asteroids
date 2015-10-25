package asteroids;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import asteroids.input.Input;
import asteroids.userinterface.Background;
import asteroids.userinterface.UserInterface;

/**
 * @author Dominik
 * 
 */
public class Game implements Runnable {
	/**
	 * @author Dominik
	 * States of game
	 */
	public enum GameState {
		DEATH, GAMEPLAY, MAIN_MENU, PAUSED;
	}

	public static boolean devMode = false;
	public static int frameCap = Display.getDesktopDisplayMode().getFrequency();
	private static boolean fullscreen = true;
	public static int height = Display.getDesktopDisplayMode().getHeight();
	private static Game instance;
	private static boolean vSyncEnabled = true;
	public static int width = Display.getDesktopDisplayMode().getWidth();

	/**
	 * @return single instance of Game class
	 */
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

	/**
	 * Exits game
	 */
	public void exit() {
		Display.destroy();
		System.gc();
		System.exit(0);
	}

	/**
	 * Starts game
	 */
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

	/**
	 * Init game
	 */
	private void init() {
		glInit();
		Input.update();
		world = new World();
		ui = new UserInterface();
		progress = new Progress();
		background = new Background();
		Input.showMouse(false);
	}

	/**
	 * Init OpenGL
	 */
	private void glInit() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Game.width, Game.height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * Pause game
	 */
	public void pause() {
		setCurrentGameState(GameState.PAUSED);
	}

	/**
	 * Set game to death state
	 */
	public void playerDied() {
		currentGameState = GameState.DEATH;
	}

	/**
	 * Renders game
	 */
	private void render() {
		background.render();
		if (currentGameState == GameState.GAMEPLAY) world.render();
		ui.render();
	}

	/**
	 * Resets game
	 */
	public void resetGame() {
		currentGameState = GameState.MAIN_MENU;
		setWorld(new World());
		progress = new Progress();
	}

	/**
	 * Main thread
	 */
	@Override
	public void run() {
		//Create display
		try {
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setFullscreen(Game.fullscreen);
			Display.setVSyncEnabled(Game.vSyncEnabled);
			Display.setResizable(false);
			//Update resolution
			Game.width = Display.getDisplayMode().getWidth();
			Game.height = Display.getDisplayMode().getHeight();
			Game.frameCap = Display.getDisplayMode().getFrequency();
			System.out.println(Display.getDisplayMode());
		} catch (LWJGLException e) {
			e.printStackTrace();
		};
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		//Init
		init();
		//Game loop
		while (!Display.isCloseRequested()) {
			update();
			render();
			Display.update();
			//Non time-critical stuff
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			System.gc();
			Display.sync(Game.frameCap); //Wait till next frame
		}
		//Exit
		exit();
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

	/**
	 * Puts game back into gameplay
	 */
	public void unpause() {
		setCurrentGameState(GameState.GAMEPLAY);
	}

	/**
	 * Updates game
	 */
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
