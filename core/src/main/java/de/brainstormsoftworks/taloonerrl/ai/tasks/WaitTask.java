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

import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;

/**
 * simple task to let the entity wait a turn
 *
 * @author David Becker
 *
 */
public class WaitTask extends LeafTask<Entity> {

	@Override
	public Status execute() {
		final TurnComponent turnComponent = ComponentMappers.getInstance().turn.getSafe(getObject());
		if (turnComponent != null) {
			// turnComponent.setNextTurn(Move.WAIT);
			turnComponent.setCurrentTurn(Move.WAIT);
			return Status.RUNNING;
		}
		return Status.FAILED;
	}

	@Override
	protected Task<Entity> copyTo(final Task<Entity> _task) {
		return _task;
	}
}
