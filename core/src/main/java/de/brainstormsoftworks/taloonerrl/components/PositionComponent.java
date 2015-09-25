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
 * component to store the position, offset of the current position and delta to
 * move for an entity
 *
 * @author David Becker
 *
 */
public class PositionComponent extends PooledComponent {

	private int positionX = 0;
	private int positionY = 0;

	// the offset this entity has from its current position
	private int offsetX = 0;
	private int offsetY = 0;

	// amount of offset that this entity will be moving
	private int totalX = 0;
	private int totalY = 0;

	@Override
	protected void reset() {
		positionX = 0;
		positionY = 0;
		offsetX = 0;
		offsetY = 0;
		totalX = 0;
		totalY = 0;
	}

	/**
	 * getter for x
	 *
	 * @return the x
	 */
	public int getX() {
		return positionX;
	}

	/**
	 * setter for x
	 *
	 * @param _x
	 *            the x to set
	 */
	public void setX(final int _x) {
		positionX = _x;
	}

	/**
	 * getter for y
	 *
	 * @return the y
	 */
	public int getY() {
		return positionY;
	}

	/**
	 * setter for y
	 *
	 * @param _y
	 *            the y to set
	 */
	public void setY(final int _y) {
		positionY = _y;
	}

	/**
	 * getter for positionX
	 *
	 * @return the positionX
	 */
	public final int getPositionX() {
		return positionX;
	}

	/**
	 * setter for positionX
	 *
	 * @param _positionX
	 *            the positionX to set
	 */
	public final void setPositionX(final int _positionX) {
		positionX = _positionX;
	}

	/**
	 * getter for positionY
	 *
	 * @return the positionY
	 */
	public final int getPositionY() {
		return positionY;
	}

	/**
	 * setter for positionY
	 *
	 * @param _positionY
	 *            the positionY to set
	 */
	public final void setPositionY(final int _positionY) {
		positionY = _positionY;
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
