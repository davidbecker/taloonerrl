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
 * TODO implement use
 *
 * @author David Becker
 *
 */
public class ControllerComponent extends PooledComponent {
	private int dX = 0;
	private int dY = 0;

	@Override
	public void reset() {
		dX = 0;
		dY = 0;
	}

	/**
	 * getter for {@link #dX}
	 *
	 * @return the dX
	 */
	public final int getdX() {
		return dX;
	}

	/**
	 * setter for dX
	 *
	 * @param _dX
	 *            the dX to set
	 */
	public final void setdX(final int _dX) {
		dX = _dX;
	}

	/**
	 * getter for {@link #dY}
	 *
	 * @return the dY
	 */
	public final int getdY() {
		return dY;
	}

	/**
	 * setter for dY
	 *
	 * @param _dY
	 *            the dY to set
	 */
	public final void setdY(final int _dY) {
		dY = _dY;
	}

}
