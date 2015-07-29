package de.brainstormsoftworks.taloonerrl.internal.actors;

import de.brainstormsoftworks.taloonerrl.dungeon.ESprite;

public class Player extends Actor {
	public Player() {
		movementComponent = new ActorMovementComponent();
		actorSpriteComponent.setSprite(ESprite.AT);
	}
}
