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

/**
 * empty component that can be added to an entity to mark it for highlighting
 *
 * @author David Becker
 *
 */
public class HighlightComponent extends PooledComponent implements IDisposableInstance {
	// FIXME currently expecting a tile size of 16
	public static final int cursorTopLeftOffsetX = -2;
	public static final int cursorTopLeftOffsetY = 11;
	public static final int cursorTopRightOffsetX = 11;
	public static final int cursorTopRightOffsetY = 11;
	public static final int cursorBottomLeftOffsetX = -2;
	public static final int cursorBottomLeftOffsetY = -2;
	public static final int cursorBottomRightOffsetX = 11;
	public static final int cursorBottomRightOffsetY = -2;

	private static TextureRegion cursorTopLeft = null;
	private static TextureRegion cursorTopRight = null;
	private static TextureRegion cursorBottomLeft = null;
	private static TextureRegion cursorBottomRight = null;

	private int cursorAnimationOffset = 0;
	private final Texture cursor;

	public HighlightComponent() {
		if (cursorTopLeft == null) {
			cursor = new Texture(Gdx.files.internal("cursor.png"));
			cursorTopLeft = new TextureRegion(cursor, 0, 0, 8, 8);
			cursorTopRight = new TextureRegion(cursor, 8, 0, 8, 8);
			cursorBottomLeft = new TextureRegion(cursor, 0, 8, 8, 8);
			cursorBottomRight = new TextureRegion(cursor, 8, 8, 8, 8);
			RenderUtil.addToDisposeList(this);
		} else {
			cursor = null;
		}
	}

	@Override
	protected void reset() {
		cursorAnimationOffset = 0;
	}

	/**
	 * getter for {@link #cursorTopLeft}
	 *
	 * @return the cursorTopLeft
	 */
	public static final TextureRegion getCursorTopLeft() {
		return cursorTopLeft;
	}

	/**
	 * setter for cursorTopLeft
	 *
	 * @param cursorTopLeft
	 *            the cursorTopLeft to set
	 */
	public static final void setCursorTopLeft(final TextureRegion cursorTopLeft) {
		HighlightComponent.cursorTopLeft = cursorTopLeft;
	}

	/**
	 * getter for {@link #cursorTopRight}
	 *
	 * @return the cursorTopRight
	 */
	public static final TextureRegion getCursorTopRight() {
		return cursorTopRight;
	}

	/**
	 * setter for cursorTopRight
	 *
	 * @param cursorTopRight
	 *            the cursorTopRight to set
	 */
	public static final void setCursorTopRight(final TextureRegion cursorTopRight) {
		HighlightComponent.cursorTopRight = cursorTopRight;
	}

	/**
	 * getter for {@link #cursorBottomLeft}
	 *
	 * @return the cursorBottomLeft
	 */
	public static final TextureRegion getCursorBottomLeft() {
		return cursorBottomLeft;
	}

	/**
	 * setter for cursorBottomLeft
	 *
	 * @param cursorBottomLeft
	 *            the cursorBottomLeft to set
	 */
	public static final void setCursorBottomLeft(final TextureRegion cursorBottomLeft) {
		HighlightComponent.cursorBottomLeft = cursorBottomLeft;
	}

	/**
	 * getter for {@link #cursorBottomRight}
	 *
	 * @return the cursorBottomRight
	 */
	public static final TextureRegion getCursorBottomRight() {
		return cursorBottomRight;
	}

	/**
	 * setter for cursorBottomRight
	 *
	 * @param cursorBottomRight
	 *            the cursorBottomRight to set
	 */
	public static final void setCursorBottomRight(final TextureRegion cursorBottomRight) {
		HighlightComponent.cursorBottomRight = cursorBottomRight;
	}

	/**
	 * getter for {@link #cursorAnimationOffset}
	 *
	 * @return the cursorAnimationOffset
	 */
	public final int getCursorAnimationOffset() {
		return cursorAnimationOffset;
	}

	/**
	 * setter for cursorAnimationOffset
	 *
	 * @param cursorAnimationOffset
	 *            the cursorAnimationOffset to set
	 */
	public final void setCursorAnimationOffset(final int cursorAnimationOffset) {
		this.cursorAnimationOffset = cursorAnimationOffset;
	}

	@Override
	public void disposeInstance() {
		cursor.dispose();
	}

}
