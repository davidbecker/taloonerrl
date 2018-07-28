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
import com.artemis.Entity;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.NameComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

/**
 * system that kills dead entities
 *
 * @author David Becker
 *
 */
public class EntityDeathSystem extends IteratingSystem {
	HealthComponent healthComponent;

	public EntityDeathSystem() {
		super(Aspect.all(HealthComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		healthComponent = ComponentMappers.getInstance().health.get(_entityId);
		if (!healthComponent.isAlive()) {
			// check if dead entity is the player
			final Entity entity = GameEngine.getInstance().getTagManager().getEntity(GameEngine.TAG_PLAYER);
			if (entity != null && _entityId == entity.getId()) {
				Gdx.app.error(getClass().getSimpleName(), "the player dies");
				// TODO add more
			}
			final NameComponent nameComponent = ComponentMappers.getInstance().name.getSafe(_entityId);
			if (nameComponent != null) {
				Gdx.app.debug(getClass().getSimpleName(),
						nameComponent.getName() + " (id=" + _entityId + ") dies");
			}
			GameEngine.getInstance().removeTargetReferences(_entityId);
			GameEngine.getInstance().deleteEntity(_entityId);
			// TODO implement loot drop?
		}
	}

}
