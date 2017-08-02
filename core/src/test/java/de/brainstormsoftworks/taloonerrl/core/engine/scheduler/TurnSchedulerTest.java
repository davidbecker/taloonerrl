/*******************************************************************************
 * Copyright (c) 2016 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/

package de.brainstormsoftworks.taloonerrl.core.engine.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.brainstormsoftworks.taloonerrl.core.engine.Move;

/**
 * @author David Becker
 *
 */
public class TurnSchedulerTest {

	private static final TurnScheduler scheduler = TurnScheduler.getInstance();

	@Test
	public void testNormalTurns() {
		final int[] turns = new int[4];
		turns[0] = Move.DOWN;
		turns[1] = Move.LEFT;
		turns[2] = Move.RIGHT;
		turns[3] = Move.UP;
		scheduler.addTurnsToQueue(turns);
		assertEquals(Move.DOWN, scheduler.getNextTurn());
		assertEquals(Move.LEFT, scheduler.getNextTurn());
		assertEquals(Move.RIGHT, scheduler.getNextTurn());
		assertEquals(Move.UP, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());

		// test that "reset" works as expected
		final int[] turns2 = new int[2];
		turns2[0] = Move.DOWN;
		turns2[1] = Move.LEFT;
		assertTrue(scheduler.isQueueEmpty());
		scheduler.addTurnsToQueue(turns2);
		assertEquals(Move.DOWN, scheduler.getNextTurn());
		assertFalse(scheduler.isQueueEmpty());
		assertEquals(Move.LEFT, scheduler.getNextTurn());
		assertTrue(scheduler.isQueueEmpty());
		assertEquals(Move.IDLE, scheduler.getNextTurn());

		// test single turn
		scheduler.addTurnToQueue(Move.LEFT);
		assertEquals(Move.LEFT, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());
		assertEquals(Move.IDLE, scheduler.getNextTurn());
	}

	@Test
	public void overLimit() {
		final int length = TurnScheduler.QUEUE_LENGTH + 1;
		final int[] turns = new int[length];
		for (int i = 0; i < length; i++) {
			turns[i] = Move.DOWN;
		}
		scheduler.addTurnsToQueue(turns);
		for (int i = 0; i < length - 1; i++) {
			assertEquals(Move.DOWN, scheduler.getNextTurn());
		}
		assertEquals(Move.IDLE, scheduler.getNextTurn());
	}

}
