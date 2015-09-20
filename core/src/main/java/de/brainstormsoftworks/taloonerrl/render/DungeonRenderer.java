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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;

public final class DungeonRenderer implements IDisposableInstance {

	private static DungeonRenderer instance = null;

	private final Texture floorTexture;
	private final Texture wallTexture;

	private final TextureRegion sFloorTopLeft;
	private final TextureRegion sFloorTop;
	private final TextureRegion sFloorTopRight;
	private final TextureRegion sFloorLeft;
	private final TextureRegion sFloorCenter;
	private final TextureRegion sFloorRight;
	private final TextureRegion sFloorBottomLeft;
	private final TextureRegion sFloorBottom;
	private final TextureRegion sFloorBottomRight;
	private final TextureRegion sFloorTopLeftOutside;
	private final TextureRegion sFloorTopRightOutside;
	private final TextureRegion sFloorBottomLeftOutside;
	private final TextureRegion sFloorBottomRightOutside;
	private final TextureRegion sFloorShadowTopLeft;
	private final TextureRegion sFloorShadowTop;
	private final TextureRegion sFloorShadowTopRight;
	private final TextureRegion sFloorShadowLeft;
	private final TextureRegion sFloorShadowCenter;
	private final TextureRegion sFloorShadowRight;
	private final TextureRegion sFloorShadowBottomLeft;
	private final TextureRegion sFloorShadowBottom;
	private final TextureRegion sFloorShadowBottomRight;
	private final TextureRegion sFloorShadowTopLeftOutside;
	private final TextureRegion sFloorShadowTopRightOutside;
	private final TextureRegion sFloorShadowBottomLeftOutside;
	private final TextureRegion sFloorShadowBottomRightOutside;
	private final TextureRegion sWallShadowTopLeft;
	private final TextureRegion sWallShadowHorizontal;
	private final TextureRegion sWallShadowTopRight;
	private final TextureRegion sWallShadowVertical;
	private final TextureRegion sWallShadowBottomLeft;
	private final TextureRegion sWallShadowBottomRight;
	private final TextureRegion sWallShadowPillar;
	private final TextureRegion sWallShadowFull;
	private final TextureRegion sWallShadowCrossSection;
	private final TextureRegion sWallShadowTsectionNorth;
	private final TextureRegion sWallShadowTsectionSouth;
	private final TextureRegion sWallShadowTsectionEast;
	private final TextureRegion sWallShadowTsectionWest;
	private final TextureRegion sWallShadowEndEast;
	private final TextureRegion sWallShadowEndWest;
	private final TextureRegion sWallTopLeft;
	private final TextureRegion sWallHorizontal;
	private final TextureRegion sWallTopRight;
	private final TextureRegion sWallVertical;
	private final TextureRegion sWallBottomLeft;
	private final TextureRegion sWallBottomRight;
	private final TextureRegion sWallPillar;
	private final TextureRegion sWallFull;
	private final TextureRegion sWallCrossSection;
	private final TextureRegion sWallTsectionNorth;
	private final TextureRegion sWallTsectionSouth;
	private final TextureRegion sWallTsectionEast;
	private final TextureRegion sWallTsectionWest;
	private final TextureRegion sWallEndEast;
	private final TextureRegion sWallEndWest;

	private static final String TEXTURE_PATH = "textures/dungeon/";

	private DungeonRenderer() {
		floorTexture = new Texture(Gdx.files.internal(TEXTURE_PATH + "Floor02.png"), false);
		floorTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		floorTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		wallTexture = new Texture(Gdx.files.internal(TEXTURE_PATH + "Wall.png"), false);
		wallTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		wallTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);

		int floorOffsetY = 0;
		sFloorTopLeft = new TextureRegion(floorTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorTop = new TextureRegion(floorTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorTopRight = new TextureRegion(floorTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorLeft = new TextureRegion(floorTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorCenter = new TextureRegion(floorTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorRight = new TextureRegion(floorTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorBottomLeft = new TextureRegion(floorTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorBottom = new TextureRegion(floorTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorBottomRight = new TextureRegion(floorTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorBottomRightOutside = new TextureRegion(floorTexture, 6 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorTopRightOutside = new TextureRegion(floorTexture, 6 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorBottomLeftOutside = new TextureRegion(floorTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorTopLeftOutside = new TextureRegion(floorTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallTopLeft = new TextureRegion(wallTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallHorizontal = new TextureRegion(wallTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallTopRight = new TextureRegion(wallTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallVertical = new TextureRegion(wallTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallBottomRight = new TextureRegion(wallTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallBottomLeft = new TextureRegion(wallTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallPillar = new TextureRegion(wallTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallCrossSection = new TextureRegion(wallTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallTsectionNorth = new TextureRegion(wallTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallTsectionSouth = new TextureRegion(wallTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallTsectionEast = new TextureRegion(wallTexture, 5 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallTsectionWest = new TextureRegion(wallTexture, 3 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallEndEast = new TextureRegion(wallTexture, 5 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallEndWest = new TextureRegion(wallTexture, 3 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallFull = new TextureRegion(wallTexture, 3 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		floorOffsetY = 6;
		sFloorShadowTopLeft = new TextureRegion(floorTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowTop = new TextureRegion(floorTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowTopRight = new TextureRegion(floorTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowLeft = new TextureRegion(floorTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowCenter = new TextureRegion(floorTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowRight = new TextureRegion(floorTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowBottomLeft = new TextureRegion(floorTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowBottom = new TextureRegion(floorTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowBottomRight = new TextureRegion(floorTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowBottomRightOutside = new TextureRegion(floorTexture, 6 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowTopRightOutside = new TextureRegion(floorTexture, 6 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowBottomLeftOutside = new TextureRegion(floorTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sFloorShadowTopLeftOutside = new TextureRegion(floorTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);

		sWallShadowTopLeft = new TextureRegion(wallTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowHorizontal = new TextureRegion(wallTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowTopRight = new TextureRegion(wallTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowVertical = new TextureRegion(wallTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowBottomRight = new TextureRegion(wallTexture, 2 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowBottomLeft = new TextureRegion(wallTexture, 0 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowPillar = new TextureRegion(wallTexture, 1 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowCrossSection = new TextureRegion(wallTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowTsectionNorth = new TextureRegion(wallTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowTsectionSouth = new TextureRegion(wallTexture, 4 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowTsectionEast = new TextureRegion(wallTexture, 5 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowTsectionWest = new TextureRegion(wallTexture, 3 * Renderer.tileSize,
				(floorOffsetY + 4) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowEndEast = new TextureRegion(wallTexture, 5 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowEndWest = new TextureRegion(wallTexture, 3 * Renderer.tileSize,
				(floorOffsetY + 5) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);
		sWallShadowFull = new TextureRegion(wallTexture, 3 * Renderer.tileSize,
				(floorOffsetY + 3) * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize);

		RenderUtil.addToDisposeList(this);
	}

	/**
	 * you <b>must</b> call {@link #disposeInstance} when you are done!
	 */
	public static void initInstance() {
		instance = new DungeonRenderer();
	}

	/**
	 * get an instance of this singleton
	 *
	 * @return {@link #instance}
	 */
	public static DungeonRenderer getInstance() {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public void disposeInstance() {
		floorTexture.dispose();
		wallTexture.dispose();
	}

	public void render(final IMap map) {
		// TODO render map into framebuffer and use buffer if player hasn't
		// moved
		final EDungeonSprites[][] spriteArray = map.getDungeonSprites();
		final boolean[][] visitedArray = map.getVisited();
		for (int x = 0; x < map.getTilesHorizontal(); x++) {
			for (int y = 0; y < map.getTilesVertical(); y++) {
				if (FovWrapper.getInstance().isLit(x, y)) {
					final TextureRegion tile = getTile(spriteArray[x][y]);
					if (tile != null) {
						Renderer.getInstance().renderOnTile(tile, x, y);
					}
					visitedArray[x][y] = true;
				} else if (visitedArray[x][y]) {
					// add "darker" textures
					final TextureRegion tile = getTile(spriteArray[x][y], true);
					if (tile != null) {
						Renderer.getInstance().renderOnTile(tile, x, y);
					}
				}
			}
		}
	}

	private TextureRegion getTile(final EDungeonSprites sprite) {
		return getTile(sprite, false);
	}

	private TextureRegion getTile(final EDungeonSprites sprite, final boolean visited) {
		switch (sprite) {
		case FLOOR_BOTTOM:
			return visited ? sFloorShadowBottom : sFloorBottom;
		case FLOOR_BOTTOMLEFT_CORNER:
			return visited ? sFloorShadowBottomLeft : sFloorBottomLeft;
		case FLOOR_BOTTOMRIGHT_CORNER:
			return visited ? sFloorShadowBottomRight : sFloorBottomRight;
		case FLOOR_CENTER:
			return visited ? sFloorShadowCenter : sFloorCenter;
		case FLOOR_LEFT:
			return visited ? sFloorShadowLeft : sFloorLeft;
		case FLOOR_RIGHT:
			return visited ? sFloorShadowRight : sFloorRight;
		case FLOOR_TOP:
			return visited ? sFloorShadowTop : sFloorTop;
		case FLOOR_TOPLEFT_CORNER:
			return visited ? sFloorShadowTopLeft : sFloorTopLeft;
		case FLOOR_TOPRIGHT_CORNER:
			return visited ? sFloorShadowTopRight : sFloorTopRight;
		case FLOOR_BOTTOMLEFT_CORNER_OUTSIDE:
			return visited ? sFloorShadowBottomLeftOutside : sFloorBottomLeftOutside;
		case FLOOR_BOTTOMRIGHT_CORNER_OUTSIDE:
			return visited ? sFloorShadowBottomRightOutside : sFloorBottomRightOutside;
		case FLOOR_TOPLEFT_CORNER_OUTSIDE:
			return visited ? sFloorShadowTopLeftOutside : sFloorTopLeftOutside;
		case FLOOR_TOPRIGHT_CORNER_OUTSIDE:
			return visited ? sFloorShadowTopRightOutside : sFloorTopRightOutside;
		case WALL_TOPLEFT_CORNER:
			return visited ? sWallShadowTopLeft : sWallTopLeft;
		case WALL_HORIZONTAL:
			return visited ? sWallShadowHorizontal : sWallHorizontal;
		case WALL_TOPRIGHT_CORNER:
			return visited ? sWallShadowTopRight : sWallTopRight;
		case WALL_VERTICAL:
			return visited ? sWallShadowVertical : sWallVertical;
		case WALL_BOTTOMLEFT_CORNER:
			return visited ? sWallShadowBottomLeft : sWallBottomLeft;
		case WALL_BOTTOMRIGHT_CORNER:
			return visited ? sWallShadowBottomRight : sWallBottomRight;
		case WALL_PILLAR:
			return visited ? sWallShadowPillar : sWallPillar;
		case WALL_CROSSSECTION:
			return visited ? sWallShadowCrossSection : sWallCrossSection;
		case WALL_TSECTION_NORTH:
			return visited ? sWallShadowTsectionNorth : sWallTsectionNorth;
		case WALL_TSECTION_SOUTH:
			return visited ? sWallShadowTsectionSouth : sWallTsectionSouth;
		case WALL_TSECTION_EAST:
			return visited ? sWallShadowTsectionEast : sWallTsectionEast;
		case WALL_TSECTION_WEST:
			return visited ? sWallShadowTsectionWest : sWallTsectionWest;
		case WALL_END_NORT:
			return visited ? sWallShadowVertical : sWallVertical;
		case WALL_END_SOUTH:
			return visited ? sWallShadowPillar : sWallPillar;
		case WALL_END_EAST:
			return visited ? sWallShadowEndEast : sWallEndEast;
		case WALL_END_WEST:
			return visited ? sWallShadowEndWest : sWallEndWest;
		case WALL_FULL:
			return visited ? sWallShadowFull : sWallFull;
		case NOTHING:
			// same as default for now
		default:
			// keep tile blank
			return null;
		}
	}

}
