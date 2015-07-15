package de.brainstormsoftworks.taloonerrl.internal.actors;

import de.brainstormsoftworks.taloonerrl.actors.IActor;
import de.brainstormsoftworks.taloonerrl.actors.IActorMovement;
import de.brainstormsoftworks.taloonerrl.actors.IActorSprite;

public abstract class Actor implements IActor {
	protected ActorMovementComponent movementComponent = null;
	protected ActorSpriteComponent actorSpriteComponent = new ActorSpriteComponent();

	@Override
	public IActorMovement getMovementComponent() {
		return movementComponent;
	}

	@Override
	public IActorSprite getSpriteComponent() {
		return actorSpriteComponent;
	}
}
