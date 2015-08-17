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
import squidpony.squidgrid.mapping.DungeonGenerator;

/**
 * Dungeon generator that uses the squidlib generator underneath
 *
 * @author David Becker
 *
 */
public final class SquidGenerator extends Generator {
	private static final SquidGenerator instance = new SquidGenerator();

	/**
	 * constructor.<br/>
	 * private on purpose
	 */
	private SquidGenerator() {
	}

	@Override
	public void generate(final char[][] map, final int _tilesHorizontal, final int _tilesVertical) {
		final DungeonGenerator generator = new DungeonGenerator();
		generator.setDungeon(map);
		final char[][] generate = generator.generate();
		// copy char over
		// refactor Generator to return char[][] instead
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				map[x][y] = generate[x][y];
			}
		}
	}

	/**
	 * getter for an instance of this singleton
	 *
	 * @return
	 */
	public static SquidGenerator getInstance() {
		return instance;
	}

}
