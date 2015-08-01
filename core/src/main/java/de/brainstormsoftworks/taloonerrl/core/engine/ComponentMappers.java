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

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;

/**
 * singleton to get the {@link Component}s for our {@link World}
 *
 * @author David Becker
 *
 */
public final class ComponentMappers {
	public final ComponentMapper<ControllerComponent> controller;
	public final ComponentMapper<HealthComponent> health;
	public final ComponentMapper<PositionComponent> position;
	public final ComponentMapper<AnimationComponent> sprite;

	private static ComponentMappers instance = null;

	private ComponentMappers(final World world) {
		controller = ComponentMapper.getFor(ControllerComponent.class, world);
		health = ComponentMapper.getFor(HealthComponent.class, world);
		position = ComponentMapper.getFor(PositionComponent.class, world);
		sprite = ComponentMapper.getFor(AnimationComponent.class, world);
	}

	/**
	 * initializes the singleton
	 *
	 * @param world
	 *            for the {@link ComponentMapper}
	 */
	public static void mapComponents(final World world) {
		instance = new ComponentMappers(world);
	}

	/**
	 * way to access the singleton instance of this mapper
	 *
	 * @return {@link #instance}
	 * @throws IllegalStateException
	 *             when {@link #mapComponents(World)} wasn't called before
	 */
	public static ComponentMappers getInstance() {
		if (instance == null) {
			throw new IllegalStateException("not instantiated");
		}
		return instance;
	}
}
