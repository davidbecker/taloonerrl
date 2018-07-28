/*******************************************************************************
c * Copyright (c) 2015-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ai.GdxAI;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TargetComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.ETurnType;
import lombok.Getter;
import lombok.Setter;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

/**
 * central part of the game<br/>
 * controls & sets up the game world
 *
 * @author David Becker
 *
 */
public final class GameEngine {

	public static final String TAG_PLAYER = "PLAYER";

	// initialized on first call via getInstance
	// done this way to not trigger the constructor on static access
	private static GameEngine instance;
	private final World world;
	/** holds which entities should move at any given time */
	@Getter
	@Setter
	private ETurnType currentTurnSide = ETurnType.PLAYER;
	@Getter
	private static final RNG rng = new RNG();

	/**
	 * total time in milliseconds that the application has been run
	 */
	private float stateTime;
	/**
	 * time since last frame
	 */
	private float deltaTime;

	private GameEngine() {
		final InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(UserIntefaceInputProcessor.getInstance());
		inputMultiplexer.addProcessor(InputSystem.getInstance());
		Gdx.input.setInputProcessor(inputMultiplexer);
		final WorldConfiguration config = new WorldConfiguration();
		config.setSystem(TagManager.class);
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
		GdxAI.getTimepiece().update(deltaTime);
		// MessageManager.getInstance().update();
		world.setDelta(deltaTime);
		world.process();
		processTurnTaken();
	}

	/**
	 * checks if all entities have their turn taken for the current turn
	 *
	 * @param _currentTurnSide
	 */
	private void processTurnTaken() {
		// if we are in a cleanup turn we skip the whole checking & resetting that we
		// would normally do
		boolean turnTaken = currentTurnSide == ETurnType.MONSTER_CLEANUP
				|| currentTurnSide == ETurnType.PLAYER_CLEANUP;
		if (checkReadyToSwitchTurns()) {
			// reset turn components
			final IntBag entities = getAspectSubscriptionManager().get(Aspect.all(TurnComponent.class))
					.getEntities();
			TurnComponent turnComponent;
			for (int i = 0; i < entities.size(); i++) {
				turnComponent = ComponentMappers.getInstance().turn.get(entities.get(i));
				if (turnComponent.getMovesOnTurn() == currentTurnSide) {
					turnComponent.setTurnTaken(false);
					turnComponent.setProcessed(false);
				}
			}
			turnTaken = true;
		}
		if (turnTaken) {
			// switch sides
			currentTurnSide = ETurnType.nextTurn(currentTurnSide);
			Gdx.app.debug("GameEngine", "switching turn sides to " + ETurnType.toString(currentTurnSide));
		}
	}

	/**
	 * check if all entities that should had their turn actually had their turn
	 *
	 * @return true if turns should be switched
	 */
	private boolean checkReadyToSwitchTurns() {
		boolean switchTurn = true;

		final IntBag entities = getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, TurnComponent.class)).getEntities();
		int entityID;
		PositionComponent positionComponent;
		TurnComponent turnComponent;
		for (int i = 0; i < entities.size(); i++) {
			entityID = entities.get(i);
			turnComponent = ComponentMappers.getInstance().turn.get(entityID);
			// only check entities that should move the current turn
			if (turnComponent.getMovesOnTurn() == currentTurnSide) {
				// don't switch turns if any component isn't processed yet
				switchTurn = switchTurn && turnComponent.isProcessed();
				if (switchTurn) {
					positionComponent = ComponentMappers.getInstance().position.get(entityID);
					// don't switch turn if any component is in transition
					switchTurn = switchTurn && !positionComponent.isProcessingTurn();
				}

			}
			// if we found any position that isn't done we skip everything
			if (!switchTurn) {
				break;
			}
		}

		return switchTurn;
	}

	/**
	 * gets the time span between the current frame and the last frame in seconds.
	 * Might be smoothed over n frames.
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
	 * deletes the entity with the given id from the world
	 *
	 * @param entityId
	 *            of the entity
	 */
	public void deleteEntity(final int entityId) {
		world.delete(entityId);
	}

	/**
	 * clears all references to the given ID from {@link TargetComponent}s
	 *
	 * @param entityId
	 *            of the entity
	 */
	public void removeTargetReferences(final int entityId) {
		final IntBag entities = getAspectSubscriptionManager().get(Aspect.one(TargetComponent.class))
				.getEntities();
		TargetComponent targetComponent;
		for (int i = 0; i < entities.size(); i++) {
			targetComponent = ComponentMappers.getInstance().target.get(entities.get(i));
			if (targetComponent.getTargetId() == entityId) {
				targetComponent.reset();
			}
		}
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
	 * creates a new entity.
	 *
	 * @param type
	 *            of entity to create
	 * @param position
	 *            position in squidpony format
	 *
	 * @return new entity
	 */
	public Entity createNewEntity(final EEntity type, final Coord position) {
		return EntityFactory.createEntity(type, world, position.x, position.y);
	}

	/**
	 * getter for instance
	 *
	 * @return the instance
	 */
	public static GameEngine getInstance() {
		if (instance == null) {
			instance = new GameEngine();
		}
		return instance;
	}

	/**
	 * getter for an aspect subscription manager. used to iterate over entities with
	 * given aspects
	 *
	 * @return manager
	 */
	public AspectSubscriptionManager getAspectSubscriptionManager() {
		return world.getAspectSubscriptionManager();
	}

	/**
	 * getter for the tag manager of the game world
	 *
	 * @return manager
	 */
	public TagManager getTagManager() {
		return world.getSystem(TagManager.class);
	}
}
