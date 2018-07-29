/*******************************************************************************
 * Copyright (c) 2015-2018 David Becker.
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

import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import lombok.Getter;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

/**
 * this class creates entities for a given {@link IMap}
 *
 * @author David Becker
 *
 */
public final class MapManager implements IMapChangeListener {
	private static final RNG rng = new RNG();
	private static final int MAX_RNG_TRIES = 100;
	private static int playerStartX = -1;
	private static int playerStartY = -1;

	private static final MapManager instance = new MapManager();
	@Getter
	private IMap map;

	private MapManager() {
		MapChangeProvider.getInstance().registerListener(this);
	}

	/**
	 * getter for {@link #instance}
	 *
	 * @return the instance
	 */
	public static MapManager getInstance() {
		return instance;
	}

	/**
	 * creates entities for the given map
	 *
	 * @param _map
	 *            to check against
	 * @param tilesHorizontal
	 *            amount of horizontal tiles
	 * @param tilesVertical
	 *            amount of vertical tiles
	 */
	private static void createEntities(final IMap map, final int tilesHorizontal, final int tilesVertical) {
		// set player starting position to a random walkable position for now
		playerStartX = -1;
		playerStartY = -1;
		while (!map.isWalkable(playerStartX, playerStartY)) {
			playerStartX = rng.nextInt(tilesHorizontal);
			playerStartY = rng.nextInt(tilesVertical);
		}

		// forces the engine to initialize
		final GameEngine gameEngine = GameEngine.getInstance();
		final Entity playerEntity = gameEngine.createNewEntity(EEntity.PLAYER);
		final PositionComponent playerPositionComponent = playerEntity.getComponent(PositionComponent.class);
		playerPositionComponent.setX(playerStartX);
		playerPositionComponent.setY(playerStartY);

		// create of on each monster at a random position
		// more bats to work with...
		EEntity type;
		Coord position;
		for (int i = 0; i < 20; i++) {
			position = getRandomFreePosition(map);
			if (position != null) {
				switch (i) {
				case 0:
					type = EEntity.BLOB;
					break;
				case 1:
					type = EEntity.SQUIRREL;
					break;
				case 2:
					type = EEntity.BAT;
					break;
				case 3:
					type = EEntity.MAGICIAN;
					break;
				case 4:
					type = EEntity.SLUG;
					break;
				case 5:
					type = EEntity.GHOST;
					break;
				case 6:
					type = EEntity.SHADOW;
					break;
				case 7:
					type = EEntity.EYEBALL;
					break;
				case 8:
					type = EEntity.GOLEM;
					break;
				case 9:
					type = EEntity.ARCHER;
					break;
				case 10:
					type = EEntity.BOMB;
					break;
				case 11:
					type = EEntity.WARPER;
					break;
				case 12:
					type = EEntity.STEALER;
					break;
				case 13:
					type = EEntity.DRAGON;
					break;
				case 14:
					type = EEntity.ACID;
					break;
				case 15:
					type = EEntity.MUSHROOM;
					break;
				default:
					type = EEntity.BAT;
					break;
				}
				GameEngine.getInstance().createNewEntity(type, position);
			}
		}

		for (int i = 0; i < 9; i++) {
			position = getRandomFreePosition(map);
			type = null;
			if (position != null) {
				switch (i) {
				case 0:
					type = EEntity.POTION_A;
					break;
				case 1:
					type = EEntity.POTION_B;
					break;
				case 2:
					type = EEntity.POTION_C;
					break;
				case 3:
					type = EEntity.POTION_D;
					break;
				case 4:
					type = EEntity.POTION_E;
					break;
				case 5:
					type = EEntity.POTION_F;
					break;
				case 6:
					type = EEntity.POTION_G;
					break;
				case 7:
					type = EEntity.POTION_H;
					break;
				case 8:
					type = EEntity.POTION_I;
					break;
				case 9:
					type = EEntity.POTION_J;
					break;
				}
				GameEngine.getInstance().createNewEntity(type, position);
			}
		}

		for (int i = 0; i < 9; i++) {
			position = getRandomFreePosition(map);
			type = null;
			if (position != null) {
				switch (i) {
				case 0:
					type = EEntity.SCROLL_A;
					break;
				case 1:
					type = EEntity.SCROLL_B;
					break;
				case 2:
					type = EEntity.SCROLL_C;
					break;
				case 3:
					type = EEntity.SCROLL_D;
					break;
				case 4:
					type = EEntity.SCROLL_E;
					break;
				case 5:
					type = EEntity.SCROLL_F;
					break;
				case 6:
					type = EEntity.SCROLL_G;
					break;
				case 7:
					type = EEntity.SCROLL_H;
					break;
				case 8:
					type = EEntity.SCROLL_I;
					break;
				case 9:
					type = EEntity.SCROLL_J;
					break;
				}
				GameEngine.getInstance().createNewEntity(type, position);
			}
		}

		for (int i = 0; i < 9; i++) {
			position = getRandomFreePosition(map);
			type = null;
			if (position != null) {
				switch (i) {
				case 0:
					type = EEntity.WAND_A;
					break;
				case 1:
					type = EEntity.WAND_B;
					break;
				case 2:
					type = EEntity.WAND_C;
					break;
				case 3:
					type = EEntity.WAND_D;
					break;
				case 4:
					type = EEntity.WAND_E;
					break;
				case 5:
					type = EEntity.WAND_F;
					break;
				case 6:
					type = EEntity.WAND_G;
					break;
				case 7:
					type = EEntity.WAND_H;
					break;
				case 8:
					type = EEntity.WAND_I;
					break;
				case 9:
					type = EEntity.WAND_J;
					break;
				}
				GameEngine.getInstance().createNewEntity(type, position);
			}
		}

		for (int i = 0; i < 9; i++) {
			position = getRandomFreePosition(map);
			type = null;
			if (position != null) {
				switch (i) {
				case 0:
					type = EEntity.RING_A;
					break;
				case 1:
					type = EEntity.RING_B;
					break;
				case 2:
					type = EEntity.RING_C;
					break;
				case 3:
					type = EEntity.RING_D;
					break;
				case 4:
					type = EEntity.RING_E;
					break;
				case 5:
					type = EEntity.RING_F;
					break;
				case 6:
					type = EEntity.RING_G;
					break;
				case 7:
					type = EEntity.RING_H;
					break;
				case 8:
					type = EEntity.RING_I;
					break;
				case 9:
					type = EEntity.RING_J;
					break;
				}
				GameEngine.getInstance().createNewEntity(type, position);
			}
		}

		// create an entity for tile highlighting
		final Entity cursorEntity = GameEngine.getInstance().createNewEntity(EEntity.CURSOR, 0, 0);
		final HighlightAbleComponent highlightComponent = cursorEntity
				.getComponent(HighlightAbleComponent.class);
		highlightComponent.setHighlightStyle(HighlightAbleComponent.HIGHLIGHT_STYLE_BLINKING);
		highlightComponent.setHighlightActive(true);
		highlightComponent.setHighlightToggleAble(false);
		highlightComponent.setHighlightOutsideFOV(true);
	}

	/**
	 * randomly selects an unoccupied position
	 *
	 * @return null when no position could be found
	 */
	private static Coord getRandomFreePosition(final IMap map) {
		int tries = 0;
		final int tilesHorizontal = map.getTilesHorizontal();
		final int tilesVertical = map.getTilesVertical();
		Coord result = null;
		boolean validPositionFound = false;
		while (tries < MAX_RNG_TRIES && !validPositionFound) {
			result = Coord.get(rng.nextInt(tilesHorizontal), rng.nextInt(tilesVertical));
			validPositionFound = map.isWalkable(result.x, result.y)
					&& !PositionUtil.isPositionTaken(result.x, result.y);
			tries += 1;
		}
		return validPositionFound ? result : null;
	}

	/** {@inheritDoc} */
	@Override
	public void setMap(final IMap _map) {
		map = _map;
		final int width = _map.getMap().length;
		final int height = width > 0 ? _map.getMap()[0].length : 0;
		createEntities(_map, width, height);
	}

}
