package com.thiefg.asteroids.userinterface.munus;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.Game.GameState;
import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.userinterface.UserInterface;

public class MainMenu implements Menu {
	private int selectedIndex;

	public MainMenu() {
		selectedIndex = 0;
	}

	private void menuKeyPress() {
		switch (selectedIndex) {
		case 0:
			Game.getInstance().setCurrentGameState(GameState.GAMEPLAY);
			break;
		case 1:
			Game.getInstance().exit();
			break;
		}
	}

	@Override
	public void render() {
		Game.getInstance().getUi().getMainFont32p();
		Game.getInstance().getUi().getMainFont24p()
				.drawString((Game.WIDTH / 2) - (UserInterface.MENU_MENU_TITLE_MESSAGE.length() * 8), 200, UserInterface.MENU_MENU_TITLE_MESSAGE);
		Game.getInstance()
				.getUi()
				.getMainFont24p()
				.drawString((Game.WIDTH / 2) - (UserInterface.MAIN_MENU_SINGLEPLAYER_MESSAGE.length() * 6), 275, UserInterface.MAIN_MENU_SINGLEPLAYER_MESSAGE,
						selectedIndex == 0 ? UserInterface.MAIN_MENU_SELECTED_COLOR : Color.white);
		Game.getInstance()
				.getUi()
				.getMainFont24p()
				.drawString((Game.WIDTH / 2) - (UserInterface.MAIN_MENU_EXIT_MESSAGE.length() * 6), 325, UserInterface.MAIN_MENU_EXIT_MESSAGE,
						selectedIndex == 1 ? UserInterface.MAIN_MENU_SELECTED_COLOR : Color.white);
	}

	@Override
	public void update() {
		if (Input.getKeyDown(Keyboard.KEY_RETURN)) menuKeyPress();
		if (Input.getKeyDown(Keyboard.KEY_DOWN) && ((UserInterface.MENU_OPTION_COUNT - 1) > selectedIndex)) selectedIndex++;
		if (Input.getKeyDown(Keyboard.KEY_UP) && (selectedIndex > 0)) selectedIndex--;
	}
}
