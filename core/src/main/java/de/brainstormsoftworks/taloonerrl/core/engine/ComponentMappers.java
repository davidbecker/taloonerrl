package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;

/**
 * singleton to get the {@link Component}s for our {@link World}
 *
 * @author David Becker
 *
 */
public final class ComponentMappers {
	public final ComponentMapper<ControllerComponent> controller;
	public final ComponentMapper<HealthComponent> health;
	public final ComponentMapper<PositionComponent> position;
	public final ComponentMapper<SpriteComponent> sprite;

	private static ComponentMappers instance = null;

	private ComponentMappers(final World world) {
		controller = ComponentMapper.getFor(ControllerComponent.class, world);
		health = ComponentMapper.getFor(HealthComponent.class, world);
		position = ComponentMapper.getFor(PositionComponent.class, world);
		sprite = ComponentMapper.getFor(SpriteComponent.class, world);
	}

	/**
	 * initializes the singleton
	 *
	 * @param world
	 *            for the {@link ComponentMapper}
	 */
	public static void mapComponents(final World world) {
		instance = new ComponentMappers(world);
	}

	/**
	 * way to access the singleton instance of this mapper
	 *
	 * @return {@link #instance}
	 * @throws IllegalStateException
	 *             when {@link #mapComponents(World)} wasn't called before
	 */
	public static ComponentMappers getInstance() {
		if (instance == null) {
			throw new IllegalStateException("not instantiated");
		}
		return instance;
	}
}
