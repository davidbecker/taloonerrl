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

import com.artemis.Component;

import de.brainstormsoftworks.taloonerrl.dungeon.ESprite;

/**
 * interface for an component
 *
 * @deprecated use {@link Component} instead
 * @author David Becker
 *
 */
@Deprecated
public interface IActorSprite {
	ESprite getSprite();
}
