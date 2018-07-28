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

import de.brainstormsoftworks.taloonerrl.render.Renderer;
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

	/**
	 * checks whether this component is in transition between two destinations
	 *
	 * @return true if in transition
	 */
	public boolean isProcessingTurn() {
		return !(totalX == 0 && totalY == 0);
	}

	/**
	 * stops the current motion
	 */
	public void resetMotion() {
		totalX = 0;
		totalY = 0;
	}

	/**
	 * gets the x position of this component<br>
	 * takes the present offset into consideration
	 *
	 * @return
	 */
	public int getXEffective() {
		return Math.round(x + (float) offsetX / Renderer.tileSize);
	}

	/**
	 * gets the y position of this component<br>
	 * takes the present offset into consideration
	 *
	 * @return
	 */
	public int getYEffective() {
		return Math.round(y + (float) offsetY / Renderer.tileSize);
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

	/**
	 * x at the end of a turn (assumes full movement of one tile each turn)
	 *
	 * @return
	 */
	public int getTargetX() {
		return totalX == 0 ? x : totalX > 0 ? x + 1 : x - 1;
	}

	/**
	 * x at the end of a turn (assumes full movement of one tile each turn)
	 *
	 * @return
	 */
	public int getTargetY() {
		return totalY == 0 ? y : totalY > 0 ? y + 1 : y - 1;
	}
}
