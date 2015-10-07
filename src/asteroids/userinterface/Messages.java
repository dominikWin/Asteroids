package com.thiefg.asteroids.userinterface;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Dominik
 * Loads custom strings from file
 */
public class Messages {
	private static final String BUNDLE_NAME = "com.thiefg.asteroids.userinterface.messages"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME);

	/**
	 * @param key
	 * @return message at key, if none found returns error message
	 */
	public static String getString(String key) {
		try {
			return Messages.RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}