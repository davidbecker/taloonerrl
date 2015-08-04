/*******************************************************************************
 * Copyright (c) 2015 David Becker.
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

import de.brainstormsoftworks.taloonerrl.math.IntVector2;

/**
 * component for an entity that can get input<br/>
 * TODO implement use
 *
 * @author David Becker
 *
 */
public class ControllerComponent extends PooledComponent {
	private final IntVector2 moveVector = new IntVector2(0, 0);

	@Override
	protected void reset() {
		moveVector.setZero();
	}

	/**
	 * getter for moveVector
	 *
	 * @return the moveVector
	 */
	public final IntVector2 getMoveVector() {
		return moveVector;
	}

}
