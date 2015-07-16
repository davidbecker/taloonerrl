package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonFeature;
import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public class Tile implements ITile {

	private EDungeonSprites dungeonSprite = EDungeonSprites.NOTHING;
	private final EDungeonFeature dungeonFeature = EDungeonFeature.NOTHING;
	boolean explored = false;
	boolean walkable = false;

	/**
	 * default contructor. creates a tile with no sprite that isn't walkable
	 */
	public Tile() {
		this(false);
	}

	public Tile(boolean _walkable) {
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
	public void setDungeonSprite(EDungeonSprites _sprite) {
		dungeonSprite = _sprite;
	}

	@Override
	public EDungeonFeature getDungeonFeature() {
		return dungeonFeature;
	}

	@Override
	public boolean isWalkable() {
		return walkable;
	}

	@Override
	public void setWalkable(boolean _walkable) {
		walkable = _walkable;
	}

}
