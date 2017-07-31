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

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * simple rendering system for a sprite
 *
 * @author David Becker
 *
 */
public class SpriteRenderSystem extends EntityProcessingSystem {

	public SpriteRenderSystem() {
		super(Aspect.all(PositionComponent.class, SpriteComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		final PositionComponent positionComponent = ComponentMappers.getInstance().position.get(e);
		final SpriteComponent spriteComponent = ComponentMappers.getInstance().sprite.get(e);
		Renderer.getInstance().renderOnTile(spriteComponent.getSprite(), positionComponent.getX(),
				positionComponent.getY());
	}
}
