package de.brainstormsoftworks.taloonerrl.dungeon;

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

	EDungeonFeature getDungeonFeature();

	boolean isWalkable();

	void setWalkable(boolean _walkable);
}
