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
package de.brainstormsoftworks.taloonerrl.render;

/**
 * interface for a utility class that should be disposed
 *
 * @author David Becker
 *
 */
public interface IDisposableInstance {
	/**
	 * disposes all resources that are loaded by this instance
	 */
	void disposeInstance();
}
