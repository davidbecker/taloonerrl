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

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

/**
 * system to check if an entity has its turn taken. set
 * {@link TurnComponent#setTurnTaken(boolean)} to true in this case
 *
 * @author David Becker
 *
 */
public class TurnTakenSystem extends IteratingSystem {

	private PositionComponent positionComponent;
	private TurnComponent turnComponent;

	public TurnTakenSystem() {
		super(Aspect.all(PositionComponent.class, TurnComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		turnComponent = ComponentMappers.getInstance().turn.get(_entityId);
		if (!turnComponent.isTurnTaken()
				&& turnComponent.getMovesOnTurn() == GameEngine.getInstance().getCurrentTurnSide()) {
			if(turnComponent.isProcessed()) {
			positionComponent = ComponentMappers.getInstance().position.get(_entityId);
			turnComponent
					.setTurnTaken(positionComponent.getOffsetX() == 0 && positionComponent.getOffsetY() == 0);}
		}
	}

}
