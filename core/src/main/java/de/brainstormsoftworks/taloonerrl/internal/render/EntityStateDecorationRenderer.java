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
import com.badlogic.gdx.graphics.g2d.Animation;

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;

/**
 * renderer that renders the current animation for the state decoration of an
 * entity in the visible area
 *
 * @author David Becker
 *
 */
public class EntityStateDecorationRenderer extends AbstractRender {
	/** increase this to speed the animation cycle up a bit */
	private static final float ANIMATION_SPEED_MODIFIER = 2.5f;

	private PositionComponent positionComponent;
	private AnimationComponent spriteComponent;
	private Animation animation;
	private int x, y;
	private float bounceOffset;

	public EntityStateDecorationRenderer() {
		super(GameEngine.getInstance().getAspectSubscriptionManager().get(Aspect.all(PositionComponent.class,
				AnimationComponent.class, StateDecorationComponent.class)));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		if (!PositionUtil.isValidPosition(positionComponent)) {
			return;
		}
		spriteComponent = ComponentMappers.getInstance().animation.get(_entityId);
		animation = spriteComponent.getAnimation();
		x = positionComponent.getX();
		y = positionComponent.getY();
		if (animation != null && FovWrapper.getInstance().isLit(x, y)
				&& ComponentMappers.getInstance().stateDecoration.get(_entityId).isActive()) {
			// we want the image to bounce a little
			/*
			 * kind of a hack to switch a float ...
			 */
			switch ((int) (GameEngine.getInstance().getStateTime() * ANIMATION_SPEED_MODIFIER) % 4) {
			case 0:
				bounceOffset = 0.9f * Renderer.tileSize;
				break;
			case 1:
			case 3:
				bounceOffset = 0.85f * Renderer.tileSize;
				break;
			case 2:
				bounceOffset = 0.8f * Renderer.tileSize;
				break;
			}
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), x, y,
					positionComponent.getOffsetX(), positionComponent.getOffsetY() + bounceOffset);

		}
	}

}
