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
package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.Generator;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

/**
 * this is a very basic level generator that just carves a large room into the
 * map
 *
 * @author david
 *
 */
public final class GeneratorCarveBigRoom extends Generator {

	private static final Generator instance = new GeneratorCarveBigRoom();

	/**
	 * constructor.<br/>
	 * private on purpose, use {@link #getInstance()} instead
	 */
	private GeneratorCarveBigRoom() {
	}

	@Override
	public void generate(final ITile[][] map, final int _tilesHorizontal, final int _tilesVertical) {
		for (int x = 1; x < _tilesHorizontal - 1; x++) {
			for (int y = 1; y < _tilesVertical - 1; y++) {
				map[x][y].setWalkable(true);
			}
		}
	}

	/**
	 * gets an instance of this {@link Generator}
	 *
	 * @return instance
	 */
	public static Generator getInstance() {
		return instance;
	}

}
