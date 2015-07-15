package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonFeature;
import de.brainstormsoftworks.taloonerrl.dungeon.EFloor;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public class Tile implements ITile {

	private EFloor floor = EFloor.NOTHING;
	private EDungeonFeature dungeonFeature = EDungeonFeature.NOTHING;
	boolean explored = false;

	public Tile(EFloor _floor) {
		floor = _floor;
	}

	@Override
	public void setVisited() {
		explored = true;
	}

	@Override
	public EFloor getFloor() {
		return floor;
	}

}
