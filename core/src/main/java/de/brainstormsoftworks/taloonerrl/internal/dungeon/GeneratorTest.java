/*******************************************************************************
 * Copyright (c) 2017 David Becker.
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
import lombok.Getter;

/**
 * test generator to generate a map from a unit test to walk about
 *
 * @author David Becker
 *
 */
public class GeneratorTest extends Generator {

	@Getter
	private static GeneratorTest instance = new GeneratorTest();

	@Override
	public void generate(final char[][] map, final int _tilesHorizontal, final int _tilesVertical) {
		for (int x = 0; x < _tilesHorizontal; x++) {
			for (int y = 0; y < _tilesVertical; y++) {
				map[x][y] = '.';
			}
		}
		for (int xy = 1; xy < 6; xy++) {
			map[xy][3] = '#';
			map[3][xy] = '#';
		}
		map[2][2] = '#';
		map[2][4] = '#';
		map[4][2] = '#';
		map[4][4] = '#';
		map[4][4] = '#';

		map[3][3] = '.';

		map[1][1] = '#';
		map[5][1] = '#';
		map[1][5] = '#';
		map[5][5] = '#';

	}

}
