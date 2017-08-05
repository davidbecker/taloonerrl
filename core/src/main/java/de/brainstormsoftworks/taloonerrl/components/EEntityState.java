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
package de.brainstormsoftworks.taloonerrl.components;

import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;

/**
 * type of entity states
 *
 * @author David Becker
 *
 */
public enum EEntityState {
	NONE, SLEEPING, DEAD, ALERT, CONFUSED;

	public static String toString(final EEntityState _state) {
		return _state != null ? _state.name() : "";
	}

	public static EEntity toDecorator(final EEntityState _state) {
		switch (_state) {
		case ALERT:
			return EEntity.STATUS_DECORATOR_ALERTED;
		case CONFUSED:
			return EEntity.STATUS_DECORATOR_CONFUSED;
		case DEAD:
			return EEntity.STATUS_DECORATOR_DEAD;
		case SLEEPING:
			return EEntity.STATUS_DECORATOR_SLEEPING;
		case NONE:
		default:
			return EEntity.STATUS_DECORATOR_NONE;
		}
	}
}