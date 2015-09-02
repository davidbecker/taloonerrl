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
package de.brainstormsoftworks.taloonerrl.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.render.DungeonRenderer;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
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

	public static IMap map = null;

	@Override
	public void create() {
		final int tilesHorizontal = Renderer.TILES_HORIZONTAL * 2;
		final int tilesVertical = Renderer.TILES_VERTICAL * 2;
		map = MapFactory.createMap(tilesHorizontal, tilesVertical);
		FovWrapper.getInstance().setFovResistance(map.getFovResistance());

		MapManager.createEntities(map, tilesHorizontal, tilesVertical);

		DungeonRenderer.initInstance();
		GuiRenderer.initInstance();
		MapOverlayRenderer.getInstance().initialize(map);

	}

	@Override
	public void resize(final int width, final int height) {
		Renderer.getInstance().resizeViewPort(width, height);
	}

	@Override
	public void render() {
		final float deltaTime = Gdx.graphics.getDeltaTime();

		Renderer.getInstance().beginWorldRendering();
		DungeonRenderer.getInstance().render(map);

		GameEngine.getInstance().update(deltaTime);
		Renderer.getInstance().endWorldRendering();

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
