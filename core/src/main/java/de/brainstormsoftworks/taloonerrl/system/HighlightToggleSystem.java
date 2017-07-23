/*******************************************************************************
 * Copyright (c) 2017 David Becker.
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

import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.InputSystem;

/**
 * (debug) system to toggle highlighting of an entity based on mouse input
 *
 * @author David Becker
 *
 */
public class HighlightToggleSystem extends EntityProcessingSystem {

	private PositionComponent positionComponent;
	private HighlightAbleComponent highlight;

	public HighlightToggleSystem() {
		super(Aspect.all(PositionComponent.class, HighlightAbleComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		if (InputSystem.getInstance().isButtonPressedLeft()) {
			positionComponent = ComponentMappers.getInstance().position.get(e);
			highlight = ComponentMappers.getInstance().highlight.get(e);
			if (positionComponent.getX() == InputSystem.getInstance().getMouseOverX()
					&& positionComponent.getY() == InputSystem.getInstance().getMouseOverY()) {
				highlight.toggleHighlighted();
				InputSystem.getInstance().reset();
			}
		}
		// FIXME more than one click is processed in one "turn"
	}
}
