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
package de.brainstormsoftworks.taloonerrl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.brainstormsoftworks.taloonerrl.actors.ActorFactory;
import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;

public class TestActorFactory {

	@Test
	public void test() {
		assertNotNull("player expected, but no actor created",
				ActorFactory.createActor(EActorTypes.PLAYER));
	}

}
