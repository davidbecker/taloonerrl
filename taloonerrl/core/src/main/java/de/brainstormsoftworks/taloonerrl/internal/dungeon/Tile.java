package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonFeature;
import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;

public class Tile implements ITile {

	private EDungeonSprites floor = EDungeonSprites.NOTHING;
	private EDungeonFeature dungeonFeature = EDungeonFeature.NOTHING;
	boolean explored = false;

	public Tile() {
		this(EDungeonSprites.NOTHING);
	}

	public Tile(EDungeonSprites _floor) {
		floor = _floor;
	}

	@Override
	public void setVisited() {
		explored = true;
	}

	@Override
	public EDungeonSprites getFloor() {
		return floor;
	}

	@Override
	public EDungeonFeature getDungeonFeature() {
		return dungeonFeature;
	}

	@Override
	public boolean isWalkable() {
		switch (floor) {
		case FLOOR_ROOM_BOTTOM:
			return true;
		case FLOOR_ROOM_BOTTOMLEFT_CORNER:
			return true;
		case FLOOR_ROOM_BOTTOMRIGHT_CORNER:
			return true;
		case FLOOR_ROOM_CENTER:
			return true;
		case FLOOR_ROOM_LEFT:
			return true;
		case FLOOR_ROOM_RIGHT:
			return true;
		case FLOOR_ROOM_TOP:
			return true;
		case FLOOR_ROOM_TOPLEFT_CORNER:
			return true;
		case FLOOR_ROOM_TOPRIGHT_CORNER:
			return true;
		case NOTHING:
		default:
			return false;
		}
	}

}
