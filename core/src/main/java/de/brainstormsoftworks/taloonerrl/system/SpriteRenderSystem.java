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
public class SpriteRenderSystem extends IteratingSystem {

	public SpriteRenderSystem() {
		super(Aspect.all(PositionComponent.class, SpriteComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		final PositionComponent positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		final SpriteComponent spriteComponent = ComponentMappers.getInstance().sprite.get(_entityId);
		Renderer.getInstance().renderOnTile(spriteComponent.getSprite(), positionComponent.getX(),
				positionComponent.getY());
	}
}
