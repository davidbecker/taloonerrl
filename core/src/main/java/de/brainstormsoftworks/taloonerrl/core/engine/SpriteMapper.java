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
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.FacingAnimationComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.render.IDisposableInstance;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * utility class that maps the animation for a sprite into components<br>
 * TODO refactor to use {@link AssetManager}
 *
 * @author David Becker
 *
 */
public final class SpriteMapper implements IDisposableInstance {
	private static final String PATH_DUNGEON = "textures/dungeon/";
	private static final String PATH_MOBS = "textures/mobs/";
	private static final String PATH_ITEMS = "textures/items/";
	private static final String PATH_GUI = "textures/gui/";
	private static final String PATH_CHARACTER = "character/";

	private static final String FILE_AVIAN0 = "Avian0.png";
	private static final String FILE_AVIAN1 = "Avian1.png";
	private static final String FILE_DECOR0 = "Decor0.png";
	private static final String FILE_DECOR1 = "Decor1.png";
	private static final String FILE_ELEMENTAL0 = "Elemental0.png";
	private static final String FILE_ELEMENTAL1 = "Elemental1.png";
	private static final String FILE_HUMANOID0 = "Humanoid0.png";
	private static final String FILE_HUMANOID1 = "Humanoid1.png";
	private static final String FILE_PEST0 = "Pest0.png";
	private static final String FILE_PEST1 = "Pest1.png";
	private static final String FILE_PLANT0 = "Plant0.png";
	private static final String FILE_PLANT1 = "Plant1.png";
	private static final String FILE_PLAYER0 = "Player0.png";
	private static final String FILE_PLAYER1 = "Player1.png";
	private static final String FILE_POTION = "Potion.png";
	private static final String FILE_REPTILE0 = "Reptile0.png";
	private static final String FILE_REPTILE1 = "Reptile1.png";
	private static final String FILE_RING = "Ring.png";
	private static final String FILE_RODENT0 = "Rodent0.png";
	private static final String FILE_RODENT1 = "Rodent1.png";
	private static final String FILE_SCROLL = "Scroll.png";
	private static final String FILE_SHIELD = "Shield.png";
	private static final String FILE_SLIME0 = "Slime0.png";
	private static final String FILE_SLIME1 = "Slime1.png";
	private static final String FILE_UNDEAD1 = "Undead1.png";
	private static final String FILE_UNDEAD0 = "Undead0.png";
	private static final String FILE_WAND = "Wand.png";
	private static final String FILE_WARRIOR = "Warrior.png";
	private static final String FILE_WEAPON = "ShortWep.png";
	private static final String FILE_GUI0 = "GUI0.png";
	private static final String FILE_GUI1 = "GUI1.png";

	private static final SpriteMapper instance = new SpriteMapper();

	private final Map<EEntity, Animation> mappedAnimations = new HashMap<>();
	private final Map<EEntity, Map<Integer, Animation>> mappedDirectionalAnimations = new HashMap<>();
	private final Map<EEntity, TextureRegion> mappedSprites = new HashMap<>();
	private final Set<Texture> toDispose = new HashSet<>();

	private Texture tAvian0 = null;
	private Texture tAvian1 = null;
	private Texture tDecor0 = null;
	private Texture tDecor1 = null;
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
	private Texture tPotions = null;
	private Texture tReptile0 = null;
	private Texture tReptile1 = null;
	private Texture tRing = null;
	private Texture tRodent0 = null;
	private Texture tRodent1 = null;
	private Texture tScroll = null;
	private Texture tShield = null;
	private Texture tSlime0 = null;
	private Texture tSlime1 = null;
	private Texture tUndead0 = null;
	private Texture tUndead1 = null;
	private Texture tWand = null;
	private Texture tWarrior = null;
	private Texture tWeapon = null;
	private Texture tGui0 = null;
	private Texture tGui1 = null;

	private SpriteMapper() {
		RenderUtil.addToDisposeList(this);
	}

	/**
	 * get an instance of this singleton
	 *
	 * @return {@link #instance}
	 */
	public static SpriteMapper getInstance() {
		return instance;
	}

	/**
	 * maps the correct animation for the entity into the component
	 *
	 * @param component
	 *            to set the animation into
	 */
	public void mapAnimation(final AnimationComponent component) {
		final EEntity type = component.getEntityType();
		if (!mappedAnimations.containsKey(type)) {
			loadAnimation(type);
		}
		component.setAnimation(mappedAnimations.get(type));
	}

	/**
	 * maps the correct animation for the entity into the component
	 *
	 * @param component
	 *            to set the animation into
	 */
	public void mapAnimation(final FacingAnimationComponent component) {
		final EEntity type = component.getEntityType();
		if (!mappedDirectionalAnimations.containsKey(type)) {
			loadAnimations(type);
		}
		component.setAnimations(mappedDirectionalAnimations.get(type));
	}

	/**
	 * maps the correct sprite for the entity into the component
	 *
	 * @param component
	 *            to set the sprite into
	 */
	public void mapSprite(final SpriteComponent component) {
		final EEntity type = component.getEntityType();
		if (!mappedSprites.containsKey(type)) {
			loadSprite(type);
		}
		component.setSprite(mappedSprites.get(type));
	}

	/**
	 * loads a single animation for the given entity type
	 *
	 * @param type
	 *            to load the animation for
	 */
	private void loadAnimation(final EEntity type) {
		final TextureRegion[] frames = new TextureRegion[2];
		switch (type) {
		case ARCHER:
			if (tPlayer0 == null) {
				tPlayer0 = loadTexture(PATH_MOBS + FILE_PLAYER0);
			}
			if (tPlayer1 == null) {
				tPlayer1 = loadTexture(PATH_MOBS + FILE_PLAYER1);
			}
			frames[0] = loadFrame(tPlayer0, 2, 3);
			frames[1] = loadFrame(tPlayer1, 2, 3);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case WARPER:
			if (tPlayer0 == null) {
				tPlayer0 = loadTexture(PATH_MOBS + FILE_PLAYER0);
			}
			if (tPlayer1 == null) {
				tPlayer1 = loadTexture(PATH_MOBS + FILE_PLAYER1);
			}
			frames[0] = loadFrame(tPlayer0, 1, 8);
			frames[1] = loadFrame(tPlayer1, 1, 8);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case BLOB:
			if (tPest0 == null) {
				tPest0 = loadTexture(PATH_MOBS + FILE_PEST0);
			}
			if (tPest1 == null) {
				tPest1 = loadTexture(PATH_MOBS + FILE_PEST1);
			}
			frames[0] = loadFrame(tPest0, 2, 3);
			frames[1] = loadFrame(tPest1, 2, 3);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case SLUG:
			if (tPest0 == null) {
				tPest0 = loadTexture(PATH_MOBS + FILE_PEST0);
			}
			if (tPest1 == null) {
				tPest1 = loadTexture(PATH_MOBS + FILE_PEST1);
			}
			frames[0] = loadFrame(tPest0, 1, 7);
			frames[1] = loadFrame(tPest1, 1, 7);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case SQUIRREL:
			if (tRodent0 == null) {
				tRodent0 = loadTexture(PATH_MOBS + FILE_RODENT0);
			}
			if (tRodent1 == null) {
				tRodent1 = loadTexture(PATH_MOBS + FILE_RODENT1);
			}
			frames[0] = loadFrame(tRodent0, 0, 0);
			frames[1] = loadFrame(tRodent1, 0, 0);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case BAT:
			if (tAvian0 == null) {
				tAvian0 = loadTexture(PATH_MOBS + FILE_AVIAN0);
			}
			if (tAvian1 == null) {
				tAvian1 = loadTexture(PATH_MOBS + FILE_AVIAN1);
			}
			frames[0] = loadFrame(tAvian0, 0, 11);
			frames[1] = loadFrame(tAvian1, 0, 11);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case MAGICIAN:
			if (tHumanoid0 == null) {
				tHumanoid0 = loadTexture(PATH_MOBS + FILE_HUMANOID0);
			}
			if (tHumanoid1 == null) {
				tHumanoid1 = loadTexture(PATH_MOBS + FILE_HUMANOID1);
			}
			frames[0] = loadFrame(tHumanoid0, 0, 23);
			frames[1] = loadFrame(tHumanoid1, 0, 23);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case GHOST:
			if (tUndead0 == null) {
				tUndead0 = loadTexture(PATH_MOBS + FILE_UNDEAD0);
			}
			if (tUndead1 == null) {
				tUndead1 = loadTexture(PATH_MOBS + FILE_UNDEAD1);
			}
			frames[0] = loadFrame(tUndead0, 0, 4);
			frames[1] = loadFrame(tUndead1, 0, 4);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case SHADOW:
			if (tUndead0 == null) {
				tUndead0 = loadTexture(PATH_MOBS + FILE_UNDEAD0);
			}
			if (tUndead1 == null) {
				tUndead1 = loadTexture(PATH_MOBS + FILE_UNDEAD1);
			}
			frames[0] = loadFrame(tUndead0, 3, 4);
			frames[1] = loadFrame(tUndead1, 3, 4);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case STEALER:
			if (tUndead0 == null) {
				tUndead0 = loadTexture(PATH_MOBS + FILE_UNDEAD0);
			}
			if (tUndead1 == null) {
				tUndead1 = loadTexture(PATH_MOBS + FILE_UNDEAD1);
			}
			frames[0] = loadFrame(tUndead0, 0, 0);
			frames[1] = loadFrame(tUndead1, 0, 0);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case EYEBALL:
			if (tElemental0 == null) {
				tElemental0 = loadTexture(PATH_MOBS + FILE_ELEMENTAL0);
			}
			if (tElemental1 == null) {
				tElemental1 = loadTexture(PATH_MOBS + FILE_ELEMENTAL1);
			}
			frames[0] = loadFrame(tElemental0, 0, 5);
			frames[1] = loadFrame(tElemental1, 0, 5);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case GOLEM:
			if (tElemental0 == null) {
				tElemental0 = loadTexture(PATH_MOBS + FILE_ELEMENTAL0);
			}
			if (tElemental1 == null) {
				tElemental1 = loadTexture(PATH_MOBS + FILE_ELEMENTAL1);
			}
			frames[0] = loadFrame(tElemental0, 4, 0);
			frames[1] = loadFrame(tElemental1, 4, 0);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case BOMB:
			if (tElemental0 == null) {
				tElemental0 = loadTexture(PATH_MOBS + FILE_ELEMENTAL0);
			}
			if (tElemental1 == null) {
				tElemental1 = loadTexture(PATH_MOBS + FILE_ELEMENTAL1);
			}
			frames[0] = loadFrame(tElemental0, 2, 5);
			frames[1] = loadFrame(tElemental1, 2, 5);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case DRAGON:
			if (tReptile0 == null) {
				tReptile0 = loadTexture(PATH_MOBS + FILE_REPTILE0);
			}
			if (tReptile1 == null) {
				tReptile1 = loadTexture(PATH_MOBS + FILE_REPTILE1);
			}
			frames[0] = loadFrame(tReptile0, 5, 2);
			frames[1] = loadFrame(tReptile1, 5, 2);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case ACID:
			if (tSlime0 == null) {
				tSlime0 = loadTexture(PATH_MOBS + FILE_SLIME0);
			}
			if (tSlime1 == null) {
				tSlime1 = loadTexture(PATH_MOBS + FILE_SLIME1);
			}
			frames[0] = loadFrame(tSlime0, 0, 2);
			frames[1] = loadFrame(tSlime1, 0, 2);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case MUSHROOM:
			if (tPlant0 == null) {
				tPlant0 = loadTexture(PATH_MOBS + FILE_PLANT0);
			}
			if (tPlant1 == null) {
				tPlant1 = loadTexture(PATH_MOBS + FILE_PLANT1);
			}
			frames[0] = loadFrame(tPlant0, 0, 7);
			frames[1] = loadFrame(tPlant1, 0, 7);
			mappedAnimations.put(type, new Animation(0.25f, frames));
			break;
		case TORCH:
			if (tDecor0 == null) {
				tDecor0 = loadTexture(PATH_DUNGEON + FILE_DECOR0);
			}
			if (tDecor1 == null) {
				tDecor1 = loadTexture(PATH_DUNGEON + FILE_DECOR1);
			}
			frames[0] = loadFrame(tDecor0, 0, 8);
			frames[1] = loadFrame(tDecor1, 0, 8);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		case STATUS_DECORATOR_ALERTED:
			if (tGui0 == null) {
				tGui0 = loadTexture(PATH_GUI + FILE_GUI0);
			}
			if (tGui1 == null) {
				tGui1 = loadTexture(PATH_GUI + FILE_GUI1);
			}
			frames[0] = loadFrame(tGui0, 12, 3);
			frames[1] = loadFrame(tGui1, 12, 3);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		case STATUS_DECORATOR_CONFUSED:
			if (tGui0 == null) {
				tGui0 = loadTexture(PATH_GUI + FILE_GUI0);
			}
			if (tGui1 == null) {
				tGui1 = loadTexture(PATH_GUI + FILE_GUI1);
			}
			frames[0] = loadFrame(tGui0, 13, 3);
			frames[1] = loadFrame(tGui1, 13, 3);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		case STATUS_DECORATOR_NONE:
			if (tGui0 == null) {
				tGui0 = loadTexture(PATH_GUI + FILE_GUI0);
			}
			if (tGui1 == null) {
				tGui1 = loadTexture(PATH_GUI + FILE_GUI1);
			}
			frames[0] = loadFrame(tGui0, 10, 1);
			frames[1] = loadFrame(tGui1, 10, 1);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		case STATUS_DECORATOR_DEAD:
			if (tGui0 == null) {
				tGui0 = loadTexture(PATH_GUI + FILE_GUI0);
			}
			if (tGui1 == null) {
				tGui1 = loadTexture(PATH_GUI + FILE_GUI1);
			}
			frames[0] = loadFrame(tGui0, 11, 3);
			frames[1] = loadFrame(tGui1, 11, 3);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		case STATUS_DECORATOR_SLEEPING:
			if (tGui0 == null) {
				tGui0 = loadTexture(PATH_GUI + FILE_GUI0);
			}
			if (tGui1 == null) {
				tGui1 = loadTexture(PATH_GUI + FILE_GUI1);
			}
			frames[0] = loadFrame(tGui0, 10, 4);
			frames[1] = loadFrame(tGui1, 10, 4);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		case STATUS_DECORATOR_WAITING:
			if (tGui0 == null) {
				tGui0 = loadTexture(PATH_GUI + FILE_GUI0);
			}
			if (tGui1 == null) {
				tGui1 = loadTexture(PATH_GUI + FILE_GUI1);
			}
			frames[0] = loadFrame(tGui0, 13, 4);
			frames[1] = loadFrame(tGui1, 13, 4);
			mappedAnimations.put(type, new Animation(0.15f, frames));
			break;
		default:
			break;
		}
	}

	/**
	 * loads multiple animations for the given entity type
	 *
	 * @param type
	 *            to load the animation for
	 */
	private void loadAnimations(final EEntity type) {
		final TextureRegion[] framesUp = new TextureRegion[4];
		final TextureRegion[] framesDown = new TextureRegion[4];
		final TextureRegion[] framesLeft = new TextureRegion[4];
		final TextureRegion[] framesRight = new TextureRegion[4];
		switch (type) {
		case PLAYER:
			if (tWarrior == null) {
				tWarrior = loadTexture(PATH_CHARACTER + FILE_WARRIOR);
			}
			for (int i = 0; i < 4; i++) {
				framesDown[i] = loadFrame(tWarrior, i, 0);
				framesLeft[i] = loadFrame(tWarrior, i, 1);
				framesRight[i] = loadFrame(tWarrior, i, 2);
				framesUp[i] = loadFrame(tWarrior, i, 3);
			}
			final Map<Integer, Animation> animations = new HashMap<>();
			animations.put(Move.UP, new Animation(0.25f, framesUp));
			animations.put(Move.DOWN, new Animation(0.25f, framesDown));
			animations.put(Move.LEFT, new Animation(0.25f, framesLeft));
			animations.put(Move.RIGHT, new Animation(0.25f, framesRight));
			mappedDirectionalAnimations.put(type, animations);
			break;
		default:
			break;
		}
	}

	private void loadSprite(final EEntity type) {
		// TODO refactor (random potions, scrolls, wands)
		switch (type) {
		case POTION_A:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 0, 0));
			break;
		case POTION_B:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 0, 1));
			break;
		case POTION_C:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 0, 2));
			break;
		case POTION_D:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 0, 3));
			break;
		case POTION_E:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 0, 4));
			break;
		case POTION_F:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 1, 0));
			break;
		case POTION_G:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 1, 1));
			break;
		case POTION_H:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 1, 2));
			break;
		case POTION_I:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 1, 3));
			break;
		case POTION_J:
			if (tPotions == null) {
				tPotions = loadTexture(PATH_ITEMS + FILE_POTION);
			}
			mappedSprites.put(type, loadFrame(tPotions, 1, 4));
			break;
		case SCROLL_A:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 0, 0));
			break;
		case SCROLL_B:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 0, 1));
			break;
		case SCROLL_C:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 0, 2));
			break;
		case SCROLL_D:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 0, 3));
			break;
		case SCROLL_E:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 1, 0));
			break;
		case SCROLL_F:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 1, 1));
			break;
		case SCROLL_G:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 1, 2));
			break;
		case SCROLL_H:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 1, 3));
			break;
		case SCROLL_I:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 2, 0));
			break;
		case SCROLL_J:
			if (tScroll == null) {
				tScroll = loadTexture(PATH_ITEMS + FILE_SCROLL);
			}
			mappedSprites.put(type, loadFrame(tScroll, 2, 1));
			break;
		case WAND_A:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 0, 0));
			break;
		case WAND_B:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 0, 1));
			break;
		case WAND_C:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 0, 2));
			break;
		case WAND_D:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 0, 3));
			break;
		case WAND_E:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 1, 0));
			break;
		case WAND_F:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 1, 1));
			break;
		case WAND_G:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 1, 2));
			break;
		case WAND_H:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 1, 3));
			break;
		case WAND_I:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 2, 0));
			break;
		case WAND_J:
			if (tWand == null) {
				tWand = loadTexture(PATH_ITEMS + FILE_WAND);
			}
			mappedSprites.put(type, loadFrame(tWand, 2, 1));
			break;
		case RING_A:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 0, 0));
			break;
		case RING_B:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 0, 1));
			break;
		case RING_C:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 0, 2));
			break;
		case RING_D:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 0, 3));
			break;
		case RING_E:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 1, 0));
			break;
		case RING_F:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 1, 1));
			break;
		case RING_G:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 1, 2));
			break;
		case RING_H:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 1, 3));
			break;
		case RING_I:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 2, 0));
			break;
		case RING_J:
			if (tRing == null) {
				tRing = loadTexture(PATH_ITEMS + FILE_RING);
			}
			mappedSprites.put(type, loadFrame(tRing, 2, 1));
			break;
		case WEAPON_A:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 0, 0));
			break;
		case WEAPON_B:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 0, 1));
			break;
		case WEAPON_C:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 0, 2));
			break;
		case WEAPON_D:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 0, 3));
			break;
		case WEAPON_E:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 1, 0));
			break;
		case WEAPON_F:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 1, 1));
			break;
		case WEAPON_G:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 1, 2));
			break;
		case WEAPON_H:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 1, 3));
			break;
		case WEAPON_I:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 2, 0));
			break;
		case WEAPON_J:
			if (tWeapon == null) {
				tWeapon = loadTexture(PATH_ITEMS + FILE_WEAPON);
			}
			mappedSprites.put(type, loadFrame(tWeapon, 2, 1));
			break;
		case SHIELD_A:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 0, 0));
			break;
		case SHIELD_B:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 1, 0));
			break;
		case SHIELD_C:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 2, 0));
			break;
		case SHIELD_D:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 3, 0));
			break;
		case SHIELD_E:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 4, 0));
			break;
		case SHIELD_F:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 5, 0));
			break;
		case SHIELD_G:
			if (tShield == null) {
				tShield = loadTexture(PATH_ITEMS + FILE_SHIELD);
			}
			mappedSprites.put(type, loadFrame(tShield, 6, 0));
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

	private static TextureRegion loadFrame(final Texture texture, final int x, final int y) {
		return new TextureRegion(texture, x * Renderer.tileSize, y * Renderer.tileSize, Renderer.tileSize,
				Renderer.tileSize);
	}

	/** {@inheritDoc} */
	@Override
	public void disposeInstance() {
		for (final Texture texture : toDispose) {
			texture.dispose();
		}
	}

}
