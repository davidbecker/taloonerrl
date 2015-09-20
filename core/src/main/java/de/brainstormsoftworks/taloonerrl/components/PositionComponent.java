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
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

/**
 * component to store the position of an entity
 *
 * @author David Becker
 *
 */
public class PositionComponent extends PooledComponent {

	private int x = 0;
	private int y = 0;

	@Override
	protected void reset() {
		x = 0;
		y = 0;
	}

	/**
	 * getter for x
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * setter for x
	 *
	 * @param _x
	 *            the x to set
	 */
	public void setX(final int _x) {
		x = _x;
	}

	/**
	 * getter for y
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * setter for y
	 *
	 * @param _y
	 *            the y to set
	 */
	public void setY(final int _y) {
		y = _y;
	}

}
