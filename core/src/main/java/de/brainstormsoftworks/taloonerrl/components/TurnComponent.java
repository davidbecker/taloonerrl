/*******************************************************************************
 * Copyright (c) 2017 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

import de.brainstormsoftworks.taloonerrl.core.engine.Direction;
import lombok.Getter;
import lombok.Setter;

/**
 * component that stores the next move for an actor
 *
 * @author David Becker
 *
 */
@Getter
@Setter
public class TurnComponent extends PooledComponent implements ISetAbleComponent<TurnComponent> {

	// TODO refactor into a proper turn queue for each component?
	private int currentTurn = Direction.NOTHING;
	private int nextTurn = Direction.NOTHING;

	@Override
	protected void reset() {
		currentTurn = Direction.NOTHING;
		nextTurn = Direction.NOTHING;
	}

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final TurnComponent _component) {
		currentTurn = _component.getCurrentTurn();
		nextTurn = _component.getNextTurn();
	}

	public int nextTurn() {
		return nextTurn(Direction.NOTHING);
	}

	/**
	 * advances the current turn to the next turn. sets following turn in the
	 * process
	 *
	 * @param direction
	 * @return
	 */
	public int nextTurn(final int direction) {
		currentTurn = nextTurn;
		nextTurn = direction;
		return currentTurn;
	}

}
