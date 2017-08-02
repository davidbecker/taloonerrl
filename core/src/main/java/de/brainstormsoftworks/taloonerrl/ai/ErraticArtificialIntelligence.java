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
package de.brainstormsoftworks.taloonerrl.ai;

import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import squidpony.squidmath.RNG;

/**
 * basic erratic intelligence for monster. will move to random tile
 *
 * @author David Becker
 *
 */
public class ErraticArtificialIntelligence implements IArtificialIntelligence {
	private static final RNG rng = new RNG();

	@Override
	public int nextTurn() {
		switch (rng.nextInt() % 5) {
		case 0:
			return Move.UP;
		case 1:
			return Move.DOWN;
		case 2:
			return Move.LEFT;
		case 3:
			return Move.RIGHT;
		case 4:
			return Move.WAIT;
		default:
			return Move.IDLE;
		}
	}

}
