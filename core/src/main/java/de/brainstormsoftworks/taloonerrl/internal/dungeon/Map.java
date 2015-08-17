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

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;

public class Map implements IMap {

	private final char[][] map;
	private final int tilesHorizontal;
	private final int tilesVertical;
	private EDungeonSprites[][] dungeonSprites;

	public Map(final int _tilesHorizontal, final int _tilesVertical) {
		tilesHorizontal = _tilesHorizontal;
		tilesVertical = _tilesVertical;
		map = new char[tilesHorizontal][tilesVertical];
		dungeonSprites = new EDungeonSprites[tilesHorizontal][tilesVertical];
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
		return true;
		// if (!isInMapBounds(x, y)) {
		// return false;
		// }
		// // TODO Auto-generated method stub
		// return false;
	}

	@Override
	public boolean isInMapBounds(final int x, final int y) {
		if (x < 0 || x >= tilesHorizontal || y < 0 || y >= tilesVertical) {
			return false;
		}
		return true;
	}

	@Override
	public char[][] getMap() {
		return map;
	}

	@Override
	public int getTilesHorizontal() {
		return tilesHorizontal;
	}

	@Override
	public int getTilesVertical() {
		return tilesVertical;
	}

	/**
	 * getter for {@link #dungeonSprites}
	 *
	 * @return the dungeonSprites
	 */
	@Override
	public final EDungeonSprites[][] getDungeonSprites() {
		return dungeonSprites;
	}

	@Override
	public void setDungeonSprites(final EDungeonSprites[][] sprites) {
		dungeonSprites = sprites;
	}

}
