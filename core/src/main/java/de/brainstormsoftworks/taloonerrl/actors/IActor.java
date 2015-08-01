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

public interface IActor {
	IActorMovement getMovementComponent();

	IActorSprite getSpriteComponent();

	/**
	 * sends a message to all the components of this actor
	 *
	 * @param msg
	 */
	void sendMessage(int msg);
}
