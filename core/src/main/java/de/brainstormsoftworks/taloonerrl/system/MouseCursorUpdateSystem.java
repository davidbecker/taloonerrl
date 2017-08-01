/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
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
import com.artemis.systems.IteratingSystem;

import de.brainstormsoftworks.taloonerrl.components.CursorComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.InputSystem;

/**
 * update system to set the position of the highlighted tile based on the mouse
 * cursor
 *
 * @author David Becker
 *
 */
public class MouseCursorUpdateSystem extends IteratingSystem {

	private PositionComponent positionComponent;

	public MouseCursorUpdateSystem() {
		super(Aspect.all(PositionComponent.class, CursorComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		positionComponent.setX(InputSystem.getInstance().getMouseOverX());
		positionComponent.setY(InputSystem.getInstance().getMouseOverY());
	}
}
