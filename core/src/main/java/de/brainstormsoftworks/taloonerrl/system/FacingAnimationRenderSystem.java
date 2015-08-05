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

import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * rendering system for animations that are dependent on a direction
 *
 * @author David Becker
 *
 */
public class FacingAnimationRenderSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public FacingAnimationRenderSystem() {
		super(Aspect.all(PositionComponent.class, FacingComponent.class, FacingAnimationComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<FacingComponent> facingMapper = ComponentMappers.getInstance().facing;
		final ComponentMapper<FacingAnimationComponent> spriteMapper = ComponentMappers.getInstance().facingAnimation;
		final PositionComponent positionComponent = posMapper.get(e);
		final FacingComponent facingComponent = facingMapper.get(e);
		final FacingAnimationComponent spriteComponent = spriteMapper.get(e);
		final Animation animation = spriteComponent.getAnimation(facingComponent.getDirection());
		if (animation != null) {
			Renderer.getInstance().BATCH.draw(RenderUtil.getKeyFrame(animation),
					positionComponent.getX() * Renderer.scale, positionComponent.getY() * Renderer.scale);
		}
	}
}
