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
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.core.EDirection;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

/**
 * this system updates the controller component of the player entity when an
 * input was registered
 *
 * @author David Becker
 *
 */
public class ControllerSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public ControllerSystem() {
		super(Aspect.all(PlayerComponent.class, FacingComponent.class, ControllerComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		final ComponentMapper<ControllerComponent> controllerMapper = ComponentMappers
				.getInstance().controller;
		final ControllerComponent controllerComponent = controllerMapper.get(e);
		final FacingComponent facingComponent = ComponentMappers.getInstance().facing.get(e);
		if (InputSystem.isKeyPressedDown()) {
			controllerComponent.setdY(-1);
			facingComponent.setDirection(EDirection.DOWN);
		} else if (InputSystem.isKeyPressedUp()) {
			controllerComponent.setdY(1);
			facingComponent.setDirection(EDirection.UP);
		} else if (InputSystem.isKeyPressedLeft()) {
			controllerComponent.setdX(-1);
			facingComponent.setDirection(EDirection.LEFT);
		} else if (InputSystem.isKeyPressedRight()) {
			controllerComponent.setdX(1);
			facingComponent.setDirection(EDirection.RIGHT);
		}
	}

}
