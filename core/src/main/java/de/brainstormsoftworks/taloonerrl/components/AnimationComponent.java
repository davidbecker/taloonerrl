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
import lombok.Getter;
import lombok.Setter;

/**
 * component for an entity that has an animation
 *
 * @author David Becker
 *
 */
public class AnimationComponent extends PooledComponent {
	private @Getter EEntity entityType = EEntity.NOTHING;
	private @Getter @Setter Animation animation = null;

	@Override
	protected void reset() {
		entityType = EEntity.NOTHING;
		animation = null;
	}

	/**
	 * maps the corresponding animation for the given entity
	 *
	 * @param _entity
	 *            entity to map the animation for
	 */
	public final void mapAnimation(final EEntity _entity) {
		entityType = _entity;
		SpriteMapper.getInstance().mapAnimation(this);
	}

}
