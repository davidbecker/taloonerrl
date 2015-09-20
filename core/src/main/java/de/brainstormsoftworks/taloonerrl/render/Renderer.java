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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * singleton to keep track of on globally accessible {@link SpriteBatch}
 *
 * @author David Becker
 *
 */
public final class Renderer implements IDisposableInstance {

	public static final float screenScale = 2.0f;
	public static final float scale = 16f;
	public static final int tileSize = 16;
	public static final int TILES_HORIZONTAL = 30;
	public static final int TILES_VERTICAL = 20;
	public static final int VIRTUAL_WIDTH = TILES_HORIZONTAL * tileSize + 4 * tileSize;
	public static final int VIRTUAL_HEIGHT = TILES_VERTICAL * tileSize;
	public static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

	private static final Renderer instance = new Renderer();

	private final OrthographicCamera cameraWorld;
	private final SpriteBatch spriteBatchWorld;
	private final SpriteBatch spriteBatchScreen;
	private final ScreenViewport worldViewport;
	ScreenViewport screenViewport;

	private Renderer() {
		spriteBatchWorld = new SpriteBatch();
		spriteBatchWorld.enableBlending();
		spriteBatchScreen = new SpriteBatch();
		cameraWorld = new OrthographicCamera();
		// cameraWorld.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		worldViewport = new ScreenViewport(cameraWorld);
		screenViewport = new ScreenViewport();
		RenderUtil.addToDisposeList(this);
	}

	public static Renderer getInstance() {
		return instance;
	}

	public void resizeViewPort(final int width, final int height) {
		worldViewport.update(width, height);
		spriteBatchWorld.setProjectionMatrix(cameraWorld.combined);
		screenViewport.update(width, height, true);
		spriteBatchScreen
				.setProjectionMatrix(screenViewport.getCamera().combined.scale(screenScale, screenScale, 0));
	}

	/**
	 * sets the world camera to a specific tile
	 *
	 * @param x
	 *            horizontal position of the tile
	 * @param y
	 *            vertical position of the tile
	 */
	public void setWorldCamera(final int x, final int y) {
		cameraWorld.position.set(x * tileSize, y * tileSize, 0);
		cameraWorld.update();
		spriteBatchWorld.setProjectionMatrix(cameraWorld.combined);
	}

	/**
	 * starts rendering on the {@link SpriteBatch} of the world.
	 */
	public void beginWorldRendering() {
		Gdx.graphics.setTitle("current fps: " + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatchWorld.begin();
	}

	/**
	 * starts rendering on the {@link SpriteBatch} of the screen.
	 */
	public void beginScreenRendering() {
		spriteBatchScreen.begin();
	}

	/**
	 * sets the blending to a semi-transparent one
	 */
	public void setScreenBlendingAlpha() {
		spriteBatchScreen.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_COLOR);
	}

	/**
	 * sets the blending for the screen sprite batch to the normal behavior
	 */
	public void setScreenBlendingNormal() {
		spriteBatchScreen.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * renders a sprite on the given tile coordinates
	 *
	 * @param region
	 *            sprite to draw
	 * @param x
	 *            horizontal tile position
	 * @param y
	 *            vertical tile position
	 */
	public void renderOnTile(final TextureRegion region, final int x, final int y) {
		renderOnTileWithOffset(region, x, y, 0, 0, region.getRegionWidth(), region.getRegionWidth());
	}

	/**
	 * renders a sprite on the given tile coordinates with an offset an the with
	 * & high of the given {@link TextureRegion}
	 *
	 * @param region
	 *            sprite to draw
	 * @param x
	 *            horizontal tile position
	 * @param y
	 *            vertical tile position
	 * @param dX
	 *            horizontal offset
	 * @param dY
	 *            vertical offset
	 */
	public void renderOnTileWithOffset(final TextureRegion region, final int x, final int y, final int dX,
			final int dY) {
		// TODO implement offset for camera, check if tile should be rendered
		renderOnTileWithOffset(region, x, y, dX, dY, region.getRegionWidth(), region.getRegionWidth());
	}

	/**
	 * renders a sprite on the given tile coordinates with an offset.<br/>
	 * stretching the region to cover the given width and height.
	 *
	 * @param region
	 *            sprite to draw
	 * @param x
	 *            horizontal tile position
	 * @param y
	 *            vertical tile position
	 * @param dX
	 *            horizontal offset
	 * @param dY
	 *            vertical offset
	 */
	public void renderOnTileWithOffset(final TextureRegion region, final int x, final int y, final int dX,
			final int dY, final int width, final int height) {
		// render on a visible tile
		// TODO refactor to take offset into account
		spriteBatchWorld.draw(region, x * tileSize + dX, y * tileSize + dY, width, height);
	}

	/**
	 * renders a sprite on the given screen coordinates
	 *
	 * @param region
	 *            sprite to draw
	 * @param x
	 *            horizontal position
	 * @param y
	 *            vertical position
	 */
	public void renderOnScreen(final TextureRegion region, final int x, final int y) {
		spriteBatchScreen.draw(region, x, y);
	}

	/**
	 * renders a sprite on the given screen coordinates
	 *
	 * @param region
	 *            sprite to draw
	 * @param x
	 *            horizontal position
	 * @param y
	 *            vertical position
	 */
	public void renderOnScreen(final TextureRegion region, final float x, final float y) {
		spriteBatchScreen.draw(region, x, y);
	}

	/**
	 * finishes rendering on the world {@link SpriteBatch}
	 */
	public void endWorldRendering() {
		spriteBatchWorld.end();
	}

	/**
	 * finishes rendering on the world {@link SpriteBatch}
	 */
	public void endScreenRendering() {
		spriteBatchScreen.end();
	}

	// FIXME reimplement
	// /**
	// * convenience method
	// *
	// * @see Camera#unproject(Vector3)
	// * @param v
	// * screen coordinates to translate
	// */
	// public void unprojectFromCamera(final Vector3 v) {
	// cameraWorld.unproject(v, viewPortX, viewPortY, viewPortWidth,
	// viewPortHeight);
	// }

	@Override
	public void disposeInstance() {
		spriteBatchWorld.dispose();
		spriteBatchScreen.dispose();
	}

}
