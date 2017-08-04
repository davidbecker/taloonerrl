/*******************************************************************************
 * Copyright (c) 2017 David Becker.
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
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * renderer that renders the current animation for a component in the visible
 * area
 *
 * @author David Becker
 *
 */
public class EntityAnimationsRenderer extends AbstractRender {
	private PositionComponent positionComponent;
	private AnimationComponent spriteComponent;
	private Animation animation;
	private int x, y;

	public EntityAnimationsRenderer() {
		super(GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, AnimationComponent.class)));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		spriteComponent = ComponentMappers.getInstance().animation.get(_entityId);
		animation = spriteComponent.getAnimation();
		x = positionComponent.getX();
		y = positionComponent.getY();
		if (animation != null && FovWrapper.getInstance().isLit(x, y)) {
			if (ComponentMappers.getInstance().stateDecoration.getSafe(_entityId) != null) {
				y += 1;
			}
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), x, y,
					positionComponent.getOffsetX(), positionComponent.getOffsetY());

		}
	}

}
