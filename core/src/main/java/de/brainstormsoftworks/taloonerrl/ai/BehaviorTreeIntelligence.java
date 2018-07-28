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
package de.brainstormsoftworks.taloonerrl.ai;

import com.artemis.Entity;
import com.artemis.annotations.EntityId;
import com.badlogic.gdx.ai.btree.BehaviorTree;

/**
 * a more sophisticated intelligence than {@link BasicIntelligence} that
 * operates on a behavior tree
 *
 * @author David Becker
 *
 */
public class BehaviorTreeIntelligence implements IAI {

	private final BehaviorTree<Entity> behaviorTree;

	@EntityId
	private int entityId = -1;

	public BehaviorTreeIntelligence(final BehaviorTree<Entity> _behaviorTree) {
		behaviorTree = _behaviorTree;
	}

	public void setEntity(final Entity _entity) {
		if (behaviorTree != null) {
			behaviorTree.setObject(_entity);
		}
		if (_entity != null) {
			entityId = _entity.getId();
		}
	}

	@Override
	public void update() {
		behaviorTree.step();
	}

}
