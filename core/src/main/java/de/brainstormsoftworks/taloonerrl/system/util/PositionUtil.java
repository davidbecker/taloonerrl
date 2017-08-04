/*******************************************************************************
 * Copyright (c) 2016 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system.util;

import com.artemis.Aspect;
import com.artemis.utils.IntBag;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * collection of utility methods related to positions
 *
 * @author David Becker
 *
 */
public final class PositionUtil {

	private PositionUtil() {
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 * @throws NullPointerException
	 *             when position is null
	 */
	public static final int getDeltaX(final PositionComponent position) {
		return position.getTotalX() == Renderer.tileSize ? 1
				: position.getTotalX() == -1 * Renderer.tileSize ? -1 : 0;
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 * @throws NullPointerException
	 *             when position is null
	 */
	public static final int getDeltaY(final PositionComponent position) {
		return position.getTotalY() == Renderer.tileSize ? 1
				: position.getTotalY() == -1 * Renderer.tileSize ? -1 : 0;
	}

	public static final boolean isMovingWholeTile(final int deltaX, final int deltaY) {
		return deltaX != 0 || deltaY != 0;
	}

	/**
	 * checks if there is an alive entity on the given position
	 *
	 * @return if there isn't no entity on the given position
	 */
	public static final boolean isPositionTaken(final int x, final int y) {
		final IntBag entities = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, HealthComponent.class)).getEntities();
		int entityID;
		PositionComponent positionComponent;
		HealthComponent healthComponent;
		for (int i = 0; i < entities.size(); i++) {
			entityID = entities.get(i);
			healthComponent = ComponentMappers.getInstance().health.get(entityID);
			if (healthComponent.isAlive()) {
				positionComponent = ComponentMappers.getInstance().position.get(entityID);
				if (positionComponent.getX() == x && positionComponent.getY() == y) {
					return true;
				}
			}
		}
		return false;
	}
}
