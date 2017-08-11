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

import java.io.Reader;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.utils.StreamUtils;

import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;

/**
 *
 * utility class to create behavior trees for {@link BehaviorTreeIntelligence}
 * to use
 *
 * @author David Becker
 *
 */
public final class BehaviorTreeFactory {

	private BehaviorTreeFactory() {
	}

	public static BehaviorTree<Entity> createBehaviorTree(final Entity entity, final EEntity type) {
		Reader reader = null;
		try {
			reader = Gdx.files.internal("data/test.tree").reader();
			final BehaviorTreeParser<Entity> parser = new BehaviorTreeParser<>(BehaviorTreeParser.DEBUG_HIGH);
			final BehaviorTree<Entity> tree = parser.parse(reader, entity);
			return tree;
		} finally {
			StreamUtils.closeQuietly(reader);
		}
	}

}
