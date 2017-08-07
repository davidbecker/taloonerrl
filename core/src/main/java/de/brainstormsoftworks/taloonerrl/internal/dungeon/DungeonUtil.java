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

import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import squidpony.squidmath.Coord;

/**
 * Utility class to collect methods that help with the dungeon
 *
 * @author David Becker
 *
 */
public final class DungeonUtil {

	private DungeonUtil() {
	}

	/**
	 * calculates the {@link EDungeonSprites} to use on the given map
	 *
	 * @param _map
	 *            the map to calculate the sprites for
	 * @param _tilesHorizontal
	 *            how many tiles are horizontally on the map
	 * @param _tilesVertical
	 *            how many tiles are vertically on the map
	 * @return array of sprites to display for the given map. has the same dimension
	 *         as _map
	 */
	public static EDungeonSprites[][] calculateDungeonSprites(final char[][] _map, final int _tilesHorizontal,
			final int _tilesVertical) {
		final EDungeonSprites[][] sprites = new EDungeonSprites[_tilesHorizontal][_tilesVertical];
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				final boolean selfWalkable = isWalkable(x, y, _map, _tilesHorizontal, _tilesVertical);
				final boolean northWalkable = isWalkable(x, y + 1, _map, _tilesHorizontal, _tilesVertical);
				final boolean southWalkable = isWalkable(x, y - 1, _map, _tilesHorizontal, _tilesVertical);
				final boolean westWalkable = isWalkable(x - 1, y, _map, _tilesHorizontal, _tilesVertical);
				final boolean eastWalkable = isWalkable(x + 1, y, _map, _tilesHorizontal, _tilesVertical);
				final boolean northWestWalkable = isWalkable(x - 1, y + 1, _map, _tilesHorizontal,
						_tilesVertical);
				final boolean northEastWalkable = isWalkable(x + 1, y + 1, _map, _tilesHorizontal,
						_tilesVertical);
				final boolean southWestWalkable = isWalkable(x - 1, y - 1, _map, _tilesHorizontal,
						_tilesVertical);
				final boolean southEastWalkable = isWalkable(x + 1, y - 1, _map, _tilesHorizontal,
						_tilesVertical);
				if (selfWalkable) {
					// floor
					if (northWalkable && southWalkable && westWalkable && eastWalkable) {
						if (northWestWalkable && northEastWalkable && southWestWalkable
								&& southEastWalkable) {
							sprites[x][y] = EDungeonSprites.FLOOR_CENTER;
						} else if (!northWestWalkable) {
							sprites[x][y] = EDungeonSprites.FLOOR_BOTTOMRIGHT_CORNER_OUTSIDE;
						} else if (!northEastWalkable) {
							sprites[x][y] = EDungeonSprites.FLOOR_BOTTOMLEFT_CORNER_OUTSIDE;
						} else if (!southWestWalkable) {
							sprites[x][y] = EDungeonSprites.FLOOR_TOPRIGHT_CORNER_OUTSIDE;
						} else if (!southEastWalkable) {
							sprites[x][y] = EDungeonSprites.FLOOR_TOPLEFT_CORNER_OUTSIDE;
						}
					} else if (eastWalkable && westWalkable && northWalkable && !southWalkable) {
						sprites[x][y] = EDungeonSprites.FLOOR_BOTTOM;
					} else if (eastWalkable && westWalkable && !northWalkable && southWalkable) {
						sprites[x][y] = EDungeonSprites.FLOOR_TOP;
					} else if (!eastWalkable) {
						if (northWalkable) {
							if (southWalkable) {
								sprites[x][y] = EDungeonSprites.FLOOR_RIGHT;
							} else {
								sprites[x][y] = EDungeonSprites.FLOOR_BOTTOMRIGHT_CORNER;
							}
						} else {
							sprites[x][y] = EDungeonSprites.FLOOR_TOPRIGHT_CORNER;
						}
					} else if (eastWalkable) {
						if (northWalkable) {
							if (southWalkable) {
								sprites[x][y] = EDungeonSprites.FLOOR_LEFT;
							} else {
								sprites[x][y] = EDungeonSprites.FLOOR_BOTTOMLEFT_CORNER;
							}
						} else {
							sprites[x][y] = EDungeonSprites.FLOOR_TOPLEFT_CORNER;
						}
					}
				} else {
					// wall
					if (!northWalkable && !eastWalkable && !southWalkable && !westWalkable
							&& !northEastWalkable && !northWestWalkable && !southWestWalkable
							&& !southEastWalkable) {
						// trapped piece -> set to nothing for the time being
						sprites[x][y] = EDungeonSprites.NOTHING;
					} else if (northWalkable && southWalkable && eastWalkable && westWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_PILLAR;
					} else if (!northWalkable && !eastWalkable && !southWalkable && !westWalkable
							&& (northEastWalkable && southWestWalkable
									|| northWestWalkable && southEastWalkable)) {
						sprites[x][y] = EDungeonSprites.WALL_CROSSSECTION;
					} else if (!westWalkable && !eastWalkable && !southWalkable
							&& (northWalkable && (southEastWalkable || southWestWalkable)
									|| southEastWalkable && southWestWalkable)) {
						sprites[x][y] = EDungeonSprites.WALL_TSECTION_NORTH;
					} else if (!westWalkable && !eastWalkable && !northWalkable
							&& (southWalkable && (northEastWalkable || northWestWalkable)
									|| northEastWalkable && northWestWalkable)) {
						sprites[x][y] = EDungeonSprites.WALL_TSECTION_SOUTH;
					} else if (!westWalkable && !southWalkable && !northWalkable
							&& (eastWalkable && (southWestWalkable || northWestWalkable)
									|| southWestWalkable && northWestWalkable)) {
						sprites[x][y] = EDungeonSprites.WALL_TSECTION_EAST;
					} else if (!eastWalkable && !southWalkable && !northWalkable
							&& (westWalkable && (southEastWalkable || northEastWalkable)
									|| southEastWalkable && northEastWalkable)) {
						sprites[x][y] = EDungeonSprites.WALL_TSECTION_WEST;
					} else if (!northWalkable && !eastWalkable && southWalkable && southWestWalkable
							&& westWalkable
							|| !northWalkable && northEastWalkable && !eastWalkable && !southEastWalkable
									&& !southWalkable && !southWestWalkable && !westWalkable
									&& !northWestWalkable
							|| !northWalkable && westWalkable && southWalkable && !eastWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_BOTTOMLEFT_CORNER;
					} else if (!northWalkable && !westWalkable && eastWalkable && southEastWalkable
							&& southWalkable
							|| northWestWalkable && !northWalkable && !northEastWalkable && !eastWalkable
									&& !southEastWalkable && !southWalkable && !southWestWalkable
									&& !westWalkable
							|| !northWalkable && eastWalkable && southWalkable && !westWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_BOTTOMRIGHT_CORNER;
					} else if (!southWalkable && !eastWalkable && westWalkable && northWestWalkable
							&& northWalkable
							|| southEastWalkable && !southWalkable && !southWestWalkable && !westWalkable
									&& !northWestWalkable && !northWalkable && !northEastWalkable
									&& !eastWalkable
							|| !southWalkable && !eastWalkable && westWalkable && northWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_TOPLEFT_CORNER;
					} else if (!southWalkable && !westWalkable && northWalkable && northEastWalkable
							&& eastWalkable
							|| southWestWalkable && !westWalkable && !northWestWalkable && !northWalkable
									&& !northEastWalkable && !eastWalkable && !southEastWalkable
									&& !southWalkable
							|| northWalkable && eastWalkable && !southWalkable && !westWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_TOPRIGHT_CORNER;
					} else if (eastWalkable && !westWalkable && northWalkable && southWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_END_EAST;
					} else if (westWalkable && !eastWalkable && northWalkable && southWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_END_WEST;
					} else if (northWalkable && !southWalkable && eastWalkable && westWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_END_NORT;
					} else if (southWalkable && !northWalkable && eastWalkable && westWalkable) {
						sprites[x][y] = EDungeonSprites.WALL_END_SOUTH;
					} else if (!westWalkable && !eastWalkable && (northWalkable || southWalkable)) {
						// horizontal piece
						sprites[x][y] = EDungeonSprites.WALL_HORIZONTAL;
					} else if (!southWalkable || !northEastWalkable) {
						// vertical piece
						sprites[x][y] = EDungeonSprites.WALL_VERTICAL;
					}
					// TODO add T-parts
					// TODO extends test cases
				}
				if (null == sprites[x][y]) {
					// this should never happen when finished
					sprites[x][y] = EDungeonSprites.NOTHING;
				}
			}
		}
		return sprites;
	}

	/**
	 * calculate the direction from one adjacent {@link Coord} to another
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static int calculateDirection(final Coord from, final Coord to) {
		if (from.x < to.x) {
			return Move.RIGHT;
		}
		if (from.x > to.x) {
			return Move.LEFT;
		}
		if (from.y < to.y) {
			return Move.UP;
		}
		if (from.y > to.y) {
			return Move.DOWN;
		}
		return Move.IDLE;
	}

	private static boolean isWalkable(final int x, final int y, final char[][] _map,
			final int _tilesHorizontal, final int _tilesVertical) {
		return isInBounds(x, y, _tilesHorizontal, _tilesVertical) && _map[x][y] != '#';
	}

	private static boolean isInBounds(final int x, final int y, final int _tilesHorizontal,
			final int _tilesVertical) {
		return x >= 0 && x < _tilesHorizontal && y >= 0 && y < _tilesVertical;
	}
}
