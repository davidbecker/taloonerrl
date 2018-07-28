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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;

import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.ETurnType;

/**
 *
 * we don't need "dangling" decorations from the last turn so we kill them all
 * in our clean up phases
 *
 * @author David Becker
 *
 */
public class StateDecorationCleanupSystem extends IteratingSystem {

	private StateDecorationComponent component;
	private ETurnType killOnTurn;

	/**
	 * Constructor.
	 */
	public StateDecorationCleanupSystem() {
		super(Aspect.all(StateDecorationComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		component = ComponentMappers.getInstance().stateDecoration.get(_entityId);
		killOnTurn = component.getKillOnTurn();
		final ETurnType currentTurnSide = GameEngine.getInstance().getCurrentTurnSide();
		// if the current turn side is the side we should kill the decoration we kill it

		// if there is no killing side set for the component we kill it on any cleanup
		// turn
		if (currentTurnSide == killOnTurn
				|| killOnTurn == null && (currentTurnSide == ETurnType.MONSTER_CLEANUP
						|| currentTurnSide == ETurnType.PLAYER_CLEANUP)) {
			GameEngine.getInstance().deleteEntity(_entityId);
		}
	}

}
