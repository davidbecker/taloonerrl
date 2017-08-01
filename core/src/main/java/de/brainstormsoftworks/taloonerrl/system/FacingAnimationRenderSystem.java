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
public class FacingAnimationRenderSystem extends IteratingSystem {

	private PositionComponent positionComponent;
	private FacingComponent facingComponent;
	private FacingAnimationComponent spriteComponent;
	private Animation animation;

	public FacingAnimationRenderSystem() {
		super(Aspect.all(PositionComponent.class, FacingComponent.class, FacingAnimationComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		facingComponent = ComponentMappers.getInstance().facing.get(_entityId);
		spriteComponent = ComponentMappers.getInstance().facingAnimation.get(_entityId);
		animation = spriteComponent.getAnimation(facingComponent.getDirection());
		// TODO check if is lid first
		// see AnimationRenderSystem
		if (animation != null) {
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), positionComponent.getX(),
					positionComponent.getY(), positionComponent.getOffsetX(), positionComponent.getOffsetY());
		}
	}
}
