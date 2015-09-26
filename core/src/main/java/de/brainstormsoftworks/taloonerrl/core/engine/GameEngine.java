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
import com.badlogic.gdx.Gdx;

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

	/**
	 * total time in milliseconds that the application has been run
	 */
	private float stateTime;
	/**
	 * time since last frame
	 */
	private float deltaTime;

	private GameEngine() {
		Gdx.input.setInputProcessor(InputSystem.getInstance());
		final WorldConfiguration config = new WorldConfiguration();
		Systems.setSystems(config);
		world = new World(config);
		ComponentMappers.mapComponents(world);
		Archetypes.createArchetypes(world);
	}

	/**
	 * updates all the systems in the word
	 *
	 * @see World#setDelta(float)
	 */
	public void update() {
		deltaTime = Gdx.graphics.getDeltaTime();
		stateTime += deltaTime;
		world.setDelta(deltaTime);
		world.process();
	}

	/**
	 * gets the time span between the current frame and the last frame in
	 * seconds. Might be smoothed over n frames.
	 *
	 * @return delta time
	 */
	public float getDeltaTime() {
		return deltaTime;
	}

	/**
	 * getter for {@link #stateTime}
	 *
	 * @return the stateTime
	 */
	public float getStateTime() {
		return stateTime;
	}

	/**
	 * retrieves the entity with the given id
	 *
	 * @param entityId
	 *            of the entity
	 * @return entity
	 */
	public Entity getEntity(final int entityId) {
		return world.getEntity(entityId);
	}

	/**
	 * creates a new entity.<br/>
	 * convenience method for {@link #createNewEntity(EEntity, 0, 0)}
	 *
	 * @param type
	 *            of entity to create
	 * @return new entity
	 */
	public Entity createNewEntity(final EEntity type) {
		return createNewEntity(type, 0, 0);
	}

	/**
	 * creates a new entity.
	 *
	 * @param type
	 *            of entity to create
	 * @param xPosition
	 *            horizontal position (tiles position)
	 * @param yPosition
	 *            vertical position (tiles position)
	 * @return new entity
	 */
	public Entity createNewEntity(final EEntity type, final int xPosition, final int yPosition) {
		return EntityFactory.createEntity(type, world, xPosition, yPosition);
	}

	/**
	 * getter for instance
	 *
	 * @return the instance
	 */
	public static GameEngine getInstance() {
		return instance;
	}
}
