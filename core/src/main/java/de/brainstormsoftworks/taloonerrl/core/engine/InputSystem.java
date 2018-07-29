/*******************************************************************************
 * Copyright (c) 2015-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.core.engine;

import java.util.ArrayList;

import com.artemis.Aspect;
import com.artemis.EntitySubscription;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import de.brainstormsoftworks.taloonerrl.components.CursorComponent;
import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.TurnScheduler;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.math.IntVector2;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import lombok.Getter;
import squidpony.squidai.DijkstraMap;
import squidpony.squidmath.Coord;

/**
 * system that processes the input of the player
 *
 * @author David Becker
 *
 */
@Getter
public final class InputSystem extends InputAdapter {

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

	@Override
	public boolean keyDown(final int keycode) {
		switch (keycode) {
		case Keys.UP:
			TurnScheduler.getInstance().addTurnToQueue(Move.UP);
			return true;
		case Keys.DOWN:
			TurnScheduler.getInstance().addTurnToQueue(Move.DOWN);
			return true;
		case Keys.LEFT:
			TurnScheduler.getInstance().addTurnToQueue(Move.LEFT);
			return true;
		case Keys.RIGHT:
			TurnScheduler.getInstance().addTurnToQueue(Move.RIGHT);
			return true;
		case Keys.SPACE:
			TurnScheduler.getInstance().addTurnToQueue(Move.WAIT);
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean keyUp(final int keycode) {
		return false;
	}

	@Override
	public boolean touchDown(final int _screenX, final int _screenY, final int _pointer, final int _button) {
		boolean processed = false;
		if (_button == Buttons.LEFT) {

			// TODO check if UI has been clicked

			// do things if the cursor is in the visible area
			if (FovWrapper.getInstance().isLit(mouseOverX, mouseOverY)) {
				processed = toggleHighlightedActors();
			}
			if (!processed) {
				processed = addPlayerWalkPath();
			}
			return processed;
		}
		return processed;
	}

	private boolean addPlayerWalkPath() {
		if (MapManager.getInstance().getMap().isInMapBounds(mouseOverX, mouseOverY)
				&& MapManager.getInstance().getMap().isVisited(mouseOverX, mouseOverY)) {
			final EntitySubscription entitySubscription = GameEngine.getInstance()
					.getAspectSubscriptionManager()
					.get(Aspect.all(PositionComponent.class, PlayerComponent.class));
			final PositionComponent positionComponent = ComponentMappers.getInstance().position
					.get(entitySubscription.getEntities().get(0));
			// skip current path and override it with new on click for now
			// if (!positionComponent.isProcessingTurn()) {
			Coord start = Coord.get(positionComponent.getX(), positionComponent.getY());
			final DijkstraMap dijkstraMap = MapManager.getInstance().getMap().getDijkstraMap();
			dijkstraMap.setGoal(mouseOverX, mouseOverY);
			final ArrayList<Coord> path = dijkstraMap.findPath(Integer.MAX_VALUE, null, null, start);
			final int size = path.size();
			final int[] directions = new int[size];
			int index = 0;
			for (final Coord coord : path) {
				directions[index] = Move.from(squidpony.squidgrid.Direction.toGoTo(start, coord));
				start = coord;
				index = index + 1;
			}
			TurnScheduler.getInstance().addTurnsToQueue(directions);
			// }
			return true;
		}
		return false;
	}

	/**
	 * toggles highlighted state of actor that is on the same tile as the mouse is
	 * over
	 */
	private boolean toggleHighlightedActors() {
		boolean toggled = false;
		@SuppressWarnings("unchecked")
		final EntitySubscription entitySubscription = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, HighlightAbleComponent.class)
						.exclude(CursorComponent.class));
		final IntBag entities = entitySubscription.getEntities();
		PositionComponent positionComponent;
		HighlightAbleComponent highlight;
		for (int i = 0; i < entities.size(); i++) {
			positionComponent = ComponentMappers.getInstance().position.getSafe(i);
			if (PositionUtil.isValidPosition(positionComponent) && positionComponent.getX() == mouseOverX
					&& positionComponent.getY() == mouseOverY) {
				highlight = ComponentMappers.getInstance().highlight.get(i);
				highlight.toggleHighlighted();
				toggled = true;
			}
		}
		return toggled;
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
		// System.err.println("dragged " + _screenX + " " + _screenY + " " + _pointer);
		return super.touchDragged(_screenX, _screenY, _pointer);
	}

	private final void unprojectMouseOver(final int _screenX, final int _screenY) {
		final IntVector2 unproject = Renderer.getInstance()
				.unprojectFromCamera(new Vector3(_screenX, _screenY, 0));
		mouseOverX = unproject.getX();
		mouseOverY = unproject.getY();
	}

}
