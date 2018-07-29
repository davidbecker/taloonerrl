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
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;

/**
 *
 * a system that checks if the offset of a position is greater than the width of
 * a tile and updates the position accordingly<br/>
 * TODO combine with {@link ControllerSystem}
 *
 * @author David Becker
 *
 */
public class OffsetSystem extends IteratingSystem {

	private PositionComponent position;

	private int offsetX = 0;
	private int offsetY = 0;
	private int absOffsetX = 0;
	private int absOffsetY = 0;
	private int deltaX;
	private int deltaY;

	/**
	 * Constructor.
	 */
	public OffsetSystem() {
		super(Aspect.all(PositionComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		position = ComponentMappers.getInstance().position.get(_entityId);
		if (!PositionUtil.isValidPosition(position)) {
			return;
		}
		offsetX = position.getOffsetX();
		offsetY = position.getOffsetY();
		// shortcut
		if (offsetX == 0 && offsetY == 0) {
			return;
		}
		absOffsetX = Math.abs(offsetX);
		absOffsetY = Math.abs(offsetY);

		deltaX = 0;
		deltaY = 0;

		// we assume that no entity can move more as one tile per frame
		if (absOffsetX >= Renderer.tileSize) {
			if (absOffsetX == offsetX) {
				offsetX -= Renderer.tileSize;
				deltaX = 1;
			} else {
				offsetX += Renderer.tileSize;
				deltaX = -1;
			}
		}
		if (absOffsetY >= Renderer.tileSize) {
			if (absOffsetY == offsetY) {
				offsetY -= Renderer.tileSize;
				deltaY = 1;
			} else {
				offsetY += Renderer.tileSize;
				deltaY = -1;
			}
		}
		position.setX(position.getX() + deltaX);
		position.setY(position.getY() + deltaY);
		position.setOffsetX(offsetX);
		position.setOffsetY(offsetY);
	}

}
