/*******************************************************************************
 * Copyright (c) 2015-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.IMapChangeListener;
import de.brainstormsoftworks.taloonerrl.dungeon.MapChangeProvider;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;

/**
 * system to check if the tile a is walkable
 *
 * @author David Becker
 *
 */
public class BlockingTileCheckSystem extends IteratingSystem implements IMapChangeListener {

	private PositionComponent position;
	private IMap map;
	private int moveToX;
	private int moveToY;
	private int deltaX;
	private int deltaY;
	boolean reseted;

	public BlockingTileCheckSystem() {
		super(Aspect.all(PositionComponent.class));
		MapChangeProvider.getInstance().registerListener(this);
	}

	@Override
	protected void process(final int _entityId) {
		reseted = false;
		position = ComponentMappers.getInstance().position.get(_entityId);
		if (!PositionUtil.isValidPosition(position)) {
			return;
		}
		deltaX = PositionUtil.getDeltaX(position);
		deltaY = PositionUtil.getDeltaY(position);
		// make sure to check only at the start of a motion
		if (PositionUtil.isMovingWholeTile(deltaX, deltaY)) {
			moveToX = position.getX() + deltaX;
			moveToY = position.getY() + deltaY;
			if (map != null) {
				if (!map.isWalkable(moveToX, moveToY)) {
					// not walkable -> reset movement
					reseted = true;
				}
			}

			/*
			 * tile is not blocked by environment, we need to check if an other entity
			 * occupies that space.if this is the case we reset the movement as well and
			 * tread the motion as an attack
			 */
			if (!reseted) {
				reseted = PositionUtil.attackEntitiesOnPosition(moveToX, moveToY, _entityId);
				// TODO implement attacks
			}

			if (reseted) {
				position.resetMotion();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setMap(final IMap _map) {
		map = _map;
	}

}
