package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Entity;
import com.artemis.World;

import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.dungeon.ESprite;

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
		case BLOB:
			return createBlob(world);
		case SQUIRREL:
			return createSquirrel(world);
		default:
			return null;
		}
	}

	private static Entity createPlayer(final World world) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().actor);
		return newEntity;
	}

	private static Entity createBlob(final World world) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().actor);
		newEntity.getComponent(SpriteComponent.class).setSprite(ESprite.BLOB);
		return newEntity;
	}

	private static Entity createSquirrel(final World world) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().actor);
		newEntity.getComponent(SpriteComponent.class).setSprite(ESprite.SQUIRREL);
		return newEntity;
	}
}
