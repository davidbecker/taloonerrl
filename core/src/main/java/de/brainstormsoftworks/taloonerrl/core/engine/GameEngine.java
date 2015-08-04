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
package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;

/**
 * central part of the game<br/>
 * controls & sets up the game world
 *
 * @author David Becker
 *
 */
public final class GameEngine {
	// maybe initialize on first call via getInstance?
	private static final GameEngine instance = new GameEngine();
	private final World world;

	private float stateTime;

	private GameEngine() {
		final WorldConfiguration config = new WorldConfiguration();
		Systems.setSystems(config);
		world = new World(config);
		ComponentMappers.mapComponents(world);
		Archetypes.createArchetypes(world);
	}

	public void update(final float delta) {
		stateTime += delta;
		world.setDelta(delta);
		world.process();
	}

	public float getStateTime() {
		return stateTime;
	}

	public Entity getEntity(final int entityId) {
		return world.getEntity(entityId);
	}

	public Entity createNewEntity(final EEntity type) {
		return createNewEntity(type, 0, 0);
	}

	public Entity createNewEntity(final EEntity type, final int xPosition, final int yPosition) {
		return EntityFactory.createEntity(type, world, xPosition, yPosition);
	}

	/**
	 * getter for instance
	 *
	 * @return the instance
	 */
	public static final GameEngine getInstance() {
		return instance;
	}
}
