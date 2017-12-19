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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.utils.IntBag;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.ETurnType;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import squidpony.squidai.DijkstraMap;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/**
 * task for an entity that flees from the player (and maybe all allies of the
 * player). <br>
 * skips tiles occupied by allies of the entity
 *
 * @author David Becker
 *
 */
public class FleeTask extends StatelessLeafTask {

	@Override
	public Status execute() {
		final Entity entity = getObject();
		final int entityId = entity.getId();
		final PositionComponent ownPosition = ComponentMappers.getInstance().position.getSafe(entityId);
		final TurnComponent turnComponent = ComponentMappers.getInstance().turn.getSafe(entityId);
		if (ownPosition == null || turnComponent == null) {
			return Status.FAILED;
		}

		// get field of view for current entity
		final double[][] fovMap = FovWrapper.getInstance().getFovForPosition(ownPosition.getX(),
				ownPosition.getY());

		final Set<Coord> visible = new HashSet<>();
		final int width = fovMap.length;
		int height = 0;
		if (width > 0) {
			height = fovMap[0].length;
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (fovMap[x][y] > 0d) {
					visible.add(Coord.get(x, y));
				}
			}
		}

		// get all visible entities and distribute them to two lists, one for allies,
		// one for enemies
		final Set<Coord> allies = new HashSet<>();
		final Set<Coord> enemies = new HashSet<>();
		final ETurnType movesOnTurn = turnComponent.getMovesOnTurn();
		final ETurnType enemyTurnType = ETurnType.getInverseType(movesOnTurn);
		final IntBag entities = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, TurnComponent.class)).getEntities();
		PositionComponent position;
		TurnComponent turn;
		int otherId;
		ETurnType ett = null;
		for (int i = 0; i < entities.size(); i++) {
			otherId = entities.get(i);
			if (otherId != entityId) {
				position = ComponentMappers.getInstance().position.getSafe(otherId);
				turn = ComponentMappers.getInstance().turn.getSafe(otherId);
				ett = turn.getMovesOnTurn();
				if (ett == movesOnTurn) {
					allies.add(Coord.get(position.getX(), position.getY()));
					allies.add(Coord.get(position.getTargetX(), position.getTargetY()));
				} else if (ett == enemyTurnType) {
					enemies.add(Coord.get(position.getX(), position.getY()));
				}
			}
		}

		if (enemies.size() == 0) {
			return Status.SUCCEEDED;
		}

		final DijkstraMap dijkstraMap = MapManager.getInstance().getMap().getDijkstraMap();
		final ArrayList<Coord> fleePath = dijkstraMap.findFleePath(5, 1.2, null, allies,
				Coord.get(ownPosition.getX(), ownPosition.getY()), enemies.toArray(new Coord[] {}));
		if (fleePath.size() == 0) {
			return Status.FAILED;
		}
		turnComponent.setCurrentTurn(
				Move.from(Direction.toGoTo(PositionUtil.convertToSquidCoord(ownPosition), fleePath.get(0))));

		return Status.RUNNING;
	}

}
