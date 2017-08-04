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

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;

import de.brainstormsoftworks.taloonerrl.ai.BasicIntelligence;
import de.brainstormsoftworks.taloonerrl.ai.states.BatState;
import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.ArtificialIntelligenceComponent;
import de.brainstormsoftworks.taloonerrl.components.EEntityState;
import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.NameComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.components.StateDecorationComponent;
import de.brainstormsoftworks.taloonerrl.components.StatusComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.ETurnType;

/**
 * factory to create entities in to the game world
 *
 * @author David Becker
 *
 */
public final class EntityFactory {

	private EntityFactory() {
	}

	/**
	 * creates a new entity
	 *
	 * @param type
	 *            of entity
	 * @param world
	 *            world to create the entity in
	 * @param xPosition
	 *            horizontal position (tiles position)
	 * @param yPosition
	 *            vertical position (tiles position)
	 * @return new entity
	 */
	protected static Entity createEntity(final EEntity type, final World world, final int xPosition,
			final int yPosition) {
		switch (type) {
		case PLAYER:
			return createPlayer(world);
		case BLOB:
		case SQUIRREL:
		case BAT:
		case MAGICIAN:
		case SLUG:
		case GHOST:
		case SHADOW:
		case EYEBALL:
		case GOLEM:
		case ARCHER:
		case BOMB:
		case WARPER:
		case STEALER:
		case DRAGON:
		case ACID:
		case MUSHROOM:
			return createMonster(type, world, xPosition, yPosition);
		case POTION_A:
		case POTION_B:
		case POTION_C:
		case POTION_D:
		case POTION_E:
		case POTION_F:
		case POTION_G:
		case POTION_H:
		case POTION_I:
		case POTION_J:
		case SCROLL_A:
		case SCROLL_B:
		case SCROLL_C:
		case SCROLL_D:
		case SCROLL_E:
		case SCROLL_F:
		case SCROLL_G:
		case SCROLL_H:
		case SCROLL_I:
		case SCROLL_J:
		case WAND_A:
		case WAND_B:
		case WAND_C:
		case WAND_D:
		case WAND_E:
		case WAND_F:
		case WAND_G:
		case WAND_H:
		case WAND_I:
		case WAND_J:
		case RING_A:
		case RING_B:
		case RING_C:
		case RING_D:
		case RING_E:
		case RING_F:
		case RING_G:
		case RING_H:
		case RING_I:
		case RING_J:
		case WEAPON_A:
		case WEAPON_B:
		case WEAPON_C:
		case WEAPON_D:
		case WEAPON_E:
		case WEAPON_F:
		case WEAPON_G:
		case WEAPON_H:
		case WEAPON_I:
		case WEAPON_J:
		case SHIELD_A:
		case SHIELD_B:
		case SHIELD_C:
		case SHIELD_D:
		case SHIELD_E:
		case SHIELD_F:
		case SHIELD_G:
			return createCollectible(type, world, xPosition, yPosition);
		case TORCH:
			return createDecoration(type, world, xPosition, yPosition);
		case CURSOR:
			return world.createEntity(Archetypes.getInstance().cursor);
		case STATUS_DECORATOR_SLEEPING:
			return createStatusDecorator(world, xPosition, yPosition);
		default:
			return null;
		}
	}

	private static Entity createStatusDecorator(final World _world, final int _xPosition,
			final int _yPosition) {
		long ttl = 0L;
		// look if there are already decorators on the given position -> if this is the
		// case, we need to get the biggest time to live to add to the new entity's TTL
		final IntBag entities = _world.getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, StateDecorationComponent.class)).getEntities();
		int entityId;
		StateDecorationComponent decorationComponent;
		PositionComponent positionComponent;
		for (int i = 0; i < entities.size(); i++) {
			entityId = entities.get(i);
			decorationComponent = ComponentMappers.getInstance().stateDecoration.get(entityId);
			if (decorationComponent.isActive()) {
				positionComponent = ComponentMappers.getInstance().position.get(entityId);
				if (positionComponent.getX() == _xPosition && positionComponent.getY() == _yPosition) {
					if (decorationComponent.getTimeToLive() > ttl) {
						ttl = decorationComponent.getTimeToLive();
					}
				}
			}
		}
		final Entity entity = _world.createEntity(Archetypes.getInstance().stateDecorator);
		decorationComponent = ComponentMappers.getInstance().stateDecoration.get(entity.getId());
		decorationComponent.setTimeToLive(ttl + StateDecorationComponent.TTL_BASE);
		decorationComponent.setActive(true);
		return entity;
	}

	private static Entity createPlayer(final World world) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().player);
		newEntity.getComponent(FacingAnimationComponent.class).mapAnimation(EEntity.PLAYER);
		newEntity.getComponent(TurnComponent.class).setMovesOnTurn(ETurnType.PLAYER);
		setName(newEntity, EEntity.PLAYER);
		return newEntity;
	}

	private static Entity createMonster(final EEntity type, final World world, final int xPosition,
			final int yPosition) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().monster);
		newEntity.getComponent(AnimationComponent.class).mapAnimation(type);
		final PositionComponent posComponent = newEntity.getComponent(PositionComponent.class);
		posComponent.setX(xPosition);
		posComponent.setY(yPosition);
		setName(newEntity, type);
		setAI(newEntity, type);
		setInitialStates(newEntity, type);
		return newEntity;
	}

	private static Entity createCollectible(final EEntity type, final World world, final int xPosition,
			final int yPosition) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().collectible);
		newEntity.getComponent(SpriteComponent.class).mapSprite(type);
		final PositionComponent posComponent = newEntity.getComponent(PositionComponent.class);
		posComponent.setX(xPosition);
		posComponent.setY(yPosition);
		return newEntity;
	}

	private static Entity createDecoration(final EEntity type, final World world, final int xPosition,
			final int yPosition) {
		final Entity newEntity = world.createEntity(Archetypes.getInstance().decoration);
		newEntity.getComponent(AnimationComponent.class).mapAnimation(type);
		final PositionComponent posComponent = newEntity.getComponent(PositionComponent.class);
		posComponent.setX(xPosition);
		posComponent.setY(yPosition);
		return newEntity;
	}

	private static void setName(final Entity entity, final EEntity type) {
		// TODO refactor
		final NameComponent component = entity.getComponent(NameComponent.class);
		switch (type) {
		case BLOB:
			component.setName("Blob");
			component.setDescription("Your most generic opponent");
			break;
		case PLAYER:
			component.setName("Adventurer");
			component.setDescription("Who might this be?");
			break;
		case SQUIRREL:
			component.setName("Squirrel");
			component.setDescription("A rat-like creature with a tail");
			break;
		case BAT:
			component.setName("Bat");
			component.setDescription("A flapping creature");
			break;
		case SLUG:
			component.setName("Slug");
			break;
		case MAGICIAN:
			component.setName("Magician");
			break;
		case GHOST:
			component.setName("Ghost");
			break;
		case SHADOW:
			component.setName("Shadow");
			break;
		case EYEBALL:
			component.setName("Giant Eyeball");
			break;
		case GOLEM:
			component.setName("Golem");
			break;
		case ARCHER:
			component.setName("Archer");
			break;
		case WARPER:
			component.setName("Warper");
			break;
		case STEALER:
			component.setName("Stealer");
			break;
		case DRAGON:
			component.setName("Dragon");
			break;
		case ACID:
			component.setName("Acid Slime");
			break;
		case MUSHROOM:
			component.setName("Mushroom");
			break;
		default:
			break;
		}

	}

	private static void setAI(final Entity entity, final EEntity type) {
		final ArtificialIntelligenceComponent aiComponent = ComponentMappers.getInstance().ai.getSafe(entity);
		if (aiComponent != null) {
			switch (type) {
			case BAT:
				aiComponent.setArtificialIntelligence(new BasicIntelligence(
						new DefaultStateMachine<Entity, State<Entity>>(entity, BatState.FLYING_ERRATICALLY)));
				break;
			default:
				aiComponent.setArtificialIntelligence(new BasicIntelligence(
						new DefaultStateMachine<Entity, State<Entity>>(entity, BatState.WAITING)));
				break;
			}
		}
	}

	private static void setInitialStates(final Entity entity, final EEntity type) {
		final StatusComponent component = ComponentMappers.getInstance().states.getSafe(entity);
		component.setEntityId(entity.getId());
		if (component != null) {
			switch (type) {
			case BAT:
				// leave the bats alone for now
				break;
			default:
				component.activateState(EEntityState.SLEEPING, 10);
				// component.activateState(EEntityState.SLEEPING, Integer.MAX_VALUE);
				break;
			}
			component.setEntityId(entity.getId());
		}
	}
}
