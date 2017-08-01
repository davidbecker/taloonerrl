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

import de.brainstormsoftworks.taloonerrl.components.CameraFollowComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * this system set the world camera to follow an entity.<br>
 * currently it is expected to be only one entity and one camera<br>
 * TODO refactor
 *
 * @author David Becker
 *
 */
public class CameraSystem extends IteratingSystem {

	private PositionComponent positionComponent;

	public CameraSystem() {
		super(Aspect.all(PositionComponent.class, CameraFollowComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		// if the full map is visible we center the camera on the screen
		// if the screen isn't big enough to display the whole map, we follow the player
		if (!Renderer.getInstance().isFullMapVisible()) {
			positionComponent = ComponentMappers.getInstance().position.get(_entityId);
			Renderer.getInstance().setWorldCamera(positionComponent.getX(), positionComponent.getY(),
					positionComponent.getOffsetX(), positionComponent.getOffsetY());
		}
	}

}
