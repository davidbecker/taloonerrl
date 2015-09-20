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

import com.artemis.WorldConfiguration;

import de.brainstormsoftworks.taloonerrl.system.AnimationRenderSystem;
import de.brainstormsoftworks.taloonerrl.system.CameraSystem;
import de.brainstormsoftworks.taloonerrl.system.FacingAnimationRenderSystem;
import de.brainstormsoftworks.taloonerrl.system.FovUpdaterSystem;
import de.brainstormsoftworks.taloonerrl.system.HealthBarRenderSystem;
import de.brainstormsoftworks.taloonerrl.system.HighlightRenderSystem;
import de.brainstormsoftworks.taloonerrl.system.MapOverlayPreparerSystem;
import de.brainstormsoftworks.taloonerrl.system.SpriteRenderSystem;

/**
 * utility class to add all systems to a given {@link WorldConfiguration}
 *
 * @author David Becker
 *
 */
public final class Systems {
	// private static final MovementSystem MOVEMENT_SYSTEM = new
	// MovementSystem();
	private static final AnimationRenderSystem ANIMATION_RENDER_SYSTEM = new AnimationRenderSystem();
	private static final FacingAnimationRenderSystem FACING_ANIMATION_RENDER_SYSTEM = new FacingAnimationRenderSystem();
	private static final SpriteRenderSystem SPRITE_RENDER_SYSTEM = new SpriteRenderSystem();
	private static final HealthBarRenderSystem RENDER_BAR_SYSTEM = new HealthBarRenderSystem();
	private static final HighlightRenderSystem HIGHLIGHT_SYSTEM = new HighlightRenderSystem();
	private static final CameraSystem CAMERA_SYSTEM = new CameraSystem();
	// private static final ControllerSystem CONTROLLER_SYSTEM = new
	// ControllerSystem();
	private static final MapOverlayPreparerSystem MAP_OVERLAY_PREPARER_SYSTEM = new MapOverlayPreparerSystem();
	private static final FovUpdaterSystem FOV_UPDATER_SYSTEM = new FovUpdaterSystem();

	private Systems() {
	}

	/**
	 * adds the systems to the given {@link WorldConfiguration}
	 *
	 * @param config
	 */
	public static void setSystems(final WorldConfiguration config) {
		// order is important! systems are processed in given order
		// config.setSystem(CONTROLLER_SYSTEM);
		config.setSystem(CAMERA_SYSTEM);
		config.setSystem(FOV_UPDATER_SYSTEM);
		config.setSystem(HIGHLIGHT_SYSTEM);
		config.setSystem(SPRITE_RENDER_SYSTEM);
		// config.setSystem(MOVEMENT_SYSTEM);
		config.setSystem(ANIMATION_RENDER_SYSTEM);
		config.setSystem(FACING_ANIMATION_RENDER_SYSTEM);
		config.setSystem(RENDER_BAR_SYSTEM);
		config.setSystem(MAP_OVERLAY_PREPARER_SYSTEM);
	}
}
