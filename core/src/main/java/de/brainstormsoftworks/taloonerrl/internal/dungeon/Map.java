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
package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public class Map implements IMap {

	private ITile[][] map;
	private final int tilesHorizontal;
	private final int tilesVertical;

	public Map(final int _tilesHorizontal, final int _tilesVertical) {
		tilesHorizontal = _tilesHorizontal;
		tilesVertical = _tilesVertical;
		map = new ITile[tilesHorizontal][tilesVertical];
	}

	@Override
	public boolean isVisible(final int x, final int y) {
		if (!isInMapBounds(x, y)) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWalkable(final int x, final int y) {
		if (!isInMapBounds(x, y)) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isInMapBounds(final int x, final int y) {
		if (x < 0 || x >= tilesHorizontal || y < 0 || y >= tilesVertical) {
			return false;
		}
		return true;
	}

	@Override
	public ITile[][] getMap() {
		return map;
	}

	public void setMap(final ITile[][] newMap) {
		map = newMap;
	}

}
