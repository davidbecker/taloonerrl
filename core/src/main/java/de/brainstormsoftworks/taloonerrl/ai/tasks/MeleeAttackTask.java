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
package de.brainstormsoftworks.taloonerrl.ai.tasks;

import com.artemis.Entity;

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

	@Override
	public Status execute() {
		final Entity attackerEntity = getObject();
		final TargetComponent attackerTarget = ComponentMappers.getInstance().target.getSafe(attackerEntity);
		final int targetId = attackerTarget != null ? attackerTarget.getTargetId() : -1;
		if (targetId == -1) {
			return Status.FAILED;
		}
		final PositionComponent targetPosition = ComponentMappers.getInstance().position.getSafe(targetId);
		final PositionComponent ownPosition = ComponentMappers.getInstance().position.getSafe(attackerEntity);
		final TurnComponent turnComponent = ComponentMappers.getInstance().turn.getSafe(attackerEntity);
		if (targetPosition != null && ownPosition != null && turnComponent != null
				&& PositionUtil.arePositionsAdjacent(targetPosition, ownPosition)) {
			turnComponent
					.setCurrentTurn(Move.from(Direction.toGoTo(PositionUtil.convertToSquidCoord(ownPosition),
							PositionUtil.convertToSquidCoord(targetPosition))));
			return Status.SUCCEEDED;
		}
		return Status.FAILED;
	}

}
