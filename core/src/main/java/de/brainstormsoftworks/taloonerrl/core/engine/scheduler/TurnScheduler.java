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

import java.util.Deque;

import de.brainstormsoftworks.taloonerrl.core.engine.Direction;

/**
 * class to shedule turns for the player
 *
 * @author David Becker
 *
 */
public final class TurnScheduler {

	// maybe instead of hardcoding a limit we should make a resize method? see
	// IntArray
	public static final int QUEUE_LENGTH = 99;

	private final int[] queue = new int[QUEUE_LENGTH];
	/** points to the next turn */
	private int pointer = 0;

	private static final TurnScheduler instance = new TurnScheduler();

	/**
	 * getter for the instance of the singleton
	 *
	 * @return instance
	 */
	public static TurnScheduler getInstance() {
		return instance;
	}

	/**
	 * gets the next turn for the player
	 *
	 * @see Deque#pollFirst()
	 * @return
	 */
	public int getNextTurn() {
		if (pointer < QUEUE_LENGTH) {
			return queue[pointer++];
		}
		return Direction.NOTHING;
	}

	/**
	 * removes all turns from queue
	 */
	public void clearQueue() {
		// it should be enough to reset the pointer and not set all positions
		// from 0 -> current pointer to Direction.NOTHING
		pointer = 0;
	}

	/**
	 * check if turns are left in the queue
	 *
	 * @return <code>true</code> if queue is empty, false otherwise
	 */
	public boolean isQueueEmpty() {
		return pointer == 0 || queue[pointer] == Direction.NOTHING;
	}

	/**
	 * add turns to queue
	 *
	 * @param turnsToAdd
	 */
	public void addTurnsToQueue(final int[] turnsToAdd) {
		clearQueue();
		int length = turnsToAdd.length;
		final boolean overLimit = length > QUEUE_LENGTH;
		if (overLimit) {
			// refactor to perform a resize of the array?
			length = QUEUE_LENGTH;
		}
		System.arraycopy(turnsToAdd, 0, queue, 0, length);
		if (!overLimit) {
			queue[length] = Direction.NOTHING;
		}
	}

}
