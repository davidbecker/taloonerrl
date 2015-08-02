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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.TreeMultiset;

/**
 * manager class for events
 *
 * @author David Becker
 *
 */
public final class EventBus {
	private static final EventBus instance = new EventBus();
	// PriorityQueue<Event> events = new PriorityQueue<Event>();
	TreeMultiset<Event> events = TreeMultiset.create();
	Set<IEventHandler> eventHandlers = new HashSet<IEventHandler>();

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

	/**
	 * adds an event to the event queue
	 *
	 * @param event
	 */
	public void addEvent(final Event event) {
		events.add(event);
	}

	/**
	 * registers an eventhandler to be informed about events
	 *
	 * @param eventHandler
	 */
	public void registerEventHandler(final IEventHandler eventHandler) {
		eventHandlers.add(eventHandler);
	}

	/**
	 * removes an eventhandler from the list of handlers to inform about events
	 *
	 * @param eventHandler
	 */
	public void unregisterEventHandler(final IEventHandler eventHandler) {
		eventHandlers.remove(eventHandler);
	}

	/**
	 * gets the amount of events in the event queue(regardless of the start &
	 * end times of the events)
	 *
	 * @return size count
	 */
	public int size() {
		return events.size();
	}

	/**
	 * checks all events in the queue.<br/>
	 * if they are expired they are removed from the queue, if they are "alive"
	 * they are given to all registered event handlers
	 */
	public void processEvents() {
		final long currentTimeMillis = System.currentTimeMillis();
		if (events.isEmpty() || eventHandlers.isEmpty()) {
			// no need to bother if we don't have events or handler to handle
			// them
			return;
		}
		final Iterator<Event> iterator = events.iterator();
		while (iterator.hasNext()) {
			final Event event = iterator.next();
			if (event.getEventStartTime() <= currentTimeMillis) {
				// event has started
				if (event.getExpireTime() < currentTimeMillis) {
					// TODO is this the right way to do it, maybe move event to
					// an unused pool instead?
					iterator.remove();
				} else {
					for (final IEventHandler eventHandler : eventHandlers) {
						eventHandler.processEvent(event);
					}
				}
			}
		}
	}

}
