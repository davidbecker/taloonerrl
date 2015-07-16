package de.brainstormsoftworks.taloonerrl.dungeon;

/**
 * generic interface for an dungeon generator
 * 
 * @author david
 *
 */
public interface IGenerator {

	/**
	 * generates the level layout in the given map
	 * 
	 * @param map
	 *            map to operate the generation on
	 * @param _tilesHorizontal
	 *            number of horizontal tiles in the map
	 * @param _tilesVertical
	 *            number of vertical tiles in the map
	 */
	void generate(ITile[][] map, int _tilesHorizontal, int _tilesVertical);
}
