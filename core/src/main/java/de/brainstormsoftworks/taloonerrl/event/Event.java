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

/**
 * simple information holder for events. stores the start & end times for an
 * event
 *
 * @author David Becker
 *
 */
public class Event implements Comparable<Event> {

	/** time that the event starts to be active */
	private final long eventStartTime;
	/** the duration that the event is active */
	private final long delay;
	/** the time when the event is considered to be over */
	private final long expireTime;
	// TODO implement storing of event information (what to do, what listener
	// types to inform)

	/**
	 * constructor
	 *
	 * @param _eventStartTime
	 *            when the event should be started
	 * @param _delay
	 *            duration of the event
	 */
	public Event(final long _eventStartTime, final long _delay) {
		eventStartTime = _eventStartTime;
		delay = _delay;
		expireTime = _eventStartTime + _delay;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(final Event other) {
		return Long.compare(getEventStartTime(), other.getEventStartTime());
	}

	/**
	 * getter for {@link #eventStartTime}
	 *
	 * @return the eventStartTime
	 */
	public final long getEventStartTime() {
		return eventStartTime;
	}

	/**
	 * getter for {@link #delay}
	 *
	 * @return the delay
	 */
	public final long getDelay() {
		return delay;
	}

	/**
	 * getter for {@link #expireTime}
	 *
	 * @return the expireTime
	 */
	public final long getExpireTime() {
		return expireTime;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (delay ^ delay >>> 32);
		result = prime * result + (int) (eventStartTime ^ eventStartTime >>> 32);
		result = prime * result + (int) (expireTime ^ expireTime >>> 32);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Event other = (Event) obj;
		if (delay != other.delay) {
			return false;
		}
		if (eventStartTime != other.eventStartTime) {
			return false;
		}
		if (expireTime != other.expireTime) {
			return false;
		}
		return true;
	}

}
