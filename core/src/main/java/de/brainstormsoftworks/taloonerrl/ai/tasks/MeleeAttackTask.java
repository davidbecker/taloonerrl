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
package de.brainstormsoftworks.taloonerrl.ai.tasks;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TargetComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import squidpony.squidgrid.Direction;

/**
 * task that tries to attack the melee target
 *
 * @author David Becker
 *
 */
public class MeleeAttackTask extends StatelessLeafTask {

	/*
	 * extracted variables into fields to avoid GC
	 */
	private static TargetComponent attackerTarget;
	private static int targetId;

	private static PositionComponent targetPosition;
	private static PositionComponent ownPosition;
	private static TurnComponent turnComponent;

	@Override
	public Status doExecute() {
		attackerTarget = ComponentMappers.getInstance().target.getSafe(entityId);
		targetId = attackerTarget != null ? attackerTarget.getTargetId() : -1;
		if (targetId == -1) {
			return Status.FAILED;
		}
		targetPosition = ComponentMappers.getInstance().position.getSafe(targetId);
		ownPosition = ComponentMappers.getInstance().position.getSafe(entity);
		turnComponent = ComponentMappers.getInstance().turn.getSafe(entity);
		if (PositionUtil.isValidPosition(targetPosition)
				&& PositionUtil.isValidPosition(ownPosition) && turnComponent != null
				&& PositionUtil.arePositionsAdjacent(targetPosition, ownPosition)) {
			turnComponent
					.setCurrentTurn(Move.from(Direction.toGoTo(PositionUtil.convertToSquidCoord(ownPosition),
							PositionUtil.convertToSquidCoord(targetPosition))));
			return Status.SUCCEEDED;
		}
		return Status.FAILED;
	}

}
