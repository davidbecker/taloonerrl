/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
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
 * a move (or non move) for an entity
 *
 * @author David Becker
 *
 */
public class Move {
	/** when the entity doesn't have a move for the current turn yet */
	public static final int IDLE = 0;
	/** when the entity doesn't want to move anywhere */
	public static final int WAIT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	public static final int RIGHT = 5;

	/**
	 * utility method to translate Direction from squid lib to a move
	 *
	 * @param _d
	 *            direction
	 * @return
	 */
	public static final int from(final squidpony.squidgrid.Direction _d) {
		switch (_d) {
		// wtf????
		case DOWN:
			return UP;
		case UP:
			return DOWN;
		case LEFT:
			return LEFT;
		case RIGHT:
			return RIGHT;
		default:
			// FIXME or IDLE?
			return WAIT;
		}
	}

	public static int randomDirection() {
		// range from UP to RIGHT
		return GameEngine.getRng().nextInt(3) + 2;
	}

	public static String toString(final int direction) {
		switch (direction) {
		case IDLE:
			return "IDLE";
		case WAIT:
			return "WAIT";
		case UP:
			return "UP";
		case DOWN:
			return "UP";
		case LEFT:
			return "LEFT";
		case RIGHT:
			return "RIGHT";
		default:
			return "unknown";
		}
	}
}
