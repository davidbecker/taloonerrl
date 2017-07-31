/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.math.ArrayHelper;
import lombok.Getter;
import lombok.Setter;
import squidpony.squidai.DijkstraMap;

public class Map implements IMap {

	@Getter
	private final char[][] map;
	@Getter
	private final double[][] fovResistance;
	/** cost map for pathfinding */
	@Getter
	private final double[][] costMap;
	@Getter
	private final boolean[][] visited;
	@Getter
	private final int tilesHorizontal;
	@Getter
	private final int tilesVertical;
	@Getter
	@Setter
	private EDungeonSprites[][] dungeonSprites;
	@Getter
	private final DijkstraMap dijkstraMap;

	public Map(final int _tilesHorizontal, final int _tilesVertical) {
		tilesHorizontal = _tilesHorizontal;
		tilesVertical = _tilesVertical;
		map = new char[tilesHorizontal][tilesVertical];
		fovResistance = new double[tilesHorizontal][tilesVertical];
		costMap = new double[tilesHorizontal][tilesVertical];
		visited = new boolean[tilesHorizontal][tilesVertical];
		dungeonSprites = new EDungeonSprites[tilesHorizontal][tilesVertical];
		dijkstraMap = new DijkstraMap();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible(final int x, final int y) {
		if (!isInMapBounds(x, y)) {
			return false;
		}
		// FIXME
		throw new UnsupportedOperationException("TODO: implement");
	}

	@Override
	public boolean isVisited(final int x, final int y) {
		return ArrayHelper.isInArrayBounds(visited, x, y) && visited[x][y];
	}

	/** {@inheritDoc} */
	@Override
	public boolean isWalkable(final int x, final int y) {
		return isInMapBounds(x, y) && map[x][y] != '#';
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInMapBounds(final int x, final int y) {
		return ArrayHelper.isInArrayBounds(map, x, y);
	}

}
