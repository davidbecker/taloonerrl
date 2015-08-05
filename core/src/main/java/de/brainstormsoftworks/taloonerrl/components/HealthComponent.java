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
 * component that stores the percentage of health and total max health that the
 * entity has
 *
 * @author David Becker
 *
 */
public class HealthComponent extends PooledComponent {

	private int healthMax = 1;
	private float healthPercent = 1.0f;
	private boolean alive = true;

	@Override
	protected void reset() {
		healthMax = 1;
		healthPercent = 1.0f;
		alive = true;
	}

	/**
	 * getter for {@link #healthMax}
	 *
	 * @return the healthMax
	 */
	public final int getHealthMax() {
		return healthMax;
	}

	/**
	 * setter for healthMax
	 *
	 * @param _healthMax
	 *            the healthMax to set
	 */
	public final void setHealthMax(final int _healthMax) {
		healthMax = _healthMax;
	}

	/**
	 * getter for {@link #healthPercent}
	 *
	 * @return the healthPercent
	 */
	public final float getHealthPercent() {
		return healthPercent;
	}

	/**
	 * setter for healthPercent
	 *
	 * @param _healthPercent
	 *            the healthPercent to set
	 */
	public final void setHealthPercent(final float _healthPercent) {
		healthPercent = _healthPercent;
		if (healthPercent < 0) {
			healthPercent = 0;
			alive = false;
		} else if (healthPercent > 1) {
			healthPercent = 1;
		}
	}

	/**
	 * getter for {@link #alive}
	 *
	 * @return the alive
	 */
	public final boolean isAlive() {
		return alive;
	}

	/**
	 * setter for alive
	 *
	 * @param _alive
	 *            the alive to set
	 */
	public final void setAlive(final boolean _alive) {
		alive = _alive;
	}

}
