package de.brainstormsoftworks.taloonerrl.dungeon;

import java.util.Random;

import de.brainstormsoftworks.taloonerrl.internal.dungeon.DungeonUtil;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.GeneratorCarveBigRoom;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Map;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Tile;

public final class MapFactory {

	private static Random rnd = new Random();

	private MapFactory() {
	}

	public static IMap createMap(int _tilesHorizontal, int _tilesVertical) {
		final IMap map = new Map(_tilesHorizontal, _tilesVertical);
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				// ITile newTile = new Tile(getFloor(rnd.nextInt(9)));
				// map.getMap()[x][y] = newTile;
				map.getMap()[x][y] = new Tile();
			}
		}
		// generate the dungeon
		GeneratorCarveBigRoom.getInstance().generate(map.getMap(),
				_tilesHorizontal, _tilesVertical);
		// calculate which sprite to use on each tile
		final EDungeonSprites[][] sprites = DungeonUtil
				.calculateDungeonSprites(map.getMap(), _tilesHorizontal,
						_tilesVertical);
		//
		return map;
	}

	private static EDungeonSprites getFloor(int index) {
		switch (index) {
		case 0:
			return EDungeonSprites.FLOOR_TOPLEFT_CORNER;
		case 1:
			return EDungeonSprites.FLOOR_TOP;
		case 2:
			return EDungeonSprites.FLOOR_TOPRIGHT_CORNER;
		case 3:
			return EDungeonSprites.FLOOR_LEFT;
		case 4:
			return EDungeonSprites.FLOOR_CENTER;
		case 5:
			return EDungeonSprites.FLOOR_RIGHT;
		case 6:
			return EDungeonSprites.FLOOR_BOTTOMLEFT_CORNER;
		case 7:
			return EDungeonSprites.FLOOR_BOTTOM;
		case 8:
			return EDungeonSprites.FLOOR_BOTTOMRIGHT_CORNER;
		default:
			return EDungeonSprites.NOTHING;
		}
	}
}
