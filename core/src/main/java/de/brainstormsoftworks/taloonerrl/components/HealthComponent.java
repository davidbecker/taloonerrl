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

import lombok.Getter;
import lombok.Setter;

/**
 * component that stores the percentage of health and total max health that the
 * entity has
 *
 * @author David Becker
 *
 */
@Getter
public class HealthComponent extends PooledComponent implements ISetAbleComponent<HealthComponent> {

	private @Setter int healthMax = 1;
	private float healthPercent = 1.0f;
	private @Setter boolean alive = true;

	@Override
	protected void reset() {
		healthMax = 1;
		healthPercent = 1.0f;
		alive = true;
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

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final HealthComponent _component) {
		healthMax = _component.getHealthMax();
		healthPercent = _component.getHealthPercent();
		alive = _component.isAlive();
	}

}
