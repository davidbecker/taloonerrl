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

/**
 * component to give a name and a description to an entity
 *
 * @author David Becker
 *
 */
public class NameComponent extends PooledComponent {

	private String name = "";
	private String description = "";

	@Override
	protected void reset() {
		name = "";
		description = "";
	}

	/**
	 * getter for {@link #name}
	 *
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * setter for name
	 *
	 * @param _name
	 *            the name to set
	 */
	public final void setName(final String _name) {
		name = _name;
	}

	/**
	 * getter for {@link #description}
	 *
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * setter for description
	 *
	 * @param _description
	 *            the description to set
	 */
	public final void setDescription(final String _description) {
		description = _description;
	}

}
