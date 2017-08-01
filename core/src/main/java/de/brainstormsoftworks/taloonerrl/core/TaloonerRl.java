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
package de.brainstormsoftworks.taloonerrl.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.render.DungeonRenderer;
import de.brainstormsoftworks.taloonerrl.render.GuiRenderer;
import de.brainstormsoftworks.taloonerrl.render.MapOverlayRenderer;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * main entry point to the game
 *
 * @author David Becker
 *
 */
public class TaloonerRl implements ApplicationListener {

	private IMap map = null;

	@Override
	public void create() {
		map = MapFactory.createMap(Renderer.TILES_HORIZONTAL, Renderer.TILES_VERTICAL);

		// forcing the instance to be created
		MapManager.getInstance();
		DungeonRenderer.initInstance();
		GuiRenderer.initInstance();
	}

	@Override
	public void resize(final int width, final int height) {
		Renderer.getInstance().resizeViewPort(width, height);
	}

	@Override
	public void render() {
		// update game systems
		GameEngine.getInstance().update();

		// begin rendering of the game world
		Renderer.getInstance().beginWorldRendering();
		DungeonRenderer.getInstance().render(map);
		Renderer.getInstance().render();
		Renderer.getInstance().endWorldRendering();

		// render user interface on top
		Renderer.getInstance().beginScreenRendering();
		Renderer.getInstance().setScreenBlendingAlpha();
		MapOverlayRenderer.getInstance().render();
		Renderer.getInstance().setScreenBlendingNormal();

		GuiRenderer.getInstance().render(Gdx.graphics.getWidth() / Renderer.screenScale,
				Gdx.graphics.getHeight() / Renderer.screenScale);
		Renderer.getInstance().endScreenRendering();

		MapOverlayRenderer.getInstance().reset();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		RenderUtil.disposeInstances();
	}
}
