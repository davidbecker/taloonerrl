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
package de.brainstormsoftworks.taloonerrl.render;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

public final class GuiRenderer implements IDisposableInstance {
	private static GuiRenderer instance = null;
	private Texture guiTexture = null;

	private final TextureRegion sBarLeft;
	private final TextureRegion sBarCenter;
	private final TextureRegion sBarRight;
	private final TextureRegion sBarSmall;
	private final TextureRegion sBarRed100;
	private final TextureRegion sBarRed75;
	private final TextureRegion sBarRed50;
	private final TextureRegion sBarRed25;
	private final TextureRegion sBarBlue100;
	private final TextureRegion sBarBlue75;
	private final TextureRegion sBarBlue50;
	private final TextureRegion sBarBlue25;
	private final TextureRegion sBarGreen100;
	private final TextureRegion sBarGreen75;
	private final TextureRegion sBarGreen50;
	private final TextureRegion sBarGreen25;
	private final TextureRegion sBarYellow100;
	private final TextureRegion sBarYellow75;
	private final TextureRegion sBarYellow50;
	private final TextureRegion sBarYellow25;
	private final TextureRegion sBarSilver100;
	private final TextureRegion sBarSilver75;
	private final TextureRegion sBarSilver50;
	private final TextureRegion sBarSilver25;

	private static final String TEXTURE_PATH = "textures/dawnlike/GUI/";

	private GuiRenderer() {
		guiTexture = new Texture(Gdx.files.internal(TEXTURE_PATH + "GUI0.png"), false);
		sBarLeft = new TextureRegion(guiTexture, 6 * Renderer.tileSize, 0 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarCenter = new TextureRegion(guiTexture, 7 * Renderer.tileSize, 0 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarRight = new TextureRegion(guiTexture, 8 * Renderer.tileSize, 0 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarSmall = new TextureRegion(guiTexture, 9 * Renderer.tileSize, 0 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);

		sBarRed100 = new TextureRegion(guiTexture, 6 * Renderer.tileSize, 1 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarRed75 = new TextureRegion(guiTexture, 7 * Renderer.tileSize, 1 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarRed50 = new TextureRegion(guiTexture, 8 * Renderer.tileSize, 1 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarRed25 = new TextureRegion(guiTexture, 9 * Renderer.tileSize, 1 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);

		sBarBlue100 = new TextureRegion(guiTexture, 6 * Renderer.tileSize, 2 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarBlue75 = new TextureRegion(guiTexture, 7 * Renderer.tileSize, 2 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarBlue50 = new TextureRegion(guiTexture, 8 * Renderer.tileSize, 2 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarBlue25 = new TextureRegion(guiTexture, 9 * Renderer.tileSize, 2 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);

		sBarGreen100 = new TextureRegion(guiTexture, 6 * Renderer.tileSize, 3 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarGreen75 = new TextureRegion(guiTexture, 7 * Renderer.tileSize, 3 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarGreen50 = new TextureRegion(guiTexture, 8 * Renderer.tileSize, 3 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarGreen25 = new TextureRegion(guiTexture, 9 * Renderer.tileSize, 3 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);

		sBarYellow100 = new TextureRegion(guiTexture, 6 * Renderer.tileSize, 4 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarYellow75 = new TextureRegion(guiTexture, 7 * Renderer.tileSize, 4 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarYellow50 = new TextureRegion(guiTexture, 8 * Renderer.tileSize, 4 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarYellow25 = new TextureRegion(guiTexture, 9 * Renderer.tileSize, 4 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);

		sBarSilver100 = new TextureRegion(guiTexture, 6 * Renderer.tileSize, 5 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarSilver75 = new TextureRegion(guiTexture, 7 * Renderer.tileSize, 5 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarSilver50 = new TextureRegion(guiTexture, 8 * Renderer.tileSize, 5 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
		sBarSilver25 = new TextureRegion(guiTexture, 9 * Renderer.tileSize, 5 * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);

		RenderUtil.addToDisposeList(this);
	}

	/**
	 * you <b>must</b> call {@link #disposeInstance} when you are done!
	 */
	public static void initInstance() {
		instance = new GuiRenderer();
	}

	public static GuiRenderer getInstance() {
		return instance;
	}

	@Override
	public void disposeInstance() {
		guiTexture.dispose();
	}

	public void render(final int tilesHorizontal, final int tilesVertical) {
		// hack to avoid null pointer on first frame
		final Entity player = GameEngine.getInstance().getEntity(0);
		if (player != null) {
			final float playerHeath = player.getComponent(HealthComponent.class).getHealthPercent();
			renderBar(playerHeath, 1, tilesHorizontal + 1, tilesVertical - 1, EBarElementColor.RED);
			renderBar(playerHeath, 2, tilesHorizontal + 1, tilesVertical - 2, EBarElementColor.RED);
			renderBar(playerHeath, 3, tilesHorizontal + 1, tilesVertical - 3, EBarElementColor.RED);
			renderBar(playerHeath, 3, tilesHorizontal + 1, tilesVertical - 4, EBarElementColor.BLUE);
			renderBar(playerHeath, 3, tilesHorizontal + 1, tilesVertical - 5, EBarElementColor.GREEN);
			renderBar(playerHeath, 3, tilesHorizontal + 1, tilesVertical - 6, EBarElementColor.YELLOW);
			renderBar(playerHeath, 3, tilesHorizontal + 1, tilesVertical - 7, EBarElementColor.SILVER);
		}
	}

	private void renderBar(final float percent, final int barWidth, final int startX, final int startY,
			final EBarElementColor color) {
		fillBar(percent, barWidth, startX, startY, color);
		renderBarOutline(barWidth, startX, startY);

	}

	private void renderBarOutline(final int barWidth, final int startX, final int startY) {
		if (barWidth <= 1) {
			Renderer.getInstance().BATCH.draw(sBarSmall, startX * Renderer.scale, startY * Renderer.scale);
		} else {
			Renderer.getInstance().BATCH.draw(sBarLeft, startX * Renderer.scale, startY * Renderer.scale);
			final int centerPieces = barWidth - 2;
			for (int i = 0; i < centerPieces; i++) {
				Renderer.getInstance().BATCH.draw(sBarCenter, (1 + i + startX) * Renderer.scale,
						startY * Renderer.scale);
			}
			Renderer.getInstance().BATCH.draw(sBarRight, (1 + centerPieces + startX) * Renderer.scale,
					startY * Renderer.scale);
		}

	}

	private void fillBar(final float percent, final int barWidth, final int x, final int y,
			final EBarElementColor color) {
		final EBarElementFilled[] calculateBarFill = RenderUtil.calculateBarFill(barWidth, percent);
		for (int i = 0; i < barWidth; i++) {
			final TextureRegion texture = getTextureForFilledState(calculateBarFill[i], color);
			if (texture != null) {
				Renderer.getInstance().BATCH.draw(texture, (i + x) * Renderer.scale, y * Renderer.scale);
			}
		}
	}

	private TextureRegion getTextureForFilledState(final EBarElementFilled barElementFilled,
			final EBarElementColor color) {
		switch (color) {
		case RED:
			switch (barElementFilled) {
			case PERCENT_100:
				return sBarRed100;
			case PERCENT_75:
				return sBarRed75;
			case PERCENT_50:
				return sBarRed50;
			case PERCENT_25:
				return sBarRed25;
			case PERCENT_0:
			default:
				return null;
			}
		case BLUE:
			switch (barElementFilled) {
			case PERCENT_100:
				return sBarBlue100;
			case PERCENT_75:
				return sBarBlue75;
			case PERCENT_50:
				return sBarBlue50;
			case PERCENT_25:
				return sBarBlue25;
			case PERCENT_0:
			default:
				return null;
			}
		case GREEN:
			switch (barElementFilled) {
			case PERCENT_100:
				return sBarGreen100;
			case PERCENT_75:
				return sBarGreen75;
			case PERCENT_50:
				return sBarGreen50;
			case PERCENT_25:
				return sBarGreen25;
			case PERCENT_0:
			default:
				return null;
			}
		case YELLOW:
			switch (barElementFilled) {
			case PERCENT_100:
				return sBarYellow100;
			case PERCENT_75:
				return sBarYellow75;
			case PERCENT_50:
				return sBarYellow50;
			case PERCENT_25:
				return sBarYellow25;
			case PERCENT_0:
			default:
				return null;
			}
		case SILVER:
			switch (barElementFilled) {
			case PERCENT_100:
				return sBarSilver100;
			case PERCENT_75:
				return sBarSilver75;
			case PERCENT_50:
				return sBarSilver50;
			case PERCENT_25:
				return sBarSilver25;
			case PERCENT_0:
			default:
				return null;
			}
		default:
			return null;
		}

	}
}
