package de.brainstormsoftworks.taloonerrl.dungeon;

public interface IMap {

	/**
	 * check if a position on the map is visible (to the player)
	 *
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @return true if position is visible, false otherwise
	 */
	boolean isVisible(int x, int y);

	/**
	 * check if a position on the map is walkable to any actor
	 *
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @return true if position is walkable, false otherwise
	 */
	boolean isWalkable(int x, int y);

	/**
	 * get the representation of the map
	 *
	 * @return array of tiles
	 */
	ITile[][] getMap();

}
