package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.Gdx;

import lombok.Getter;

/**
 * component to hold active states for an entity
 *
 * @author David Becker
 *
 */
public class StatusComponent extends PooledComponent {

	@Getter
	private final EntityStatus sleepingStatus = new EntityStatus(EEntityState.SLEEPING);
	// TODO add more states

	/**
	 * tests if a given state is active for the entity
	 *
	 * @param _state
	 * @return
	 */
	public boolean isActive(final EEntityState _state) {
		switch (_state) {
		case SLEEPING:
			return sleepingStatus.isActive();
		default:
			return false;
		}
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
		EntityStatus state = null;
		switch (_state) {
		case SLEEPING:
			state = sleepingStatus;
			break;
		default:
			break;
		}
		if (state != null) {
			state.reset();
			state.setActive(true);
			if (0 < _duration && _duration < Integer.MAX_VALUE) {
				state.setCooldownActive(true);
				state.setCooldownDuration(_duration);
			}
		} else {
			Gdx.app.error("StatusComponent", "failed to activate " + EEntityState.toString(_state));
		}
	}

	@Override
	protected void reset() {
		sleepingStatus.reset();
	}
}
