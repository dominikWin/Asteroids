package com.thiefg.asteroids.userinterface;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.thiefg.asteroids.Game;

public class UserInterface {

	private static final boolean ALIAS_TEXT = false;
	public static final String FONT_LOCATION_PREFIX = "res/fonts/";
	public static final String FONT_TYPE_SUFFIX = ".ttf";
	public static final String FONT_BITWISE = "bitwise";
	public static final String FONT_CIRCUT = "circut";
	public static final String FONT_DIGITAL = "digital";
	public static final String FONT_DIGITALE = "digitale";
	public static final String FONT_TRANSISTOR = "transistor";

	private TrueTypeFont mainFont24p;

	public UserInterface() {
		try {
			InputStream inputStream = new FileInputStream(FONT_LOCATION_PREFIX + FONT_BITWISE + FONT_TYPE_SUFFIX);
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			tmpFont = tmpFont.deriveFont(24f); // set font size
			mainFont24p = new TrueTypeFont(tmpFont, ALIAS_TEXT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {

	}

	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		mainFont24p.drawString(100, 100,
				String.valueOf(Game.getWorld().getAsteroids().size()),
				Color.green);
		GL11.glDisable(GL11.GL_BLEND);
		System.out.println(String
				.valueOf(Game.getWorld().getAsteroids().size()));
	}
}
