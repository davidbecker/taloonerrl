package de.brainstormsoftworks.taloonerrl.internal.actors;

import de.brainstormsoftworks.taloonerrl.actors.IActorMovement;

/**
 * 
 * 
 * @author david
 *
 */
public class ActorMovementComponent implements IActorMovement {

	private int xPosition;
	private int yPosition;

	public ActorMovementComponent() {
		this(0, 0);
	}

	public ActorMovementComponent(int initialXPosition, int initialYPosition) {
		xPosition = initialXPosition;
		yPosition = initialYPosition;
	}

	@Override
	public void move(int dX, int dY) {
		xPosition += dX;
		yPosition += dY;
	}

	@Override
	public int getXPosition() {
		return xPosition;
	}

	@Override
	public int getYPosition() {
		return yPosition;
	}
}
