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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

/**
 *
 * system to control the expiration of a state decoration entity
 *
 * @author David Becker
 *
 */
public class StateDecorationExpirationSystem extends IteratingSystem {

	private StateDecorationComponent component;

	/**
	 * Constructor.
	 */
	public StateDecorationExpirationSystem() {
		super(Aspect.all(StateDecorationComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		component = ComponentMappers.getInstance().stateDecoration.get(_entityId);
		float stateTime = GameEngine.getInstance().getStateTime();
		if (component.isActive()) {
			if (component.getTimeToLive() < stateTime) {
				Gdx.app.debug(getClass().getSimpleName(), "kill entity " + _entityId);
				GameEngine.getInstance().deleteEntity(_entityId);
			}
		} else {
			// check if component should get activated
			component.setActive(component.getTimeToLiveStart() <= stateTime
					&& component.getTimeToLive() >= stateTime);
		}
	}

}
