package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.WorldConfiguration;

import de.brainstormsoftworks.taloonerrl.system.MovementSystem;
import de.brainstormsoftworks.taloonerrl.system.AnimationRenderSystem;

/**
 * utility class to add all systems to a given {@link WorldConfiguration}
 *
 * @author David Becker
 *
 */
public final class Systems {
	private static final MovementSystem MOVEMENT_SYSTEM = new MovementSystem();
	private static final AnimationRenderSystem ANIMATION_RENDER_SYSTEM = new AnimationRenderSystem();

	public static void setSystems(final WorldConfiguration config) {
		config.setSystem(MOVEMENT_SYSTEM);
		config.setSystem(ANIMATION_RENDER_SYSTEM);
	}
}
