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
public class FovUpdaterSystem extends IteratingSystem {
	private PositionComponent positionComponent;

	public FovUpdaterSystem() {
		super(Aspect.all(PositionComponent.class, PlayerComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		// TODO move into different thread
		FovWrapper.getInstance().calculateFovForPosition(positionComponent.getXEffective(),
				positionComponent.getYEffective());
	}

}
