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
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.Animation;

import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.SpriteMapper;

public class AnimationComponent extends PooledComponent {
	private EEntity entityType = EEntity.NOTHING;
	private Animation animation = null;

	@Override
	protected void reset() {
		entityType = EEntity.NOTHING;
		animation = null;
	}

	/**
	 * getter for entityType
	 *
	 * @return the entityType
	 */
	public final EEntity getEntity() {
		return entityType;
	}

	/**
	 * setter for sprite
	 *
	 * @param sprite
	 *            the sprite to set
	 */
	public final void setSprite(final EEntity sprite) {
		entityType = sprite;
		SpriteMapper.getInstance().mapSprite(this);
	}

	/**
	 * getter for animation
	 *
	 * @return the animation
	 */
	public final Animation getAnimation() {
		return animation;
	}

	/**
	 * setter for animation
	 *
	 * @param animation
	 *            the animation to set
	 */
	public final void setAnimation(final Animation animation) {
		this.animation = animation;
	}
}
