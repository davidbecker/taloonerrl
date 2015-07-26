package de.brainstormsoftworks.taloonerrl.internal.actors;

import de.brainstormsoftworks.taloonerrl.actors.IActorMovement;
import de.brainstormsoftworks.taloonerrl.core.TaloonerRl;

/**
 *
 *
 * @author david
 *
 */
public class ActorMovementComponent implements IActorMovement, IComponent {

	private int xPosition;
	private int yPosition;

	public ActorMovementComponent() {
		this(0, 0);
	}

	public ActorMovementComponent(final int initialXPosition, final int initialYPosition) {
		xPosition = initialXPosition;
		yPosition = initialYPosition;
	}

	@Override
	public void move(final int dX, final int dY) {
		final int newX = xPosition + dX;
		final int newY = yPosition + dY;
		if (TaloonerRl.map.getMap()[newX][newY].isWalkable()) {
			xPosition += dX;
			yPosition += dY;
		}
	}

	@Override
	public int getXPosition() {
		return xPosition;
	}

	@Override
	public int getYPosition() {
		return yPosition;
	}

	@Override
	public void recieveMessage(final int msg) {
		// TODO Auto-generated method stub

	}
}
