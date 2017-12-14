package de.brainstormsoftworks.taloonerrl.ai;

import com.artemis.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;

/**
 * basic intelligence that operates on a state machine
 *
 * @author David Becker
 * @deprecated use {@link BehaviorTreeIntelligence} instead
 *
 */
@Deprecated
public class BasicIntelligence implements IAI {

	protected StateMachine<Entity, State<Entity>> stateMachine;

	public BasicIntelligence(final StateMachine<Entity, State<Entity>> _stateMachine) {
		stateMachine = _stateMachine;
	}

	@Override
	public void update() {
		if (stateMachine != null) {
			stateMachine.update();
		}
	}

}
