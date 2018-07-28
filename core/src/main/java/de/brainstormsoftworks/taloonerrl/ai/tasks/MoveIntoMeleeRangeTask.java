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

import com.artemis.Entity;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TargetComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;

/**
 * task that tries to figure out the next step to take until target is in range
 *
 * @author David Becker
 *
 */
public class MoveIntoMeleeRangeTask extends StatelessLeafTask {

	/*
	 * extracted variables into fields to avoid GC
	 */

	private static Entity attackerEntity;
	private static int attackerId;
	private static int targetId;

	private static TargetComponent attackerTarget;
	private static PositionComponent targetPosition;
	private static PositionComponent ownPosition;
	private static TurnComponent turnComponent;

	private static int ownX;
	private static int ownY;
	private static int targetX;
	private static int targetY;
	private static int deltaX;
	private static int deltaY;
	private static int dX;
	private static int dY;

	private static boolean preferX;
	private static Status returnStatus;
	private static int direction;

	@Override
	public Status execute() {
		attackerEntity = getObject();
		attackerId = attackerEntity.getId();
		attackerTarget = ComponentMappers.getInstance().target.getSafe(attackerEntity);
		targetId = attackerTarget != null ? attackerTarget.getTargetId() : -1;
		if (targetId == -1) {
			return Status.FAILED;
		}
		targetPosition = ComponentMappers.getInstance().position.getSafe(targetId);
		ownPosition = ComponentMappers.getInstance().position.getSafe(attackerEntity);
		turnComponent = ComponentMappers.getInstance().turn.getSafe(attackerEntity);
		if (targetPosition != null && ownPosition != null && turnComponent != null) {
			if (PositionUtil.arePositionsAdjacent(targetPosition, ownPosition)) {
				return Status.SUCCEEDED;
			}
			ownX = ownPosition.getX();
			ownY = ownPosition.getY();
			targetX = targetPosition.getX();
			targetY = targetPosition.getY();
			deltaX = targetX - ownX;
			deltaY = targetY - ownY;
			dX = PositionUtil.nominalDirection(deltaX);
			dY = PositionUtil.nominalDirection(deltaY);

			// replace with absolute values
			deltaX = Math.abs(deltaX);
			deltaY = Math.abs(deltaY);
			// if both directions are the same distance away, we choose one randomly
			preferX = deltaX > deltaY || deltaX == deltaY && GameEngine.getRng().nextBoolean();

			returnStatus = preferX ? moveHorizontal() : moveVertically();
			// if we couldn't find a direction to move to and both directions are the same
			// distance away we try the other one
			if (returnStatus == null && deltaX == deltaY) {
				returnStatus = !preferX ? moveHorizontal() : moveVertically();
			}
			return returnStatus == null ? Status.FAILED : returnStatus;
		}
		return Status.FAILED;
	}

	private static Status moveHorizontal() {
		if (dX != 0 && MapManager.getInstance().getMap().isWalkable(ownX + dX, ownY)
				&& !PositionUtil.isPositionTaken(ownX + dX, ownY, attackerId)) {
			turnComponent.setCurrentTurn(dX > 0 ? Move.RIGHT : Move.LEFT);
			return getReturnStatus(ownX, dX, targetX, ownY, 0, targetY);
		}
		direction = GameEngine.getRng().nextBoolean() ? 1 : -1;
		Status returnStatus = verticalStep(direction);
		if (returnStatus == null) {
			direction *= -1;
			returnStatus = verticalStep(direction);
		}
		return returnStatus;
	}

	private static Status moveVertically() {
		if (dY != 0 && MapManager.getInstance().getMap().isWalkable(ownX, ownY + dY)
				&& !PositionUtil.isPositionTaken(ownX, ownY + dY, attackerId)) {
			turnComponent.setCurrentTurn(dY > 0 ? Move.UP : Move.DOWN);
			return getReturnStatus(ownX, 0, targetX, ownY, dY, targetY);
		}
		direction = GameEngine.getRng().nextBoolean() ? 1 : -1;
		Status returnStatus = horizontalStep(direction);
		if (returnStatus == null) {
			direction *= -1;
			returnStatus = horizontalStep(direction);
		}
		return returnStatus;
	}

	private static Status horizontalStep(final int _dX) {
		if (MapManager.getInstance().getMap().isWalkable(ownX + _dX, ownY)
				&& !PositionUtil.isPositionTaken(ownX + _dX, ownY, attackerId)) {
			turnComponent.setCurrentTurn(_dX == 1 ? Move.RIGHT : Move.LEFT);
			return getReturnStatus(ownX, _dX, targetX, ownY, 0, targetY);
		}
		return null;
	}

	private static Status verticalStep(final int _dY) {
		if (MapManager.getInstance().getMap().isWalkable(ownX, ownY + _dY)
				&& !PositionUtil.isPositionTaken(ownX, ownY + _dY, attackerId)) {
			turnComponent.setCurrentTurn(_dY == 1 ? Move.UP : Move.DOWN);
			return getReturnStatus(ownX, 0, targetX, ownY, _dY, targetY);
		}
		return null;
	}

	private static Status getReturnStatus(final int _x, final int _dX, final int _targetX, final int _y,
			final int _dY, final int _targetY) {
		return _x + _dY == _targetX && _y + _dY == _targetY ? Status.SUCCEEDED : Status.RUNNING;
	}

}
