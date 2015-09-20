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

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * simple rendering system for animations that aren't dependent on a direction
 *
 * @author David Becker
 *
 */
public class AnimationRenderSystem extends EntityProcessingSystem {

	private PositionComponent positionComponent;
	private AnimationComponent spriteComponent;
	private Animation animation;
	private int x = -1;
	private int y = -1;

	public AnimationRenderSystem() {
		super(Aspect.all(PositionComponent.class, AnimationComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		spriteComponent = ComponentMappers.getInstance().animation.get(e);
		animation = spriteComponent.getAnimation();
		x = positionComponent.getX();
		y = positionComponent.getY();
		if (animation != null && FovWrapper.getInstance().isLit(x, y)) {
			Renderer.getInstance().renderOnTile(RenderUtil.getKeyFrame(animation), x, y);
		}
	}
}
