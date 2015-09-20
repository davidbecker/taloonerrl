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
package de.brainstormsoftworks.taloonerrl.render;

import de.brainstormsoftworks.taloonerrl.math.ArrayHelper;
import squidpony.squidgrid.FOV;
import squidpony.squidgrid.Radius;

/**
 * this singleton know the current state of the field of view of the player
 *
 * @author David Becker
 *
 */
public final class FovWrapper {

	private static final FovWrapper instance = new FovWrapper();

	private final FOV fov = new FOV(FOV.SHADOW);
	private double[][] fovResistance;

	private FovWrapper() {
	}

	/**
	 * sets the position for which the fov should be calculated
	 *
	 * @param x
	 *            horizontal tile position
	 * @param y
	 *            vertical tile position
	 */
	public void calculateFovForPosition(final int x, final int y) {
		if (ArrayHelper.isInArrayBounds(fovResistance, x, y)) {
			fov.calculateFOV(fovResistance, x, y, 8, Radius.DIAMOND);
		}
	}

	/**
	 * checks if the given position is visible
	 *
	 * @param x
	 *            horizontal tile position
	 * @param y
	 *            vertical tile position
	 * @return true if position is visible, false otherwise
	 */
	public boolean isLit(final int x, final int y) {
		if (ArrayHelper.isInArrayBounds(fovResistance, x, y)) {
			return fov.isLit(x, y);
		}
		return false;
	}

	/**
	 * sets the resistance map that is used for fov calculation
	 *
	 * @param _fovResistance
	 *            the fovResistance to set
	 */
	public void setFovResistance(final double[][] _fovResistance) {
		fovResistance = _fovResistance;
	}

	/**
	 * getter for {@link #instance}
	 *
	 * @return the instance
	 */
	public static FovWrapper getInstance() {
		return instance;
	}

}
