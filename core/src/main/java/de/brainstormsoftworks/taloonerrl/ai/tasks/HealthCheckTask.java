/*******************************************************************************
 * Copyright (c) 2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

/**
 * a tasks that will compare the health of a {@link HealthComponent} to a
 * threshold value
 *
 * @author David Becker
 *
 */
public class HealthCheckTask extends StatelessLeafTask {

	private static Entity entity;
	private static int entityId;

	private static HealthComponent healthComponent;

	@TaskAttribute(required = true)
	public float healthThreshold = 0.5f;

	@Override
	public Status execute() {
		entity = getObject();
		entityId = entity.getId();
		healthComponent = ComponentMappers.getInstance().health.getSafe(entityId);
		if (healthComponent != null && healthComponent.getHealthPercent() <= healthThreshold) {
			return Status.SUCCEEDED;
		}
		return Status.FAILED;
	}

}
