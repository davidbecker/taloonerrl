/*******************************************************************************
 * Copyright (c) 2017-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.ai.tasks;

import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;

/**
 * simple task to let the entity wait a turn
 *
 * @author David Becker
 *
 */
public class WaitTask extends StatelessLeafTask {

	private static TurnComponent turnComponent;

	@Override
	public Status doExecute() {
		turnComponent = ComponentMappers.getInstance().turn.getSafe(entityId);
		if (turnComponent != null) {
			// Gdx.app.log("WaitTask", "waited");
			turnComponent.setCurrentTurn(Move.WAIT);
			return Status.SUCCEEDED;
		}
		return Status.FAILED;
	}

}
