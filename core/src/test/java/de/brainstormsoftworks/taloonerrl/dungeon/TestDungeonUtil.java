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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.brainstormsoftworks.taloonerrl.internal.dungeon.DungeonUtil;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.Tile;

public class TestDungeonUtil {

	@Test
	public void simpleRoom() {
		// make a map with the border filled and rest walkable
		final ITile[][] map = new ITile[5][5];
		for (int i = 0; i < 5; i++) {
			map[0][i] = new Tile(false);
			map[4][i] = new Tile(false);
			map[i][0] = new Tile(false);
			map[i][4] = new Tile(false);
		}
		for (int i = 1; i < 4; i++) {
			map[1][i] = new Tile(true);
			map[2][i] = new Tile(true);
			map[3][i] = new Tile(true);
		}

		final EDungeonSprites[][] sprites = DungeonUtil
				.calculateDungeonSprites(map, 5, 5);
		assertNotNull("expected calculated sprites but got nothing", sprites);

		// corners
		assertEquals("corner wall piece",
				EDungeonSprites.WALL_BOTTOMLEFT_CORNER, sprites[0][0]);
		assertEquals("corner floor piece",
				EDungeonSprites.FLOOR_BOTTOMLEFT_CORNER, sprites[1][1]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_TOPLEFT_CORNER,
				sprites[0][4]);
		assertEquals("corner floor piece",
				EDungeonSprites.FLOOR_TOPLEFT_CORNER, sprites[1][3]);
		assertEquals("corner wall piece",
				EDungeonSprites.WALL_BOTTOMRIGHT_CORNER, sprites[4][0]);
		assertEquals("corner floor piece",
				EDungeonSprites.FLOOR_BOTTOMRIGHT_CORNER, sprites[3][1]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_TOPRIGHT_CORNER,
				sprites[4][4]);
		assertEquals("corner floor piece",
				EDungeonSprites.FLOOR_TOPRIGHT_CORNER, sprites[3][3]);

		// outer walls
		for (int i = 1; i < 4; i++) {
			assertEquals("horizontal wall piece [" + i + "][0]",
					EDungeonSprites.WALL_HORIZONTAL, sprites[i][0]);
			assertEquals("horizontal wall piece [" + i + "][4]",
					EDungeonSprites.WALL_HORIZONTAL, sprites[i][4]);
			assertEquals("vertical wall piece [0][" + i + "]",
					EDungeonSprites.WALL_VERTICAL, sprites[0][i]);
			assertEquals("vertical wall piece [4][" + i + "]",
					EDungeonSprites.WALL_VERTICAL, sprites[4][i]);
		}
	}
}
