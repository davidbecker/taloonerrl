package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Entity;
import com.artemis.World;

import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;

/**
 * factory to create entities in to the game world
 *
 * @author David Becker
 *
 */
public final class EntityFactory {
	public static Entity createEntity(final EActorTypes type, final World world) {
		switch (type) {
		case PLAYER:
			return createPlayer(world);
		default:
			return null;
		}
	}

	private static Entity createPlayer(final World world) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().playerArchetype);
		return newEntity;
	}
}
