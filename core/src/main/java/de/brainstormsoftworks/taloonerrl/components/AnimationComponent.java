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
	 * getter for sprite
	 *
	 * @return the sprite
	 */
	public final EEntity getSprite() {
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
