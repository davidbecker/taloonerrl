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
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

import lombok.Getter;
import lombok.Setter;

/**
 * component to store the position, offset of the current position and delta to
 * move for an entity
 *
 * @author David Becker
 *
 */
@Getter
@Setter
public class PositionComponent extends PooledComponent implements ISetAbleComponent<PositionComponent> {

	private static final int VELOCITY_DEFAULT = 2;
	private int velocity = VELOCITY_DEFAULT;

	private int x = 0;
	private int y = 0;

	// the offset this entity has from its current position
	private int offsetX = 0;
	private int offsetY = 0;

	// amount of offset that this entity will be moving
	private int totalX = 0;
	private int totalY = 0;

	@Override
	protected void reset() {
		x = 0;
		y = 0;
		offsetX = 0;
		offsetY = 0;
		totalX = 0;
		totalY = 0;
		velocity = VELOCITY_DEFAULT;
	}

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final PositionComponent _component) {
		x = _component.getX();
		y = _component.getY();
		offsetX = _component.getOffsetX();
		offsetY = _component.getOffsetY();
		totalX = _component.getTotalX();
		totalY = _component.getTotalY();
		velocity = _component.getVelocity();
	}
}
