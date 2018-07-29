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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.components.ExploredComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;

/**
 * simple rendering system for a sprite
 *
 * @author David Becker
 *
 */
public class SpriteRenderSystem extends AbstractRender {

	private static PositionComponent positionComponent;
	private static SpriteComponent spriteComponent;
	private static TextureRegion sprite;
	private static ExploredComponent exploredComponent;

	public SpriteRenderSystem() {
		super(GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, SpriteComponent.class)));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		if (!PositionUtil.isValidPosition(positionComponent)) {
			return;
		}
		spriteComponent = ComponentMappers.getInstance().sprite.get(_entityId);
		sprite = spriteComponent.getSprite();
		exploredComponent = ComponentMappers.getInstance().explored.getSafe(_entityId);
		if (exploredComponent == null || exploredComponent.isExplored()
				|| FovWrapper.getInstance().isLit(positionComponent.getX(), positionComponent.getY())) {
			Renderer.getInstance().renderOnTile(sprite, positionComponent.getX(), positionComponent.getY());
		}
	}
}
