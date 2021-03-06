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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;

import de.brainstormsoftworks.taloonerrl.components.ArtificialIntelligenceComponent;
import de.brainstormsoftworks.taloonerrl.components.CollectibleComponent;
import de.brainstormsoftworks.taloonerrl.components.EEntityState;
import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.InventoryComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.StatusComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.TurnScheduler;
import de.brainstormsoftworks.taloonerrl.render.Renderer;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import de.brainstormsoftworks.taloonerrl.system.util.StateDecorationUtil;

/**
 * this system updates the controller component of the player entity when an
 * input was registered
 *
 * @author David Becker
 *
 */
public class TurnProcessSystem extends IteratingSystem {

	private PositionComponent positionComponent;
	private FacingComponent facingComponent;
	private TurnComponent turnComponent;
	private ArtificialIntelligenceComponent artificialIntelligenceComponent;
	private StatusComponent statusComponent;
	private InventoryComponent inventoryComponent;
	private boolean isPlayer;
	private int nextTurn;
	private boolean turnForcefullySkipped;

	public TurnProcessSystem() {
		super(Aspect.all(PositionComponent.class, TurnComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		nextTurn = Move.IDLE;
		turnForcefullySkipped = false;
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		if (PositionUtil.isValidPosition(positionComponent) && !positionComponent.isProcessingTurn()) {
			isPlayer = ComponentMappers.getInstance().player.getSafe(_entityId) != null;
			turnComponent = ComponentMappers.getInstance().turn.get(_entityId);
			if (!turnComponent.isProcessed()
					&& turnComponent.getMovesOnTurn() == GameEngine.getInstance().getCurrentTurnSide()) {

				// check if we have an active state that blocks the turn for the entity
				statusComponent = ComponentMappers.getInstance().states.getSafe(_entityId);
				if (statusComponent != null && statusComponent.isBlockingStatusActive()) {
					nextTurn = Move.WAIT;
					turnForcefullySkipped = true;
				} else {
					if (isPlayer) {
						// TODO refactor into "intelligence" for player entity?
						nextTurn = turnComponent.nextTurn(TurnScheduler.getInstance().getNextTurn());
					} else {
						artificialIntelligenceComponent = ComponentMappers.getInstance().ai
								.getSafe(_entityId);
						if (artificialIntelligenceComponent != null) {
							artificialIntelligenceComponent.update();
						}
						nextTurn = turnComponent.getCurrentTurn();
					}
				}

				if (nextTurn != Move.IDLE) {
					if (nextTurn == Move.DOWN) {
						positionComponent.setOffsetY(0);
						positionComponent.setTotalY(-1 * Renderer.tileSize);
					} else if (nextTurn == Move.UP) {
						positionComponent.setOffsetY(1);
						positionComponent.setTotalY(Renderer.tileSize);
					} else if (nextTurn == Move.LEFT) {
						positionComponent.setOffsetX(0);
						positionComponent.setTotalX(-1 * Renderer.tileSize);
					} else if (nextTurn == Move.RIGHT) {
						positionComponent.setOffsetX(0);
						positionComponent.setTotalX(Renderer.tileSize);
					}
					if (nextTurn != Move.WAIT) {
						turnComponent.setTurnTaken(true);
					} else {
						if (!turnForcefullySkipped) {
							StateDecorationUtil.getInsance().spawnStatusDecoration(_entityId,
									EEntityState.WAITING, -0.5f);
						}
					}
					if (isPlayer && nextTurn != Move.WAIT) {
						facingComponent = ComponentMappers.getInstance().facing.getSafe(_entityId);
						if (facingComponent != null) {
							facingComponent.setDirection(nextTurn);
						}
					}

					// pickup any items on waiting turns
					if (nextTurn == Move.WAIT) {
						inventoryComponent = ComponentMappers.getInstance().inventory.getSafe(_entityId);
						if (inventoryComponent != null && inventoryComponent.hasCapacity()) {
							final IntBag entities = GameEngine.getInstance().getAspectSubscriptionManager()
									.get(Aspect.all(PositionComponent.class, CollectibleComponent.class))
									.getEntities();
							PositionComponent otherPosition;
							int otherId = -1;
							for (int i = 0; i < entities.size(); i++) {
								otherId = entities.get(i);
								otherPosition = ComponentMappers.getInstance().position.getSafe(otherId);
								if (PositionUtil.isValidPosition(otherPosition)) {
									if (otherPosition.getX() == positionComponent.getX()
											&& otherPosition.getY() == positionComponent.getY()) {
										inventoryComponent.addEntity(otherId);
									}
								}
							}
						}
					}

					turnComponent.setProcessed(true);
					// TODO refactor into own system in the future? - should be OK for now though
					if (statusComponent != null) {
						statusComponent.processCooldowns();
					}
				}
			}
		}
	}

}
