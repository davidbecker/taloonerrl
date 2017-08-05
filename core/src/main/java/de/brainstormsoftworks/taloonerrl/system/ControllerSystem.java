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

/**
 * updates {@link PositionComponent}s
 *
 * @author David Becker
 *
 */
public class ControllerSystem extends IteratingSystem {

	private int totalX = 0;
	private int totalY = 0;
	private int absTotalX = 0;
	private int absTotalY = 0;
	private int deltaX = 0;
	private int deltaY = 0;

	private int offsetX = 0;
	private int offsetY = 0;

	private int velocity = 0;

	/**
	 * Constructor.
	 */
	public ControllerSystem() {
		super(Aspect.all(PositionComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		final PositionComponent position = ComponentMappers.getInstance().position.get(_entityId);
		totalX = position.getTotalX();
		totalY = position.getTotalY();
		offsetX = position.getOffsetX();
		offsetY = position.getOffsetY();
		velocity = position.getVelocity();
		// shortcut
		if (totalX == 0 && totalY == 0) {
			return;
		}

		absTotalX = Math.abs(totalX);
		absTotalY = Math.abs(totalY);
		deltaX = Math.min(absTotalX, velocity);
		deltaY = Math.min(absTotalY, velocity);

		if (absTotalX > 0) {
			if (absTotalX == totalX) {
				totalX -= deltaX;
				offsetX += deltaX;
			} else {
				totalX += deltaX;
				offsetX -= deltaX;
			}
		}
		if (absTotalY > 0) {
			if (absTotalY == totalY) {
				totalY -= deltaY;
				offsetY += deltaY;
			} else {
				totalY += deltaY;
				offsetY -= deltaY;
			}
		}
		position.setOffsetX(offsetX);
		position.setOffsetY(offsetY);
		position.setTotalX(totalX);
		position.setTotalY(totalY);
	}

}
