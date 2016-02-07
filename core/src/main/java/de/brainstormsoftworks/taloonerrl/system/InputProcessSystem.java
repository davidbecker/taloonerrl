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

import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Direction;
import de.brainstormsoftworks.taloonerrl.core.engine.InputSystem;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * this system updates the controller component of the player entity when an
 * input was registered
 *
 * @author David Becker
 *
 */
public class InputProcessSystem extends EntityProcessingSystem {

	private PositionComponent positionComponent;
	private FacingComponent facingComponent;

	public InputProcessSystem() {
		super(Aspect.all(PlayerComponent.class, FacingComponent.class, PositionComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		facingComponent = ComponentMappers.getInstance().facing.get(e);
		/*
		 * FIXME refactor input system to use an event based system to move each
		 * component. this way we can have proper animations when transition
		 * between tiles and not a "warping" to the next tile
		 *
		 */
		if (positionComponent.getTotalX() == 0 && positionComponent.getTotalY() == 0) {
			if (InputSystem.getInstance().isKeyPressedDown()) {
				positionComponent.setOffsetY(0);
				positionComponent.setTotalY(-1 * Renderer.tileSize);
				facingComponent.setDirection(Direction.DOWN);
				InputSystem.getInstance().reset();
			} else if (InputSystem.getInstance().isKeyPressedUp()) {
				positionComponent.setOffsetY(1);
				positionComponent.setTotalY(Renderer.tileSize);
				facingComponent.setDirection(Direction.UP);
				InputSystem.getInstance().reset();
			} else if (InputSystem.getInstance().isKeyPressedLeft()) {
				positionComponent.setOffsetX(0);
				positionComponent.setTotalX(-1 * Renderer.tileSize);
				facingComponent.setDirection(Direction.LEFT);
				InputSystem.getInstance().reset();
			} else if (InputSystem.getInstance().isKeyPressedRight()) {
				positionComponent.setOffsetX(0);
				positionComponent.setTotalX(Renderer.tileSize);
				facingComponent.setDirection(Direction.RIGHT);
				InputSystem.getInstance().reset();
			}
		}
	}

}
