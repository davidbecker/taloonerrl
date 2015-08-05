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

	private Texture tAvian0 = null;
	private Texture tAvian1 = null;
	private Texture tElemental0 = null;
	private Texture tElemental1 = null;
	private Texture tHumanoid0 = null;
	private Texture tHumanoid1 = null;
	private Texture tPest0 = null;
	private Texture tPest1 = null;
	private Texture tPlant0 = null;
	private Texture tPlant1 = null;
	private Texture tPlayer0 = null;
	private Texture tPlayer1 = null;
	private Texture tReptile0 = null;
	private Texture tReptile1 = null;
	private Texture tRodent0 = null;
	private Texture tRodent1 = null;
	private Texture tSlime0 = null;
	private Texture tSlime1 = null;
	private Texture tUndead0 = null;
	private Texture tUndead1 = null;
	private Texture tDecor0 = null;
	private Texture tDecor1 = null;
	private Texture tWarrior = null;

	private SpriteMapper() {
		RenderUtil.addToDisposeList(this);
	}

	public static SpriteMapper getInstance() {
		return instance;
	}

	public void mapAnimation(final AnimationComponent component) {
		final EEntity sprite = component.getEntityType();
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
		case ARCHER:
			if (tPlayer0 == null) {
				tPlayer0 = loadTexture(PATH_MOBS + "Player0.png");
			}
			if (tPlayer1 == null) {
				tPlayer1 = loadTexture(PATH_MOBS + "Player1.png");
			}
			frames[0] = loadFrame(tPlayer0, 2, 3);
			frames[1] = loadFrame(tPlayer1, 2, 3);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case WARPER:
			if (tPlayer0 == null) {
				tPlayer0 = loadTexture(PATH_MOBS + "Player0.png");
			}
			if (tPlayer1 == null) {
				tPlayer1 = loadTexture(PATH_MOBS + "Player1.png");
			}
			frames[0] = loadFrame(tPlayer0, 1, 8);
			frames[1] = loadFrame(tPlayer1, 1, 8);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case BLOB:
			if (tPest0 == null) {
				tPest0 = loadTexture(PATH_MOBS + "Pest0.png");
			}
			if (tPest1 == null) {
				tPest1 = loadTexture(PATH_MOBS + "Pest1.png");
			}
			frames[0] = loadFrame(tPest0, 2, 3);
			frames[1] = loadFrame(tPest1, 2, 3);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case SLUG:
			if (tPest0 == null) {
				tPest0 = loadTexture(PATH_MOBS + "Pest0.png");
			}
			if (tPest1 == null) {
				tPest1 = loadTexture(PATH_MOBS + "Pest1.png");
			}
			frames[0] = loadFrame(tPest0, 1, 7);
			frames[1] = loadFrame(tPest1, 1, 7);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case SQUIRREL:
			if (tRodent0 == null) {
				tRodent0 = loadTexture(PATH_MOBS + "Rodent0.png");
			}
			if (tRodent1 == null) {
				tRodent1 = loadTexture(PATH_MOBS + "Rodent1.png");
			}
			frames[0] = loadFrame(tRodent0, 0, 0);
			frames[1] = loadFrame(tRodent1, 0, 0);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case BAT:
			if (tAvian0 == null) {
				tAvian0 = loadTexture(PATH_MOBS + "Avian0.png");
			}
			if (tAvian1 == null) {
				tAvian1 = loadTexture(PATH_MOBS + "Avian1.png");
			}
			frames[0] = loadFrame(tAvian0, 0, 11);
			frames[1] = loadFrame(tAvian1, 0, 11);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case MAGICIAN:
			if (tHumanoid0 == null) {
				tHumanoid0 = loadTexture(PATH_MOBS + "Humanoid0.png");
			}
			if (tHumanoid1 == null) {
				tHumanoid1 = loadTexture(PATH_MOBS + "Humanoid1.png");
			}
			frames[0] = loadFrame(tHumanoid0, 0, 23);
			frames[1] = loadFrame(tHumanoid1, 0, 23);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case GHOST:
			if (tUndead0 == null) {
				tUndead0 = loadTexture(PATH_MOBS + "Undead0.png");
			}
			if (tUndead1 == null) {
				tUndead1 = loadTexture(PATH_MOBS + "Undead1.png");
			}
			frames[0] = loadFrame(tUndead0, 0, 4);
			frames[1] = loadFrame(tUndead1, 0, 4);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case SHADOW:
			if (tUndead0 == null) {
				tUndead0 = loadTexture(PATH_MOBS + "Undead0.png");
			}
			if (tUndead1 == null) {
				tUndead1 = loadTexture(PATH_MOBS + "Undead1.png");
			}
			frames[0] = loadFrame(tUndead0, 3, 4);
			frames[1] = loadFrame(tUndead1, 3, 4);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case STEALER:
			if (tUndead0 == null) {
				tUndead0 = loadTexture(PATH_MOBS + "Undead0.png");
			}
			if (tUndead1 == null) {
				tUndead1 = loadTexture(PATH_MOBS + "Undead1.png");
			}
			frames[0] = loadFrame(tUndead0, 0, 0);
			frames[1] = loadFrame(tUndead1, 0, 0);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case EYEBALL:
			if (tElemental0 == null) {
				tElemental0 = loadTexture(PATH_MOBS + "Elemental0.png");
			}
			if (tElemental1 == null) {
				tElemental1 = loadTexture(PATH_MOBS + "Elemental1.png");
			}
			frames[0] = loadFrame(tElemental0, 0, 5);
			frames[1] = loadFrame(tElemental1, 0, 5);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case GOLEM:
			if (tElemental0 == null) {
				tElemental0 = loadTexture(PATH_MOBS + "Elemental0.png");
			}
			if (tElemental1 == null) {
				tElemental1 = loadTexture(PATH_MOBS + "Elemental1.png");
			}
			frames[0] = loadFrame(tElemental0, 4, 0);
			frames[1] = loadFrame(tElemental1, 4, 0);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case BOMB:
			if (tElemental0 == null) {
				tElemental0 = loadTexture(PATH_MOBS + "Elemental0.png");
			}
			if (tElemental1 == null) {
				tElemental1 = loadTexture(PATH_MOBS + "Elemental1.png");
			}
			frames[0] = loadFrame(tElemental0, 2, 5);
			frames[1] = loadFrame(tElemental1, 2, 5);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case DRAGON:
			if (tReptile0 == null) {
				tReptile0 = loadTexture(PATH_MOBS + "Reptile0.png");
			}
			if (tReptile1 == null) {
				tReptile1 = loadTexture(PATH_MOBS + "Reptile1.png");
			}
			frames[0] = loadFrame(tReptile0, 5, 2);
			frames[1] = loadFrame(tReptile1, 5, 2);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case ACID:
			if (tSlime0 == null) {
				tSlime0 = loadTexture(PATH_MOBS + "Slime0.png");
			}
			if (tSlime1 == null) {
				tSlime1 = loadTexture(PATH_MOBS + "Slime1.png");
			}
			frames[0] = loadFrame(tSlime0, 0, 2);
			frames[1] = loadFrame(tSlime1, 0, 2);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case MUSHROOM:
			if (tPlant0 == null) {
				tPlant0 = loadTexture(PATH_MOBS + "Plant0.png");
			}
			if (tPlant1 == null) {
				tPlant1 = loadTexture(PATH_MOBS + "Plant1.png");
			}
			frames[0] = loadFrame(tPlant0, 0, 7);
			frames[1] = loadFrame(tPlant1, 0, 7);
			mappedAnimations.put(sprite, new Animation(0.25f, frames));
			break;
		case TORCH:
			if (tDecor0 == null) {
				tDecor0 = loadTexture(PATH_DUNGEON + "Decor0.png");
			}
			if (tDecor1 == null) {
				tDecor1 = loadTexture(PATH_DUNGEON + "Decor1.png");
			}
			frames[0] = loadFrame(tDecor0, 0, 8);
			frames[1] = loadFrame(tDecor1, 0, 8);
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
				framesDown[i] = loadFrame(tWarrior, i, 0);
				framesLeft[i] = loadFrame(tWarrior, i, 1);
				framesRight[i] = loadFrame(tWarrior, i, 2);
				framesUp[i] = loadFrame(tWarrior, i, 3);
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

	private TextureRegion loadFrame(final Texture texture, final int x, final int y) {
		return new TextureRegion(texture, x * Renderer.tileSize, y * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
	}

	@Override
	public void disposeInstance() {
		for (final Texture texture : toDispose) {
			texture.dispose();
		}
	}

}
