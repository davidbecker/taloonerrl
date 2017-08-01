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
package de.brainstormsoftworks.taloonerrl.internal.render;

import com.artemis.Aspect;
import com.badlogic.gdx.graphics.g2d.Animation;

import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * rendering system for animations that are dependent on a direction
 *
 * @author David Becker
 *
 */
public class EntityFacingAnimationsRenderer extends AbstractRender {

	private PositionComponent positionComponent;
	private FacingComponent facingComponent;
	private FacingAnimationComponent spriteComponent;
	private Animation animation;
	int x, y;

	public EntityFacingAnimationsRenderer() {
		super(GameEngine.getInstance().getAspectSubscriptionManager().get(
				Aspect.all(PositionComponent.class, FacingComponent.class, FacingAnimationComponent.class)));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		facingComponent = ComponentMappers.getInstance().facing.get(_entityId);
		spriteComponent = ComponentMappers.getInstance().facingAnimation.get(_entityId);
		animation = spriteComponent.getAnimation(facingComponent.getDirection());
		x = positionComponent.getX();
		y = positionComponent.getY();
		if (animation != null && FovWrapper.getInstance().isLit(x, y)) {
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), x, y,
					positionComponent.getOffsetX(), positionComponent.getOffsetY());
		}

	}
}
