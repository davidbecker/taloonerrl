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

import de.brainstormsoftworks.taloonerrl.components.CollectibleComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.MapOverlayRenderer;
import de.brainstormsoftworks.taloonerrl.render.MapOverlayRenderer.Visible;

/**
 * system that prepares the overlay data for the map overlay
 *
 * @author David Becker
 *
 */
public class MapOverlayPreparerSystem extends EntityProcessingSystem {
	private HealthComponent healthComponent;
	private PositionComponent positionComponent;
	private CollectibleComponent collectibleComponent;

	@SuppressWarnings("unchecked")
	public MapOverlayPreparerSystem() {
		super(Aspect.all(PositionComponent.class).one(HealthComponent.class, CollectibleComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		healthComponent = ComponentMappers.getInstance().health.get(e);
		collectibleComponent = ComponentMappers.getInstance().collectible.get(e);
		if (healthComponent != null && healthComponent.isAlive()) {
			// check if the entity is the player
			if (ComponentMappers.getInstance().player.get(e) != null) {
				MapOverlayRenderer.getInstance().setOverlay(positionComponent.getX(),
						positionComponent.getY(), Visible.PLAYER);
			} else {
				MapOverlayRenderer.getInstance().setOverlay(positionComponent.getX(),
						positionComponent.getY(), Visible.MONSTER);
			}
		}
		if (collectibleComponent != null) {
			MapOverlayRenderer.getInstance().setOverlay(positionComponent.getX(), positionComponent.getY(),
					Visible.COLLECTIBLE);
		}

	}
}
