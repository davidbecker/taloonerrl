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
package de.brainstormsoftworks.taloonerrl.math;

/**
 * utility class to hold helper methods for primitive arrays
 *
 * @author David Becker
 *
 */
public final class ArrayHelper {
	private ArrayHelper() {
	}

	/**
	 * checks if the given positions are in the bounds of an array
	 *
	 * @param array
	 *            to check against
	 * @param x
	 *            horizontal position to check
	 * @param y
	 *            vertical position to check
	 * @return true if (x, y) is in the bounds of the array
	 */
	public static boolean isInArrayBounds(final int[][] array, final int x, final int y) {
		final int width = array.length;
		int height = 0;
		if (width > 0) {
			height = array[0].length;
		}
		return isInBounds(x, y, width, height);
	}

	/**
	 * checks if the given positions are in the bounds of an array
	 *
	 * @param array
	 *            to check against
	 * @param x
	 *            horizontal position to check
	 * @param y
	 *            vertical position to check
	 * @return true if (x, y) is in the bounds of the array
	 */
	public static boolean isInArrayBounds(final char[][] array, final int x, final int y) {
		final int width = array.length;
		int height = 0;
		if (width > 0) {
			height = array[0].length;
		}
		return isInBounds(x, y, width, height);
	}

	/**
	 * checks if the given positions are in the bounds of an array
	 *
	 * @param array
	 *            to check against
	 * @param x
	 *            horizontal position to check
	 * @param y
	 *            vertical position to check
	 * @return true if (x, y) is in the bounds of the array
	 */
	public static boolean isInArrayBounds(final double[][] array, final int x, final int y) {
		if (array == null) {
			return false;
		}
		final int width = array.length;
		int height = 0;
		if (width > 0) {
			height = array[0].length;
		}
		return isInBounds(x, y, width, height);
	}

	/**
	 * checks if the given positions are in the bounds of an array
	 *
	 * @param array
	 *            to check against
	 * @param x
	 *            horizontal position to check
	 * @param y
	 *            vertical position to check
	 * @return true if (x, y) is in the bounds of the array
	 */
	public static boolean isInArrayBounds(final boolean[][] array, final int x, final int y) {
		final int width = array.length;
		int height = 0;
		if (width > 0) {
			height = array[0].length;
		}
		return isInBounds(x, y, width, height);
	}

	/**
	 * helper method to hold the "math" part
	 *
	 * @param x
	 *            horizontal position to check
	 * @param y
	 *            vertical position to check
	 * @param width
	 *            of the array
	 * @param height
	 *            of the array
	 * @return true if (x, y) is in the bounds of the array
	 */
	private static boolean isInBounds(final int x, final int y, final int width, final int height) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return false;
		}
		return true;
	}
}
