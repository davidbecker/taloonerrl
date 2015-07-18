package de.brainstormsoftworks.taloonerrl.internal.actors;

import de.brainstormsoftworks.taloonerrl.actors.IActorSprite;
import de.brainstormsoftworks.taloonerrl.dungeon.ESprite;

public class ActorSpriteComponent implements IActorSprite, IComponent {

	private ESprite sprite = ESprite.NOTHING;

	@Override
	public ESprite getSprite() {
		return sprite;
	}

	public void setSprite(ESprite newSprite) {
		sprite = newSprite;
	}

	@Override
	public void recieveMessage(int msg) {
		// TODO Auto-generated method stub

	}
}
