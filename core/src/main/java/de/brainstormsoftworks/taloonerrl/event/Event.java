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
package de.brainstormsoftworks.taloonerrl.event;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Event implements Delayed {

	private final long queueInsertTime;
	private final long delay;
	// TODO implement storing of event information (what to do, what listener
	// types to inform)

	public Event(final long queueInsertTime, final long delay) {
		this.queueInsertTime = queueInsertTime;
		this.delay = delay;
	}

	@Override
	public int compareTo(final Delayed arg0) {
		if (!(arg0 instanceof Event)) {
			return 0;
		}
		final Event other = (Event) arg0;
		return new Long(getQueueInsertTime() + getDelay()).compareTo(other.getQueueInsertTime() + other.getDelay());
	}

	@Override
	public long getDelay(final TimeUnit unit) {
		return unit.convert((getQueueInsertTime() - System.currentTimeMillis()) + getDelay(), TimeUnit.MILLISECONDS);
	}

	/**
	 * getter for queueInsertTime
	 *
	 * @return the queueInsertTime
	 */
	public final long getQueueInsertTime() {
		return queueInsertTime;
	}

	/**
	 * getter for delay
	 *
	 * @return the delay
	 */
	public final long getDelay() {
		return delay;
	}

}
