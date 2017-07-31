/*******************************************************************************
 * Copyright (c) 2016 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/

package de.brainstormsoftworks.taloonerrl.core.engine;

import com.badlogic.gdx.InputAdapter;

/**
 * input adaptar that processes input that is relevant for the user interface
 *
 * @author David Becker
 *
 */
public class UserIntefaceInputProcessor extends InputAdapter {

	private static final UserIntefaceInputProcessor instance = new UserIntefaceInputProcessor();

	/**
	 * getter for the instance of the singleton
	 *
	 * @return instance
	 */
	public static UserIntefaceInputProcessor getInstance() {
		return instance;
	}

}
