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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * holder for 1x1 pixel textures for our palette
 *
 * @author David Becker
 *
 */
public final class PaletteUtil implements IDisposableInstance {
	private static final PaletteUtil instance = new PaletteUtil();
	private final Texture paletteTexture;
	public final TextureRegion BLACK;
	public final TextureRegion GREY_BLUE;
	public final TextureRegion VIOLETT;
	public final TextureRegion DARK_BLUE;
	public final TextureRegion GREY;
	public final TextureRegion BROWN;
	public final TextureRegion DARK_GREEN;
	public final TextureRegion RED;
	public final TextureRegion LIGHT_GREY;
	public final TextureRegion LIGHT_BLUE;
	public final TextureRegion LIGHT_BROWN;
	public final TextureRegion LIGHT_GREY_BLUE;
	public final TextureRegion LIGHT_GREEN;
	public final TextureRegion LIGHT_RED;
	public final TextureRegion CYAN;
	public final TextureRegion YELLOW;
	public final TextureRegion WHITE;

	private PaletteUtil() {
		paletteTexture = new Texture(Gdx.files.internal("palette.png"), false);
		BLACK = new TextureRegion(paletteTexture, 0, 0, 1, 1);
		GREY_BLUE = new TextureRegion(paletteTexture, 1, 0, 1, 1);
		VIOLETT = new TextureRegion(paletteTexture, 2, 0, 1, 1);
		DARK_BLUE = new TextureRegion(paletteTexture, 3, 0, 1, 1);
		GREY = new TextureRegion(paletteTexture, 4, 0, 1, 1);
		BROWN = new TextureRegion(paletteTexture, 5, 0, 1, 1);
		DARK_GREEN = new TextureRegion(paletteTexture, 6, 0, 1, 1);
		RED = new TextureRegion(paletteTexture, 7, 0, 1, 1);
		LIGHT_GREY = new TextureRegion(paletteTexture, 8, 0, 1, 1);
		LIGHT_BLUE = new TextureRegion(paletteTexture, 9, 0, 1, 1);
		LIGHT_BROWN = new TextureRegion(paletteTexture, 10, 0, 1, 1);
		LIGHT_GREY_BLUE = new TextureRegion(paletteTexture, 11, 0, 1, 1);
		LIGHT_GREEN = new TextureRegion(paletteTexture, 12, 0, 1, 1);
		LIGHT_RED = new TextureRegion(paletteTexture, 13, 0, 1, 1);
		CYAN = new TextureRegion(paletteTexture, 14, 0, 1, 1);
		YELLOW = new TextureRegion(paletteTexture, 15, 0, 1, 1);
		WHITE = new TextureRegion(paletteTexture, 16, 0, 1, 1);

		RenderUtil.addToDisposeList(this);
	}

	/**
	 * get an instance of this singleton
	 *
	 * @return {@link #instance}
	 */
	public static PaletteUtil getInstance() {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public void disposeInstance() {
		paletteTexture.dispose();
	}

}
