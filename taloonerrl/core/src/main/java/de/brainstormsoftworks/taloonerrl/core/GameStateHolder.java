package de.brainstormsoftworks.taloonerrl.core;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;

/**
 * lazy way to keep track of the global state of the game world
 * 
 * @author david
 *
 */
public final class GameStateHolder {

	public static IMap map;

	private GameStateHolder() {
	}
}
