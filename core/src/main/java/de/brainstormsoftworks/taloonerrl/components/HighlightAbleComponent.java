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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.render.IDisposableInstance;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * empty component that can be added to an entity to mark it for highlighting
 *
 * @author David Becker
 *
 */
public class HighlightAbleComponent extends PooledComponent implements IDisposableInstance {
	public static final int HIGHLIGHT_STYLE_NONE = 0;
	public static final int HIGHLIGHT_STYLE_BLINKING = 1;
	public static final int HIGHLIGHT_STYLE_STATIC = 2;

	private static final Texture cursor = new Texture(Gdx.files.internal("cursor.png"));
	// FIXME currently expecting a tile size of 16
	public static final int cursorTopLeftOffsetX = -2;
	public static final int cursorTopLeftOffsetY = 11;
	public static final int cursorTopRightOffsetX = 11;
	public static final int cursorTopRightOffsetY = 11;
	public static final int cursorBottomLeftOffsetX = -2;
	public static final int cursorBottomLeftOffsetY = -2;
	public static final int cursorBottomRightOffsetX = 11;
	public static final int cursorBottomRightOffsetY = -2;

	private static final @Getter TextureRegion cursorTopLeft = new TextureRegion(cursor, 0, 0, 8, 8);
	private static final @Getter TextureRegion cursorTopRight = new TextureRegion(cursor, 8, 0, 8, 8);
	private static final @Getter TextureRegion cursorBottomLeft = new TextureRegion(cursor, 0, 8, 8, 8);
	private static final @Getter TextureRegion cursorBottomRight = new TextureRegion(cursor, 8, 8, 8, 8);

	{
		RenderUtil.addToDisposeList(this);
	}

	private @Getter @Setter int cursorAnimationOffset = 0;
	private @Getter @Setter int highlightStyle = HIGHLIGHT_STYLE_NONE;

	public HighlightAbleComponent() {
	}

	@Override
	protected void reset() {
		cursorAnimationOffset = 0;
	}

	@Override
	public void disposeInstance() {
		cursor.dispose();
	}

	public final boolean isHighlightStyleNone() {
		return highlightStyle == HIGHLIGHT_STYLE_NONE;
	}

	public final boolean isHighlightStyleBlinking() {
		return highlightStyle == HIGHLIGHT_STYLE_BLINKING;
	}

}
