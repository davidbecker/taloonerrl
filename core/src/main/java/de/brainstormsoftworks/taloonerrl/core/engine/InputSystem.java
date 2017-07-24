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
package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Aspect;
import com.artemis.EntitySubscription;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.math.IntVector2;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import lombok.Getter;

/**
 * system that processes the input of the player
 *
 * @author David Becker
 *
 */
@Getter
public final class InputSystem extends InputAdapter {

	private boolean keyPressedUp = false;
	private boolean keyPressedDown = false;
	private boolean keyPressedLeft = false;
	private boolean keyPressedRight = false;

	private int mouseOverX = 0;
	private int mouseOverY = 0;

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

	@Override
	public boolean touchDown(final int _screenX, final int _screenY, final int _pointer, final int _button) {
		// invalidate all the previous input
		reset();

		if (_button == Buttons.LEFT) {
			// TODO check if UI has been clicked

			toggleHighlightedActors();
			return true;
		}
		return false;
	}

	/**
	 * toggles highlighted state of actor that is on the same tile as the mouse is
	 * over
	 */
	private void toggleHighlightedActors() {
		final EntitySubscription entitySubscription = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, HighlightAbleComponent.class));
		final IntBag entities = entitySubscription.getEntities();
		PositionComponent positionComponent;
		HighlightAbleComponent highlight;
		for (int i = 0; i < entities.size(); i++) {
			positionComponent = ComponentMappers.getInstance().position.get(i);
			if (positionComponent.getX() == InputSystem.getInstance().getMouseOverX()
					&& positionComponent.getY() == InputSystem.getInstance().getMouseOverY()) {
				highlight = ComponentMappers.getInstance().highlight.get(i);
				highlight.toggleHighlighted();
			}
		}
	}

	@Override
	public boolean touchUp(final int _screenX, final int _screenY, final int _pointer, final int _button) {
		return false;
	}

	@Override
	public boolean mouseMoved(final int _screenX, final int _screenY) {
		unprojectMouseOver(_screenX, _screenY);
		return true;
	}

	@Override
	public boolean touchDragged(final int _screenX, final int _screenY, final int _pointer) {
		unprojectMouseOver(_screenX, _screenY);
		System.err.println("dragged " + _screenX + " " + _screenY + " " + _pointer);
		return super.touchDragged(_screenX, _screenY, _pointer);
	}

	private final void unprojectMouseOver(final int _screenX, final int _screenY) {
		final IntVector2 unproject = Renderer.getInstance()
				.unprojectFromCamera(new Vector3(_screenX, _screenY, 0));
		mouseOverX = unproject.getX();
		mouseOverY = unproject.getY();
	}

}
