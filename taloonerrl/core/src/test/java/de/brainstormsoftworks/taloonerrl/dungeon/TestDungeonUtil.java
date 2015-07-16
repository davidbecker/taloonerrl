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
		ITile[][] map = new ITile[5][5];
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

		EDungeonSprites[][] sprites = DungeonUtil.calculateDungeonSprites(map,
				5, 5);
		assertNotNull("expected calculated sprites but got nothing", sprites);

		// corners
		assertEquals("corner wall piece",
				EDungeonSprites.WALL_BOTTOMLEFT_CORNER, sprites[0][0]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_TOPLEFT_CORNER,
				sprites[0][4]);
		assertEquals("corner wall piece",
				EDungeonSprites.WALL_BOTTOMRIGHT_CORNER, sprites[4][0]);
		assertEquals("corner wall piece", EDungeonSprites.WALL_TOPRIGHT_CORNER,
				sprites[4][4]);

		// outer walls
		for (int i = 1; i < 4; i++) {
			assertEquals("horizontal wall piece",
					EDungeonSprites.WALL_HORIZONTAL, sprites[0][i]);
			assertEquals("horizontal wall piece",
					EDungeonSprites.WALL_HORIZONTAL, sprites[4][i]);
			assertEquals("vertical wall piece", EDungeonSprites.WALL_VERTICAL,
					sprites[i][0]);
			assertEquals("vertical wall piece", EDungeonSprites.WALL_VERTICAL,
					sprites[i][4]);
		}
	}
}
