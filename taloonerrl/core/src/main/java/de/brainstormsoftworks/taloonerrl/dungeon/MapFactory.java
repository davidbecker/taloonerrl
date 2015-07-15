package de.brainstormsoftworks.taloonerrl.dungeon;

import java.util.Random;

import de.brainstormsoftworks.taloonerrl.internal.dungeon.Map;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Tile;

public final class MapFactory {

	private static Random rnd = new Random();

	private MapFactory() {
	}

	public static IMap createMap(int tilesHorizontal, int tilesVertical) {
		IMap map = new Map(tilesHorizontal, tilesVertical);
		for (int x = 0; x < tilesHorizontal; x++) {
			for (int y = 0; y < tilesVertical; y++) {
				ITile newTile = new Tile(getFloor(rnd.nextInt(9)));
				map.getMap()[x][y] = newTile;
			}
		}
		return map;
	}

	private static EDungeonSprites getFloor(int index) {
		switch (index) {
		case 0:
			return EDungeonSprites.FLOOR_ROOM_TOPLEFT_CORNER;
		case 1:
			return EDungeonSprites.FLOOR_ROOM_TOP;
		case 2:
			return EDungeonSprites.FLOOR_ROOM_TOPRIGHT_CORNER;
		case 3:
			return EDungeonSprites.FLOOR_ROOM_LEFT;
		case 4:
			return EDungeonSprites.FLOOR_ROOM_CENTER;
		case 5:
			return EDungeonSprites.FLOOR_ROOM_RIGHT;
		case 6:
			return EDungeonSprites.FLOOR_ROOM_BOTTOMLEFT_CORNER;
		case 7:
			return EDungeonSprites.FLOOR_ROOM_BOTTOM;
		case 8:
			return EDungeonSprites.FLOOR_ROOM_BOTTOMRIGHT_CORNER;
		default:
			return EDungeonSprites.NOTHING;
		}
	}
}
