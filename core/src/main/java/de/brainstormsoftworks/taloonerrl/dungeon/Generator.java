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

/**
 * generic superclass for an dungeon generator
 *
 * @author david
 *
 */
public abstract class Generator {

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
	public abstract void generate(final ITile[][] map, final int _tilesHorizontal, final int _tilesVertical);
}
