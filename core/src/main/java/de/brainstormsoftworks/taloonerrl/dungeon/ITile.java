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
package de.brainstormsoftworks.taloonerrl.dungeon;

@Deprecated
public interface ITile {

	/**
	 * was this tile in field of view before?
	 *
	 * @return
	 */
	boolean isVisited();

	/**
	 * set flag that the tile was in field of view before
	 */
	void setVisited();

	EDungeonSprites getDungeonSprite();

	void setDungeonSprite(EDungeonSprites _sprite);

	boolean isWalkable();

	void setWalkable(boolean _walkable);
}
