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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.SpriteMapper;

/**
 * component for an entity that has an animation
 *
 * @author David Becker
 *
 */
public class SpriteComponent extends PooledComponent {
	private EEntity entityType = EEntity.NOTHING;
	private TextureRegion sprite = null;

	@Override
	protected void reset() {
		entityType = EEntity.NOTHING;
		sprite = null;
	}

	/**
	 * getter for entityType
	 *
	 * @return the entityType
	 */
	public final EEntity getEntityType() {
		return entityType;
	}

	/**
	 * maps the corresponding animation for the given entity
	 *
	 * @param _entity
	 *            entity to map the animation for
	 */
	public final void mapSprite(final EEntity _entity) {
		entityType = _entity;
		SpriteMapper.getInstance().mapSprite(this);
	}

	/**
	 * getter for {@link #sprite}
	 *
	 * @return the sprite
	 */
	public final TextureRegion getSprite() {
		return sprite;
	}

	/**
	 * setter for sprite
	 *
	 * @param sprite
	 *            the sprite to set
	 */
	public final void setSprite(final TextureRegion sprite) {
		this.sprite = sprite;
	}
}
