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
import com.badlogic.gdx.graphics.g2d.Animation;

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

public class AnimationRenderSystem extends EntityProcessingSystem {
	private static final float scale = 16f;

	@SuppressWarnings("unchecked")
	public AnimationRenderSystem() {
		super(Aspect.all(PositionComponent.class, AnimationComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<AnimationComponent> spriteMapper = ComponentMappers.getInstance().animation;
		final PositionComponent positionComponent = posMapper.get(e);
		final AnimationComponent spriteComponent = spriteMapper.get(e);
		final Animation animation = spriteComponent.getAnimation();
		if (animation != null) {
			Renderer.getInstance().BATCH.draw(RenderUtil.getKeyFrame(animation), positionComponent.getX() * scale,
					positionComponent.getY() * scale);
		}
	}
}
