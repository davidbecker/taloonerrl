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
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import squidpony.squidmath.Coord;

/**
 * task for an entity to take one unoccupied adjacent tile
 *
 * @author David Becker
 *
 */
public class WanderingTask extends StatelessLeafTask {
	private static final int THRESHOLD = 5;

	/*
	 * extracted variables into fields to avoid GC
	 */

	private static PositionComponent positionComponent;
	private static TurnComponent turnComponent;

	private static int timeTried;
	private static int direction;
	private static Coord newPosition;
	private static Coord currentCoord;

	@Override
	public Status doExecute() {
		positionComponent = ComponentMappers.getInstance().position.getSafe(entityId);
		turnComponent = ComponentMappers.getInstance().turn.getSafe(entity);
		if (PositionUtil.isValidPosition(positionComponent) && turnComponent != null) {
			timeTried = 0;
			currentCoord = Coord.get(positionComponent.getX(), positionComponent.getY());
			while (timeTried < THRESHOLD) {
				direction = Move.randomDirection();
				newPosition = PositionUtil.apply(currentCoord, direction);
				if (MapManager.getInstance().getMap().isWalkable(newPosition.x, newPosition.y)
						&& !PositionUtil.isPositionTaken(newPosition.x, newPosition.y, entity.getId())) {
					turnComponent.setCurrentTurn(direction);
					// Gdx.app.log("WanderingTask", "direction: " + Move.toString(direction));
					return Status.SUCCEEDED;
				}
				timeTried += 1;
			}
		}
		return Status.FAILED;
	}

}
