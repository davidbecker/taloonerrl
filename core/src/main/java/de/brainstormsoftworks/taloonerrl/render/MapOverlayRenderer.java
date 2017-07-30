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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;

/**
 * utility class that renders a map overlay onto the screen
 *
 * @author David Becker
 *
 */
public final class MapOverlayRenderer {

	private static final MapOverlayRenderer instance = new MapOverlayRenderer();
	private int tilesHorizontal = 0;
	private int tilesVertical = 0;
	// reference to the map
	private IMap map = null;

	private Visible[][] overlay;

	private MapOverlayRenderer() {
	}

	/**
	 * getter for instance
	 *
	 * @return the instance
	 */
	public static MapOverlayRenderer getInstance() {
		return instance;
	}

	/**
	 * initializes this overlay renderer with the map that should be used for the
	 * rendering
	 *
	 * @param _map
	 */
	public void initialize(final IMap _map) {
		map = _map;
		tilesHorizontal = map.getTilesHorizontal();
		tilesVertical = map.getTilesVertical();
		overlay = new Visible[tilesHorizontal][tilesVertical];
		reset();
	}

	/**
	 * resets the overlay information to empty map
	 */
	public void reset() {
		for (int x = 0; x < tilesHorizontal; x++) {
			for (int y = 0; y < tilesVertical; y++) {
				overlay[x][y] = Visible.NOTHING;
			}
		}
	}

	/**
	 * renders a map overlay onto the screen
	 *
	 * @param map2
	 */
	public void render() {
		if (!Renderer.getInstance().isFullMapVisible()) {
			// TODO add FOV data as parameter
			// compute the offset to draw the overlay
			final float offsetX = (Gdx.graphics.getWidth() - tilesHorizontal) / Renderer.screenScale / 2;
			final float offsetY = (Gdx.graphics.getHeight() - tilesVertical) / Renderer.screenScale / 1.5f;
			TextureRegion tempOverlay = null;
			for (int x = 0; x < tilesHorizontal; x++) {
				for (int y = 0; y < tilesVertical; y++) {
					if (Visible.NOTHING.equals(overlay[x][y]) && map.getVisited()[x][y]
							&& map.isWalkable(x, y)) {
						tempOverlay = getOverlay(Visible.WALKABLE);
					} else {
						tempOverlay = getOverlay(overlay[x][y]);
					}
					if (tempOverlay != null) {
						Renderer.getInstance().renderOnScreen(tempOverlay, x + offsetX, y + offsetY);
					}
				}
			}
			// TODO run in background thread
			reset();
		}
	}

	private static TextureRegion getOverlay(final Visible v) {
		switch (v) {
		case WALKABLE:
			return PaletteUtil.getInstance().LIGHT_BLUE;
		case MONSTER:
			return PaletteUtil.getInstance().RED;
		case COLLECTIBLE:
			return PaletteUtil.getInstance().LIGHT_GREEN;
		case PLAYER:
			return PaletteUtil.getInstance().WHITE;
		case NOTHING:
		default:
			return null;
		}
	}

	/**
	 * sets the overlay of the given position
	 *
	 * @param x
	 *            horizontal coordinate
	 * @param y
	 *            vertical coordinate
	 * @param v
	 *            what should be rendered on the given position
	 */
	public void setOverlay(final int x, final int y, final Visible v) {
		if (map.isInMapBounds(x, y)) {
			// TODO change to enum set to be able to render multiple states on a
			// given tile?
			overlay[x][y] = v;
		}
	}

	/**
	 * helper class to distinguish the different things that could be rendered at a
	 * given position
	 *
	 * @author David Becker
	 *
	 */
	public static enum Visible {
		NOTHING, WALKABLE, COLLECTIBLE, MONSTER, PLAYER, STAIRS
	}
}
