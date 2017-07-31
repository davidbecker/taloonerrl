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
package de.brainstormsoftworks.taloonerrl.dungeon;

/**
 * can manages {@link IMapChangeListener}
 *
 * @author David Becker
 *
 */
public interface IMapChangeProvider {

	/**
	 * adds the given listener to be added to the list of registered listeners
	 *
	 * @param listener
	 *            listener to register
	 */
	void registerListener(IMapChangeListener listener);

	/**
	 * unregisters the given listener
	 *
	 * @param listener
	 *            listerer to remove
	 */
	void removeListener(IMapChangeListener listener);

	/**
	 * propagate the given map to the listeners
	 *
	 * @param map
	 *            map to set
	 */
	void propagateMap(IMap map);
}
