package com.thiefg.asteroids.userinterface;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.Game.GameState;
import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.subobjects.Vector2d;

public class UserInterface {

	private static final boolean ALIAS_TEXT = false;
	public static final String FONT_LOCATION_PREFIX = "res/fonts/"; //$NON-NLS-1$
	public static final String FONT_TYPE_SUFFIX = ".ttf"; //$NON-NLS-1$
	public static final String FONT_BITWISE = "bitwise"; //$NON-NLS-1$
	public static final String FONT_CIRCUT = "circut"; //$NON-NLS-1$
	public static final String FONT_DIGITAL = "digital"; //$NON-NLS-1$
	public static final String FONT_DIGITALE = "digitale"; //$NON-NLS-1$
	public static final String FONT_TRANSISTOR = "transistor"; //$NON-NLS-1$

	int round;
	int livesLeft;
	int score;
	ArrayList<PlayerModel> playerModels;

	private TrueTypeFont mainFont24p;
	private TrueTypeFont mainFont32p;
	private String message;
	private long messageEndTime;
	private int mainMenuSelectedIndex;

	public UserInterface() {
		try {
			InputStream inputStream = new FileInputStream(FONT_LOCATION_PREFIX
					+ FONT_BITWISE + FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(24f); // set font size
			mainFont24p = new TrueTypeFont(tmpFont, ALIAS_TEXT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			InputStream inputStream = new FileInputStream(FONT_LOCATION_PREFIX
					+ FONT_BITWISE + FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(32f); // set font size
			mainFont32p = new TrueTypeFont(tmpFont, ALIAS_TEXT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		round = 0;
		livesLeft = Game.getWorld().getPlayer().getLivesLeft();
		score = Game.getWorld().getPlayer().getScore();
		playerModels = new ArrayList<PlayerModel>();
		message = ""; //$NON-NLS-1$
		messageEndTime = 0;
		setMainMenuSelectedIndex(0);
	}

	public void update() {
		switch (Game.getCurrentGameState()) {
		case DEATH:
			if (Input.getKeyDown(Keyboard.KEY_RETURN))
				deathKeyPress();
			break;
		case GAMEPLAY:
			round = Game.getProgress().getRound();
			livesLeft = Game.getWorld().getPlayer().getLivesLeft();
			score = Game.getWorld().getPlayer().getScore();
			playerModels.clear();
			for (int i = 0; i < livesLeft; i++) {
				playerModels.add(new PlayerModel(
						new Vector2d(100 + 25 * i, 150), 270));
			}
			if (System.currentTimeMillis() > messageEndTime) {
				message = ""; //$NON-NLS-1$
			}
			break;
		case MAIN_MENU:
			if (Input.getKeyDown(Keyboard.KEY_RETURN))
				menuKeyPress();
			break;
		case PAUSED:
			break;
		default:
			break;

		}

	}

	private void deathKeyPress() {
		Game.resetGame();
	}

	private void menuKeyPress() {
		if (mainMenuSelectedIndex == 0)
			Game.setCurrentGameState(GameState.GAMEPLAY);
	}

	public void render() {
		glEnable(GL11.GL_BLEND);

		switch (Game.getCurrentGameState()) {
		case DEATH:
			mainFont32p
					.drawString(
							(Game.WIDTH / 2)
									- Messages
											.getString(
													"UserInterface.DeathMessage").length() * 8, 200, Messages.getString("UserInterface.DeathMessage")); //$NON-NLS-1$ //$NON-NLS-2$
			mainFont24p
					.drawString(
							(Game.WIDTH / 2)
									- Messages
											.getString(
													"UserInterface.DeathSubMessage").length() * 6, 275, //$NON-NLS-1$
							Messages.getString("UserInterface.DeathSubMessage")); //$NON-NLS-1$
			break;
		case GAMEPLAY:
			glDisable(GL11.GL_BLEND);
			for (PlayerModel pm : playerModels)
				pm.render();
			glEnable(GL11.GL_BLEND);
			mainFont24p.drawString(100, 100, String.valueOf(score));
			mainFont24p.drawString(Game.WIDTH / 2 - 24, 100,
					String.valueOf(round));
			mainFont24p.drawString(Game.WIDTH / 2 - (message.length() * 6),
					150, message);
			break;
		case MAIN_MENU:
			mainFont32p.drawString(
					(Game.WIDTH / 2)
							- Messages.getString("UserInterface.MainMenuMain") //$NON-NLS-1$
									.length() * 8, 200,
					Messages.getString("UserInterface.MainMenuMain")); //$NON-NLS-1$
			break;
		case PAUSED:
			break;
		default:
			break;

		}

		glDisable(GL11.GL_BLEND);
	}

	public void setMessage(String message, long time) {
		this.message = message;
		this.messageEndTime = time + System.currentTimeMillis();
	}

	public int getMainMenuSelectedIndex() {
		return mainMenuSelectedIndex;
	}

	public void setMainMenuSelectedIndex(int mainMenuSelectedIndex) {
		this.mainMenuSelectedIndex = mainMenuSelectedIndex;
	}
}
