package asteroids.userinterface.munus;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import asteroids.Game;
import asteroids.input.Input;
import asteroids.subobjects.Vector2d;
import asteroids.userinterface.PlayerModel;

/**
 * @author Dominik
 * Player HUD
 */
public class GameplayMenu implements Menu {
	int livesLeft;
	private String message;
	private long messageEndTime;
	ArrayList<PlayerModel> playerModels;
	int round;
	int score;

	/**
	 *  Creates basic HUD
	 */
	public GameplayMenu() {
		message = ""; //$NON-NLS-1$
		messageEndTime = 0;
		round = 0;
		playerModels = new ArrayList<PlayerModel>();
	}

	/**
	 * Renders menu
	 */
	@Override
	public void render() {
		GL11.glDisable(GL11.GL_BLEND);
		//Draw lives
		for (PlayerModel pm : playerModels)
			pm.render();
		GL11.glEnable(GL11.GL_BLEND);
		Game.getInstance().getUi().getMainFont24p().drawString(100, 100, String.valueOf(score)); //Draw score
		Game.getInstance().getUi().getMainFont24p().drawString((Game.width / 2) - 24, 100, String.valueOf(round)); //Draw round
		Game.getInstance().getUi().getMainFont24p().drawString((Game.width / 2) - (message.length() * 6), 150, message); //Draw custom message
	}

	/**
	 * Adds new message with given time in milliseconds
	 * @param message
	 * @param time
	 */
	public void setMessage(String message, long time) {
		this.message = message;
		messageEndTime = time + System.currentTimeMillis();
	}

	/**
	 * Updates menu
	 */
	@Override
	public void update() {
		//Update game data
		round = Game.getInstance().getProgress().getRound();
		score = Game.getInstance().getWorld().getPlayer().getScore();
		
		//Update player life models
		if (livesLeft != Game.getInstance().getWorld().getPlayer().getLivesLeft()) {
			livesLeft = Game.getInstance().getWorld().getPlayer().getLivesLeft();
			playerModels.clear();
			for (int i = 0; i < livesLeft; i++)
				playerModels.add(new PlayerModel(new Vector2d(100 + (25 * i), 150), 270));
		}
		//Remove old messages
		if (System.currentTimeMillis() > messageEndTime) message = ""; //$NON-NLS-1$
		//Handle pause input
		if (Input.getKeyDown(Keyboard.KEY_ESCAPE)) Game.getInstance().pause();
	}
}
