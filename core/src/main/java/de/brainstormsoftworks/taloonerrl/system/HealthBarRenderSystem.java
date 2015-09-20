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

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.PaletteUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * system that draws health bars under entities
 *
 * @author David Becker
 *
 */
public class HealthBarRenderSystem extends EntityProcessingSystem {
	private static final int totalSize = Renderer.tileSize - 2;
	private HealthComponent healthComponent;
	private int sizeGreen = 0;
	private int sizeRed = 0;
	private PositionComponent positionComponent;
	private int x = -1;
	private int y = -1;

	public HealthBarRenderSystem() {
		super(Aspect.all(PositionComponent.class, HealthComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		healthComponent = ComponentMappers.getInstance().health.get(e);
		if (healthComponent.isAlive()) {
			positionComponent = ComponentMappers.getInstance().position.get(e);
			x = positionComponent.getX();
			y = positionComponent.getY();
			if (FovWrapper.getInstance().isLit(x, y)) {
				sizeGreen = (int) (totalSize * healthComponent.getHealthPercent());
				sizeRed = totalSize - sizeGreen;
				Renderer.getInstance().renderOnTileWithOffset(PaletteUtil.getInstance().BLACK, x, y, 0, 0,
						Renderer.tileSize, 3);
				for (int i = 0; i < sizeGreen; i++) {
					Renderer.getInstance().renderOnTileWithOffset(PaletteUtil.getInstance().LIGHT_GREEN, x, y,
							i + 1, 1);
				}
				for (int i = 0; i < sizeRed; i++) {
					Renderer.getInstance().renderOnTileWithOffset(PaletteUtil.getInstance().RED, x, y,
							i + 1 + sizeGreen, 1);
				}
			}
		}

	}
}
