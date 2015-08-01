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
package de.brainstormsoftworks.taloonerrl.core.engine;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;

public final class Archetypes {
	/** archetype for monsters & player */
	public final Archetype actor;
	public final Archetype decoration;
	private static Archetypes instance;

	private Archetypes(final World world) {
		actor = new ArchetypeBuilder().add(ControllerComponent.class).add(HealthComponent.class)
				.add(PositionComponent.class).add(AnimationComponent.class).build(world);
		decoration = new ArchetypeBuilder().add(PositionComponent.class).add(AnimationComponent.class).build(world);
	}

	public static void createArchetypes(final World world) {
		instance = new Archetypes(world);
	}

	/**
	 * way to access the singleton instance
	 *
	 * @return {@link #instance}
	 * @throws IllegalStateException
	 *             when {@link #createArchetypes(World)} wasn't called before
	 */
	public static Archetypes getInstance() {
		if (instance == null) {
			throw new IllegalStateException("not instantiated");
		}
		return instance;
	}
}
