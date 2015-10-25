package asteroids.userinterface.menus;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import asteroids.Game;
import asteroids.Game.GameState;
import asteroids.input.Input;
import asteroids.userinterface.UserInterface;

/**
 * @author Dominik
 *
 */
public class MainMenu implements Menu {
	private int selectedIndex;

	/**
	 * Creates new MainMenu with index 0
	 */
	public MainMenu() {
		selectedIndex = 0;
	}

	/**
	 * Creates new MainMenu with custom statring index
	 * 
	 * @param selectedIndex
	 */
	public MainMenu(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * Handle a confirm key press
	 */
	private void menuKeyPress() {
		switch (selectedIndex) {
		case 0:
			// Singleplayer
			Game.getInstance().setCurrentGameState(GameState.GAMEPLAY);
			break;
		case 1:
			// Exit
			Game.getInstance().exit();
			break;
		}
	}

	/**
	 * Renders menu
	 */
	@Override
	public void render() {
		// Title
		Game.getInstance().getUi().getMainFont24p()
				.drawString((Game.width / 2) - (UserInterface.MENU_MENU_TITLE_MESSAGE.length() * 8), 200, UserInterface.MENU_MENU_TITLE_MESSAGE);
		// Singleplayer
		Game.getInstance()
				.getUi()
				.getMainFont24p()
				.drawString((Game.width / 2) - (UserInterface.MAIN_MENU_SINGLEPLAYER_MESSAGE.length() * 6), 275, UserInterface.MAIN_MENU_SINGLEPLAYER_MESSAGE,
						selectedIndex == 0 ? UserInterface.MAIN_MENU_SELECTED_COLOR : Color.white);
		// Exit
		Game.getInstance()
				.getUi()
				.getMainFont24p()
				.drawString((Game.width / 2) - (UserInterface.MAIN_MENU_EXIT_MESSAGE.length() * 6), 325, UserInterface.MAIN_MENU_EXIT_MESSAGE,
						selectedIndex == 1 ? UserInterface.MAIN_MENU_SELECTED_COLOR : Color.white);
	}

	/**
	 * Updates Menu
	 */
	@Override
	public void update(double time) {
		if (Input.getKeyDown(Keyboard.KEY_RETURN)) //Confirm key
			menuKeyPress();//Handle input
		
		//Updates selected option
		if (Input.getKeyDown(Keyboard.KEY_DOWN) && ((UserInterface.MENU_OPTION_COUNT - 1) > selectedIndex)) selectedIndex++;
		if (Input.getKeyDown(Keyboard.KEY_UP) && (selectedIndex > 0)) selectedIndex--;
	}
}
