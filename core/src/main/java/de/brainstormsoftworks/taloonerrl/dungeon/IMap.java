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
package de.brainstormsoftworks.taloonerrl.dungeon;

import squidpony.squidai.DijkstraMap;
import squidpony.squidgrid.FOV;

/**
 * interface for a map
 *
 * @author David Becker
 *
 */
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
	 * check if a position on the map was visited by the player
	 *
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @return true if position was visited, false otherwise
	 */
	boolean isVisited(int x, int y);

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
	char[][] getMap();

	/**
	 * gets the resistance map for {@link FOV}
	 *
	 * @return array with 1.0 for a wall and 0.0 for any walkable tile
	 */
	double[][] getFovResistance();

	/**
	 * gets the cost map for pathfinding
	 *
	 * @return array with 1.0 for a wall and 0.0 for any walkable tile
	 */
	double[][] getCostMap();

	/**
	 * gets the visited map
	 *
	 * @return array
	 */
	boolean[][] getVisited();

	/**
	 * get the types of tiles on the map
	 *
	 * @return array
	 */
	EDungeonSprites[][] getDungeonSprites();

	/**
	 * get the number of tiles in the map
	 *
	 * @return horizontal dimension of map
	 */
	int getTilesHorizontal();

	/**
	 * get the number of tiles in the map
	 *
	 * @return vertical dimension of map
	 */
	int getTilesVertical();

	/**
	 * helper method to check if given tile coordinates are in the bounds of the map
	 *
	 * @param x
	 *            horizontal coordinate
	 * @param y
	 *            vertical coordinate
	 * @return true if coordinates are in the bounds of the map, false otherwise
	 */
	boolean isInMapBounds(int x, int y);

	DijkstraMap getDijkstraMap();

}
