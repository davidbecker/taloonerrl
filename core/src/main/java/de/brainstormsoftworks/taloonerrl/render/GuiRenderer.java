package de.brainstormsoftworks.taloonerrl.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class GuiRenderer implements IDisposableInstance {
	private static GuiRenderer instance = null;
	private Texture guiTexture = null;
	private static final float scale = 16f;
	private static final int tileSize = 16;

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

	// FIXME
	public static float playerHeath = 1.0f;

	private static final String TEXTURE_PATH = "textures/dawnlike/GUI/";

	private GuiRenderer() {
		guiTexture = new Texture(Gdx.files.internal(TEXTURE_PATH + "GUI0.png"), false);
		sBarLeft = new TextureRegion(guiTexture, 6 * tileSize, 0 * tileSize, tileSize, tileSize);
		sBarCenter = new TextureRegion(guiTexture, 7 * tileSize, 0 * tileSize, tileSize, tileSize);
		sBarRight = new TextureRegion(guiTexture, 8 * tileSize, 0 * tileSize, tileSize, tileSize);
		sBarSmall = new TextureRegion(guiTexture, 9 * tileSize, 0 * tileSize, tileSize, tileSize);

		sBarRed100 = new TextureRegion(guiTexture, 6 * tileSize, 1 * tileSize, tileSize, tileSize);
		sBarRed75 = new TextureRegion(guiTexture, 7 * tileSize, 1 * tileSize, tileSize, tileSize);
		sBarRed50 = new TextureRegion(guiTexture, 8 * tileSize, 1 * tileSize, tileSize, tileSize);
		sBarRed25 = new TextureRegion(guiTexture, 9 * tileSize, 1 * tileSize, tileSize, tileSize);

		sBarBlue100 = new TextureRegion(guiTexture, 6 * tileSize, 2 * tileSize, tileSize, tileSize);
		sBarBlue75 = new TextureRegion(guiTexture, 7 * tileSize, 2 * tileSize, tileSize, tileSize);
		sBarBlue50 = new TextureRegion(guiTexture, 8 * tileSize, 2 * tileSize, tileSize, tileSize);
		sBarBlue25 = new TextureRegion(guiTexture, 9 * tileSize, 2 * tileSize, tileSize, tileSize);

		sBarGreen100 = new TextureRegion(guiTexture, 6 * tileSize, 3 * tileSize, tileSize, tileSize);
		sBarGreen75 = new TextureRegion(guiTexture, 7 * tileSize, 3 * tileSize, tileSize, tileSize);
		sBarGreen50 = new TextureRegion(guiTexture, 8 * tileSize, 3 * tileSize, tileSize, tileSize);
		sBarGreen25 = new TextureRegion(guiTexture, 9 * tileSize, 3 * tileSize, tileSize, tileSize);

		sBarYellow100 = new TextureRegion(guiTexture, 6 * tileSize, 4 * tileSize, tileSize, tileSize);
		sBarYellow75 = new TextureRegion(guiTexture, 7 * tileSize, 4 * tileSize, tileSize, tileSize);
		sBarYellow50 = new TextureRegion(guiTexture, 8 * tileSize, 4 * tileSize, tileSize, tileSize);
		sBarYellow25 = new TextureRegion(guiTexture, 9 * tileSize, 4 * tileSize, tileSize, tileSize);

		sBarSilver100 = new TextureRegion(guiTexture, 6 * tileSize, 5 * tileSize, tileSize, tileSize);
		sBarSilver75 = new TextureRegion(guiTexture, 7 * tileSize, 5 * tileSize, tileSize, tileSize);
		sBarSilver50 = new TextureRegion(guiTexture, 8 * tileSize, 5 * tileSize, tileSize, tileSize);
		sBarSilver25 = new TextureRegion(guiTexture, 9 * tileSize, 5 * tileSize, tileSize, tileSize);
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

	public void render(final SpriteBatch batch, final int tilesHorizontal, final int tilesVertical) {
		renderBar(batch, playerHeath, 1, tilesHorizontal + 1, tilesVertical - 1, EBarElementColor.RED);
		renderBar(batch, playerHeath, 2, tilesHorizontal + 1, tilesVertical - 2, EBarElementColor.RED);
		renderBar(batch, playerHeath, 3, tilesHorizontal + 1, tilesVertical - 3, EBarElementColor.RED);
		renderBar(batch, playerHeath, 3, tilesHorizontal + 1, tilesVertical - 4, EBarElementColor.BLUE);
		renderBar(batch, playerHeath, 3, tilesHorizontal + 1, tilesVertical - 5, EBarElementColor.GREEN);
		renderBar(batch, playerHeath, 3, tilesHorizontal + 1, tilesVertical - 6, EBarElementColor.YELLOW);
		renderBar(batch, playerHeath, 3, tilesHorizontal + 1, tilesVertical - 7, EBarElementColor.SILVER);

	}

	private void renderBar(final SpriteBatch batch, final float percent, final int barWidth, final int startX,
			final int startY, final EBarElementColor color) {
		fillBar(batch, percent, barWidth, startX, startY, color);
		renderBarOutline(batch, barWidth, startX, startY);

	}

	private void renderBarOutline(final SpriteBatch batch, final int barWidth, final int startX, final int startY) {
		if (barWidth <= 1) {
			batch.draw(sBarSmall, startX * scale, startY * scale);
		} else {
			batch.draw(sBarLeft, startX * scale, startY * scale);
			final int centerPieces = barWidth - 2;
			for (int i = 0; i < centerPieces; i++) {
				batch.draw(sBarCenter, (1 + i + startX) * scale, startY * scale);
			}
			batch.draw(sBarRight, (1 + centerPieces + startX) * scale, startY * scale);
		}

	}

	private void fillBar(final SpriteBatch batch, final float percent, final int barWidth, final int x, final int y,
			final EBarElementColor color) {
		final EBarElementFilled[] calculateBarFill = RenderUtil.calculateBarFill(barWidth, percent);
		for (int i = 0; i < barWidth; i++) {
			final TextureRegion texture = getTextureForFilledState(calculateBarFill[i], color);
			if (texture != null) {
				batch.draw(texture, (i + x) * scale, y * scale);
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
