package de.brainstormsoftworks.taloonerrl.actors;

public interface IActor {
	IActorMovement getMovementComponent();

	IActorSprite getSpriteComponent();

	/**
	 * sends a message to all the components of this actor
	 *
	 * @param msg
	 */
	void sendMessage(int msg);
}
