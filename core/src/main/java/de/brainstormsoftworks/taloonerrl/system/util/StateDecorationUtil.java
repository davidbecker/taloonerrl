/*******************************************************************************
 * Copyright (c) 2017-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system.util;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.EEntityState;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.ETurnType;
import lombok.Getter;

/**
 * utility class to create notifications for entities about states
 *
 * @author David Becker
 *
 */
public final class StateDecorationUtil {

	/** defaut time to live should be 1 seconds for now */
	public static final float TTL_BASE = 1.0f;

	@Getter
	private final static StateDecorationUtil insance = new StateDecorationUtil();

	private StateDecorationUtil() {
	}

	/**
	 * creates a new decoration entity at the same position as the parent entity<br>
	 * will display a speech bubble with the given state for a while to the player
	 *
	 * @param _entityId
	 *            id of the entity to decorate
	 * @param _state
	 *            state to display to the player
	 * @param _duration
	 *            how long the decoration should be displayed (maximum). 1.0f = 1
	 *            second
	 */
	public void spawnStatusDecoration(final int _entityId, final EEntityState _state) {
		spawnStatusDecoration(_entityId, _state, 0f, 0f);
	}

	/**
	 * creates a new decoration entity at the same position as the parent entity<br>
	 * will display a speech bubble with the given state for a while to the player
	 *
	 * @param _entityId
	 *            id of the entity to decorate
	 * @param _state
	 *            state to display to the player
	 * @param _duration
	 *            how long the decoration should be displayed (maximum). 1.0f = 1
	 *            second
	 */
	public void spawnStatusDecoration(final int _entityId, final EEntityState _state, final float _duration) {
		spawnStatusDecoration(_entityId, _state, _duration, 0f);
	}

	/**
	 * creates a new decoration entity at the same position as the parent entity<br>
	 * will display a speech bubble with the given state for a while to the player
	 *
	 * @param _entityId
	 *            id of the entity to decorate
	 * @param _state
	 *            state to display to the player
	 * @param _duration
	 *            how long the decoration should be displayed (maximum). 1.0f = 1
	 *            second
	 * @param _delay
	 *            delay until the decoration is displayed
	 */
	public void spawnStatusDecoration(final int _entityId, final EEntityState _state, final float _duration,
			final float _delay) {
		spawnStatusDecoration(_entityId, _state, _duration, _delay, getCleanupPhaseForEntity(_entityId));
	}

	/**
	 * creates a new decoration entity at the same position as the parent entity<br>
	 * will display a speech bubble with the given state for a while to the player
	 *
	 * @param _entityId
	 *            id of the entity to decorate
	 * @param _state
	 *            state to display to the player
	 * @param _duration
	 *            how long the decoration should be displayed (maximum). 1.0f = 1
	 *            second
	 * @param _delay
	 *            delay until the decoration is displayed
	 * @param _killTurn
	 *            turn on which the decoration shall be killed. set to null to not
	 *            kill decoration in a specific turn
	 */
	public void spawnStatusDecoration(final int _entityId, final EEntityState _state, final float _duration,
			final float _delay, final ETurnType _killTurn) {
		// sanity checks
		if (_entityId == -1) {
			Gdx.app.error(getClass().getSimpleName(),
					"no entity to display state " + EEntityState.toString(_state) + " for");
			return;
		}
		float end = TTL_BASE + _duration;
		if (end <= 0f) {
			Gdx.app.log(getClass().getSimpleName(), "no time to display anything -> aborting");
			return;
		}

		// find position component for entity that this component belongs too
		final PositionComponent positionComponentParent = ComponentMappers.getInstance().position
				.getSafe(_entityId);
		if (!PositionUtil.isValidPosition(positionComponentParent)) {
			Gdx.app.error(getClass().getSimpleName(), "parent position component not found -> aborting");
			return;
		}

		// get the start time for our new decoration
		final float start = GameEngine.getInstance().getStateTime();

		end += start;

		// spawn new decorator entity for given state
		final EEntity decorator = EEntityState.toDecorator(_state);
		final Entity newEntity = GameEngine.getInstance().createNewEntity(decorator,
				positionComponentParent.getX(), positionComponentParent.getY());
		final StateDecorationComponent stateDecorationComponent = ComponentMappers
				.getInstance().stateDecoration.getSafe(newEntity);
		if (stateDecorationComponent != null) {
			// now we set the state into the decorator component
			stateDecorationComponent.setState(_state);
			stateDecorationComponent.setTimeToLiveStart(start);
			stateDecorationComponent.setTimeToLive(end);
			stateDecorationComponent.setKillOnTurn(_killTurn);
			// and clone the position from the parent entity for the new entity
			ComponentMappers.getInstance().position.get(newEntity).overrideComponent(positionComponentParent);
			// and set the correct sprite
			newEntity.getComponent(AnimationComponent.class).mapAnimation(decorator);

			Gdx.app.debug(getClass().getSimpleName(),
					"created decoration for state " + EEntityState.toString(_state) + " for entity "
							+ _entityId + " start:" + start + " end:" + end);
		}
	}

	/**
	 * helper method to get the clean up turn for a given entity
	 *
	 * @param _entityId
	 * @return
	 */
	public static ETurnType getCleanupPhaseForEntity(final int _entityId) {
		final TurnComponent component = ComponentMappers.getInstance().turn.getSafe(_entityId);
		if (component != null) {
			switch (component.getMovesOnTurn()) {
			case MONSTER:
			case MONSTER_CLEANUP:
				return ETurnType.PLAYER_CLEANUP;
			case PLAYER:
			case PLAYER_CLEANUP:
				return ETurnType.MONSTER_CLEANUP;
			}
		}
		return null;
	}
}
