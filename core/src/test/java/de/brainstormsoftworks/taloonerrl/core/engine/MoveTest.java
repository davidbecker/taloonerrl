/*******************************************************************************
 * Copyright (c) 2015 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.core.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoveTest {

	@Test
	public void testRandomDirection() {
		int nextTurn;
		for (int i = 0; i < 1000; i++) {
			nextTurn = Move.randomDirection();
			assertEquals(true, nextTurn == Move.UP || nextTurn == Move.DOWN || nextTurn == Move.LEFT
					|| nextTurn == Move.RIGHT);
			assertEquals(true, nextTurn != Move.WAIT && nextTurn != Move.IDLE);
		}
	}
}
