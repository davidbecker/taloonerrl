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
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

public class MovementSystem extends EntityProcessingSystem {

	private PositionComponent position;
	private ControllerComponent controllerComponent;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.all(PositionComponent.class, ControllerComponent.class));
	}

	@Override
	protected void process(final Entity entity) {
		position = ComponentMappers.getInstance().position.get(entity);
		controllerComponent = ComponentMappers.getInstance().controller.get(entity);
		// adds the vector to the position
		position.setX(position.getX() + controllerComponent.getdX());
		position.setY(position.getY() + controllerComponent.getdY());
		// resets controller vector
		controllerComponent.reset();
	}

}
