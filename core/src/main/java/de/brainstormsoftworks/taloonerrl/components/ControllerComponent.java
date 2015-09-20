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
 * component for an entity that can get input<br/>
 * is ment to be used as an offset for {@link PositionComponent}
 *
 * @author David Becker
 *
 */
public class ControllerComponent extends PooledComponent {
	// the offset this entity has from its current position
	private int offsetX = 0;
	private int offsetY = 0;

	// amount of offset that this entity will be moving
	private int totalX = 0;
	private int totalY = 0;

	@Override
	public void reset() {
		offsetX = 0;
		offsetY = 0;
		totalX = 0;
		totalY = 0;
	}

	/**
	 * getter for offsetX
	 *
	 * @return the offsetX
	 */
	public final int getOffsetX() {
		return offsetX;
	}

	/**
	 * setter for offsetX
	 *
	 * @param _offsetX
	 *            the offsetX to set
	 */
	public final void setOffsetX(final int _offsetX) {
		offsetX = _offsetX;
	}

	/**
	 * getter for offsetY
	 *
	 * @return the offsetY
	 */
	public final int getOffsetY() {
		return offsetY;
	}

	/**
	 * setter for offsetY
	 *
	 * @param _offsetY
	 *            the offsetY to set
	 */
	public final void setOffsetY(final int _offsetY) {
		offsetY = _offsetY;
	}

	/**
	 * getter for totalX
	 *
	 * @return the totalX
	 */
	public final int getTotalX() {
		return totalX;
	}

	/**
	 * setter for totalX
	 *
	 * @param _totalX
	 *            the totalX to set
	 */
	public final void setTotalX(final int _totalX) {
		totalX = _totalX;
	}

	/**
	 * getter for totalY
	 *
	 * @return the totalY
	 */
	public final int getTotalY() {
		return totalY;
	}

	/**
	 * setter for totalY
	 *
	 * @param _totalY
	 *            the totalY to set
	 */
	public final void setTotalY(final int _totalY) {
		totalY = _totalY;
	}

}
