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
import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.core.EDirection;
import de.brainstormsoftworks.taloonerrl.render.IDisposableInstance;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * utility class that maps the animation for to a sprite
 *
 * @author David Becker
 *
 */
public final class SpriteMapper implements IDisposableInstance {

	private final Map<EEntity, Animation> mappedAnimations = new HashMap<EEntity, Animation>();
	private final Map<EEntity, Map<EDirection, Animation>> mappedDirectionalAnimations = new HashMap<EEntity, Map<EDirection, Animation>>();
	private final Set<Texture> toDispose = new HashSet<Texture>();

	private static final String PATH_DUNGEON = "textures/dungeon/";
	private static final String PATH_MOBS = "textures/mobs/";
	private static final String PATH_CHARACTER = "character/";
	private static final SpriteMapper instance = new SpriteMapper();

	private Texture tPest0 = null;
	private Texture tPest1 = null;
	private Texture tRodent0 = null;
	private Texture tRodent1 = null;
	private Texture tDecor0 = null;
	private Texture tDecor1 = null;
	private Texture tWarrior = null;

	private SpriteMapper() {
		RenderUtil.toDispose.add(this);
	}

	public static SpriteMapper getInstance() {
		return instance;
	}

	public void mapAnimation(final AnimationComponent component) {
		final EEntity sprite = component.getEntity();
		if (!mappedAnimations.containsKey(sprite)) {
			loadAnimation(sprite);
		}
		component.setAnimation(mappedAnimations.get(sprite));
	}

	public void mapAnimation(final FacingAnimationComponent component) {
		final EEntity sprite = component.getEntityType();
		if (!mappedDirectionalAnimations.containsKey(sprite)) {
			loadAnimations(sprite);
		}
		component.setAnimations(mappedDirectionalAnimations.get(sprite));
	}

	private void loadAnimation(final EEntity sprite) {
		final TextureRegion[] frames = new TextureRegion[2];
		switch (sprite) {
		case BLOB:
			if (tPest0 == null) {
				tPest0 = loadTexture(PATH_MOBS + "Pest0.png");
			}
			if (tPest1 == null) {
				tPest1 = loadTexture(PATH_MOBS + "Pest1.png");
			}
			frames[0] = new TextureRegion(tPest0, 2 * Renderer.tileSize, 3 * Renderer.tileSize, Renderer.tileSize,
					Renderer.tileSize);
			frames[1] = new TextureRegion(tPest1, 2 * Renderer.tileSize, 3 * Renderer.tileSize, Renderer.tileSize,
					Renderer.tileSize);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case SQUIRREL:
			if (tRodent0 == null) {
				tRodent0 = loadTexture(PATH_MOBS + "Rodent0.png");
			}
			if (tRodent1 == null) {
				tRodent1 = loadTexture(PATH_MOBS + "Rodent1.png");
			}
			frames[0] = new TextureRegion(tRodent0, 0 * Renderer.tileSize, 0 * Renderer.tileSize, Renderer.tileSize,
					Renderer.tileSize);
			frames[1] = new TextureRegion(tRodent1, 0 * Renderer.tileSize, 0 * Renderer.tileSize, Renderer.tileSize,
					Renderer.tileSize);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case TORCH:
			if (tDecor0 == null) {
				tDecor0 = loadTexture(PATH_DUNGEON + "Decor0.png");
			}
			if (tDecor1 == null) {
				tDecor1 = loadTexture(PATH_DUNGEON + "Decor1.png");
			}
			frames[0] = new TextureRegion(tDecor0, 0 * Renderer.tileSize, 8 * Renderer.tileSize, Renderer.tileSize,
					Renderer.tileSize);
			frames[1] = new TextureRegion(tDecor1, 0 * Renderer.tileSize, 8 * Renderer.tileSize, Renderer.tileSize,
					Renderer.tileSize);
			mappedAnimations.put(sprite, new Animation(0.15f, frames));
			break;
		default:
			break;
		}
	}

	private void loadAnimations(final EEntity sprite) {
		final TextureRegion[] framesUp = new TextureRegion[4];
		final TextureRegion[] framesDown = new TextureRegion[4];
		final TextureRegion[] framesLeft = new TextureRegion[4];
		final TextureRegion[] framesRight = new TextureRegion[4];
		switch (sprite) {
		case PLAYER:
			if (tWarrior == null) {
				tWarrior = loadTexture(PATH_CHARACTER + "Warrior.png");
			}
			for (int i = 0; i < 4; i++) {
				framesDown[i] = new TextureRegion(tWarrior, i * Renderer.tileSize, 0 * Renderer.tileSize,
						Renderer.tileSize, Renderer.tileSize);
				framesLeft[i] = new TextureRegion(tWarrior, i * Renderer.tileSize, 1 * Renderer.tileSize,
						Renderer.tileSize, Renderer.tileSize);
				framesRight[i] = new TextureRegion(tWarrior, i * Renderer.tileSize, 2 * Renderer.tileSize,
						Renderer.tileSize, Renderer.tileSize);
				framesUp[i] = new TextureRegion(tWarrior, i * Renderer.tileSize, 3 * Renderer.tileSize,
						Renderer.tileSize, Renderer.tileSize);
			}
			final Map<EDirection, Animation> animations = new HashMap<EDirection, Animation>();
			animations.put(EDirection.UP, new Animation(0.25f, framesUp));
			animations.put(EDirection.DOWN, new Animation(0.25f, framesDown));
			animations.put(EDirection.LEFT, new Animation(0.25f, framesLeft));
			animations.put(EDirection.RIGHT, new Animation(0.25f, framesRight));
			mappedDirectionalAnimations.put(sprite, animations);
			break;
		default:
			break;
		}

	}

	private Texture loadTexture(final String fileName) {
		final Texture texture = new Texture(Gdx.files.internal(fileName), false);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		toDispose.add(texture);
		return texture;
	}

	@Override
	public void disposeInstance() {
		for (final Texture texture : toDispose) {
			texture.dispose();
		}
	}

}
