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
package de.brainstormsoftworks.taloonerrl.core.engine;

/**
 * a hack<br/>
 * TODO remove
 *
 * @author David Becker
 *
 */
public class Direction {
	public static final int NOTHING = 1 >> 1;
	public static final int UP = 1;
	public static final int DOWN = UP << 1;
	public static final int LEFT = DOWN << 1;
	public static final int RIGHT = LEFT << 1;
}
