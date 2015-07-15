package de.brainstormsoftworks.taloonerrl.dungeon;

import java.util.Random;

import de.brainstormsoftworks.taloonerrl.internal.dungeon.Tile;

public final class MapFactory {

	private static Random rnd = new Random();

	private MapFactory() {
	}

	public static ITile[][] createMap(int tilesHorizontal, int tilesVertical) {
		ITile[][] map = new ITile[tilesHorizontal][tilesVertical];
		for (int x = 0; x < tilesHorizontal; x++) {
			for (int y = 0; y < tilesVertical; y++) {
				ITile newTile = new Tile(rnd.nextInt(9));
				map[x][y] = newTile;
			}
		}
		return map;
	}
}
