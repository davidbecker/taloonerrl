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

import com.artemis.PooledComponent;
import com.artemis.annotations.EntityId;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.system.util.StateDecorationUtil;
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

	@Setter
	@EntityId
	private int entityId = -1;

	/*
	 * when adding new states the following methods must be modified: reset(),
	 * isBlockingStatusActive() or isAnyStatusActive(), processCooldowns() and
	 * getState()
	 */
	private final EntityStatus sleepStatus = new EntityStatus(EEntityState.SLEEPING);
	private final EntityStatus alertStatus = new EntityStatus(EEntityState.ALERT);
	private final EntityStatus confusedStatus = new EntityStatus(EEntityState.CONFUSED);
	private final EntityStatus deadStatus = new EntityStatus(EEntityState.DEAD) {
		@Override
		boolean processCooldown() {
			// for kind of obvious reasons...
			return false;
		};
	};

	@Override
	protected void reset() {
		entityId = -1;
		sleepStatus.reset();
		alertStatus.reset();
		confusedStatus.reset();
		deadStatus.reset();
		// add new states here
	}

	/**
	 * checks if any state is active that prevents the entity from taking their turn
	 *
	 * @return true if entity is blocked by state
	 */
	public boolean isBlockingStatusActive() {
		// add additional blocking states here
		return sleepStatus.isActive() || deadStatus.isActive() || sleepStatus.isActive()
				|| confusedStatus.isActive();
	}

	public boolean isAnyStatusActive() {
		// add additional non-blocking states here
		return isBlockingStatusActive() || alertStatus.isActive();
	}

	/**
	 * cool down all states by one turn<br>
	 * will deactivate state that expired
	 */
	public void processCooldowns() {
		// add new states here
		final boolean processed = sleepStatus.processCooldown() || alertStatus.processCooldown()
				|| confusedStatus.processCooldown();
		// last active state has cooled down
		if (processed && !isAnyStatusActive()) {
			StateDecorationUtil.getInsance().spawnStatusDecoration(entityId, EEntityState.NONE, 0f, 0f);
		}
	}

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
		activateState(_state, _duration, 0f, 0f);
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
	public void activateState(final EEntityState _state, final int _duration, final float _ttl,
			final float _ttlDelay) {
		final EntityStatus state = getState(_state);
		if (state != null) {
			if (!state.isActive()) {
				StateDecorationUtil.getInsance().spawnStatusDecoration(entityId, _state, _ttl, _ttlDelay);
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

	private EntityStatus getState(final EEntityState _state) {
		switch (_state) {
		case ALERT:
			return alertStatus;
		case CONFUSED:
			return confusedStatus;
		case DEAD:
			return deadStatus;
		case SLEEPING:
			return sleepStatus;
		// add new states here
		default:
			Gdx.app.error(getClass().getSimpleName(), "unknown state " + EEntityState.toString(_state));
			return null;
		}
	}
}
