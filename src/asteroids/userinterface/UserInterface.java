package asteroids.userinterface;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import asteroids.Game;
import asteroids.userinterface.menus.DeathMenu;
import asteroids.userinterface.menus.GameplayMenu;
import asteroids.userinterface.menus.MainMenu;
import asteroids.userinterface.menus.Menu;
import asteroids.userinterface.menus.PauseMenu;

/**
 * @author Dominik
 * UserInterface, all non game object displays
 */
public class UserInterface {
	private static final boolean ANTIALIAS_TEXT = false;
	public static final String DEATH_MESSAGE = Messages.getString("UserInterface.DeathMessage"); //$NON-NLS-1$
	public static final String DEATH_SUB_MESSAGE = Messages.getString(Messages.getString("UserInterface.2")); //$NON-NLS-1$
	public static final String FONT_BITWISE = "bitwise"; //$NON-NLS-1$
	public static final String FONT_LOCATION_PREFIX = "res/fonts/"; //$NON-NLS-1$
	public static final String FONT_TYPE_SUFFIX = ".ttf"; //$NON-NLS-1$
	public static final String MAIN_MENU_EXIT_MESSAGE = Messages.getString("UserInterface.MainMenuExit"); //$NON-NLS-1$
	public static final Color MAIN_MENU_SELECTED_COLOR = Color.red;
	public static final String MAIN_MENU_SINGLEPLAYER_MESSAGE = Messages.getString("UserInterface.MainMenuSingleplayer"); //$NON-NLS-1$
	public static final String MENU_MENU_TITLE_MESSAGE = Messages.getString("UserInterface.MainMenuMain") //$NON-NLS-1$
	;
	public static final int MENU_OPTION_COUNT = 2;
	public static final String PAUSE_MENU_TEXT = Messages.getString("UserInterface.PauseMenu");
	Menu deathMenu;
	GameplayMenu gameplayMenu;
	private TrueTypeFont mainFont24p;
	private TrueTypeFont mainFont32p;
	Menu mainMenu;
	private int mainMenuSelectedIndex;
	Menu pauseMenu;

	/**
	 * Creates UserInterface
	 */
	public UserInterface() {
		//Load font 24px
		try {
			InputStream inputStream = new FileInputStream(UserInterface.FONT_LOCATION_PREFIX + UserInterface.FONT_BITWISE + UserInterface.FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(24f); // set font size
			setMainFont24p(new TrueTypeFont(tmpFont, UserInterface.ANTIALIAS_TEXT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Load font 32px
		try {
			InputStream inputStream = new FileInputStream(UserInterface.FONT_LOCATION_PREFIX + UserInterface.FONT_BITWISE + UserInterface.FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(32f); // set font size
			setMainFont32p(new TrueTypeFont(tmpFont, UserInterface.ANTIALIAS_TEXT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Set default index
		setMainMenuSelectedIndex(0);
		//Init Menus
		mainMenu = new MainMenu();
		deathMenu = new DeathMenu();
		gameplayMenu = new GameplayMenu();
		pauseMenu = new PauseMenu();
	}

	public TrueTypeFont getMainFont24p() {
		return mainFont24p;
	}

	public TrueTypeFont getMainFont32p() {
		return mainFont32p;
	}

	public int getMainMenuSelectedIndex() {
		return mainMenuSelectedIndex;
	}

	/**
	 * Renders UserInterface
	 */
	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		switch (Game.getInstance().getCurrentGameState()) {
		case DEATH:
			deathMenu.render();
			break;
		case GAMEPLAY:
			gameplayMenu.render();
			break;
		case MAIN_MENU:
			mainMenu.render();
			break;
		case PAUSED:
			pauseMenu.render();
			break;
		default:
			break;
		}
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void setMainFont24p(TrueTypeFont mainFont24p) {
		this.mainFont24p = mainFont24p;
	}

	public void setMainFont32p(TrueTypeFont mainFont32p) {
		this.mainFont32p = mainFont32p;
	}

	public void setMainMenuSelectedIndex(int mainMenuSelectedIndex) {
		this.mainMenuSelectedIndex = mainMenuSelectedIndex;
	}

	public void setMessage(String message, long time) {
		gameplayMenu.setMessage(message, time);
	}

	/**
	 * Updates UserInterface
	 * @param time 
	 */
	public void update(double time) {
		switch (Game.getInstance().getCurrentGameState()) {
		case DEATH:
			deathMenu.update(time);
			break;
		case GAMEPLAY:
			gameplayMenu.update(time);
			break;
		case MAIN_MENU:
			mainMenu.update(time);
			break;
		case PAUSED:
			pauseMenu.update(time);
			break;
		default:
			break;
		}
	}
}
