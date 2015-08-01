package de.brainstormsoftworks.taloonerrl.core.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;

/**
 * utility class that maps the animation for to a sprite
 *
 * @author David Becker
 *
 */
public final class SpriteMapper {

	private final Map<EEntity, Animation> mappedAnimations = new HashMap<EEntity, Animation>();
	private final Set<Texture> toDispose = new HashSet<Texture>();

	private static final String PATH = "character/";
	private static final int tileSize = 16;
	private static final SpriteMapper instance = new SpriteMapper();

	private Texture tPest0 = null;
	private Texture tPest1 = null;
	private Texture tRodent0 = null;
	private Texture tRodent1 = null;

	public static SpriteMapper getInstance() {
		return instance;
	}

	public void mapSprite(final AnimationComponent component) {
		final EEntity sprite = component.getSprite();
		if (!mappedAnimations.containsKey(sprite)) {
			loadAnimation(sprite);
		}
		component.setAnimation(mappedAnimations.get(sprite));
	}

	private void loadAnimation(final EEntity sprite) {
		final TextureRegion[] frames = new TextureRegion[2];
		switch (sprite) {
		case BLOB:
			if (tPest0 == null) {
				tPest0 = loadTexture("Pest0.png");
			}
			if (tPest1 == null) {
				tPest1 = loadTexture("Pest1.png");
			}
			frames[0] = new TextureRegion(tPest0, 2 * tileSize, 3 * tileSize, tileSize, tileSize);
			frames[1] = new TextureRegion(tPest1, 2 * tileSize, 3 * tileSize, tileSize, tileSize);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case SQUIRREL:
			if (tRodent0 == null) {
				tRodent0 = loadTexture("Rodent0.png");
			}
			if (tRodent1 == null) {
				tRodent1 = loadTexture("Rodent1.png");
			}
			frames[0] = new TextureRegion(tRodent0, 0 * tileSize, 0 * tileSize, tileSize, tileSize);
			frames[1] = new TextureRegion(tRodent1, 0 * tileSize, 0 * tileSize, tileSize, tileSize);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		default:
			break;
		}
	}

	private Texture loadTexture(final String fileName) {
		final Texture texture = new Texture(Gdx.files.internal(PATH + fileName), false);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		toDispose.add(texture);
		return texture;
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
