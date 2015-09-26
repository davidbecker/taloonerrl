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
public class NameComponent extends PooledComponent {

	private @Getter @Setter String name = "";
	private @Getter @Setter String description = "";

	@Override
	protected void reset() {
		name = "";
		description = "";
	}
}
