/*******************************************************************************
 * Copyright (c) 2018 David Becker.
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

import lombok.Getter;
import lombok.Setter;

/**
 * component to mark an entity as being explored by the player
 *
 * @author David Becker
 *
 */
@Getter
@Setter
public class ExploredComponent extends PooledComponent implements ISetAbleComponent<ExploredComponent> {

	private boolean explored = false;

	@Override
	protected void reset() {
		explored = false;
	}

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final ExploredComponent _component) {
		explored = _component.isExplored();
	}
}
