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
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * singleton to keep track of on globally accessible {@link SpriteBatch}
 *
 * TODO move methods that access {@link #BATCH} here
 *
 * @author David Becker
 *
 */
public final class Renderer {

	public static final float scale = 16f;
	public static final int tileSize = 16;
	public static final int TILES_HORIZONTAL = 30;
	public static final int TILES_VERTICAL = 20;
	public static final int VIRTUAL_WIDTH = TILES_HORIZONTAL * tileSize + 4 * tileSize;
	public static final int VIRTUAL_HEIGHT = TILES_VERTICAL * tileSize;
	public static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

	private static final Renderer instance = new Renderer();

	public final SpriteBatch BATCH;
	public final OrthographicCamera camera;

	private Rectangle viewport;
	private int viewPortX;
	private int viewPortY;
	private int viewPortWidth;
	private int viewPortHeight;

	private Renderer() {
		BATCH = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
	}

	public static Renderer getInstance() {
		return instance;
	}

	public void resizeViewPort(final int width, final int height) {
		// calculate new viewport
		final float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		final Vector2 crop = new Vector2(0f, 0f);
		if (aspectRatio > ASPECT_RATIO) {
			scale = height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
		} else if (aspectRatio < ASPECT_RATIO) {
			scale = width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
		} else {
			scale = width / (float) VIRTUAL_WIDTH;
		}

		final float w = VIRTUAL_WIDTH * scale;
		final float h = VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);

		camera.update();

		// see http://codebin.co.uk/blog/pixelated-rendering-in-libgdx/

		viewPortX = Math.round(viewport.x * Renderer.tileSize) / Renderer.tileSize;
		viewPortY = Math.round(viewport.y * Renderer.tileSize) / Renderer.tileSize;
		viewPortWidth = Math.round(viewport.width * Renderer.tileSize) / Renderer.tileSize;
		viewPortHeight = Math.round(viewport.height * Renderer.tileSize) / Renderer.tileSize;
		Gdx.gl.glViewport(viewPortX, viewPortY, viewPortWidth, viewPortHeight);
	}

	/**
	 * starts rendering
	 */
	public void beginRendering() {
		Gdx.graphics.setTitle("current fps: " + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BATCH.setProjectionMatrix(camera.combined);
		BATCH.begin();
		BATCH.enableBlending();
	}

	/**
	 * finishes rendering
	 */
	public void endRendering() {
		BATCH.end();
	}

	/**
	 * convenience method
	 *
	 * @see Camera#unproject(Vector3)
	 * @param v
	 *            screen coordinates to translate
	 */
	public void unprojectFromCamera(final Vector3 v) {
		camera.unproject(v, viewPortX, viewPortY, viewPortWidth, viewPortHeight);
	}

}
