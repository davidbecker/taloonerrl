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
package de.brainstormsoftworks.taloonerrl.render;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

/**
 * renders the graphical user interface on the screen
 *
 * @author David Becker
 *
 */
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

	private final TextureRegion guiRedBlackTopLeft;
	private final TextureRegion guiRedBlackTop;
	private final TextureRegion guiRedBlackTopRight;
	private final TextureRegion guiRedBlackRight;
	private final TextureRegion guiRedBlackBottomRight;
	private final TextureRegion guiRedBlackBottom;
	private final TextureRegion guiRedBlackBottomLeft;
	private final TextureRegion guiRedBlackLeft;

	private final TextureRegion guiBlueBlackTopLeft;
	private final TextureRegion guiBlueBlackTop;
	private final TextureRegion guiBlueBlackTopRight;
	private final TextureRegion guiBlueBlackRight;
	private final TextureRegion guiBlueBlackBottomRight;
	private final TextureRegion guiBlueBlackBottom;
	private final TextureRegion guiBlueBlackBottomLeft;
	private final TextureRegion guiBlueBlackLeft;

	private final TextureRegion guiGreenBlackTopLeft;
	private final TextureRegion guiGreenBlackTop;
	private final TextureRegion guiGreenBlackTopRight;
	private final TextureRegion guiGreenBlackRight;
	private final TextureRegion guiGreenBlackBottomRight;
	private final TextureRegion guiGreenBlackBottom;
	private final TextureRegion guiGreenBlackBottomLeft;
	private final TextureRegion guiGreenBlackLeft;

	private final TextureRegion guiYellowBlackTopLeft;
	private final TextureRegion guiYellowBlackTop;
	private final TextureRegion guiYellowBlackTopRight;
	private final TextureRegion guiYellowBlackRight;
	private final TextureRegion guiYellowBlackBottomRight;
	private final TextureRegion guiYellowBlackBottom;
	private final TextureRegion guiYellowBlackBottomLeft;
	private final TextureRegion guiYellowBlackLeft;

	private final TextureRegion guiGreenRedTopLeft;
	private final TextureRegion guiGreenRedTop;
	private final TextureRegion guiGreenRedTopRight;
	private final TextureRegion guiGreenRedRight;
	private final TextureRegion guiGreenRedBottomRight;
	private final TextureRegion guiGreenRedBottom;
	private final TextureRegion guiGreenRedBottomLeft;
	private final TextureRegion guiGreenRedLeft;

	private final TextureRegion guiOrangeBlueTopLeft;
	private final TextureRegion guiOrangeBlueTop;
	private final TextureRegion guiOrangeBlueTopRight;
	private final TextureRegion guiOrangeBlueRight;
	private final TextureRegion guiOrangeBlueBottomRight;
	private final TextureRegion guiOrangeBlueBottom;
	private final TextureRegion guiOrangeBlueBottomLeft;
	private final TextureRegion guiOrangeBlueLeft;

	private final TextureRegion guiRedGreenTopLeft;
	private final TextureRegion guiRedGreenTop;
	private final TextureRegion guiRedGreenTopRight;
	private final TextureRegion guiRedGreenRight;
	private final TextureRegion guiRedGreenBottomRight;
	private final TextureRegion guiRedGreenBottom;
	private final TextureRegion guiRedGreenBottomLeft;
	private final TextureRegion guiRedGreenLeft;

	private final TextureRegion guiBlackYellowTopLeft;
	private final TextureRegion guiBlackYellowTop;
	private final TextureRegion guiBlackYellowTopRight;
	private final TextureRegion guiBlackYellowRight;
	private final TextureRegion guiBlackYellowBottomRight;
	private final TextureRegion guiBlackYellowBottom;
	private final TextureRegion guiBlackYellowBottomLeft;
	private final TextureRegion guiBlackYellowLeft;

	// "tiny" variant for GUI - only needed corners (not 16x16)
	private final TextureRegion guiRedBlackTinyTopLeft;
	private final TextureRegion guiRedBlackTinyTop;
	private final TextureRegion guiRedBlackTinyTopRight;
	private final TextureRegion guiRedBlackTinyRight;
	private final TextureRegion guiRedBlackTinyBottomRight;
	private final TextureRegion guiRedBlackTinyBottom;
	private final TextureRegion guiRedBlackTinyBottomLeft;
	private final TextureRegion guiRedBlackTinyLeft;
	private final TextureRegion guiRedBlackTinyBackground;

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

		int guiOffsetX = 1;
		int guiOffsetY = 7;
		guiRedBlackTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiRedBlackTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiRedBlackTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiRedBlackRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiRedBlackBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiRedBlackBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiRedBlackBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiRedBlackLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 1;
		guiOffsetY = 10;
		guiBlueBlackTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiBlueBlackTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiBlueBlackTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiBlueBlackRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiBlueBlackBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiBlueBlackBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiBlueBlackBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiBlueBlackLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 1;
		guiOffsetY = 13;
		guiGreenBlackTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiGreenBlackTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiGreenBlackTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiGreenBlackRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiGreenBlackBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiGreenBlackBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiGreenBlackBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiGreenBlackLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 1;
		guiOffsetY = 16;
		guiYellowBlackTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiYellowBlackTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiYellowBlackTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiYellowBlackRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiYellowBlackBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiYellowBlackBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiYellowBlackBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiYellowBlackLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 5;
		guiOffsetY = 7;
		guiGreenRedTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiGreenRedTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiGreenRedTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiGreenRedRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiGreenRedBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiGreenRedBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiGreenRedBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiGreenRedLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 5;
		guiOffsetY = 10;
		guiOrangeBlueTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiOrangeBlueTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiOrangeBlueTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiOrangeBlueRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiOrangeBlueBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiOrangeBlueBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiOrangeBlueBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiOrangeBlueLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 5;
		guiOffsetY = 13;
		guiRedGreenTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiRedGreenTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiRedGreenTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiRedGreenRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiRedGreenBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiRedGreenBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiRedGreenBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiRedGreenLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiOffsetX = 5;
		guiOffsetY = 16;
		guiBlackYellowTopLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY);
		guiBlackYellowTop = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY);
		guiBlackYellowTopRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY);
		guiBlackYellowRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 1);
		guiBlackYellowBottomRight = createTextureRegion(guiTexture0, guiOffsetX + 2, guiOffsetY + 2);
		guiBlackYellowBottom = createTextureRegion(guiTexture0, guiOffsetX + 1, guiOffsetY + 2);
		guiBlackYellowBottomLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 2);
		guiBlackYellowLeft = createTextureRegion(guiTexture0, guiOffsetX, guiOffsetY + 1);

		guiRedBlackTinyTopLeft = new TextureRegion(guiTexture0, 0, 7 * Renderer.tileSize, 5, 5);
		guiRedBlackTinyTop = new TextureRegion(guiTexture0, 5, 7 * Renderer.tileSize, 1, 5);
		guiRedBlackTinyTopRight = new TextureRegion(guiTexture0, Renderer.tileSize - 6, 7 * Renderer.tileSize,
				5, 5);
		guiRedBlackTinyRight = new TextureRegion(guiTexture0, Renderer.tileSize - 6,
				7 * Renderer.tileSize + 6, 5, 1);
		guiRedBlackTinyBottomRight = new TextureRegion(guiTexture0, Renderer.tileSize - 6,
				8 * Renderer.tileSize - 6, 5, 5);
		guiRedBlackTinyBottom = new TextureRegion(guiTexture0, 5, 8 * Renderer.tileSize - 6, 1, 5);
		guiRedBlackTinyBottomLeft = new TextureRegion(guiTexture0, 0, 8 * Renderer.tileSize - 6, 5, 5);
		guiRedBlackTinyLeft = new TextureRegion(guiTexture0, 0, 7 * Renderer.tileSize + 6, 5, 1);
		guiRedBlackTinyBackground = new TextureRegion(guiTexture0, 6, 7 * Renderer.tileSize + 6, 1, 1);

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
