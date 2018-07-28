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

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.ETurnType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * helper component to render a new status for an entity
 *
 * @author David Becker
 *
 */
@PooledWeaver
@Getter
@Setter
public class StateDecorationComponent extends Component {

	private boolean active = false;
	private float timeToLiveStart = 0f;
	private float timeToLive = 0f;
	private EEntityState state = null;
	private ETurnType killOnTurn = null;

}
