package com.thiefg.asteroids;

import com.thiefg.asteroids.objects.Asteroid;

public class Progress {
	private static final int INIT_DELAY = 5000;

	private enum ProgressState {
		WAIT_INIT, WAIT_PLAYER;
	}

	private long startNextRoundTime;
	private int round;
	private int secsToNextRound;

	ProgressState stage;

	public Progress() {
		stage = ProgressState.WAIT_INIT;
		setStartNextRoundTime(System.currentTimeMillis() + INIT_DELAY);
		setSecsToNextRound(5);
	}

	public void update() {
		switch (stage) {
		case WAIT_INIT:
			if (System.currentTimeMillis() > startNextRoundTime)
				startRound();
			Game.getUi().setMessage("Next round in " + secsToNextRound + " seconds!", 0);
			break;
		case WAIT_PLAYER:
			if (Game.getWorld().getAsteroids().isEmpty())
				endRound();
			break;
		}
		secsToNextRound = (int) (startNextRoundTime - System.currentTimeMillis() / 1000);
	}

	private void endRound() {
		startNextRoundTime = INIT_DELAY + System.currentTimeMillis();
		stage = ProgressState.WAIT_INIT;
	}

	private void startRound() {
		round++;
		stage = ProgressState.WAIT_PLAYER;
		for (int i = 0; i < getNextRoundAsteroidCount(); i++) {
			Game.getWorld().addAsteroid(
					new Asteroid(Asteroid.getRandomLocation()));
		}
	}

	private int getNextRoundAsteroidCount() {
		return 5 + 3 * round;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public long getStartNextRoundTime() {
		return startNextRoundTime;
	}

	public void setStartNextRoundTime(long startNextRoundTime) {
		this.startNextRoundTime = startNextRoundTime;
	}

	public int getSecsToNextRound() {
		return secsToNextRound;
	}

	public void setSecsToNextRound(int secsToNextRound) {
		this.secsToNextRound = secsToNextRound;
	}
}
