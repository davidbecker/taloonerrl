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

import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.SpriteMapper;
import lombok.Getter;

/**
 * component to store animations for entities that change based on direction
 *
 * @author David Becker
 *
 */
public class FacingAnimationComponent extends PooledComponent
		implements ISetAbleComponent<FacingAnimationComponent> {

	private @Getter EEntity entityType = EEntity.NOTHING;
	private final Map<Integer, Animation> animationMap = new HashMap<Integer, Animation>();

	@Override
	protected void reset() {
		entityType = EEntity.NOTHING;
		animationMap.clear();
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

	public void setAnimations(final Map<Integer, Animation> animations) {
		animationMap.putAll(animations);
	}

	public Animation getAnimation(final Integer direction) {
		return animationMap.get(direction);
	}

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final FacingAnimationComponent _component) {
		entityType = _component.getEntityType();
		animationMap.clear();
		animationMap.putAll(_component.animationMap);

	}

}
