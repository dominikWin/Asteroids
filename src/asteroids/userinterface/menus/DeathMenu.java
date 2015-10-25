package asteroids.userinterface.menus;

import org.lwjgl.input.Keyboard;

import asteroids.Game;
import asteroids.input.Input;
import asteroids.userinterface.UserInterface;

/**
 * @author Dominik
 * Menu for player death
 */
public class DeathMenu implements Menu {
	/**
	 * Resets game
	 */
	private void deathKeyPress() {
		Game.getInstance().resetGame();
	}

	/**
	 * Renders menu
	 */
	@Override
	public void render() {
		Game.getInstance().getUi().getMainFont32p().drawString((Game.width / 2) - (UserInterface.DEATH_MESSAGE.length() * 8), 200, UserInterface.DEATH_MESSAGE); //Draw message
		Game.getInstance().getUi().getMainFont24p()
				.drawString((Game.width / 2) - (UserInterface.DEATH_SUB_MESSAGE.length() * 6), 275, UserInterface.DEATH_SUB_MESSAGE); //Draw sub-message
	}

	/**
	 * Updates menu
	 */
	@Override
	public void update(double time) {
		if (Input.getKeyDown(Keyboard.KEY_RETURN)) deathKeyPress(); //Handle input
	}
}
