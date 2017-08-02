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
package de.brainstormsoftworks.taloonerrl.core.engine.scheduler;

/**
 *
 * @author David Becker
 *
 */
public enum ETurnType {
	/** entity moves on the player turn */
	PLAYER,
	/** entity moves on the monster turn */
	MONSTER;

	public static String toString(final ETurnType _eTurnType) {
		switch (_eTurnType) {
		case MONSTER:
			return "MONSTER";
		case PLAYER:
			return "PLAYER";
		default:
			return "";
		}
	}

}
