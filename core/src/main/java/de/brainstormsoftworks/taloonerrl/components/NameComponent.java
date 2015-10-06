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
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

import lombok.Getter;
import lombok.Setter;

/**
 * component to give a name and a description to an entity
 *
 * @author David Becker
 *
 */
@Getter
@Setter
public class NameComponent extends PooledComponent implements ISetAbleComponent<NameComponent> {

	private String name = "";
	private String description = "";

	@Override
	protected void reset() {
		name = "";
		description = "";
	}

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final NameComponent _component) {
		name = _component.getName();
		description = _component.getDescription();
	}
}
