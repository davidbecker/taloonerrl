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
 * manager class for events
 *
 * @author David Becker
 *
 */
public final class EventBus {
	private static final EventBus instance = new EventBus();
	// Set<Event> events = new SynchronizedSortedSet<Event>();

	/**
	 * constructor.<br/>
	 * private on purpose
	 */
	private EventBus() {
	}

	/**
	 * getter for an instance
	 *
	 * @return {@link #instance}
	 */
	public static EventBus getInstance() {
		return instance;
	}

}
