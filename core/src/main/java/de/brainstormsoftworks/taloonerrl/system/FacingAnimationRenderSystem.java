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

	private PositionComponent positionComponent;
	private FacingComponent facingComponent;
	private FacingAnimationComponent spriteComponent;
	private Animation animation;

	public FacingAnimationRenderSystem() {
		super(Aspect.all(PositionComponent.class, FacingComponent.class, FacingAnimationComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		facingComponent = ComponentMappers.getInstance().facing.get(e);
		spriteComponent = ComponentMappers.getInstance().facingAnimation.get(e);
		animation = spriteComponent.getAnimation(facingComponent.getDirection());
		// TODO check if is lid first
		// see AnimationRenderSystem
		if (animation != null) {
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), positionComponent.getX(),
					positionComponent.getY(), positionComponent.getOffsetX(), positionComponent.getOffsetY());
		}
	}
}
