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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

public class MovementSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.all(PositionComponent.class, ControllerComponent.class));
	}

	@Override
	protected void process(final Entity entity) {
		/*
		 * FIXME probably shouldn't be done this way - implement event system
		 * for turn based entities instead I'm considering this approach for
		 * particles (if needed)
		 */
		// // TODO implement collision
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<ControllerComponent> controllerMapper = ComponentMappers
				.getInstance().controller;
		final PositionComponent position = posMapper.get(entity);
		final ControllerComponent controllerComponent = controllerMapper.get(entity);
		// adds the vector to the position
		position.setX(position.getX() + controllerComponent.getdX());
		position.setY(position.getY() + controllerComponent.getdY());
		// resets controller vector
		controllerComponent.reset();
	}

}
