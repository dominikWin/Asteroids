package com.thiefg.asteroids.userinterface;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Vector2d;

public class UserInterface {

	private static final boolean ALIAS_TEXT = false;
	public static final String FONT_LOCATION_PREFIX = "res/fonts/";
	public static final String FONT_TYPE_SUFFIX = ".ttf";
	public static final String FONT_BITWISE = "bitwise";
	public static final String FONT_CIRCUT = "circut";
	public static final String FONT_DIGITAL = "digital";
	public static final String FONT_DIGITALE = "digitale";
	public static final String FONT_TRANSISTOR = "transistor";

	int round;
	int livesLeft;
	int score;
	ArrayList<PlayerModel> playerModels;

	private TrueTypeFont mainFont24p;
	private String message;
	private long messageEndTime;

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
		round = 0;
		livesLeft = Game.getWorld().getPlayer().getLivesLeft();
		score = Game.getWorld().getPlayer().getScore();
		playerModels = new ArrayList<PlayerModel>();
		message = "";
		messageEndTime = 0;
	}

	public void update() {
		switch (Game.getCurrentGameState()) {
		case DEATH:
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
				message = "";
			}
			break;
		case MAIN_MENU:
			break;
		case PAUSED:
			break;
		default:
			break;

		}

	}

	public void render() {
		glEnable(GL11.GL_BLEND);
		
		switch (Game.getCurrentGameState()) {
		case DEATH:
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
}
