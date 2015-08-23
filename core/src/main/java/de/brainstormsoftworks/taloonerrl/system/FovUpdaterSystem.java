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

import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;

/**
 * this system forces the update for the field of view for the given entity
 *
 * @author David Becker
 *
 */
public class FovUpdaterSystem extends EntityProcessingSystem {
	private PositionComponent positionComponent;
	private int oldX = -1;
	private int oldY = -1;

	@SuppressWarnings("unchecked")
	public FovUpdaterSystem() {
		super(Aspect.all(PositionComponent.class, PlayerComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		final int x = positionComponent.getX();
		final int y = positionComponent.getY();
		// only compute fov if position has changed
		if (!(oldX == x && oldY == y)) {
			// TODO move into different thread
			FovWrapper.getInstance().calculateFovForPosition(x, y);
		}
		oldX = x;
		oldY = y;
	}

}
