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
package de.brainstormsoftworks.taloonerrl.internal.actors;

import com.artemis.Entity;

import de.brainstormsoftworks.taloonerrl.actors.IActor;
import de.brainstormsoftworks.taloonerrl.actors.IActorMovement;
import de.brainstormsoftworks.taloonerrl.actors.IActorSprite;

/**
 * prototype for an entity
 *
 * @deprecated use {@link Entity} instead
 *
 * @author David Becker
 *
 */
@Deprecated
public abstract class Actor implements IActor {
	protected ActorMovementComponent movementComponent = null;
	protected ActorSpriteComponent actorSpriteComponent = new ActorSpriteComponent();

	@Override
	public IActorMovement getMovementComponent() {
		return movementComponent;
	}

	@Override
	public IActorSprite getSpriteComponent() {
		return actorSpriteComponent;
	}

	@Override
	public void sendMessage(final int msg) {
		if (movementComponent != null) {
			movementComponent.recieveMessage(msg);
		}
		if (actorSpriteComponent != null) {
			actorSpriteComponent.recieveMessage(msg);
		}

	}
}
