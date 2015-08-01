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
package de.brainstormsoftworks.taloonerrl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;

public class TestMapFactory {

	private static int tilesHorizontal = 40;
	private static int tilesVertical = 60;

	private static IMap map = null;

	@Before
	public void setUp() {
		map = MapFactory.createMap(tilesHorizontal, tilesVertical);
	}

	@Test
	public void testVisited() {
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
