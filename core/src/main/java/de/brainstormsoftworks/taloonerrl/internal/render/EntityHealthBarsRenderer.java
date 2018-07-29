/*******************************************************************************
 * Copyright (c) 2017-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.internal.render;

import com.artemis.Aspect;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.PaletteUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;

/**
 * renders health bars for applicable entities
 *
 * @author David Becker
 *
 */
public class EntityHealthBarsRenderer extends AbstractRender {
	// maximum size of health bar
	private static final int totalSize = Renderer.tileSize - 2;

	private HealthComponent healthComponent;
	private PositionComponent positionComponent;
	private int x, y, offsetX, offsetY, sizeGreen, sizeRed;

	public EntityHealthBarsRenderer() {
		super(GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, HealthComponent.class)));
	}

	@Override
	protected void process(final int _entityId) {
		healthComponent = ComponentMappers.getInstance().health.get(_entityId);
		if (healthComponent.isAlive() /* && healthComponent.getHealthPercent() < 1f */) {
			positionComponent = ComponentMappers.getInstance().position.get(_entityId);
			if (!PositionUtil.isValidPosition(positionComponent)) {
				return;
			}
			x = positionComponent.getX();
			y = positionComponent.getY();
			offsetX = positionComponent.getOffsetX();
			offsetY = positionComponent.getOffsetY();
			if (FovWrapper.getInstance().isLit(x, y)) {
				sizeGreen = (int) (totalSize * healthComponent.getHealthPercent());
				sizeRed = totalSize - sizeGreen;
				Renderer.getInstance().renderOnTile(PaletteUtil.getInstance().BLACK, x, y, offsetX, offsetY,
						Renderer.tileSize, 3);
				for (int i = 0; i < sizeGreen; i++) {
					Renderer.getInstance().renderOnTile(PaletteUtil.getInstance().LIGHT_GREEN, x, y,
							i + 1 + offsetX, 1 + offsetY);
				}
				for (int i = 0; i < sizeRed; i++) {
					Renderer.getInstance().renderOnTile(PaletteUtil.getInstance().RED, x, y,
							i + 1 + sizeGreen + offsetX, 1 + offsetY);
				}
			}
		}
	}

}
