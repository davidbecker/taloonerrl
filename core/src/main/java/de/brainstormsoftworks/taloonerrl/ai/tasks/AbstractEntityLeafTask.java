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
import com.badlogic.gdx.ai.btree.LeafTask;

/**
 * base class for all leaf tasks dealing with {@link Entity} as the blackboard
 * object
 *
 * @author David Becker
 *
 */
public abstract class AbstractEntityLeafTask extends LeafTask<Entity> {

	protected static Entity entity;
	protected static int entityId;

	@Override
	public Status execute() {
		entity = getObject();
		entityId = entity.getId();
		return doExecute();
	}

	/**
	 * called after entity & entityId are filled
	 *
	 * @see {@link LeafTask#execute()}
	 *
	 */
	protected abstract Status doExecute();

}
