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
 * renderer that renders the current animation for a component in the visible
 * area<br>
 * excludes {@link StateDecorationComponent} as they are rendered by
 * {@link EntityStateDecorationRenderer}
 *
 * @author David Becker
 *
 */
public class EntityAnimationsRenderer extends AbstractRender {
	private PositionComponent positionComponent;
	private AnimationComponent spriteComponent;
	private Animation animation;
	private int x, y;

	@SuppressWarnings("unchecked")
	public EntityAnimationsRenderer() {
		super(GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, AnimationComponent.class)
						.exclude(StateDecorationComponent.class)));
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
		if (animation != null && FovWrapper.getInstance().isLit(x, y)) {
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), x, y,
					positionComponent.getOffsetX(), positionComponent.getOffsetY());

		}
	}

}
