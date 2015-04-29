package com.thiefg.asteroids.userinterface;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.thiefg.asteroids.Game;

public class UserInterface {

	private TrueTypeFont mainFont;

	public UserInterface() {
		mainFont = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24),
				false);

	}

	public void update() {

	}

	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		mainFont.drawString(100, 100,
				String.valueOf(Game.getWorld().getAsteroids().size()),
				Color.green);
		GL11.glDisable(GL11.GL_BLEND);
		System.out.println(String.valueOf(Game.getWorld().getAsteroids().size()));
	}
}
