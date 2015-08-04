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

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;

/**
 * factory to create entities in to the game world
 *
 * @author David Becker
 *
 */
public final class EntityFactory {
	public static Entity createEntity(final EEntity type, final World world, final int xPosition, final int yPosition) {
		switch (type) {
		case PLAYER:
			return createPlayer(world);
		case BLOB:
			return createActor(type, world, xPosition, yPosition);
		case SQUIRREL:
			return createActor(type, world, xPosition, yPosition);
		case TORCH:
			return createDecoration(type, world, xPosition, yPosition);
		default:
			return null;
		}
	}

	private static Entity createPlayer(final World world) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().player);
		newEntity.getComponent(FacingAnimationComponent.class).mapAnimation(EEntity.PLAYER);
		return newEntity;
	}

	private static Entity createActor(final EEntity type, final World world, final int xPosition, final int yPosition) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().actor);
		newEntity.getComponent(AnimationComponent.class).mapAnimation(type);
		final PositionComponent posComponent = newEntity.getComponent(PositionComponent.class);
		posComponent.setX(xPosition);
		posComponent.setY(yPosition);
		return newEntity;
	}

	private static Entity createDecoration(final EEntity type, final World world, final int xPosition,
			final int yPosition) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().decoration);
		newEntity.getComponent(AnimationComponent.class).mapAnimation(type);
		final PositionComponent posComponent = newEntity.getComponent(PositionComponent.class);
		posComponent.setX(xPosition);
		posComponent.setY(yPosition);
		return newEntity;
	}
}
