/*******************************************************************************
 * Copyright (c) 2017 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/

package de.brainstormsoftworks.taloonerrl.components;

import com.badlogic.gdx.Gdx;

import lombok.Getter;
import lombok.Setter;

/**
 * single status for an entity
 *
 * @author David Becker
 *
 */
@Getter
class EntityStatus {

	@Setter
	private boolean active = false;
	@Setter
	private boolean cooldownActive = false;
	@Setter
	private int cooldownDuration = 0;
	private final EEntityState type;

	/**
	 *
	 * Constructor.<br>
	 * package private on purpose
	 *
	 * @param _type
	 *            type of this state
	 */
	EntityStatus(final EEntityState _type) {
		type = _type;
	}

	public void reset() {
		active = false;
		cooldownActive = false;
		cooldownDuration = 0;
	}

	void processCooldown() {
		if (active && cooldownActive) {
			cooldownDuration -= 1;
			if (cooldownDuration <= 0) {
				active = false;
				Gdx.app.log(getClass().getSimpleName(), EEntityState.toString(type) + " cooled down");
			}
		}
	}

}
