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
 * types of turns. each turn type corresponds to a specific phase of a given
 * game turn
 *
 * @author David Becker
 *
 */
public enum ETurnType {
	/** entity moves on the player turn */
	PLAYER, PLAYER_CLEANUP,
	/** entity moves on the monster turn */
	MONSTER, MONSTER_CLEANUP;

	public static String toString(final ETurnType _eTurnType) {
		return _eTurnType != null ? _eTurnType.name() : "";
	}

	public static ETurnType nextTurn(final ETurnType _eTurnType) {
		switch (_eTurnType) {
		case MONSTER:
			return MONSTER_CLEANUP;
		case MONSTER_CLEANUP:
			return PLAYER;
		case PLAYER:
			return PLAYER_CLEANUP;
		case PLAYER_CLEANUP:
			return MONSTER;
		default:
			return PLAYER;
		}
	}

}
