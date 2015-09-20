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
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.core.EDirection;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * this system updates the controller component of the player entity when an
 * input was registered
 *
 * @author David Becker
 *
 */
public class InputProcessSystem extends EntityProcessingSystem {

	private ControllerComponent controllerComponent;
	private FacingComponent facingComponent;

	public InputProcessSystem() {
		super(Aspect.all(PlayerComponent.class, FacingComponent.class, ControllerComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		controllerComponent = ComponentMappers.getInstance().controller.get(e);
		facingComponent = ComponentMappers.getInstance().facing.get(e);
		// TODO add check if tile is walkable/occupied by monster
		/*
		 * FIXME refactor input system to use an event based system to move each
		 * component. this way we can have proper animations when transition
		 * between tiles and not a "warping" to the next tile
		 *
		 */
		if (InputSystem.getInstance().isKeyPressedDown()) {
			controllerComponent.setOffsetY(0);
			controllerComponent.setTotalY(-1 * Renderer.tileSize);
			facingComponent.setDirection(EDirection.DOWN);
			InputSystem.getInstance().reset();
		} else if (InputSystem.getInstance().isKeyPressedUp()) {
			controllerComponent.setOffsetY(1);
			controllerComponent.setTotalY(Renderer.tileSize);
			facingComponent.setDirection(EDirection.UP);
			InputSystem.getInstance().reset();
		} else if (InputSystem.getInstance().isKeyPressedLeft()) {
			controllerComponent.setOffsetX(0);
			controllerComponent.setTotalX(-1 * Renderer.tileSize);
			facingComponent.setDirection(EDirection.LEFT);
			InputSystem.getInstance().reset();
		} else if (InputSystem.getInstance().isKeyPressedRight()) {
			controllerComponent.setOffsetX(0);
			controllerComponent.setTotalX(Renderer.tileSize);
			facingComponent.setDirection(EDirection.RIGHT);
			InputSystem.getInstance().reset();
		}
	}

}
