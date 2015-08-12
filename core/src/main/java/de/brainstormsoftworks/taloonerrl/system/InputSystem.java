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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 * system that processes the input of the player
 *
 * @author David Becker
 *
 */
public final class InputSystem extends InputAdapter {

	private static boolean keyPressedUp = false;
	private static boolean keyPressedDown = false;
	private static boolean keyPressedLeft = false;
	private static boolean keyPressedRight = false;

	private float delayToNextTurn = 0f;
	/** minimum delay between player turns */
	private static final float delayBetweenTurns = 0.03f;

	@Override
	public boolean keyDown(final int keycode) {
		// invalidate all the previous input
		keyPressedUp = false;
		keyPressedDown = false;
		keyPressedLeft = false;
		keyPressedRight = false;

		final float deltaTime = Gdx.graphics.getDeltaTime();
		delayToNextTurn -= deltaTime;
		if (delayToNextTurn <= 0f) {
			// set new input
			if (Keys.UP == keycode) {
				keyPressedUp = true;
			} else if (Keys.DOWN == keycode) {
				keyPressedDown = true;
			} else if (Keys.LEFT == keycode) {
				keyPressedLeft = true;
			} else if (Keys.RIGHT == keycode) {
				keyPressedRight = true;
			}
			delayToNextTurn = delayBetweenTurns;
			return true;
		}
		return false;
	}

	/**
	 * getter for {@link #keyPressedUp}
	 *
	 * @return the keyPressedUp
	 */
	public static boolean isKeyPressedUp() {
		return keyPressedUp;
	}

	/**
	 * getter for {@link #keyPressedDown}
	 *
	 * @return the keyPressedDown
	 */
	public static boolean isKeyPressedDown() {
		return keyPressedDown;
	}

	/**
	 * getter for {@link #keyPressedLeft}
	 *
	 * @return the keyPressedLeft
	 */
	public static boolean isKeyPressedLeft() {
		return keyPressedLeft;
	}

	/**
	 * getter for {@link #keyPressedRight}
	 *
	 * @return the keyPressedRight
	 */
	public static boolean isKeyPressedRight() {
		return keyPressedRight;
	}

}
