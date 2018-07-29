/*******************************************************************************
 * Copyright (c) 2016-2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.system.util;

import com.artemis.Aspect;
import com.artemis.utils.IntBag;

import de.brainstormsoftworks.taloonerrl.components.EEntityState;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.StatusComponent;
import de.brainstormsoftworks.taloonerrl.components.TargetComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import squidpony.squidmath.Coord;

/**
 * collection of utility methods related to positions
 *
 * @author David Becker
 *
 */
public final class PositionUtil {

	private PositionUtil() {
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 * @throws NullPointerException
	 *             when position is null
	 */
	public static final int getDeltaX(final PositionComponent position) {
		return getDelta(position.getTotalX());
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 * @throws NullPointerException
	 *             when position is null
	 */
	public static final int getDeltaY(final PositionComponent position) {
		return getDelta(position.getTotalY());
	}

	/**
	 * get the number of tiles that the entity should move
	 *
	 * @param position
	 * @return 1, -1 or 0
	 */
	public static final int getDelta(final int totalXY) {
		return totalXY == Renderer.tileSize ? 1 : totalXY == -1 * Renderer.tileSize ? -1 : 0;
	}

	public static final boolean isMovingWholeTile(final int deltaX, final int deltaY) {
		return deltaX != 0 || deltaY != 0;
	}

	/**
	 * checks if there is an alive entity on the given position
	 *
	 * @return if there isn't no entity on the given position
	 */
	public static final boolean isPositionTaken(final int x, final int y) {
		return isPositionTaken(x, y, -1);
	}

	/**
	 * check if a given position is taken by an alive entity (static or in movement)
	 *
	 * @param x
	 * @param y
	 * @param skipEntityId
	 * @return
	 */
	public static final boolean isPositionTaken(final int x, final int y, final int skipEntityId) {
		final IntBag entities = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, HealthComponent.class)).getEntities();
		int entityID;
		PositionComponent positionComponent;
		HealthComponent healthComponent;
		for (int i = 0; i < entities.size(); i++) {
			entityID = entities.get(i);
			if (entityID != skipEntityId) {
				healthComponent = ComponentMappers.getInstance().health.get(entityID);
				if (healthComponent.isAlive()) {
					positionComponent = ComponentMappers.getInstance().position.get(entityID);
					if (isPositionTakenByComponent(x, y, positionComponent)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static final boolean isPositionTakenByComponent(final int x, final int y,
			final PositionComponent positionComponent) {
		if (isValidPosition(positionComponent)) {
			if (positionComponent.getX() == x && positionComponent.getY() == y || positionComponent.getX()
					+ nominalDirection(positionComponent.getTotalX()) == x
					&& positionComponent.getY() + nominalDirection(positionComponent.getTotalY()) == y) {
				return true;
			}
		}
		return false;
	}

	public static int nominalDirection(final int delta) {
		return delta > 0 ? 1 : delta < 0 ? -1 : 0;
	}

	/**
	 * attacks all entities on a given map tile by the given entity with a melee
	 * attack
	 *
	 * @param x
	 * @param y
	 * @param _attackerEntityId
	 * @return
	 */
	public static boolean attackEntitiesOnPosition(final int x, final int y, final int _attackerEntityId) {
		boolean attacked = false;
		final IntBag entities = GameEngine.getInstance().getAspectSubscriptionManager()
				.get(Aspect.all(PositionComponent.class, HealthComponent.class)).getEntities();
		int entityID;

		PositionComponent positionComponent;
		HealthComponent healthComponent;
		StatusComponent statusComponent;
		TargetComponent targetComponent;
		int targetId;
		for (int i = 0; i < entities.size(); i++) {
			entityID = entities.get(i);
			healthComponent = ComponentMappers.getInstance().health.get(entityID);
			if (healthComponent.isAlive()) {
				positionComponent = ComponentMappers.getInstance().position.get(entityID);
				if (isValidPosition(positionComponent) && positionComponent.getX() == x
						&& positionComponent.getY() == y) {
					// TODO get some sort of attacker component for _attackerEntityId?
					// add half the health as damage for now
					healthComponent.addDamage(healthComponent.getHealthMax() / 3);
					attacked = true;
					// check if entity is sleeping -> wake it up then
					statusComponent = ComponentMappers.getInstance().states.getSafe(entityID);
					if (statusComponent != null && statusComponent.isActive(EEntityState.SLEEPING)) {
						statusComponent.getSleepStatus().setActive(false);
						statusComponent.activateState(EEntityState.ALERT, 5);
					}
					// if the entity has no target yet it gets the attacker as a target
					// TODO exclude player entity from getting target set?
					targetComponent = ComponentMappers.getInstance().target.getSafe(entityID);
					if (targetComponent != null) {
						targetId = targetComponent.getTargetId();
						if (targetId == -1) {
							targetComponent.setTargetId(_attackerEntityId);
						}
					}

				}
			}
		}
		return attacked;
	}

	/**
	 * convenience/wrapper method to check if two positions are adjacent to each
	 * other.<br>
	 * see {@link Coord#isAdjacent(Coord)}
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static boolean arePositionsAdjacent(final PositionComponent pos1, final PositionComponent pos2) {
		return isValidPosition(pos1) && isValidPosition(pos2)
				&& arePositionsAdjacent(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
	}

	/**
	 * convenience/wrapper method to check if two positions are adjacent to each
	 * other.<br>
	 * see {@link Coord#isAdjacent(Coord)}
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static boolean arePositionsAdjacent(final int x1, final int y1, final int x2, final int y2) {
		return x1 == x2 && Math.abs(y1 - y2) == 1 || y1 == y2 && Math.abs(x1 - x2) == 1;
	}

	public static Coord convertToSquidCoord(final PositionComponent pos) {
		return Coord.get(pos.getX(), pos.getY());
	}

	public static Coord apply(final Coord coord, final int direction) {
		switch (direction) {
		case Move.UP:
			return coord.translate(0, 1);
		case Move.DOWN:
			return coord.translate(0, -1);
		case Move.LEFT:
			return coord.translate(-1, 0);
		case Move.RIGHT:
			return coord.translate(1, 0);
		default:
			return coord;
		}
	}

	/**
	 * checks whether the given component should be processed
	 */
	public static boolean isValidPosition(final PositionComponent component) {
		return component != null && (component.x >= 0 || component.y >= 0);
	}
}
