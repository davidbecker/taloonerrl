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

import com.artemis.WorldConfiguration;

import de.brainstormsoftworks.taloonerrl.system.BlockingTileCheckSystem;
import de.brainstormsoftworks.taloonerrl.system.CameraSystem;
import de.brainstormsoftworks.taloonerrl.system.ControllerSystem;
import de.brainstormsoftworks.taloonerrl.system.EntityDeathSystem;
import de.brainstormsoftworks.taloonerrl.system.FovUpdaterSystem;
import de.brainstormsoftworks.taloonerrl.system.HealthUpdateSystem;
import de.brainstormsoftworks.taloonerrl.system.MapOverlayPreparerSystem;
import de.brainstormsoftworks.taloonerrl.system.MouseCursorUpdateSystem;
import de.brainstormsoftworks.taloonerrl.system.OffsetSystem;
import de.brainstormsoftworks.taloonerrl.system.StateDecorationExpirationSystem;
import de.brainstormsoftworks.taloonerrl.system.TurnProcessSystem;
import de.brainstormsoftworks.taloonerrl.system.TurnTakenSystem;

/**
 * utility class to add all systems to a given {@link WorldConfiguration}
 *
 * @author David Becker
 *
 */
public final class Systems {
	private static final TurnProcessSystem TURN_PROCESS_SYSTEM = new TurnProcessSystem();
	private static final BlockingTileCheckSystem BLOCKING_TILE_CHECK_SYSTEM = new BlockingTileCheckSystem();
	private static final ControllerSystem CONTROLLER_SYSTEM = new ControllerSystem();
	private static final HealthUpdateSystem HEALTH_UPDATE_SYSTEM = new HealthUpdateSystem();
	private static final EntityDeathSystem ENTITY_DEATH_SYSTEM = new EntityDeathSystem();
	private static final OffsetSystem OFFSET_SYSTEM = new OffsetSystem();
	private static final TurnTakenSystem TURN_TAKEN_SYSTEM = new TurnTakenSystem();
	private static final CameraSystem CAMERA_SYSTEM = new CameraSystem();
	private static final FovUpdaterSystem FOV_UPDATER_SYSTEM = new FovUpdaterSystem();
	private static final MouseCursorUpdateSystem HIGHLIGHT_UPDATE_SYSTEM = new MouseCursorUpdateSystem();
	private static final MapOverlayPreparerSystem MAP_OVERLAY_PREPARER_SYSTEM = new MapOverlayPreparerSystem();
	private static final StateDecorationExpirationSystem STATE_DECORATION_EXPIRATION_SYSTEM = new StateDecorationExpirationSystem();

	private Systems() {
	}

	/**
	 * adds the systems to the given {@link WorldConfiguration}
	 *
	 * @param config
	 *            configuration to add systems to
	 */
	public static void setSystems(final WorldConfiguration config) {
		// order is important! systems are processed in given order
		config.setSystem(TURN_PROCESS_SYSTEM);
		config.setSystem(BLOCKING_TILE_CHECK_SYSTEM);
		config.setSystem(CONTROLLER_SYSTEM);
		config.setSystem(HEALTH_UPDATE_SYSTEM);
		config.setSystem(ENTITY_DEATH_SYSTEM);
		config.setSystem(OFFSET_SYSTEM);
		config.setSystem(TURN_TAKEN_SYSTEM);
		config.setSystem(CAMERA_SYSTEM);
		config.setSystem(FOV_UPDATER_SYSTEM);
		config.setSystem(HIGHLIGHT_UPDATE_SYSTEM);
		config.setSystem(MAP_OVERLAY_PREPARER_SYSTEM);
		config.setSystem(STATE_DECORATION_EXPIRATION_SYSTEM);
	}
}
