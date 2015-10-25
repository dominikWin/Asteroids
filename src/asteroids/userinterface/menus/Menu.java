package asteroids.userinterface.menus;

/**
 * @author Dominik
 * Menu interface for all UI menus
 */
public interface Menu {
	/**
	 * Renders Menu
	 */
	public void render();

	
	/**
	 * Updates Menu
	 * @param time 
	 */
	public void update(double time);
}
