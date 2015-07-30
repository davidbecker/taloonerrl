package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;

public final class Archetypes {
	public final Archetype playerArchetype;
	private static Archetypes instance;

	private Archetypes(final World world) {
		playerArchetype = new ArchetypeBuilder().add(ControllerComponent.class).add(HealthComponent.class)
				.add(PositionComponent.class).add(SpriteComponent.class).build(world);
	}

	public static void createArchetypes(final World world) {
		instance = new Archetypes(world);
	}

	/**
	 * way to access the singleton instance
	 *
	 * @return {@link #instance}
	 * @throws IllegalStateException
	 *             when {@link #createArchetypes(World)} wasn't called before
	 */
	public static Archetypes getInstance() {
		if (instance == null) {
			throw new IllegalStateException("not instantiated");
		}
		return instance;
	}
}
