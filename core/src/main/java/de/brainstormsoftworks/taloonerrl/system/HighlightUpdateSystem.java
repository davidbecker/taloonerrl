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

import de.brainstormsoftworks.taloonerrl.components.HighlightComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.InputSystem;

/**
 * update system to set the position of the highlighted tile
 *
 * @author David Becker
 *
 */
public class HighlightUpdateSystem extends EntityProcessingSystem {

	private PositionComponent positionComponent;

	public HighlightUpdateSystem() {
		super(Aspect.all(PositionComponent.class, HighlightComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		positionComponent.setX(InputSystem.getInstance().getMouseOverX());
		positionComponent.setY(InputSystem.getInstance().getMouseOverY());
	}
}
