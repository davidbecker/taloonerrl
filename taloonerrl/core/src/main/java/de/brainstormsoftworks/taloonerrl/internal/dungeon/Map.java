package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public class Map implements IMap {

	private ITile[][] map;
	private int tilesHorizontal;
	private int tilesVertical;

	public Map(int _tilesHorizontal, int _tilesVertical) {
		tilesHorizontal = _tilesHorizontal;
		tilesVertical = _tilesVertical;
		map = new ITile[tilesHorizontal][tilesVertical];
	}

	@Override
	public boolean isVisible(int x, int y) {
		if (!isInMapBounds(x, y)) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWalkable(int x, int y) {
		if (!isInMapBounds(x, y)) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isInMapBounds(int x, int y) {
		if (x < 0 || x >= tilesHorizontal || y < 0 || y >= tilesVertical) {
			return false;
		}
		return true;
	}

	@Override
	public ITile[][] getMap() {
		return map;
	}

	public void setMap(ITile[][] newMap) {
		map = newMap;
	}

}
