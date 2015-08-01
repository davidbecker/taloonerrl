package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.WorldConfiguration;

import de.brainstormsoftworks.taloonerrl.system.MovementSystem;
import de.brainstormsoftworks.taloonerrl.system.RenderSystem;

/**
 * utility class to add all systems to a given {@link WorldConfiguration}
 *
 * @author David Becker
 *
 */
public final class Systems {
	private static final MovementSystem MOVEMENT_SYSTEM = new MovementSystem();
	private static final RenderSystem RENDER_SYSTEM = new RenderSystem();

	public static void setSystems(final WorldConfiguration config) {
		config.setSystem(MOVEMENT_SYSTEM);
		config.setSystem(RENDER_SYSTEM);
	}
}
