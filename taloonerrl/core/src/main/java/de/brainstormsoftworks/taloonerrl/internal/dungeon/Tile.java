package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.IDungeonFeature;
import de.brainstormsoftworks.taloonerrl.dungeon.IFloor;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public class Tile implements ITile {

	private int floor = IFloor.NOTHING;
	private int dungeonFeature = IDungeonFeature.NOTHING;
	boolean explored = false;

	public Tile(int _floor) {
		floor = _floor;
	}

	@Override
	public void setVisited() {
		explored = true;
	}

	@Override
	public int getFloor() {
		return floor;
	}

}
