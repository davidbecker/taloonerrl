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

	/*
	 * extracted variables into fields to avoid GC
	 */

	private static Entity entity;
	private static int entityId;
	private static PositionComponent ownPosition;
	private static TurnComponent turnComponent;

	private static double[][] fovMap;
	private static Set<Coord> visible;
	private static int width;
	private static int height;

	private static Set<Coord> allies = new HashSet<>();
	private static Set<Coord> enemies = new HashSet<>();
	private static ETurnType movesOnTurn;
	private static ETurnType enemyTurnType;

	private static IntBag entities;
	private static PositionComponent position;
	private static TurnComponent turn;

	private static int otherId;
	private static ETurnType tmpTurnType;

	private static DijkstraMap dijkstraMap;
	private static ArrayList<Coord> fleePath;

	@Override
	public Status execute() {
		entity = getObject();
		entityId = entity.getId();
		ownPosition = ComponentMappers.getInstance().position.getSafe(entityId);
		turnComponent = ComponentMappers.getInstance().turn.getSafe(entityId);
		if (ownPosition == null || turnComponent == null) {
			return Status.FAILED;
		}

		// get field of view for current entity
		fovMap = FovWrapper.getInstance().getFovForPosition(ownPosition.getX(), ownPosition.getY());

		visible = new HashSet<>();
		width = fovMap.length;
		height = 0;
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
		allies.clear();
		enemies.clear();
		movesOnTurn = turnComponent.getMovesOnTurn();
		enemyTurnType = ETurnType.getInverseType(movesOnTurn);
		entities = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, TurnComponent.class)).getEntities();
		tmpTurnType = null;
		for (int i = 0; i < entities.size(); i++) {
			otherId = entities.get(i);
			if (otherId != entityId) {
				position = ComponentMappers.getInstance().position.getSafe(otherId);
				turn = ComponentMappers.getInstance().turn.getSafe(otherId);
				tmpTurnType = turn.getMovesOnTurn();
				if (tmpTurnType == movesOnTurn) {
					allies.add(Coord.get(position.getX(), position.getY()));
					allies.add(Coord.get(position.getTargetX(), position.getTargetY()));
				} else if (tmpTurnType == enemyTurnType) {
					enemies.add(Coord.get(position.getX(), position.getY()));
				}
			}
		}

		if (enemies.size() == 0) {
			return Status.SUCCEEDED;
		}

		dijkstraMap = MapManager.getInstance().getMap().getDijkstraMap();
		fleePath = dijkstraMap.findFleePath(5, 1.2, null, allies,
				Coord.get(ownPosition.getX(), ownPosition.getY()), enemies.toArray(new Coord[] {}));
		if (fleePath.size() == 0) {
			return Status.FAILED;
		}
		turnComponent.setCurrentTurn(
				Move.from(Direction.toGoTo(PositionUtil.convertToSquidCoord(ownPosition), fleePath.get(0))));

		return Status.RUNNING;
	}

}
