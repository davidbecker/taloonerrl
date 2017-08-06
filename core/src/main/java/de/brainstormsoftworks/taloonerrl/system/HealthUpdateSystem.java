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

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

/**
 * system to process the stored up damage for the {@link HealthComponent}s
 *
 * @author David Becker
 *
 */
public class HealthUpdateSystem extends IteratingSystem {
	HealthComponent healthComponent;
	private int damage;
	private int health;

	public HealthUpdateSystem() {
		super(Aspect.all(HealthComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		healthComponent = ComponentMappers.getInstance().health.get(_entityId);
		if (healthComponent.isAlive()) {
			damage = healthComponent.getDamage();
			if (damage > 0) {
				health = healthComponent.getHealth();
				health -= damage;
				healthComponent.setDamage(0);
				healthComponent.setHealth(health);
				if (health <= 0) {
					healthComponent.setAlive(false);
					healthComponent.setHealthPercent(0f);
				} else {
					healthComponent.setHealthPercent((float) health / healthComponent.getHealthMax());
				}
			}
		}
	}

}
