package com.thiefg.asteroids.userinterface.munus;

import org.lwjgl.input.Keyboard;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.userinterface.UserInterface;

public class DeathMenu implements Menu {
	private void deathKeyPress() {
		Game.getInstance().resetGame();
	}

	@Override
	public void render() {
		Game.getInstance().getUi().getMainFont32p().drawString((Game.WIDTH / 2) - (UserInterface.DEATH_MESSAGE.length() * 8), 200, UserInterface.DEATH_MESSAGE);
		Game.getInstance().getUi().getMainFont24p()
				.drawString((Game.WIDTH / 2) - (UserInterface.DEATH_SUB_MESSAGE.length() * 6), 275, UserInterface.DEATH_SUB_MESSAGE);
	}

	@Override
	public void update() {
		if (Input.getKeyDown(Keyboard.KEY_RETURN)) deathKeyPress();
	}
}
