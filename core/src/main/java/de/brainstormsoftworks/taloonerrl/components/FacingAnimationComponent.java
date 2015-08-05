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

import java.util.HashMap;
import java.util.Map;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.Animation;

import de.brainstormsoftworks.taloonerrl.core.EDirection;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.SpriteMapper;

/**
 * component to store animations for entities that change based on direction
 *
 * @author David Becker
 *
 */
public class FacingAnimationComponent extends PooledComponent {

	private EEntity entityType = EEntity.NOTHING;
	final Map<EDirection, Animation> animationMap = new HashMap<EDirection, Animation>();

	@Override
	protected void reset() {
		entityType = EEntity.NOTHING;
		animationMap.clear();
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
	 * @param entity
	 *            entity to map the animation for
	 */
	public final void mapAnimation(final EEntity entity) {
		entityType = entity;
		SpriteMapper.getInstance().mapAnimation(this);
	}

	public void setAnimations(final Map<EDirection, Animation> animations) {
		animationMap.putAll(animations);
	}

	public Animation getAnimation(final EDirection direction) {
		return animationMap.get(direction);
	}

}
