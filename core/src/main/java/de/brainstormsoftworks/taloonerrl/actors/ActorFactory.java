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
package de.brainstormsoftworks.taloonerrl.actors;

import java.util.ArrayList;
import java.util.List;

import de.brainstormsoftworks.taloonerrl.internal.actors.Player;

public final class ActorFactory {
	private static List<IActor> actors = new ArrayList<IActor>();

	private ActorFactory() {
	}

	public static IActor createActor(final EActorTypes type) {
		IActor newActor = null;
		switch (type) {
		case PLAYER:
			newActor = new Player();
			break;
		default:
			break;
		}
		actors.add(newActor);
		return newActor;
	}
}
