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

import de.brainstormsoftworks.taloonerrl.internal.dungeon.DungeonUtil;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Map;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.SquidGenerator;

/**
 * factory for an {@link IMap}
 *
 * @author David Becker
 *
 */
public final class MapFactory {

	private MapFactory() {
	}

	/**
	 * creates a new map
	 *
	 * @param _tilesHorizontal
	 *            amount of horizontal tiles
	 * @param _tilesVertical
	 *            amount of vertical tiles
	 * @return map
	 */
	public static synchronized IMap createMap(final int _tilesHorizontal, final int _tilesVertical) {
		final Map map = new Map(_tilesHorizontal, _tilesVertical);
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				map.getMap()[x][y] = '#';
			}
		}

		// generate the dungeon
		// GeneratorCarveBigRoom.getInstance().generate(map.getMap(),
		// _tilesHorizontal, _tilesVertical);
		SquidGenerator.getInstance().generate(map.getMap(), _tilesHorizontal, _tilesVertical);

		// sets the resistance for FOV
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				map.getFovResistance()[x][y] = map.getMap()[x][y] == '#' ? 1.0 : 0.00;
			}
		}

		// calculate which sprite to use on each tile
		map.setDungeonSprites(
				DungeonUtil.calculateDungeonSprites(map.getMap(), _tilesHorizontal, _tilesVertical));

		return map;
	}
}
