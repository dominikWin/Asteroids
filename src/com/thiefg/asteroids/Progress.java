package com.thiefg.asteroids;

import com.thiefg.asteroids.objects.Asteroid;
import com.thiefg.asteroids.objects.modifiers.GunModifier;

public class Progress {
	private static final int PER_ROUND_LIFE_BOOST = 2;
	private static final int UNLOCK_MESSAGE_TIME = 3000;
	private static final int INIT_DELAY = 5000;

	private enum ProgressState {
		WAIT_INIT, WAIT_PLAYER;
	}

	private long startNextRoundTime;
	private int round;
	private int secsToNextRound;

	private boolean fastGunUnlocked, dualGunUnlocked, trippleGunUnlocked,
			quadGunUnlocked, octGunUnlocked;

	ProgressState stage;

	public Progress() {
		stage = ProgressState.WAIT_INIT;
		setStartNextRoundTime(System.currentTimeMillis() + INIT_DELAY);
		setSecsToNextRound(5);
		fastGunUnlocked = false;
		dualGunUnlocked = false;
		trippleGunUnlocked = false;
		quadGunUnlocked = false;
		octGunUnlocked = false;
	}

	public void update() {
		switch (stage) {
		case WAIT_INIT:
			if (System.currentTimeMillis() > startNextRoundTime)
				startRound();
			Game.getUi().setMessage(
					"Next round in " + secsToNextRound + " seconds!", 50);
			break;
		case WAIT_PLAYER:
			if (Game.getWorld().getAsteroids().isEmpty())
				endRound();
			if (Game.getWorld().getPlayer().getScore() > 1000
					&& !fastGunUnlocked) {
				fastGunUnlocked = true;
				Game.getWorld().getPlayer().setGunModifier(GunModifier.FAST);
				Game.getUi().setMessage("Fast gun unlocked!",
						UNLOCK_MESSAGE_TIME);
			}
			if (Game.getWorld().getPlayer().getScore() > 2000
					&& !dualGunUnlocked) {
				dualGunUnlocked = true;
				Game.getWorld().getPlayer().setGunModifier(GunModifier.DUAL);
				Game.getUi().setMessage("Dual gun unlocked!",
						UNLOCK_MESSAGE_TIME);
			}
			if (Game.getWorld().getPlayer().getScore() > 5000
					&& !trippleGunUnlocked) {
				trippleGunUnlocked = true;
				Game.getWorld().getPlayer().setGunModifier(GunModifier.TRIPLE);
				Game.getUi().setMessage("Tripple gun unlocked",
						UNLOCK_MESSAGE_TIME);
			}
			if (Game.getWorld().getPlayer().getScore() > 10000
					&& !quadGunUnlocked) {
				quadGunUnlocked = true;
				Game.getWorld().getPlayer().setGunModifier(GunModifier.QUAD);
				Game.getUi().setMessage("Quad gun unlocked",
						UNLOCK_MESSAGE_TIME);
			}
			if (Game.getWorld().getPlayer().getScore() > 25000
					&& !octGunUnlocked) {
				octGunUnlocked = true;
				Game.getWorld().getPlayer().setGunModifier(GunModifier.OCT);
				Game.getUi()
						.setMessage("Oct gun unlocked", UNLOCK_MESSAGE_TIME);
			}
			break;
		}
		secsToNextRound = (int) ((startNextRoundTime - System
				.currentTimeMillis()) / 1000) + 1;
	}

	private void endRound() {
		startNextRoundTime = INIT_DELAY + System.currentTimeMillis();
		stage = ProgressState.WAIT_INIT;
		Game.getWorld().getPlayer()
				.setLivesLeft(Game.getWorld().getPlayer().getLivesLeft() + PER_ROUND_LIFE_BOOST);
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

	public boolean isFastGunUnlocked() {
		return fastGunUnlocked;
	}

	public void setFastGunUnlocked(boolean fastGunUnlocked) {
		this.fastGunUnlocked = fastGunUnlocked;
	}

	public boolean isDualGunUnlocked() {
		return dualGunUnlocked;
	}

	public void setDualGunUnlocked(boolean dualGunUnlocked) {
		this.dualGunUnlocked = dualGunUnlocked;
	}

	public boolean isTrippleGunUnlocked() {
		return trippleGunUnlocked;
	}

	public void setTrippleGunUnlocked(boolean trippleGunUnlocked) {
		this.trippleGunUnlocked = trippleGunUnlocked;
	}

	public boolean isQuadGunUnlocked() {
		return quadGunUnlocked;
	}

	public void setQuadGunUnlocked(boolean quadGunUnlocked) {
		this.quadGunUnlocked = quadGunUnlocked;
	}

	public boolean isOctGunUnlocked() {
		return octGunUnlocked;
	}

	public void setOctGunUnlocked(boolean octGunUnlocked) {
		this.octGunUnlocked = octGunUnlocked;
	}
}
