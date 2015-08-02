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

/**
 * component that stores the percentage of health that the entity has
 *
 * @author David Becker
 *
 */
public class HealthComponent extends PooledComponent {

	private float healthPercent = 1.0f;
	private boolean alive = true;

	@Override
	protected void reset() {
		healthPercent = 1.0f;
		alive = true;
	}

	/**
	 * getter for heathPercent
	 *
	 * @return the heathPercent
	 */
	public float getHealthPercent() {
		return healthPercent;
	}

	/**
	 * setter for heathPercent
	 *
	 * @param _healthPercent
	 *            the heathPercent to set
	 */
	public void setHealthPercent(final float _healthPercent) {
		healthPercent = _healthPercent;
		if (healthPercent < 0) {
			healthPercent = 0;
			alive = false;
		} else if (healthPercent > 1) {
			healthPercent = 1;
		}
	}

	/**
	 * getter for alive
	 *
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * setter for alive
	 *
	 * @param alive
	 *            the alive to set
	 */
	public void setAlive(final boolean alive) {
		this.alive = alive;
	}

}
