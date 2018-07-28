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

import com.artemis.PooledComponent;
import com.artemis.annotations.EntityId;

import lombok.Getter;
import lombok.Setter;

/**
 * component for entities that can target an other entity. holds the reference
 * to the ID of the target entity
 *
 * @author David Becker
 *
 */
@Getter
@Setter
public class TargetComponent extends PooledComponent {

	@EntityId
	private int targetId = -1;

	@Override
	public void reset() {
		targetId = -1;
	}

}
