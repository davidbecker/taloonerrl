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
package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

@Deprecated
public class Tile implements ITile {

	private EDungeonSprites dungeonSprite = EDungeonSprites.NOTHING;
	boolean explored = false;
	boolean walkable = false;

	/**
	 * default contructor. creates a tile with no sprite that isn't walkable
	 */
	public Tile() {
		this(false);
	}

	public Tile(final boolean _walkable) {
		walkable = _walkable;
	}

	@Override
	public void setVisited() {
		explored = true;
	}

	@Override
	public boolean isVisited() {
		return explored;
	}

	@Override
	public EDungeonSprites getDungeonSprite() {
		return dungeonSprite;
	}

	@Override
	public void setDungeonSprite(final EDungeonSprites _sprite) {
		dungeonSprite = _sprite;
	}

	@Override
	public boolean isWalkable() {
		return walkable;
	}

	@Override
	public void setWalkable(final boolean _walkable) {
		walkable = _walkable;
	}

}
