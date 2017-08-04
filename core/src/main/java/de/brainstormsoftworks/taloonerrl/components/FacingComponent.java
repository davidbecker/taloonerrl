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

import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import lombok.Getter;
import lombok.Setter;

/**
 * component for an entity that has a direction to face
 *
 * @author David Becker
 *
 */
public class FacingComponent extends PooledComponent {
	private @Getter @Setter int direction = Move.RIGHT;

	@Override
	protected void reset() {
		direction = Move.RIGHT;
	}

}
