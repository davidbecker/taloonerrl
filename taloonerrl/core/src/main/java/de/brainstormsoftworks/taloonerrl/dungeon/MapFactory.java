package de.brainstormsoftworks.taloonerrl.dungeon;

import java.util.Random;

import de.brainstormsoftworks.taloonerrl.internal.dungeon.Map;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Tile;

public final class MapFactory {

	private static Random rnd = new Random();

	private MapFactory() {
	}

	// public static ITile[][] createMap(int tilesHorizontal, int tilesVertical)
	// {
	// ITile[][] map = new ITile[tilesHorizontal][tilesVertical];
	// for (int x = 0; x < tilesHorizontal; x++) {
	// for (int y = 0; y < tilesVertical; y++) {
	// ITile newTile = new Tile(getFloor(rnd.nextInt(9)));
	// map[x][y] = newTile;
	// }
	// }
	// return map;
	// }

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

	private static EFloor getFloor(int index) {
		switch (index) {
		case 0:
			return EFloor.ROOM_TOPLEFT_CORNER;
		case 1:
			return EFloor.ROOM_TOP;
		case 2:
			return EFloor.ROOM_TOPRIGHT_CORNER;
		case 3:
			return EFloor.ROOM_LEFT;
		case 4:
			return EFloor.ROOM_CENTER;
		case 5:
			return EFloor.ROOM_RIGHT;
		case 6:
			return EFloor.ROOM_BOTTOMLEFT_CORNER;
		case 7:
			return EFloor.ROOM_BOTTOM;
		case 8:
			return EFloor.ROOM_BOTTOMRIGHT_CORNER;
		default:
			return EFloor.NOTHING;
		}
	}
}
