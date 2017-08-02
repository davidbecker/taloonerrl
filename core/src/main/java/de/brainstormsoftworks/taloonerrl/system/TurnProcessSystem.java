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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;

import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.core.engine.Move;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.TurnScheduler;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

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
	private boolean isPlayer;
	private int nextTurn;

	public TurnProcessSystem() {
		super(Aspect.all(PositionComponent.class, TurnComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		if (!positionComponent.isProcessingTurn()) {
			isPlayer = ComponentMappers.getInstance().player.getSafe(_entityId) != null;
			turnComponent = ComponentMappers.getInstance().turn.get(_entityId);
			if (!turnComponent.isProcessed()
					&& turnComponent.getMovesOnTurn() == GameEngine.getInstance().getCurrentTurnSide()) {
				// for now only the player can have turns...
				if (isPlayer) {
					nextTurn = turnComponent.nextTurn(TurnScheduler.getInstance().getNextTurn());
				} else {
					// TODO implement ai
					nextTurn = turnComponent.nextTurn(Move.WAIT);
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
					}
					if (isPlayer && nextTurn != Move.WAIT) {
						facingComponent = ComponentMappers.getInstance().facing.getSafe(_entityId);
						if (facingComponent != null) {
							facingComponent.setDirection(nextTurn);
						}
					}
					turnComponent.setProcessed(true);
				}
			}
		}
	}

}
