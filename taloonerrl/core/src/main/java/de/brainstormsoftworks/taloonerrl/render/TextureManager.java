package de.brainstormsoftworks.taloonerrl.render;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonFeature;
import de.brainstormsoftworks.taloonerrl.dungeon.EDungeonSprites;

/**
 * manages the loading & disposing of textures<br/>
 * TODO rewrite with array & EnumSet to improve performance FIXME this is just a
 * concept (and does perform poorly at its current state)
 *
 * @author david
 *
 */
@Deprecated
public final class TextureManager {
	private final Set<Texture> toDispose = new HashSet<Texture>();
	private final HashMap<EDungeonSprites, TextureRegion> spritesCache = new HashMap<EDungeonSprites, TextureRegion>();
	private boolean dungeonFeaturesLoadad = false;
	private boolean dungeonSpritesLoadad = false;

	private Texture floorTexture;
	private Texture wallTexture;
	private static final int tileSize = 16;

	private static TextureManager instance = new TextureManager();

	private TextureManager() {
	}

	public static TextureManager getInstance() {
		return instance;
	}

	public TextureRegion getTile(EDungeonFeature dungeonFeature) {
		if (!dungeonFeaturesLoadad) {
			// first time this method is called -> load the texture
			dungeonFeaturesLoadad = true;
		}
		return null;
	}

	public TextureRegion getTile(EDungeonSprites eDungeonSprites) {
		if (!dungeonSpritesLoadad) {
			// first time this method is called -> load the texture
			floorTexture = new Texture(Gdx.files.internal("Floor.png"), false);
			floorTexture
					.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			floorTexture.setWrap(TextureWrap.ClampToEdge,
					TextureWrap.ClampToEdge);
			toDispose.add(floorTexture);
			// wallTexture = new Texture(Gdx.files.internal("Wall.png"), false);
			// wallTexture.setFilter(TextureFilter.Nearest,
			// TextureFilter.Nearest);
			// wallTexture.setWrap(TextureWrap.ClampToEdge,
			// TextureWrap.ClampToEdge);
			// toDispose.add(wallTexture);

			// load tiles into cache
			spritesCache.put(EDungeonSprites.FLOOR_TOPLEFT_CORNER,
					new TextureRegion(floorTexture, 0 * tileSize,
							1 + 3 * tileSize, tileSize, tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_TOP, new TextureRegion(
					floorTexture, 1 * tileSize, 1 + 3 * tileSize, tileSize,
					tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_TOPRIGHT_CORNER,
					new TextureRegion(floorTexture, 2 * tileSize,
							1 + 3 * tileSize, tileSize, tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_LEFT, new TextureRegion(
					floorTexture, 0 * tileSize, 1 + 4 * tileSize, tileSize,
					tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_CENTER, new TextureRegion(
					floorTexture, 1 * tileSize, 1 + 4 * tileSize, tileSize,
					tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_RIGHT, new TextureRegion(
					floorTexture, 2 * tileSize, 1 + 4 * tileSize, tileSize,
					tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_BOTTOMLEFT_CORNER,
					new TextureRegion(floorTexture, 0 * tileSize,
							1 + 5 * tileSize, tileSize, tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_BOTTOM, new TextureRegion(
					floorTexture, 1 * tileSize, 1 + 5 * tileSize, tileSize,
					tileSize));
			spritesCache.put(EDungeonSprites.FLOOR_BOTTOMRIGHT_CORNER,
					new TextureRegion(floorTexture, 2 * tileSize,
							1 + 5 * tileSize, tileSize, tileSize));
			dungeonSpritesLoadad = true;
		}
		return spritesCache.get(eDungeonSprites);
	}

	/**
	 * disposes all managed ressources
	 */
	public void disposeAll() {
		for (final Texture texture : toDispose) {
			texture.dispose();
		}
	}
}
