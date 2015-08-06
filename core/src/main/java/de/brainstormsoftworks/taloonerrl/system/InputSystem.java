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

import com.artemis.BaseSystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * system that processes the input of the player
 *
 * @author David Becker
 *
 */
public class InputSystem extends BaseSystem implements InputProcessor {

	private static boolean keyPressedUp = false;
	private static boolean keyPressedDown = false;
	private static boolean keyPressedLeft = false;
	private static boolean keyPressedRight = false;

	@Override
	public boolean keyDown(final int keycode) {
		// invalidate all the previous input
		keyPressedUp = false;
		keyPressedDown = false;
		keyPressedLeft = false;
		keyPressedRight = false;

		// set new input
		if (Keys.UP == keycode) {
			keyPressedUp = true;
			return true;
		}
		if (Keys.DOWN == keycode) {
			keyPressedDown = true;
			return true;
		}
		if (Keys.LEFT == keycode) {
			keyPressedLeft = true;
			return true;
		}
		if (Keys.RIGHT == keycode) {
			keyPressedRight = true;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(final int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processSystem() {
		// TODO Auto-generated method stub

	}

	/**
	 * getter for {@link #keyPressedUp}
	 *
	 * @return the keyPressedUp
	 */
	public static final boolean isKeyPressedUp() {
		return keyPressedUp;
	}

	/**
	 * getter for {@link #keyPressedDown}
	 *
	 * @return the keyPressedDown
	 */
	public static final boolean isKeyPressedDown() {
		return keyPressedDown;
	}

	/**
	 * getter for {@link #keyPressedLeft}
	 *
	 * @return the keyPressedLeft
	 */
	public static final boolean isKeyPressedLeft() {
		return keyPressedLeft;
	}

	/**
	 * getter for {@link #keyPressedRight}
	 *
	 * @return the keyPressedRight
	 */
	public static final boolean isKeyPressedRight() {
		return keyPressedRight;
	}

}
