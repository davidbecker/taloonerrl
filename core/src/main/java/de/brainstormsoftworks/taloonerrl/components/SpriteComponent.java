package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

import de.brainstormsoftworks.taloonerrl.dungeon.ESprite;

public class SpriteComponent extends PooledComponent {
	private ESprite sprite = ESprite.NOTHING;

	@Override
	protected void reset() {
		sprite = ESprite.NOTHING;
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
	}
}
