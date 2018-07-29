/*******************************************************************************
 * Copyright (c) 2015-2018 David Becker.
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

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.ArtificialIntelligenceComponent;
import de.brainstormsoftworks.taloonerrl.components.CameraFollowComponent;
import de.brainstormsoftworks.taloonerrl.components.CollectibleComponent;
import de.brainstormsoftworks.taloonerrl.components.CursorComponent;
import de.brainstormsoftworks.taloonerrl.components.ExploredComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.InventoryComponent;
import de.brainstormsoftworks.taloonerrl.components.NameComponent;
import de.brainstormsoftworks.taloonerrl.components.PlayerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.components.StatusComponent;
import de.brainstormsoftworks.taloonerrl.components.TargetComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;

/**
 * singleton to get the {@link Component}s for our {@link World}
 *
 * @author David Becker
 *
 */
public final class ComponentMappers {
	public final ComponentMapper<FacingComponent> facing;
	public final ComponentMapper<HealthComponent> health;
	public final ComponentMapper<PositionComponent> position;
	public final ComponentMapper<AnimationComponent> animation;
	public final ComponentMapper<FacingAnimationComponent> facingAnimation;
	public final ComponentMapper<SpriteComponent> sprite;
	public final ComponentMapper<NameComponent> name;
	public final ComponentMapper<HighlightAbleComponent> highlight;
	public final ComponentMapper<CameraFollowComponent> camera;
	public final ComponentMapper<CollectibleComponent> collectible;
	public final ComponentMapper<PlayerComponent> player;
	public final ComponentMapper<CursorComponent> cursor;
	public final ComponentMapper<TurnComponent> turn;
	public final ComponentMapper<ArtificialIntelligenceComponent> ai;
	public final ComponentMapper<StatusComponent> states;
	public final ComponentMapper<StateDecorationComponent> stateDecoration;
	public final ComponentMapper<TargetComponent> target;
	public final ComponentMapper<ExploredComponent> explored;
	public final ComponentMapper<InventoryComponent> inventory;

	private static ComponentMappers instance = null;

	private ComponentMappers(final World world) {
		facing = ComponentMapper.getFor(FacingComponent.class, world);
		health = ComponentMapper.getFor(HealthComponent.class, world);
		position = ComponentMapper.getFor(PositionComponent.class, world);
		animation = ComponentMapper.getFor(AnimationComponent.class, world);
		facingAnimation = ComponentMapper.getFor(FacingAnimationComponent.class, world);
		sprite = ComponentMapper.getFor(SpriteComponent.class, world);
		name = ComponentMapper.getFor(NameComponent.class, world);
		highlight = ComponentMapper.getFor(HighlightAbleComponent.class, world);
		camera = ComponentMapper.getFor(CameraFollowComponent.class, world);
		collectible = ComponentMapper.getFor(CollectibleComponent.class, world);
		player = ComponentMapper.getFor(PlayerComponent.class, world);
		cursor = ComponentMapper.getFor(CursorComponent.class, world);
		turn = ComponentMapper.getFor(TurnComponent.class, world);
		ai = ComponentMapper.getFor(ArtificialIntelligenceComponent.class, world);
		states = ComponentMapper.getFor(StatusComponent.class, world);
		stateDecoration = ComponentMapper.getFor(StateDecorationComponent.class, world);
		target = ComponentMapper.getFor(TargetComponent.class, world);
		explored = ComponentMapper.getFor(ExploredComponent.class, world);
		inventory = ComponentMapper.getFor(InventoryComponent.class, world);
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
