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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

public final class GuiRenderer implements IDisposableInstance {
	private static GuiRenderer instance = null;
	private Texture guiTexture0 = null;
	private Texture guiTexture1 = null;

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

	private final Animation heartAnimation;
	private final Animation levelUpAnimation;

	private static final String TEXTURE_PATH = "textures/gui/";

	private GuiRenderer() {
		guiTexture0 = new Texture(Gdx.files.internal(TEXTURE_PATH + "GUI0.png"), false);
		guiTexture1 = new Texture(Gdx.files.internal(TEXTURE_PATH + "GUI1.png"), false);

		// outline for GUI bars
		sBarLeft = createTextureRegion(guiTexture0, 6, 0);
		sBarCenter = createTextureRegion(guiTexture0, 7, 0);
		sBarRight = createTextureRegion(guiTexture0, 8, 0);
		sBarSmall = createTextureRegion(guiTexture0, 9, 0);

		// red content for GUI bars
		sBarRed100 = createTextureRegion(guiTexture0, 6, 1);
		sBarRed75 = createTextureRegion(guiTexture0, 7, 1);
		sBarRed50 = createTextureRegion(guiTexture0, 8, 1);
		sBarRed25 = createTextureRegion(guiTexture0, 9, 1);

		// blue content for GUI bars
		sBarBlue100 = createTextureRegion(guiTexture0, 6, 2);
		sBarBlue75 = createTextureRegion(guiTexture0, 7, 2);
		sBarBlue50 = createTextureRegion(guiTexture0, 8, 2);
		sBarBlue25 = createTextureRegion(guiTexture0, 9, 2);

		// green content for GUI bars
		sBarGreen100 = createTextureRegion(guiTexture0, 6, 3);
		sBarGreen75 = createTextureRegion(guiTexture0, 7, 3);
		sBarGreen50 = createTextureRegion(guiTexture0, 8, 3);
		sBarGreen25 = createTextureRegion(guiTexture0, 9, 3);

		// yellow content for GUI bars
		sBarYellow100 = createTextureRegion(guiTexture0, 6, 4);
		sBarYellow75 = createTextureRegion(guiTexture0, 7, 4);
		sBarYellow50 = createTextureRegion(guiTexture0, 8, 4);
		sBarYellow25 = createTextureRegion(guiTexture0, 9, 4);

		// silver content for GUI bars
		sBarSilver100 = createTextureRegion(guiTexture0, 6, 5);
		sBarSilver75 = createTextureRegion(guiTexture0, 7, 5);
		sBarSilver50 = createTextureRegion(guiTexture0, 8, 5);
		sBarSilver25 = createTextureRegion(guiTexture0, 9, 5);

		final TextureRegion[] heartFrames = new TextureRegion[2];
		heartFrames[0] = createTextureRegion(guiTexture0, 0, 5);
		heartFrames[1] = createTextureRegion(guiTexture1, 0, 5);
		heartAnimation = new Animation(1f, heartFrames);
		final TextureRegion[] levelUpFrames = new TextureRegion[2];
		levelUpFrames[0] = createTextureRegion(guiTexture0, 1, 6);
		levelUpFrames[1] = createTextureRegion(guiTexture1, 1, 6);
		levelUpAnimation = new Animation(1f, levelUpFrames);

		RenderUtil.addToDisposeList(this);
	}

	/**
	 * convenience method to create a texture region from our sprite sheet
	 *
	 * @param texture
	 *            base texture
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @return
	 */
	private static TextureRegion createTextureRegion(final Texture texture, final int x, final int y) {
		return new TextureRegion(texture, x * Renderer.tileSize, y * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
	}

	/**
	 * you <b>must</b> call {@link #disposeInstance} when you are done!
	 */
	public static void initInstance() {
		instance = new GuiRenderer();
	}

	/**
	 * get an instance of this singleton
	 *
	 * @return {@link #instance}
	 */
	public static GuiRenderer getInstance() {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public void disposeInstance() {
		guiTexture0.dispose();
	}

	/**
	 * renderes the gui
	 *
	 * @param width
	 *            width of the screen
	 * @param height
	 *            height of the screen
	 */
	public void render(final float width, final float height) {
		// hack to avoid null pointer on first frame
		final Entity player = GameEngine.getInstance().getEntity(0);
		if (player != null) {
			final float playerHeath = player.getComponent(HealthComponent.class).getHealthPercent();
			renderBar(playerHeath, 3, 1, 2, EBarElementColor.RED);
			Renderer.getInstance().renderOnScreen(RenderUtil.getKeyFrame(heartAnimation),
					-Renderer.tileSize / 4, 2 * Renderer.tileSize + Renderer.tileSize / 4);
			renderBar(0, 3, 1, 1, EBarElementColor.SILVER);
			Renderer.getInstance().renderOnScreen(RenderUtil.getKeyFrame(levelUpAnimation),
					-Renderer.tileSize / 4, 1 * Renderer.tileSize + Renderer.tileSize / 4);

		}
	}

	private void renderBar(final float percent, final int barWidth, final int startX, final int startY,
			final EBarElementColor color) {
		fillBar(percent, barWidth, startX, startY, color);
		renderBarOutline(barWidth, startX, startY);

	}

	private void renderBarOutline(final int barWidth, final int startX, final int startY) {
		if (barWidth <= 1) {
			Renderer.getInstance().renderOnScreen(sBarSmall, startX * Renderer.tileSize,
					startY * Renderer.tileSize);
		} else {
			Renderer.getInstance().renderOnScreen(sBarLeft, startX * Renderer.tileSize,
					startY * Renderer.tileSize);
			final int centerPieces = barWidth - 2;
			for (int i = 0; i < centerPieces; i++) {
				Renderer.getInstance().renderOnScreen(sBarCenter, (1 + i + startX) * Renderer.tileSize,
						startY * Renderer.tileSize);
			}
			Renderer.getInstance().renderOnScreen(sBarRight, (1 + centerPieces + startX) * Renderer.tileSize,
					startY * Renderer.tileSize);
		}

	}

	private void fillBar(final float percent, final int barWidth, final int x, final int y,
			final EBarElementColor color) {
		final EBarElementFilled[] calculateBarFill = RenderUtil.calculateBarFill(barWidth, percent);
		for (int i = 0; i < barWidth; i++) {
			final TextureRegion texture = getTextureForFilledState(calculateBarFill[i], color);
			if (texture != null) {
				Renderer.getInstance().renderOnScreen(texture, (i + x) * Renderer.tileSize,
						y * Renderer.tileSize);
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
