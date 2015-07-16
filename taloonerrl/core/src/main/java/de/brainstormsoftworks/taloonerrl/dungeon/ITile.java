package de.brainstormsoftworks.taloonerrl.dungeon;

public interface ITile {

	public boolean isVisited();

	public void setVisited();

	public EDungeonSprites getDungeonSprite();

	public void setDungeonSprite(EDungeonSprites _sprite);

	public EDungeonFeature getDungeonFeature();

	public boolean isWalkable();

	public void setWalkable(boolean _walkable);
}
