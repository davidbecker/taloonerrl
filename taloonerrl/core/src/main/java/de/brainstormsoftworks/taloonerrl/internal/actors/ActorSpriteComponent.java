package de.brainstormsoftworks.taloonerrl.internal.actors;

import de.brainstormsoftworks.taloonerrl.actors.IActorSprite;
import de.brainstormsoftworks.taloonerrl.dungeon.ISprite;

public class ActorSpriteComponent implements IActorSprite {

	private int sprite = ISprite.NOTHING;

	@Override
	public int getSprite() {
		return sprite;
	}

	public void setSprite(int newSprite) {
		sprite = newSprite;
	}
}
