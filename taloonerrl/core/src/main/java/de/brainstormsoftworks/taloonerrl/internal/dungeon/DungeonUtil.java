package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public final class DungeonUtil {

	/**
	 * calculates the {@link EDungeonSprites} to use on the given map
	 * 
	 * @param _map
	 *            the map to calculate the sprites for
	 * @param _tilesHorizontal
	 *            how many tiles are horizontally on the map
	 * @param _tilesVertical
	 *            how many tiles are vertically on the map
	 * @return array of sprites to display for the given map. has the same
	 *         dimension as _map
	 */
	public static EDungeonSprites[][] calculateDungeonSprites(ITile[][] _map,
			int _tilesHorizontal, int _tilesVertical) {
		final EDungeonSprites[][] sprites = new EDungeonSprites[_tilesHorizontal][_tilesVertical];
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				final boolean selfWalkable = isWalkable(x, y, _map,
						_tilesHorizontal, _tilesVertical);
				final boolean northWalkable = isWalkable(x, y + 1, _map,
						_tilesHorizontal, _tilesVertical);
				final boolean southWalkable = isWalkable(x, y - 1, _map,
						_tilesHorizontal, _tilesVertical);
				final boolean westWalkable = isWalkable(x - 1, y, _map,
						_tilesHorizontal, _tilesVertical);
				final boolean eastWalkable = isWalkable(x + 1, y, _map,
						_tilesHorizontal, _tilesVertical);
				final boolean northWestWalkable = isWalkable(x - 1, y + 1,
						_map, _tilesHorizontal, _tilesVertical);
				final boolean northEastWalkable = isWalkable(x + 1, y + 1,
						_map, _tilesHorizontal, _tilesVertical);
				final boolean southWestWalkable = isWalkable(x - 1, y - 1,
						_map, _tilesHorizontal, _tilesVertical);
				final boolean southEastWalkable = isWalkable(x + 1, y - 1,
						_map, _tilesHorizontal, _tilesVertical);

				if (selfWalkable) {
					// floor
					if (northWalkable && southWalkable && westWalkable
							&& eastWalkable) {
						// center piece
						sprites[x][y] = EDungeonSprites.FLOOR_ROOM_CENTER;
					} else if (eastWalkable && westWalkable && northWalkable
							&& !southWalkable) {
						sprites[x][y] = EDungeonSprites.FLOOR_ROOM_BOTTOM;
					} else if (eastWalkable && westWalkable && !northWalkable
							&& southWalkable) {
						sprites[x][y] = EDungeonSprites.FLOOR_ROOM_TOP;
					} else if (!eastWalkable) {
						if (northWalkable) {
							if (southWalkable) {
								sprites[x][y] = EDungeonSprites.FLOOR_ROOM_RIGHT;
							} else {
								sprites[x][y] = EDungeonSprites.FLOOR_ROOM_BOTTOMRIGHT_CORNER;
							}
						} else {
							sprites[x][y] = EDungeonSprites.FLOOR_ROOM_TOPRIGHT_CORNER;
						}
					} else if (eastWalkable) {
						if (northWalkable) {
							if (southWalkable) {
								sprites[x][y] = EDungeonSprites.FLOOR_ROOM_LEFT;
							} else {
								sprites[x][y] = EDungeonSprites.FLOOR_ROOM_BOTTOMLEFT_CORNER;
							}
						} else {
							sprites[x][y] = EDungeonSprites.FLOOR_ROOM_TOPLEFT_CORNER;
						}
					}
				} else {
					// wall
					if (!northWalkable && !eastWalkable && northEastWalkable) {
						// FIXME there are other possibilities
						// bottom corner
						sprites[x][y] = EDungeonSprites.WALL_BOTTOMLEFT_CORNER;
					} else if (!northWalkable && !westWalkable
							&& northWestWalkable) {
						// FIXME there are other possibilities
						// top corner
						sprites[x][y] = EDungeonSprites.WALL_BOTTOMRIGHT_CORNER;
					} else if (!southWalkable && !eastWalkable
							&& southEastWalkable) {
						// FIXME there are other possibilities
						// top corner
						sprites[x][y] = EDungeonSprites.WALL_TOPLEFT_CORNER;
					} else if (!southWalkable && !westWalkable
							&& southWestWalkable) {
						// FIXME there are other possibilities
						// bottom corner
						sprites[x][y] = EDungeonSprites.WALL_TOPRIGHT_CORNER;
					} else if (!westWalkable && !eastWalkable
							&& (northWalkable || southWalkable)) {
						// horizontal piece
						sprites[x][y] = EDungeonSprites.WALL_HORIZONTAL;
					} else if (!southWalkable || !northEastWalkable) {
						// vertical piece
						sprites[x][y] = EDungeonSprites.WALL_VERTICAL;
					}
				}
				if (null == sprites[x][y]) {
					// this should never happen when finished
					sprites[x][y] = EDungeonSprites.NOTHING;
				}
				// TODO is it better to set sprites information here or in
				// MapFactory?
				_map[x][y].setDungeonSprite(sprites[x][y]);
			}
		}
		return sprites;
	}

	private static boolean isWalkable(int x, int y, ITile[][] _map,
			int _tilesHorizontal, int _tilesVertical) {
		return isInBounds(x, y, _tilesHorizontal, _tilesVertical)
				&& _map[x][y].isWalkable();
	}

	private static boolean isInBounds(int x, int y, int _tilesHorizontal,
			int _tilesVertical) {
		return x >= 0 && x < _tilesHorizontal && y >= 0 && y < _tilesVertical;
	}
}
