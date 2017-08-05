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
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.Entity;
import com.artemis.PooledComponent;
import com.artemis.annotations.EntityId;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import lombok.Getter;
import lombok.Setter;

/**
 * component to hold active states for an entity
 *
 * @author David Becker
 *
 */
@Getter
public class StatusComponent extends PooledComponent implements IGetEntityId {

	private final EntityStatus sleepingStatus = new EntityStatus(EEntityState.SLEEPING);
	// TODO add more states

	@Setter
	@EntityId
	private int entityId = -1;

	/**
	 * tests if a given state is active for the entity
	 *
	 * @param _state
	 * @return
	 */
	public boolean isActive(final EEntityState _state) {
		final EntityStatus state = getState(_state);
		return state != null && state.isActive();
	}

	/**
	 * activates a given state
	 *
	 * @param _state
	 *            state to activate
	 * @param _duration
	 *            amount of turns that this state should be active. cool down will
	 *            be infinite if negative values are provided or duration is set to
	 *            {@link Integer#MAX_VALUE} or 0
	 */
	public void activateState(final EEntityState _state, final int _duration) {
		final EntityStatus state = getState(_state);
		if (state != null) {
			if (!state.isActive() && entityId != -1) {
				// find position component for entity that this component belongs too
				final PositionComponent positionComponentThis = ComponentMappers.getInstance().position
						.get(entityId);
				// spawn new decorator entity for given state
				final Entity newEntity = GameEngine.getInstance().createNewEntity(
						EEntity.STATUS_DECORATOR_SLEEPING, positionComponentThis.getX(),
						positionComponentThis.getY());
				final StateDecorationComponent stateDecorationComponent = ComponentMappers
						.getInstance().stateDecoration.get(newEntity);
				// now we set the state into the decorator component
				stateDecorationComponent.setState(_state);
				// and clone the position from the parent entity for the new entity
				ComponentMappers.getInstance().position.get(newEntity)
						.overrideComponent(positionComponentThis);
				// and set the correct sprite
				newEntity.getComponent(AnimationComponent.class)
						.mapAnimation(EEntity.STATUS_DECORATOR_SLEEPING);
			}
			state.reset();
			state.setActive(true);
			if (0 < _duration && _duration < Integer.MAX_VALUE) {
				state.setCooldownActive(true);
				state.setCooldownDuration(_duration);
			}
		} else {
			Gdx.app.error(getClass().getSimpleName(), "failed to activate " + EEntityState.toString(_state));
		}
	}

	/**
	 * cool down all states by one turn<br>
	 * will deactivate state that expired
	 */
	public void processCooldowns() {
		sleepingStatus.processCooldown();
	}

	/**
	 * checks if any state is active that prevents the entity from taking their turn
	 *
	 * @return true if entity is blocked by state
	 */
	public boolean isBlockingStatusActive() {
		return sleepingStatus.isActive();
	}

	@Override
	protected void reset() {
		sleepingStatus.reset();
		entityId = -1;
	}

	private EntityStatus getState(final EEntityState _state) {
		switch (_state) {
		case SLEEPING:
			return sleepingStatus;
		default:
			Gdx.app.error(getClass().getSimpleName(), "unknown state " + EEntityState.toString(_state));
			return null;
		}
	}
}
