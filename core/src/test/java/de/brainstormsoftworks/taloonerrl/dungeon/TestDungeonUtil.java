/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
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

import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.internal.dungeon.DungeonUtil;
import squidpony.squidmath.Coord;

public class TestDungeonUtil {

	@Test
	public void simpleRoom() {
		// make a map with the border filled and rest walkable
		final char[][] map = new char[5][5];
		for (int i = 0; i < 5; i++) {
			map[0][i] = '#';
			map[4][i] = '#';
			map[i][0] = '#';
			map[i][4] = '#';
		}
		for (int i = 1; i < 4; i++) {
			map[1][i] = '.';
			map[2][i] = '.';
			map[3][i] = '.';
		}

		final EDungeonSprites[][] sprites = DungeonUtil.calculateDungeonSprites(map, 5, 5);
		assertNotNull("expected calculated sprites but got nothing", sprites);

		// corners
		assertEquals("corner wall piece", EDungeonSprites.WALL_BOTTOMLEFT_CORNER, sprites[0][0]);
		assertEquals("corner floor piece", EDungeonSprites.FLOOR_BOTTOMLEFT_CORNER, sprites[1][1]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_TOPLEFT_CORNER, sprites[0][4]);
		assertEquals("corner floor piece", EDungeonSprites.FLOOR_TOPLEFT_CORNER, sprites[1][3]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_BOTTOMRIGHT_CORNER, sprites[4][0]);
		assertEquals("corner floor piece", EDungeonSprites.FLOOR_BOTTOMRIGHT_CORNER, sprites[3][1]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_TOPRIGHT_CORNER, sprites[4][4]);
		assertEquals("corner floor piece", EDungeonSprites.FLOOR_TOPRIGHT_CORNER, sprites[3][3]);

		// outer walls
		for (int i = 1; i < 4; i++) {
			assertEquals("horizontal wall piece [" + i + "][0]", EDungeonSprites.WALL_HORIZONTAL,
					sprites[i][0]);
			assertEquals("horizontal wall piece [" + i + "][4]", EDungeonSprites.WALL_HORIZONTAL,
					sprites[i][4]);
			assertEquals("vertical wall piece [0][" + i + "]", EDungeonSprites.WALL_VERTICAL, sprites[0][i]);
			assertEquals("vertical wall piece [4][" + i + "]", EDungeonSprites.WALL_VERTICAL, sprites[4][i]);
		}
	}

	@Test
	public void diamondTest() {
		// .......
		// .#.#.#.
		// ..###..
		// .##.##.
		// ..###..
		// .#.#.#.
		// .......
		final int tilesHorizontal = 8;
		final int tilesVertical = 7;
		final char[][] map = new char[tilesHorizontal][tilesVertical];
		for (int x = 0; x < tilesHorizontal; x++) {
			for (int y = 0; y < tilesVertical; y++) {
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
		map[3][3] = '.';
		map[1][1] = '#';
		map[5][1] = '#';
		map[1][5] = '#';
		map[5][5] = '#';

		final EDungeonSprites[][] sprites = DungeonUtil.calculateDungeonSprites(map, tilesHorizontal,
				tilesVertical);
		assertNotNull("expected calculated sprites but got nothing", sprites);

		assertEquals(EDungeonSprites.WALL_PILLAR, sprites[1][1]);
		assertEquals(EDungeonSprites.WALL_PILLAR, sprites[1][5]);
		assertEquals(EDungeonSprites.WALL_PILLAR, sprites[5][1]);
		assertEquals(EDungeonSprites.WALL_PILLAR, sprites[5][5]);
		assertEquals(EDungeonSprites.WALL_BOTTOMLEFT_CORNER, sprites[2][2]);
		assertEquals(EDungeonSprites.WALL_TOPLEFT_CORNER, sprites[2][4]);
		assertEquals(EDungeonSprites.WALL_BOTTOMRIGHT_CORNER, sprites[4][2]);
		assertEquals(EDungeonSprites.WALL_TOPRIGHT_CORNER, sprites[4][4]);

	}

	@Test
	public void directionFromCoords() {
		final Coord up = Coord.get(1, 2);
		final Coord down = Coord.get(1, 0);
		final Coord left = Coord.get(0, 1);
		final Coord right = Coord.get(2, 1);
		final Coord center = Coord.get(1, 1);
		assertEquals(Move.DOWN, DungeonUtil.calculateDirection(up, center));
		assertEquals(Move.UP, DungeonUtil.calculateDirection(down, center));
		assertEquals(Move.LEFT, DungeonUtil.calculateDirection(right, center));
		assertEquals(Move.RIGHT, DungeonUtil.calculateDirection(left, center));
		assertEquals(Move.IDLE, DungeonUtil.calculateDirection(center, center));
	}
}
