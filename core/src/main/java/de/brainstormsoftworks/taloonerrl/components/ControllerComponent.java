package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

import de.brainstormsoftworks.taloonerrl.math.IntVector2;

public class ControllerComponent extends PooledComponent {
	private final IntVector2 moveVector = new IntVector2(0, 0);

	@Override
	protected void reset() {
		moveVector.setZero();
	}

	/**
	 * getter for moveVector
	 *
	 * @return the moveVector
	 */
	public final IntVector2 getMoveVector() {
		return moveVector;
	}

}
