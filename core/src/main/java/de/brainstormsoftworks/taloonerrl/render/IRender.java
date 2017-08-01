/*******************************************************************************
 * Copyright (c) 2017 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.render;

/**
 * interface for renderer to be called by {@link Renderer} to draw various
 * things
 *
 * @author David Becker
 *
 */
public interface IRender {

	/**
	 * render all the things
	 */
	public void render();

}
