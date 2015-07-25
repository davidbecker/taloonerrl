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

	private static final float percent100 = 1.00f;
	private static final float percent75 = .75f;
	private static final float percent50 = .50f;
	private static final float percent25 = .25f;

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

	private GuiRenderer() {
		guiTexture = new Texture(Gdx.files.internal("GUI0.png"), false);
		sBarLeft = new TextureRegion(guiTexture, 6 * tileSize, 0 * tileSize,
				tileSize, tileSize);
		sBarCenter = new TextureRegion(guiTexture, 7 * tileSize, 0 * tileSize,
				tileSize, tileSize);
		sBarRight = new TextureRegion(guiTexture, 8 * tileSize, 0 * tileSize,
				tileSize, tileSize);
		sBarSmall = new TextureRegion(guiTexture, 9 * tileSize, 0 * tileSize,
				tileSize, tileSize);
		sBarRed100 = new TextureRegion(guiTexture, 6 * tileSize, 1 * tileSize,
				tileSize, tileSize);
		sBarRed75 = new TextureRegion(guiTexture, 7 * tileSize, 1 * tileSize,
				tileSize, tileSize);
		sBarRed50 = new TextureRegion(guiTexture, 8 * tileSize, 1 * tileSize,
				tileSize, tileSize);
		sBarRed25 = new TextureRegion(guiTexture, 9 * tileSize, 1 * tileSize,
				tileSize, tileSize);
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

	public void render(SpriteBatch batch, int tilesHorizontal, int tilesVertical) {
		fillBar(batch, playerHeath, tilesHorizontal + 1, tilesVertical - 1);
		renderBar(batch, tilesHorizontal + 1, tilesVertical - 1);
		fillBarSmall(batch, playerHeath, tilesHorizontal + 1, tilesVertical - 2);
		renderBarSmall(batch, tilesHorizontal + 1, tilesVertical - 2);

	}

	private void fillBar(SpriteBatch batch, float percent, int x, int y) {
		final int barWidth = 3;
		final float filledPerTile = 1.00f / barWidth;
		for (int i = 1; i <= barWidth; i++) {
			final float f = filledPerTile * i;
			// System.err.println(i + " - " + percent + " ("
			// + (percent - filledPerTile * i) + ") - " + f);
			if (f > percent - filledPerTile * i) {
				batch.draw(sBarRed100, (i - 1 + x) * scale, y * scale);
			}
			System.err.println(i + " " + (percent - f));
			if (f <= percent) {
				batch.draw(sBarRed100, (i - 1 + x) * scale, y * scale);
			} else if (f * percent75 <= percent) {
				batch.draw(sBarRed75, (i - 1 + x) * scale, y * scale);
			} else if (f * percent50 <= percent) {
				batch.draw(sBarRed50, (i - 1 + x) * scale, y * scale);
			} else if (f * percent25 <= percent) {
				batch.draw(sBarRed25, (i - 1 + x) * scale, y * scale);
			}
			// if (f < percent25 * percent) {
			// System.err.println(i + " 25%");
			// batch.draw(sBarRed25, (i - 1 + x) * scale, y * scale);
			// } else if (f < percent50 * percent) {
			// System.err.println(i + " 50%");
			// batch.draw(sBarRed50, (i - 1 + x) * scale, y * scale);
			// } else if (f < percent75 * percent) {
			// System.err.println(i + " 75%");
			// batch.draw(sBarRed75, (i - 1 + x) * scale, y * scale);
			// } else {
			// System.err.println(i + " 100%");
			// batch.draw(sBarRed100, (i - 1 + x) * scale, y * scale);
			// }
		}
	}

	private void fillBarSmall(SpriteBatch batch, float percent, int x, int y) {
		if (percent >= percent100) {
			batch.draw(sBarRed100, x * scale, y * scale);
		} else if (percent >= percent75) {
			batch.draw(sBarRed75, x * scale, y * scale);
		} else if (percent >= percent50) {
			batch.draw(sBarRed50, x * scale, y * scale);
		} else if (percent >= percent25) {
			batch.draw(sBarRed25, x * scale, y * scale);
		}
	}

	private void renderBar(SpriteBatch batch, int x, int y) {
		batch.draw(sBarLeft, x * scale, y * scale);
		batch.draw(sBarCenter, (1 + x) * scale, y * scale);
		batch.draw(sBarRight, (2 + x) * scale, y * scale);
	}

	private void renderBarSmall(SpriteBatch batch, int x, int y) {
		batch.draw(sBarSmall, x * scale, y * scale);
	}
}
