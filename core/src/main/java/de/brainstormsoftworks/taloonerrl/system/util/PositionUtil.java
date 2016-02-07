/*******************************************************************************
 * Copyright (c) 2016 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system.util;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * collection of utility methods related to positons
 *
 * @author David Becker
 *
 */
public final class PositionUtil {

	private PositionUtil() {
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 * @throws NullPointerException
	 *             when position is null
	 */
	public static final int getDeltaX(final PositionComponent position) {
		return position.getTotalX() == Renderer.tileSize ? 1
				: position.getTotalX() == -1 * Renderer.tileSize ? -1 : 0;
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 * @throws NullPointerException
	 *             when position is null
	 */
	public static final int getDeltaY(final PositionComponent position) {
		return position.getTotalY() == Renderer.tileSize ? 1
				: position.getTotalY() == -1 * Renderer.tileSize ? -1 : 0;
	}

	public static final boolean isMovingWholeTile(final int deltaX, final int deltaY) {
		return deltaX != 0 || deltaY != 0;
	}
}
