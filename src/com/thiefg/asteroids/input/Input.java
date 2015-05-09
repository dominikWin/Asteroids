package com.thiefg.asteroids.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.thiefg.asteroids.Game;
import com.thiefg.asteroids.subobjects.Vector2d;

public class Input {
    private static ArrayList<Integer> currentKeys = new ArrayList<Integer>();
    private static ArrayList<Integer> currentMouse = new ArrayList<Integer>();
    private static ArrayList<Integer> downKeys = new ArrayList<Integer>();
    private static ArrayList<Integer> downMouse = new ArrayList<Integer>();
    public static final int NUM_KEYCODES = 256;

    public static final int NUM_MOUSEBUTTONS = 5;
    private static ArrayList<Integer> upKeys = new ArrayList<Integer>();
    private static ArrayList<Integer> upMouse = new ArrayList<Integer>();

    public static boolean getKey(int keyCode) {
	return Keyboard.isKeyDown(keyCode);
    }

    public static boolean getKeyDown(int keyCode) {
	return downKeys.contains(keyCode);
    }

    public static boolean getKeyUp(int keyCode) {
	return upKeys.contains(keyCode);
    }

    public static boolean getMouse(int mouseButton) {
	return Mouse.isButtonDown(mouseButton);
    }

    public static boolean getMouseDown(int mouseButton) {
	return downMouse.contains(mouseButton);
    }

    public static int getMouseDX() {
	return Mouse.getDX();
    }

    public static int getMouseDY() {
	return Mouse.getDY();
    }

    public static Vector2d getMousePosition() {
	return new Vector2d(Mouse.getX(), Game.HEIGHT - Mouse.getY());
    }

    public static boolean getMouseUp(int mouseButton) {
	return upMouse.contains(mouseButton);
    }

    public static int getMouseX() {
	return Mouse.getX();
    }

    public static int getMouseY() {
	return Mouse.getY();
    }

    public static void showMouse(boolean show) {
	Mouse.setGrabbed(true);
    }

    public static void update() {
	upKeys.clear();
	for (int i = 0; i < NUM_KEYCODES; i++) {
	    if (!getKey(i) && currentKeys.contains(i))
		upKeys.add(i);
	}
	downKeys.clear();
	for (int i = 0; i < NUM_KEYCODES; i++) {
	    if (getKey(i) && !currentKeys.contains(i))
		downKeys.add(i);
	}
	currentKeys.clear();
	for (int i = 0; i < NUM_KEYCODES; i++) {
	    if (getKey(i))
		currentKeys.add(i);
	}

	upMouse.clear();
	for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
	    if (!getMouse(i) && currentMouse.contains(i))
		upMouse.add(i);
	}
	downMouse.clear();
	for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
	    if (getMouse(i) && !currentMouse.contains(i))
		downMouse.add(i);
	}
	currentMouse.clear();
	for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
	    if (getMouse(i))
		currentMouse.add(i);
	}
    }
}
