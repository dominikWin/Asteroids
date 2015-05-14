package com.thiefg.asteroids;

import com.thiefg.asteroids.objects.Asteroid;
import com.thiefg.asteroids.objects.modifiers.GunModifier;

public class Progress {
	private enum ProgressState {
		WAIT_INIT, WAIT_PLAYER;
	}

	private static final int INIT_DELAY = 5000;
	private static final int PER_ROUND_LIFE_BOOST = 2;
	private static final int UNLOCK_MESSAGE_TIME = 3000;
	private boolean fastGunUnlocked, dualGunUnlocked, trippleGunUnlocked,
			quadGunUnlocked, octGunUnlocked;
	private int round;
	private int secsToNextRound;
	ProgressState stage;
	private long startNextRoundTime;

	public Progress() {
		stage = ProgressState.WAIT_INIT;
		fastGunUnlocked = false;
		dualGunUnlocked = false;
		trippleGunUnlocked = false;
		quadGunUnlocked = false;
		octGunUnlocked = false;
	}

	private void endRound() {
		startNextRoundTime = Progress.INIT_DELAY + System.currentTimeMillis();
		stage = ProgressState.WAIT_INIT;
		Game.getInstance()
				.getWorld()
				.getPlayer()
				.setLivesLeft(
						Game.getInstance().getWorld().getPlayer()
								.getLivesLeft()
								+ Progress.PER_ROUND_LIFE_BOOST);
	}

	private int getNextRoundAsteroidCount() {
		return 5 + (3 * round);
	}

	public int getRound() {
		return round;
	}

	public int getSecsToNextRound() {
		return secsToNextRound;
	}

	public long getStartNextRoundTime() {
		return startNextRoundTime;
	}

	public void init() {
		setStartNextRoundTime(System.currentTimeMillis() + Progress.INIT_DELAY);
		setSecsToNextRound(5);
	}

	public boolean isDualGunUnlocked() {
		return dualGunUnlocked;
	}

	public boolean isFastGunUnlocked() {
		return fastGunUnlocked;
	}

	public boolean isOctGunUnlocked() {
		return octGunUnlocked;
	}

	public boolean isQuadGunUnlocked() {
		return quadGunUnlocked;
	}

	public boolean isTrippleGunUnlocked() {
		return trippleGunUnlocked;
	}

	public void setDualGunUnlocked(boolean dualGunUnlocked) {
		this.dualGunUnlocked = dualGunUnlocked;
	}

	public void setFastGunUnlocked(boolean fastGunUnlocked) {
		this.fastGunUnlocked = fastGunUnlocked;
	}

	public void setOctGunUnlocked(boolean octGunUnlocked) {
		this.octGunUnlocked = octGunUnlocked;
	}

	public void setQuadGunUnlocked(boolean quadGunUnlocked) {
		this.quadGunUnlocked = quadGunUnlocked;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void setSecsToNextRound(int secsToNextRound) {
		this.secsToNextRound = secsToNextRound;
	}

	public void setStartNextRoundTime(long startNextRoundTime) {
		this.startNextRoundTime = startNextRoundTime;
	}

	public void setTrippleGunUnlocked(boolean trippleGunUnlocked) {
		this.trippleGunUnlocked = trippleGunUnlocked;
	}

	private void startRound() {
		round++;
		stage = ProgressState.WAIT_PLAYER;
		for (int i = 0; i < getNextRoundAsteroidCount(); i++)
			Game.getInstance()
					.getWorld()
					.addAsteroid(
							new Asteroid(Asteroid.getRandomLocation(), Math
									.random() * 360, (Math.random() - .5) * 4
									* (Math.random() < .01 ? 10 : 1)));
	}

	public void update() {
		switch (stage) {
		case WAIT_INIT:
			if (System.currentTimeMillis() > startNextRoundTime)
				startRound();
			Game.getInstance()
					.getUi()
					.setMessage(
							"Next round in " + secsToNextRound + " seconds!",
							50);
			break;
		case WAIT_PLAYER:
			if (Game.getInstance().getWorld().getAsteroids().isEmpty())
				endRound();
			if ((Game.getInstance().getWorld().getPlayer().getScore() > 1000)
					&& !fastGunUnlocked) {
				fastGunUnlocked = true;
				Game.getInstance().getWorld().getPlayer()
						.setGunModifier(GunModifier.FAST);
				Game.getInstance()
						.getUi()
						.setMessage("Fast gun unlocked!",
								Progress.UNLOCK_MESSAGE_TIME);
			}
			if ((Game.getInstance().getWorld().getPlayer().getScore() > 2000)
					&& !dualGunUnlocked) {
				dualGunUnlocked = true;
				Game.getInstance().getWorld().getPlayer()
						.setGunModifier(GunModifier.DUAL);
				Game.getInstance()
						.getUi()
						.setMessage("Dual gun unlocked!",
								Progress.UNLOCK_MESSAGE_TIME);
			}
			if ((Game.getInstance().getWorld().getPlayer().getScore() > 5000)
					&& !trippleGunUnlocked) {
				trippleGunUnlocked = true;
				Game.getInstance().getWorld().getPlayer()
						.setGunModifier(GunModifier.TRIPLE);
				Game.getInstance()
						.getUi()
						.setMessage("Tripple gun unlocked",
								Progress.UNLOCK_MESSAGE_TIME);
			}
			if ((Game.getInstance().getWorld().getPlayer().getScore() > 10000)
					&& !quadGunUnlocked) {
				quadGunUnlocked = true;
				Game.getInstance().getWorld().getPlayer()
						.setGunModifier(GunModifier.QUAD);
				Game.getInstance()
						.getUi()
						.setMessage("Quad gun unlocked",
								Progress.UNLOCK_MESSAGE_TIME);
			}
			if ((Game.getInstance().getWorld().getPlayer().getScore() > 25000)
					&& !octGunUnlocked) {
				octGunUnlocked = true;
				Game.getInstance().getWorld().getPlayer()
						.setGunModifier(GunModifier.OCT);
				Game.getInstance()
						.getUi()
						.setMessage("Oct gun unlocked",
								Progress.UNLOCK_MESSAGE_TIME);
			}
			if ((Game.getInstance().getWorld().getPlayer().getScore() > 50000)
					&& !octGunUnlocked) {
				octGunUnlocked = true;
				Game.getInstance().getWorld().getPlayer()
						.setGunModifier(GunModifier.FAST_OCT);
				Game.getInstance()
						.getUi()
						.setMessage("Fast Oct gun unlocked",
								Progress.UNLOCK_MESSAGE_TIME);
			}
			break;
		}
		secsToNextRound = (int) ((startNextRoundTime - System
				.currentTimeMillis()) / 1000) + 1;
	}
}
