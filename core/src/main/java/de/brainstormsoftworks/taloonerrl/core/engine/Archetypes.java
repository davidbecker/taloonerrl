/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
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

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.ArtificialIntelligenceComponent;
import de.brainstormsoftworks.taloonerrl.components.CameraFollowComponent;
import de.brainstormsoftworks.taloonerrl.components.CollectibleComponent;
import de.brainstormsoftworks.taloonerrl.components.CursorComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.NameComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.components.StatusComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;

/**
 * utility class to load the archetypes used to create entities for the given
 * world
 *
 * @author David Becker
 *
 */
public final class Archetypes {
	/** decorator for an entity */
	public final Archetype stateDecorator;
	/** archetype for general actor */
	public final Archetype actor;
	public final Archetype monster;
	/** archetype for monsters & player */
	public final Archetype player;
	public final Archetype collectible;
	public final Archetype decoration;
	public final Archetype highlightAble;

	public final Archetype cursor;
	private static Archetypes instance;

	private Archetypes(final World world) {
		stateDecorator = new ArchetypeBuilder().add(PositionComponent.class)
				.add(StateDecorationComponent.class).add(AnimationComponent.class).build(world);
		highlightAble = new ArchetypeBuilder().add(PositionComponent.class).add(HighlightAbleComponent.class)
				.build(world);
		actor = new ArchetypeBuilder(highlightAble).add(HealthComponent.class).add(AnimationComponent.class)
				.add(NameComponent.class).add(TurnComponent.class).add(StatusComponent.class).build(world);
		monster = new ArchetypeBuilder(actor).add(ArtificialIntelligenceComponent.class).build(world);
		player = new ArchetypeBuilder(actor).add(PlayerComponent.class).add(FacingComponent.class)
				.remove(AnimationComponent.class).add(FacingAnimationComponent.class)
				.add(CameraFollowComponent.class).build(world);
		collectible = new ArchetypeBuilder().add(PositionComponent.class).add(SpriteComponent.class)
				.add(CollectibleComponent.class).build(world);
		decoration = new ArchetypeBuilder().add(PositionComponent.class).add(AnimationComponent.class)
				.build(world);
		cursor = new ArchetypeBuilder(highlightAble).add(CursorComponent.class).build(world);
	}

	/**
	 * initializes the archetypes for the given world
	 *
	 * @param world
	 *            to use for initialization
	 */
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
