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

import java.util.HashMap;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.IMapChangeListener;
import de.brainstormsoftworks.taloonerrl.dungeon.MapChangeProvider;
import de.brainstormsoftworks.taloonerrl.math.ArrayHelper;
import squidpony.squidgrid.FOV;
import squidpony.squidgrid.Radius;
import squidpony.squidmath.Coord;

/**
 * this singleton know the current state of the field of view for the player.
 * <br/>
 * it is a wrapper around {@link FOV}
 *
 * @author David Becker
 *
 */
public final class FovWrapper implements IMapChangeListener {

	private static final int FOV_TYPE = FOV.SHADOW;
	private static final int FOV_RADIUS = 8;
	private static final Radius RADIUS_TECHNIQUE = Radius.DIAMOND;

	private static final FovWrapper instance = new FovWrapper();

	private final HashMap<Coord, double[][]> storedFovMaps = new HashMap<>();
	private final FOV fov = new FOV(FOV_TYPE);

	private double[][] fovResistance;
	private double[][] lightMap;

	private FovWrapper() {
		MapChangeProvider.getInstance().registerListener(this);
	}

	/**
	 * sets the position for which the field of view should be calculated
	 *
	 * @param x
	 *            horizontal tile position
	 * @param y
	 *            vertical tile position
	 */
	public void calculateFovForPosition(final int x, final int y) {
		final Coord c = Coord.get(x, y);
		if (storedFovMaps.containsKey(c)) {
			lightMap = storedFovMaps.get(c);
		} else {
			if (ArrayHelper.isInArrayBounds(fovResistance, x, y)) {
				lightMap = fov.calculateFOV(fovResistance, x, y, FOV_RADIUS, RADIUS_TECHNIQUE);
				storedFovMaps.put(c, lightMap);
			}
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
		return ArrayHelper.isInArrayBounds(lightMap, x, y) && lightMap[x][y] > 0;
	}

	/**
	 * getter for {@link #instance}
	 *
	 * @return the instance
	 */
	public static FovWrapper getInstance() {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public void setMap(final IMap map) {
		fovResistance = map != null ? map.getFovResistance() : null;
		storedFovMaps.clear();
	}

}
