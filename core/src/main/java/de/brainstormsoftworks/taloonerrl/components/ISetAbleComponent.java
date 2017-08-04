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

import com.artemis.Component;

/**
 * interface for an component that can be set with an other object of the same
 * type as an input.<br>
 * clients of this interface are expected to provide a way to set all fields to
 * the values of the input object
 *
 * @author David Becker
 * @param <T>
 *            type of component
 */
public interface ISetAbleComponent<T extends Component> {

	/**
	 * overrides all fields of this object with the values of the fields of the
	 * parameter object
	 *
	 * @param component
	 *            component to take the values from
	 */
	void overrideComponent(T component);

}
