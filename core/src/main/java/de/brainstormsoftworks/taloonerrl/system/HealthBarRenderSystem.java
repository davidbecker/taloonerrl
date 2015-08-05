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
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.PaletteUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * system that draws health bars under entities
 *
 * @author David Becker
 *
 */
public class HealthBarRenderSystem extends EntityProcessingSystem {
	private static final int tileSize = 16;
	private static final int totalSize = tileSize - 2;
	private int sizeGreen = 0;
	private int sizeRed = 0;

	@SuppressWarnings("unchecked")
	public HealthBarRenderSystem() {
		super(Aspect.all(PositionComponent.class, HealthComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<HealthComponent> healthMapper = ComponentMappers.getInstance().health;
		final HealthComponent healthComponent = healthMapper.get(e);
		if (healthComponent.isAlive()) {
			final PositionComponent positionComponent = posMapper.get(e);
			sizeGreen = (int) (totalSize * healthComponent.getHealthPercent());
			sizeRed = totalSize - sizeGreen;
			// draw a black border around the bar
			Renderer.getInstance().BATCH.draw(PaletteUtil.getInstance().BLACK,
					positionComponent.getX() * tileSize, positionComponent.getY() * tileSize, tileSize, 3);
			for (int i = 0; i < sizeGreen; i++) {
				Renderer.getInstance().BATCH.draw(PaletteUtil.getInstance().LIGHT_GREEN,
						positionComponent.getX() * tileSize + i + 1, positionComponent.getY() * tileSize + 1);
			}
			for (int i = 0; i < sizeRed; i++) {
				Renderer.getInstance().BATCH.draw(PaletteUtil.getInstance().RED,
						positionComponent.getX() * tileSize + i + 1 + sizeGreen,
						positionComponent.getY() * tileSize + 1);
			}
		}

	}
}
