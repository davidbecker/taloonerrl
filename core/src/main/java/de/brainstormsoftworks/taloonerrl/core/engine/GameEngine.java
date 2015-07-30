package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;

import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;

public final class GameEngine {
	// maybe initialize on first call via getInstance?
	public static final GameEngine instance = new GameEngine();
	private final World world;

	private GameEngine() {
		final WorldConfiguration config = new WorldConfiguration();
		Systems.setSystems(config);
		world = new World(config);
		ComponentMappers.mapComponents(world);
		Archetypes.createArchetypes(world);
	}

	public void update(final float delta) {
		world.setDelta(delta);
		world.process();
	}

	public Entity createNewEntity(final EActorTypes type) {
		return EntityFactory.createEntity(type, world);
	}
}
