package de.brainstormsoftworks.taloonerrl.actors;

public interface IActor {
	public IActorMovement getMovementComponent();

	public IActorSprite getSpriteComponent();

	/**
	 * sends a message to all the components of this actor
	 * 
	 * @param msg
	 */
	public void sendMessage(int msg);
}
