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
package de.brainstormsoftworks.taloonerrl.internal.render;

import com.artemis.EntitySubscription;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;

import de.brainstormsoftworks.taloonerrl.render.IRender;

/**
 * after creating this I realized I have basically reimplemented
 * {@link IteratingSystem} :/
 *
 * @author David Becker
 *
 */
abstract class AbstractRender implements IRender {

	final protected EntitySubscription entitySubscription;
	private IntBag entities;

	AbstractRender(final EntitySubscription _entitySubscription) {
		entitySubscription = _entitySubscription;
	}

	@Override
	public void render() {
		entities = entitySubscription.getEntities();
		for (int i = 0; i < entities.size(); i++) {
			process(entities.get(i));
		}
	}

	/**
	 * to be implemented by clients
	 *
	 * @param _entityId
	 */
	protected abstract void process(int _entityId);

}
