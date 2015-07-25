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
		renderBar(batch, playerHeath, 1, tilesHorizontal + 1, tilesVertical - 1);
		renderBar(batch, playerHeath, 2, tilesHorizontal + 1, tilesVertical - 2);
		renderBar(batch, playerHeath, 3, tilesHorizontal + 1, tilesVertical - 3);

		// // renderBar(batch, tilesHorizontal + 1, tilesVertical - 1);
		// fillBarSmall(batch, playerHeath, tilesHorizontal + 1, tilesVertical -
		// 2);
		// renderBarSmall(batch, tilesHorizontal + 1, tilesVertical - 2);

	}

	private void renderBar(final SpriteBatch batch, final float percent, final int barWidth, final int startX,
			final int startY) {
		fillBar(batch, percent, barWidth, startX, startY);
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

	private void fillBar(final SpriteBatch batch, final float percent, final int barWidth, final int x, final int y) {
		final EBarElementFilled[] calculateBarFill = RenderUtil.calculateBarFill(barWidth, percent);
		for (int i = 0; i < barWidth; i++) {
			final TextureRegion texture = getTextureForFilledState(calculateBarFill[i]);
			if (texture != null) {
				batch.draw(texture, (i + x) * scale, y * scale);
			}
		}
	}

	private TextureRegion getTextureForFilledState(final EBarElementFilled barElementFilled) {
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

	}
}
