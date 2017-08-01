/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
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
	private int deltaX;
	private int deltaY;

	public BlockingTileCheckSystem() {
		super(Aspect.all(PositionComponent.class));
		MapChangeProvider.getInstance().registerListener(this);
	}

	@Override
	protected void process(final int _entityId) {
		position = ComponentMappers.getInstance().position.get(_entityId);
		if (map != null) {
			deltaX = PositionUtil.getDeltaX(position);
			deltaY = PositionUtil.getDeltaY(position);
			if (PositionUtil.isMovingWholeTile(deltaX, deltaY)) {
				if (!map.isWalkable(position.getX() + deltaX, position.getY() + deltaY)) {
					// not walkable -> reset movement
					position.setTotalX(0);
					position.setTotalY(0);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setMap(final IMap _map) {
		map = _map;
	}

}
