/*******************************************************************************
 * Copyright (c) 2015 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 * system that processes the input of the player
 *
 * @author David Becker
 *
 */
public final class InputSystem extends InputAdapter {

	private boolean keyPressedUp = false;
	private boolean keyPressedDown = false;
	private boolean keyPressedLeft = false;
	private boolean keyPressedRight = false;

	private static final InputSystem instance = new InputSystem();

	/**
	 * Constructor.<br/>
	 * private on purpose
	 */
	private InputSystem() {
	}

	/**
	 * getter for the instance of the singleton
	 *
	 * @return instance
	 */
	public static InputSystem getInstance() {
		return instance;
	}

	// FIXME not needed?
	// private final float delayToNextTurn = 0f;
	// /** minimum delay between player turns */
	// private static final float delayBetweenTurns = 0.03f;

	@Override
	public boolean keyDown(final int keycode) {
		// invalidate all the previous input
		reset();

		// final float deltaTime = Gdx.graphics.getDeltaTime();
		// delayToNextTurn -= deltaTime;
		// if (delayToNextTurn <= 0f) {
		// set new input
		switch (keycode) {
		case Keys.UP:
			keyPressedUp = true;
			// delayToNextTurn = delayBetweenTurns;
			return true;
		case Keys.DOWN:
			keyPressedDown = true;
			// delayToNextTurn = delayBetweenTurns;
			return true;
		case Keys.LEFT:
			keyPressedLeft = true;
			// delayToNextTurn = delayBetweenTurns;
			return true;
		case Keys.RIGHT:
			keyPressedRight = true;
			// delayToNextTurn = delayBetweenTurns;
			return true;
		default:
			// don't count as a turn
			return false;
		}
		// }
		// return false;
	}

	/**
	 * sets all key presses to false
	 */
	public void reset() {
		keyPressedUp = false;
		keyPressedDown = false;
		keyPressedLeft = false;
		keyPressedRight = false;
	}

	@Override
	public boolean keyUp(final int keycode) {
		switch (keycode) {
		case Keys.UP:
			keyPressedUp = false;
			return true;
		case Keys.DOWN:
			keyPressedDown = false;
			return true;
		case Keys.LEFT:
			keyPressedLeft = false;
			return true;
		case Keys.RIGHT:
			keyPressedRight = false;
			return true;
		default:
			return false;
		}
	}

	/**
	 * getter for keyPressedUp
	 *
	 * @return the keyPressedUp
	 */
	public boolean isKeyPressedUp() {
		return keyPressedUp;
	}

	/**
	 * getter for keyPressedDown
	 *
	 * @return the keyPressedDown
	 */
	public boolean isKeyPressedDown() {
		return keyPressedDown;
	}

	/**
	 * getter for keyPressedLeft
	 *
	 * @return the keyPressedLeft
	 */
	public boolean isKeyPressedLeft() {
		return keyPressedLeft;
	}

	/**
	 * getter for keyPressedRight
	 *
	 * @return the keyPressedRight
	 */
	public boolean isKeyPressedRight() {
		return keyPressedRight;
	}

}
