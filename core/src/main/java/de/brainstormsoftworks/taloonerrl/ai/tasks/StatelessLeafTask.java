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
package de.brainstormsoftworks.taloonerrl.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * abstract parent class for tasks that have no internal state and therefore
 * don't need the copy method
 *
 * @author David Becker
 *
 */
public abstract class StatelessLeafTask extends LeafTask<Entity> {

	@Override
	protected Task<Entity> copyTo(final Task<Entity> _task) {
		return _task;
	}

}
