/*******************************************************************************
 * Copyright (c) 2015-2018 David Becker.
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
import com.artemis.utils.IntBag;

import de.brainstormsoftworks.taloonerrl.components.ExploredComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;

/**
 * this system forces the update for the field of view for the given entity
 *
 * @author David Becker
 *
 */
public class FovUpdaterSystem extends IteratingSystem {

	private static PositionComponent positionComponent;
	private static PositionComponent otherPosition;
	private static ExploredComponent exploredComponent;
	private static int entityID;

	public FovUpdaterSystem() {
		super(Aspect.all(PositionComponent.class, PlayerComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		// TODO move into different thread
		FovWrapper.getInstance().calculateFovForPosition(positionComponent.getXEffective(),
				positionComponent.getYEffective());

		// find newly visible collectibles and update their explored state
		final IntBag entities = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, ExploredComponent.class)).getEntities();

		for (int i = 0; i < entities.size(); i++) {
			entityID = entities.get(i);
			exploredComponent = ComponentMappers.getInstance().explored.get(entityID);
			if (!exploredComponent.isExplored()) {
				otherPosition = ComponentMappers.getInstance().position.get(entityID);
				exploredComponent.setExplored(
						FovWrapper.getInstance().isLit(otherPosition.getX(), otherPosition.getY()));
				;
			}
		}

	}

}
