/*******************************************************************************
 * Copyright (c) 2015 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.dungeon;

import com.artemis.Entity;

import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import squidpony.squidmath.RNG;

/**
 * this class creates entities for a given {@link IMap}
 *
 * @author David Becker
 *
 */
public final class MapManager {
	private static final RNG rng = new RNG();
	private static int playerStartX = -1;
	private static int playerStartY = -1;

	// TODO refactor - don't keep references here
	private static PositionComponent playerPositionComponent;
	private static FacingComponent playerFacingComponent;

	private MapManager() {
	}

	/**
	 * creates entities for the given map
	 *
	 * @param _map
	 *            to check against
	 * @param _tilesHorizontal
	 *            amount of horizontal tiles
	 * @param _tilesVertical
	 *            amount of vertical tiles
	 */
	public static void createEntities(final IMap _map, final int _tilesHorizontal, final int _tilesVertical) {
		// set player starting position to a random walkable position for now
		playerStartX = -1;
		playerStartY = -1;
		while (!_map.isWalkable(playerStartX, playerStartY)) {
			playerStartX = rng.nextInt(_tilesHorizontal);
			playerStartY = rng.nextInt(_tilesVertical);
		}

		// forces the engine to initialize
		final GameEngine gameEngine = GameEngine.getInstance();
		final Entity playerEntity = gameEngine.createNewEntity(EEntity.PLAYER);
		playerPositionComponent = playerEntity.getComponent(PositionComponent.class);
		playerFacingComponent = playerEntity.getComponent(FacingComponent.class);
		playerPositionComponent.setX(playerStartX);
		playerPositionComponent.setY(playerStartY);

		// TODO refactor
		// simple method to create 10 monster, it is possible to create monster
		// on top of each other!
		for (int i = 0; i < 10; i++) {
			int monsterX = -1;
			int monsterY = -1;
			while (!_map.isWalkable(monsterX, monsterY)) {
				monsterX = rng.nextInt(_tilesHorizontal);
				monsterY = rng.nextInt(_tilesVertical);
			}
			GameEngine.getInstance().createNewEntity(EEntity.BAT, monsterX, monsterY);
		}

	}

	/**
	 * getter for {@link #playerPositionComponent}
	 *
	 * @return the playerPositionComponent
	 */
	public static PositionComponent getPlayerPositionComponent() {
		return playerPositionComponent;
	}

	/**
	 * getter for {@link #playerFacingComponent}
	 *
	 * @return the playerFacingComponent
	 */
	public static FacingComponent getPlayerFacingComponent() {
		return playerFacingComponent;
	}

}
