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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * singleton to keep track of on globally accessible {@link SpriteBatch}
 *
 * TODO move methods that access {@link #BATCH} here
 *
 * @author David Becker
 *
 */
public final class Renderer {
	public final SpriteBatch BATCH;

	private static final Renderer instance = new Renderer();

	private Renderer() {
		BATCH = new SpriteBatch();
	}

	public static Renderer getInstance() {
		return instance;
	}

}
