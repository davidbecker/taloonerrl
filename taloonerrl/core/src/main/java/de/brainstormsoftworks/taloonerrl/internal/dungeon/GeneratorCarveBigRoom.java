package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.IGenerator;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

/**
 * this is a very basic level generator that just carves a large room into the
 * map
 * 
 * @author david
 *
 */
public class GeneratorCarveBigRoom implements IGenerator {

	@Override
	public void generate(ITile[][] map, int _tilesHorizontal, int _tilesVertical) {
		for (int x = 1; x < _tilesHorizontal - 1; x++) {
			for (int y = 1; y < _tilesVertical - 1; y++) {
				map[x][y].setWalkable(true);
			}
		}

	}

}
