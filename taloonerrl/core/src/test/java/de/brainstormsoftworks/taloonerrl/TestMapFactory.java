package de.brainstormsoftworks.taloonerrl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;

public class TestMapFactory {

	private static int tilesHorizontal = 40;
	private static int tilesVertical = 60;

	@Test
	public void test() {
		IMap map = MapFactory.createMap(tilesHorizontal, tilesVertical);
		assertNotNull("map expected, but no map created", map);
		assertFalse("unvisited tile expected",
				map.getMap()[tilesHorizontal / 2][tilesVertical / 2]
						.isVisited());
		map.getMap()[tilesHorizontal / 2][tilesVertical / 2].setVisited();
		assertTrue("visited tile expected",
				map.getMap()[tilesHorizontal / 2][tilesVertical / 2]
						.isVisited());
	}

}
