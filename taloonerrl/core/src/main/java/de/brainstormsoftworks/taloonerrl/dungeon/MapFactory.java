package de.brainstormsoftworks.taloonerrl.dungeon;

import de.brainstormsoftworks.taloonerrl.internal.dungeon.DungeonUtil;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.GeneratorCarveBigRoom;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Map;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Tile;

public final class MapFactory {

	private MapFactory() {
	}

	public static IMap createMap(final int _tilesHorizontal, final int _tilesVertical) {
		final IMap map = new Map(_tilesHorizontal, _tilesVertical);
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				// ITile newTile = new Tile(getFloor(rnd.nextInt(9)));
				// map.getMap()[x][y] = newTile;
				map.getMap()[x][y] = new Tile();
			}
		}
		// generate the dungeon
		GeneratorCarveBigRoom.getInstance().generate(map.getMap(), _tilesHorizontal, _tilesVertical);
		// calculate which sprite to use on each tile
		DungeonUtil.calculateDungeonSprites(map.getMap(), _tilesHorizontal, _tilesVertical);
		return map;
	}
}
