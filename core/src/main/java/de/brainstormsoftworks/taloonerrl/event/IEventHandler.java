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
 * interface for event handler to implement
 *
 * @author David Becker
 *
 */
public interface IEventHandler {
	/**
	 * this method receives all events that are processed by the
	 * {@link EventBus}, there is no filtering currently in place and all
	 * {@link IEventHandler} should check for themselves if they should process
	 * the event
	 *
	 * @param event
	 *            to process by this handler
	 */
	void processEvent(Event event);
}
