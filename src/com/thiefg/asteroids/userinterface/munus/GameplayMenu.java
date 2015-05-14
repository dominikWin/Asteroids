package com.thiefg.asteroids.userinterface.munus;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.input.Input;
import com.thiefg.asteroids.subobjects.Vector2d;
import com.thiefg.asteroids.userinterface.PlayerModel;

public class GameplayMenu implements Menu {
	int livesLeft;
	private String message;
	private long messageEndTime;
	ArrayList<PlayerModel> playerModels;
	int round;
	int score;

	public GameplayMenu() {
		message = ""; //$NON-NLS-1$
		messageEndTime = 0;
		round = 0;
		playerModels = new ArrayList<PlayerModel>();
	}

	@Override
	public void render() {
		GL11.glDisable(GL11.GL_BLEND);
		for (PlayerModel pm : playerModels)
			pm.render();
		GL11.glEnable(GL11.GL_BLEND);
		Game.getInstance().getUi().getMainFont24p().drawString(100, 100, String.valueOf(score));
		Game.getInstance().getUi().getMainFont24p().drawString((Game.WIDTH / 2) - 24, 100, String.valueOf(round));
		Game.getInstance().getUi().getMainFont24p().drawString((Game.WIDTH / 2) - (message.length() * 6), 150, message);
	}

	public void setMessage(String message, long time) {
		this.message = message;
		messageEndTime = time + System.currentTimeMillis();
	}

	@Override
	public void update() {
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
	}
}
