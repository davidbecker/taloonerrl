package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.Animation;

import de.brainstormsoftworks.taloonerrl.core.engine.SpriteMapper;
import de.brainstormsoftworks.taloonerrl.dungeon.ESprite;

public class SpriteComponent extends PooledComponent {
	private ESprite sprite = ESprite.NOTHING;
	private Animation animation = null;

	@Override
	protected void reset() {
		sprite = ESprite.NOTHING;
		animation = null;
	}

	/**
	 * getter for sprite
	 *
	 * @return the sprite
	 */
	public final ESprite getSprite() {
		return sprite;
	}

	/**
	 * setter for sprite
	 *
	 * @param sprite
	 *            the sprite to set
	 */
	public final void setSprite(final ESprite sprite) {
		this.sprite = sprite;
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
