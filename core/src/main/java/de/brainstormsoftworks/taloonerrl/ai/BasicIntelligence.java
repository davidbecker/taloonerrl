package de.brainstormsoftworks.taloonerrl.ai;

import com.artemis.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;

/**
 * basic intelligence that operates on a state machine
 *
 * @author David Becker
 *
 */
public class BasicIntelligence {

	protected StateMachine<Entity, State<Entity>> stateMachine;

	public BasicIntelligence(final StateMachine<Entity, State<Entity>> _stateMachine) {
		stateMachine = _stateMachine;
	}

	public void update() {
		if (stateMachine != null) {
			stateMachine.update();
		}
	}

}
