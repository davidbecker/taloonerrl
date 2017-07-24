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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.TurnComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.Direction;
import de.brainstormsoftworks.taloonerrl.core.engine.scheduler.TurnScheduler;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * this system updates the controller component of the player entity when an
 * input was registered
 *
 * @author David Becker
 *
 */
public class TurnProcessSystem extends EntityProcessingSystem {

	private PositionComponent positionComponent;
	private FacingComponent facingComponent;
	private TurnComponent turnComponent;
	private boolean isPlayer;
	private int nextTurn;

	public TurnProcessSystem() {
		super(Aspect.all(PositionComponent.class, TurnComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		if (!positionComponent.isProcessingTurn()) {
			isPlayer = ComponentMappers.getInstance().player.getSafe(e) != null;
			turnComponent = ComponentMappers.getInstance().turn.get(e);

			// for now only the player can have turns...
			if (isPlayer) {
				nextTurn = turnComponent.nextTurn(TurnScheduler.getInstance().getNextTurn());
			} else {
				nextTurn = turnComponent.nextTurn();
			}

			if (nextTurn != Direction.NOTHING) {
				if (nextTurn == Direction.DOWN) {
					positionComponent.setOffsetY(0);
					positionComponent.setTotalY(-1 * Renderer.tileSize);
				} else if (nextTurn == Direction.UP) {
					positionComponent.setOffsetY(1);
					positionComponent.setTotalY(Renderer.tileSize);
				} else if (nextTurn == Direction.LEFT) {
					positionComponent.setOffsetX(0);
					positionComponent.setTotalX(-1 * Renderer.tileSize);
				} else if (nextTurn == Direction.RIGHT) {
					positionComponent.setOffsetX(0);
					positionComponent.setTotalX(Renderer.tileSize);
				}
				positionComponent.setProcessingTurn(true);
				if (isPlayer) {
					facingComponent = ComponentMappers.getInstance().facing.getSafe(e);
					if (facingComponent != null) {
						facingComponent.setDirection(nextTurn);
					}
				}
			}

		}
	}

}
