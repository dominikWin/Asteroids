package asteroids.objects;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import asteroids.Game;
import asteroids.subobjects.Vector2d;

/**
 * @author Dominik
 *
 */
public class ParticleEffect {
	private class Particle {
		private static final double GRAVITY_MULTIPLYER = .1;
		Vector2d location, velocity;
		double r, g, b;

		/**
		 * @param loc
		 * @param force
		 * Creates Particle at loc with force
		 */
		public Particle(Vector2d loc, double force) {
			location = new Vector2d(loc.getX(), loc.getY());
			velocity = new Vector2d(new Vector2d(0, 0), Math.random() * 360, force * 2 * Math.random());
			//Random bright color
			r = .5d + (Math.random() / 2);
			g = .5d + (Math.random() / 2);
			b = .5d + (Math.random() / 2);
		}

		/**
		 * @param loc
		 * @param force
		 * @param multiply
		 * Creates particle at loc with force & initial velocity
		 */
		public Particle(Vector2d loc, double force, Vector2d multiply) {
			location = new Vector2d(loc.getX(), loc.getY());
			velocity = new Vector2d(new Vector2d(0, 0), Math.random() * 360, force * 2 * Math.random());
			velocity.add(multiply);
			//Random bright color
			r = .5d + (Math.random() / 2);
			g = .5d + (Math.random() / 2);
			b = .5d + (Math.random() / 2);
		}

		/**
		 * Render particle
		 */
		public void render() {
			GL11.glColor3d(r, g, b);
			GL11.glVertex2i((int) location.getX(), (int) location.getY());
		}

		/**
		 * Update particle location
		 * @param time 
		 */
		public void update(double time) {
			location.add(Vector2d.multiply(velocity, time));
			velocity.add(Vector2d.multiply(new Vector2d(0, Particle.GRAVITY_MULTIPLYER), time)); //Apply particle gravity
		}
	}

	private ArrayList<Particle> particles;

	/**
	 * @param loc
	 * @param size
	 * @param force
	 * Creates a ParticleEffect at loc with size and initial force
	 */
	public ParticleEffect(Vector2d loc, int size, double force) {
		setParticles(new ArrayList<ParticleEffect.Particle>());
		for (int i = 0; i < size; i++)
			getParticles().add(new Particle(loc, force));
	}

	/**
	 * @param loc
	 * @param size
	 * @param force
	 * @param initDirection
	 * @param initDirectionMultiplyer
	 * Creates particle at loc with size and force & initial velocity and velocity impact multiplier
	 */
	public ParticleEffect(Vector2d loc, int size, double force, Vector2d initDirection, double initDirectionMultiplyer) {
		setParticles(new ArrayList<ParticleEffect.Particle>());
		for (int i = 0; i < size; i++)
			getParticles().add(new Particle(loc, force, Vector2d.between(initDirection, new Vector2d(0, 0), initDirectionMultiplyer * Math.random())));
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	/**
	 * Removes off-screen particles
	 */
	private void removeOffScreenParticles() {
		getParticles().removeIf(t -> {
			Vector2d loc = t.location;
			return (loc.getX() < 0) || (loc.getX() > Game.width) || (loc.getY() < 0) || (loc.getY() > Game.height);
		});
	}

	/**
	 * Renders all particles
	 */
	public void render() {
		GL11.glBegin(GL11.GL_POINTS);
		particles.forEach(p -> p.render()); //Renders each particle
		GL11.glEnd();
		GL11.glColor3d(1, 1, 1);
	}

	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	/**
	 * Updates all particles
	 * @param time 
	 */
	public void update(double time) {
		removeOffScreenParticles();
		particles.forEach(p -> p.update(time)); //Updates all particles
	}
}
