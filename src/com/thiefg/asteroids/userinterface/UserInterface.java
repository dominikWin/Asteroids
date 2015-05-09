package com.thiefg.asteroids.userinterface;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.Game.GameState;
import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.subobjects.Vector2d;

public class UserInterface {
	private static final boolean ANTIALIAS_TEXT = false;
	private static final String DEATH_MESSAGE = Messages.getString("UserInterface.DeathMessage"); //$NON-NLS-1$
	private static final String DEATH_SUB_MESSAGE = Messages.getString(Messages.getString("UserInterface.2")); //$NON-NLS-1$
	public static final String FONT_BITWISE = "bitwise"; //$NON-NLS-1$
	public static final String FONT_LOCATION_PREFIX = "res/fonts/"; //$NON-NLS-1$
	public static final String FONT_TYPE_SUFFIX = ".ttf"; //$NON-NLS-1$
	private static final String MAIN_MENU_EXIT_MESSAGE = Messages.getString("UserInterface.MainMenuExit"); //$NON-NLS-1$
	private static final Color MAIN_MENU_SELECTED_COLOR = Color.red;
	private static final String MAIN_MENU_SINGLEPLAYER_MESSAGE = Messages.getString("UserInterface.MainMenuSingleplayer"); //$NON-NLS-1$
	private static final String MENU_MENU_TITLE_MESSAGE = Messages.getString("UserInterface.MainMenuMain") //$NON-NLS-1$
			;
	private static final int MENU_OPTION_COUNT = 2;
	private static final String PAUSE_MENU_TEXT = Messages.getString("UserInterface.PauseMenu");
	int livesLeft;
	private TrueTypeFont mainFont24p;
	private TrueTypeFont mainFont32p;
	private int mainMenuSelectedIndex;
	private String message;
	private long messageEndTime;
	ArrayList<PlayerModel> playerModels;
	int round;
	int score;

	public UserInterface() {
		try {
			InputStream inputStream = new FileInputStream(UserInterface.FONT_LOCATION_PREFIX + UserInterface.FONT_BITWISE + UserInterface.FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(24f); // set font size
			mainFont24p = new TrueTypeFont(tmpFont, UserInterface.ANTIALIAS_TEXT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			InputStream inputStream = new FileInputStream(UserInterface.FONT_LOCATION_PREFIX + UserInterface.FONT_BITWISE + UserInterface.FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(32f); // set font size
			mainFont32p = new TrueTypeFont(tmpFont, UserInterface.ANTIALIAS_TEXT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		round = 0;
		playerModels = new ArrayList<PlayerModel>();
		message = ""; //$NON-NLS-1$
		messageEndTime = 0;
		setMainMenuSelectedIndex(0);
	}

	private void deathKeyPress() {
		Game.getInstance().resetGame();
	}

	public int getMainMenuSelectedIndex() {
		return mainMenuSelectedIndex;
	}

	private void menuKeyPress() {
		switch (mainMenuSelectedIndex) {
		case 0:
			Game.getInstance().setCurrentGameState(GameState.GAMEPLAY);
			break;
		case 1:
			Game.getInstance().exit();
			break;
		}
	}

	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		switch (Game.getInstance().getCurrentGameState()) {
		case DEATH:
			mainFont32p.drawString((Game.WIDTH / 2) - (UserInterface.DEATH_MESSAGE.length() * 8), 200, UserInterface.DEATH_MESSAGE);
			mainFont24p.drawString((Game.WIDTH / 2) - (UserInterface.DEATH_SUB_MESSAGE.length() * 6), 275, UserInterface.DEATH_SUB_MESSAGE);
			break;
		case GAMEPLAY:
			GL11.glDisable(GL11.GL_BLEND);
			for (PlayerModel pm : playerModels)
				pm.render();
			GL11.glEnable(GL11.GL_BLEND);
			mainFont24p.drawString(100, 100, String.valueOf(score));
			mainFont24p.drawString((Game.WIDTH / 2) - 24, 100, String.valueOf(round));
			mainFont24p.drawString((Game.WIDTH / 2) - (message.length() * 6), 150, message);
			break;
		case MAIN_MENU:
			mainFont32p.drawString((Game.WIDTH / 2) - (UserInterface.MENU_MENU_TITLE_MESSAGE.length() * 8), 200, UserInterface.MENU_MENU_TITLE_MESSAGE);
			mainFont24p.drawString((Game.WIDTH / 2) - (UserInterface.MAIN_MENU_SINGLEPLAYER_MESSAGE.length() * 6), 275,
					UserInterface.MAIN_MENU_SINGLEPLAYER_MESSAGE, mainMenuSelectedIndex == 0 ? UserInterface.MAIN_MENU_SELECTED_COLOR : Color.white);
			mainFont24p.drawString((Game.WIDTH / 2) - (UserInterface.MAIN_MENU_EXIT_MESSAGE.length() * 6), 325, UserInterface.MAIN_MENU_EXIT_MESSAGE,
					mainMenuSelectedIndex == 1 ? UserInterface.MAIN_MENU_SELECTED_COLOR : Color.white);
			break;
		case PAUSED:
			mainFont32p.drawString(100, 100, UserInterface.PAUSE_MENU_TEXT);
			break;
		default:
			break;
		}
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void setMainMenuSelectedIndex(int mainMenuSelectedIndex) {
		this.mainMenuSelectedIndex = mainMenuSelectedIndex;
	}

	public void setMessage(String message, long time) {
		this.message = message;
		messageEndTime = time + System.currentTimeMillis();
	}

	public void update() {
		switch (Game.getInstance().getCurrentGameState()) {
		case DEATH:
			if (Input.getKeyDown(Keyboard.KEY_RETURN)) deathKeyPress();
			break;
		case GAMEPLAY:
			round = Game.getInstance().getProgress().getRound();
			score = Game.getInstance().getWorld().getPlayer().getScore();
			if (livesLeft != Game.getInstance().getWorld().getPlayer().getLivesLeft()) {
				livesLeft = Game.getInstance().getWorld().getPlayer().getLivesLeft();
				playerModels.clear();
				for (int i = 0; i < livesLeft; i++)
					playerModels.add(new PlayerModel(new Vector2d(100 + (25 * i), 150), 270));
			}
			if (System.currentTimeMillis() > messageEndTime) message = ""; //$NON-NLS-1$
			if (Input.getKeyDown(Keyboard.KEY_ESCAPE)) Game.getInstance().pause();
			break;
		case MAIN_MENU:
			if (Input.getKeyDown(Keyboard.KEY_RETURN)) menuKeyPress();
			if (Input.getKeyDown(Keyboard.KEY_DOWN) && ((UserInterface.MENU_OPTION_COUNT - 1) > mainMenuSelectedIndex)) mainMenuSelectedIndex++;
			if (Input.getKeyDown(Keyboard.KEY_UP) && (mainMenuSelectedIndex > 0)) mainMenuSelectedIndex--;
			break;
		case PAUSED:
			if (Input.getKeyDown(Keyboard.KEY_ESCAPE)) Game.getInstance().unpause();
			break;
		default:
			break;
		}
	}
}
