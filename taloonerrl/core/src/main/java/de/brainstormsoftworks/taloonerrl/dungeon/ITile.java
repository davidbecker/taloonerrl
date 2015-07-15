package de.brainstormsoftworks.taloonerrl.dungeon;

public interface ITile {

	public boolean isVisited();

	public void setVisited();

	public EDungeonSprites getFloor();

	public EDungeonFeature getDungeonFeature();

	public boolean isWalkable();
}
