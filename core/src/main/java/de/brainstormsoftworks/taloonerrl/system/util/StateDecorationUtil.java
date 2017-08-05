/*******************************************************************************
 * Copyright (c) 2017 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system.util;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.EEntityState;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
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

	// TODO refactor into more performant collection ?
	private final Map<Integer, Float> baseDelay = new HashMap<>();

	private StateDecorationUtil() {
	}

	/**
	 * creates a new decoration entity at the same position as the parent entity<br>
	 * will display a speech bubble with the given state for a while to the player
	 *
	 * @param _state
	 *            state to display to the player
	 * @param _delay
	 */
	public void spawnStatusDecoration(final int _entityId, final EEntityState _state, final float _duration,
			final float _delay) {
		// sanity checks
		if (_entityId == -1) {
			Gdx.app.error(getClass().getSimpleName(),
					"no entity to display state " + EEntityState.toString(_state) + " for");
			return;
		}
		float end = TTL_BASE + _duration;
		if (end <= 0f) {
			Gdx.app.log(getClass().getSimpleName(), "no time to display anything -> aborting");
		}

		// find position component for entity that this component belongs too
		final PositionComponent positionComponentParent = ComponentMappers.getInstance().position
				.getSafe(_entityId);
		if (positionComponentParent == null) {
			Gdx.app.error(getClass().getSimpleName(), "parent position component not found -> aborting");
			return;
		}

		// get the start time for our new decoration
		final Integer entityId = Integer.valueOf(_entityId);
		final Float baseDelayStart = baseDelay.get(entityId);
		final float stateTime = GameEngine.getInstance().getStateTime();
		float start = baseDelayStart != null ? baseDelayStart.floatValue() : stateTime;
		start += _delay;
		if (start < stateTime) {
			start = stateTime;
		}

		end += start;

		// spawn new decorator entity for given state
		final EEntity decorator = EEntityState.toDecorator(_state);
		final Entity newEntity = GameEngine.getInstance().createNewEntity(decorator,
				positionComponentParent.getX(), positionComponentParent.getY());
		final StateDecorationComponent stateDecorationComponent = ComponentMappers
				.getInstance().stateDecoration.get(newEntity);
		// now we set the state into the decorator component
		stateDecorationComponent.setState(_state);
		stateDecorationComponent.setTimeToLiveStart(start);
		stateDecorationComponent.setTimeToLive(end);
		// and clone the position from the parent entity for the new entity
		ComponentMappers.getInstance().position.get(newEntity).overrideComponent(positionComponentParent);
		// and set the correct sprite
		newEntity.getComponent(AnimationComponent.class).mapAnimation(decorator);

		Gdx.app.debug(getClass().getSimpleName(),
				"created decoration for state " + EEntityState.toString(_state) + " for entity " + _entityId
						+ " start:" + start + " end:" + end);
		// store our end time as our new start time for the given entity
		baseDelay.put(entityId, Float.valueOf(end));
	}
}
