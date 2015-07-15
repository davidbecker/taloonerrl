package de.brainstormsoftworks.taloonerrl.dungeon;

public interface IMap {

	public boolean isVisible(int x, int y);

	public boolean isWalkable(int x, int y);

	public ITile[][] getMap();

}
