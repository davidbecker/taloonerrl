package de.brainstormsoftworks.taloonerrl.ai.states;

import com.artemis.Entity;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import squidpony.squidmath.RNG;

@Deprecated
public enum BatState implements State<Entity> {

	FLYING_ERRATICALLY() {

		private final RNG rng = new RNG();
		private TurnComponent turnComponent;

		@Override
		public void update(final Entity _entity) {
			debug("updating state FLYING_ERRATICALLY");
			turnComponent = ComponentMappers.getInstance().turn.getSafe(_entity);
			if (turnComponent != null) {
				switch (rng.nextInt() % 5) {
				case 0:
					turnComponent.nextTurn(Move.UP);
					break;
				case 1:
					turnComponent.nextTurn(Move.DOWN);
					break;
				case 2:
					turnComponent.nextTurn(Move.LEFT);
					break;
				case 3:
					turnComponent.nextTurn(Move.RIGHT);
					break;
				case 4:
					turnComponent.nextTurn(Move.WAIT);
					break;
				default:
					turnComponent.nextTurn(Move.IDLE);
					break;
				}
			}
		}

	},
	WAITING() {

		private TurnComponent turnComponent;

		@Override
		public void update(final Entity _entity) {
			debug("updating state WAITING");
			turnComponent = ComponentMappers.getInstance().turn.getSafe(_entity);
			if (turnComponent != null) {
				turnComponent.nextTurn(Move.WAIT);
			}
		}

	};
	@Override
	public void enter(final Entity _entity) {
	}

	@Override
	public void exit(final Entity _entity) {
	}

	@Override
	public boolean onMessage(final Entity _entity, final Telegram _telegram) {
		return false;
	}

	protected static void debug(final String _msg) {
		GdxAI.getLogger().debug(BatState.class.getSimpleName(), _msg);
	}

}
