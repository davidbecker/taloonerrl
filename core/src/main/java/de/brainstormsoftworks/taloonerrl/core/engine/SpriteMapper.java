/*******************************************************************************
 * Copyright (c) 2015-2018 David Becker.
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
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

/**
 * utility class that maps the animation for a sprite into components<br>
 * TODO refactor to use {@link AssetManager}
 *
 * @author David Becker
 *
 */
public final class SpriteMapper implements IDisposableInstance {

	private static final float ANIMATION_DURATION_CREATURE = 0.25f;
	private static final float ANIMATION_DURATION_DECORATION = 0.15f;

	/*
	 * relative path in resources folder
	 */
	private static final String PATH_DUNGEON = "textures/dungeon/";
	private static final String PATH_MOBS = "textures/mobs/";
	private static final String PATH_ITEMS = "textures/items/";
	private static final String PATH_GUI = "textures/gui/";
	private static final String PATH_CHARACTER = "character/";

	/*
	 * file names for sprite sheets
	 */
	private static final String FILE_AVIAN0 = "Avian0.png";
	private static final String FF_AVIAN0 = PATH_MOBS + FILE_AVIAN0;
	private static final String FILE_AVIAN1 = "Avian1.png";
	private static final String FF_AVIAN1 = PATH_MOBS + FILE_AVIAN1;
	private static final String FILE_DECOR0 = "Decor0.png";
	private static final String FF_DECOR0 = PATH_GUI + FILE_DECOR0;
	private static final String FILE_DECOR1 = "Decor1.png";
	private static final String FF_DECOR1 = PATH_GUI + FILE_DECOR1;
	private static final String FILE_ELEMENTAL0 = "Elemental0.png";
	private static final String FF_ELEMENTAL0 = PATH_MOBS + FILE_ELEMENTAL0;
	private static final String FILE_ELEMENTAL1 = "Elemental1.png";
	private static final String FF_ELEMENTAL1 = PATH_MOBS + FILE_ELEMENTAL1;
	private static final String FILE_HUMANOID0 = "Humanoid0.png";
	private static final String FF_HUMANOID0 = PATH_MOBS + FILE_HUMANOID0;
	private static final String FILE_HUMANOID1 = "Humanoid1.png";
	private static final String FF_HUMANOID1 = PATH_MOBS + FILE_HUMANOID1;
	private static final String FILE_PEST0 = "Pest0.png";
	private static final String FF_PEST0 = PATH_MOBS + FILE_PEST0;
	private static final String FILE_PEST1 = "Pest1.png";
	private static final String FF_PEST1 = PATH_MOBS + FILE_PEST1;
	private static final String FILE_PLANT0 = "Plant0.png";
	private static final String FF_PLANT0 = PATH_MOBS + FILE_PLANT0;
	private static final String FILE_PLANT1 = "Plant1.png";
	private static final String FF_PLANT1 = PATH_MOBS + FILE_PLANT1;
	private static final String FILE_PLAYER0 = "Player0.png";
	private static final String FF_PLAYER0 = PATH_MOBS + FILE_PLAYER0;
	private static final String FILE_PLAYER1 = "Player1.png";
	private static final String FF_PLAYER1 = PATH_MOBS + FILE_PLAYER1;
	private static final String FILE_POTION = "Potion.png";
	private static final String FF_POTION = PATH_ITEMS + FILE_POTION;
	private static final String FILE_REPTILE0 = "Reptile0.png";
	private static final String FF_REPTILE0 = PATH_MOBS + FILE_REPTILE0;
	private static final String FILE_REPTILE1 = "Reptile1.png";
	private static final String FF_REPTILE1 = PATH_MOBS + FILE_REPTILE1;
	private static final String FILE_RING = "Ring.png";
	private static final String FF_RING = PATH_ITEMS + FILE_RING;
	private static final String FILE_RODENT0 = "Rodent0.png";
	private static final String FF_RODENT0 = PATH_MOBS + FILE_RODENT0;
	private static final String FILE_RODENT1 = "Rodent1.png";
	private static final String FF_RODENT1 = PATH_MOBS + FILE_RODENT1;
	private static final String FILE_SCROLL = "Scroll.png";
	private static final String FF_SCROLL = PATH_ITEMS + FILE_SCROLL;
	private static final String FILE_SHIELD = "Shield.png";
	private static final String FF_SHIELD = PATH_ITEMS + FILE_SHIELD;
	private static final String FILE_SLIME0 = "Slime0.png";
	private static final String FF_SLIME0 = PATH_MOBS + FILE_SLIME0;
	private static final String FILE_SLIME1 = "Slime1.png";
	private static final String FF_SLIME1 = PATH_MOBS + FILE_SLIME1;
	private static final String FILE_UNDEAD1 = "Undead1.png";
	private static final String FF_UNDEAD1 = PATH_MOBS + FILE_UNDEAD1;
	private static final String FILE_UNDEAD0 = "Undead0.png";
	private static final String FF_UNDEAD0 = PATH_MOBS + FILE_UNDEAD0;
	private static final String FILE_WAND = "Wand.png";
	private static final String FF_WAND = PATH_ITEMS + FILE_WAND;
	private static final String FILE_WARRIOR = "Warrior.png";
	private static final String FF_WARRIOR = PATH_CHARACTER + FILE_WARRIOR;
	private static final String FILE_WEAPON = "ShortWep.png";
	private static final String FF_WEAPON = PATH_ITEMS + FILE_WEAPON;
	private static final String FILE_GUI0 = "GUI0.png";
	private static final String FF_GUI0 = PATH_GUI + FILE_GUI0;
	private static final String FILE_GUI1 = "GUI1.png";
	private static final String FF_GUI1 = PATH_GUI + FILE_GUI1;

	private static final SpriteMapper instance = new SpriteMapper();

	private final Map<EEntity, Animation> mappedAnimations = new HashMap<>();
	private final Map<EEntity, Map<Integer, Animation>> mappedDirectionalAnimations = new HashMap<>();
	private final Map<EEntity, TextureRegion> mappedSprites = new HashMap<>();
	private final Set<Texture> toDispose = new HashSet<>();
	private final Map<String, Texture> textures = new HashMap<>();

	/*
	 * pool for random sprite selection for items
	 */
	private final Coord[] spriteSheetPositionPotions = { Coord.get(0, 0), Coord.get(0, 1), Coord.get(0, 2),
			Coord.get(0, 3), Coord.get(0, 4), Coord.get(1, 0), Coord.get(1, 1), Coord.get(1, 2),
			Coord.get(1, 3), Coord.get(1, 4) };
	private final Coord[] spriteSheetPositionScrolls = { Coord.get(0, 0), Coord.get(0, 1), Coord.get(0, 2),
			Coord.get(0, 3), Coord.get(1, 0), Coord.get(1, 1), Coord.get(1, 2), Coord.get(1, 3),
			Coord.get(2, 0), Coord.get(2, 1) };
	private final Coord[] spriteSheetPositionWands = spriteSheetPositionScrolls;
	private final Coord[] spriteSheetPositionRings = spriteSheetPositionScrolls;

	private final Map<EEntity, Coord> spriteSheetSelectionPotions = new HashMap<>();
	private final Map<EEntity, Coord> spriteSheetSelectionScrolls = new HashMap<>();
	private final Map<EEntity, Coord> spriteSheetSelectionWands = new HashMap<>();
	private final Map<EEntity, Coord> spriteSheetSelectionRings = new HashMap<>();

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
		switch (type) {
		case ARCHER:
			mapAnimationPlayer(type, 2, 3);
			break;
		case WARPER:
			mapAnimationPlayer(type, 1, 8);
			break;
		case BLOB:
			mapAnimationPest(type, 2, 3);
			break;
		case SLUG:
			mapAnimationPest(type, 1, 7);
			break;
		case SQUIRREL:
			mapAnimationRodent(type, 0, 0);
			break;
		case BAT:
			mapAnimationAvian(type, 0, 11);
			break;
		case MAGICIAN:
			mapAnimationHumanoid(type, 0, 23);
			break;
		case GHOST:
			mapAnimationUndead(type, 0, 4);
			break;
		case SHADOW:
			mapAnimationUndead(type, 3, 4);
			break;
		case STEALER:
			mapAnimationUndead(type, 0, 0);
			break;
		case EYEBALL:
			mapAnimationElemental(type, 0, 5);
			break;
		case GOLEM:
			mapAnimationElemental(type, 4, 0);
			break;
		case BOMB:
			mapAnimationElemental(type, 2, 5);
			break;
		case DRAGON:
			mapAnimationReptile(type, 5, 2);
			break;
		case ACID:
			mapAnimationSlime(type, 0, 2);
			break;
		case MUSHROOM:
			mapAnimationPlant(type, 0, 2);
			break;
		case TORCH:
			mapAnimationDecor(type, 0, 8);
			break;
		case STATUS_DECORATOR_ALERTED:
			mapAnimationGui(type, 12, 3);
			break;
		case STATUS_DECORATOR_CONFUSED:
			mapAnimationGui(type, 13, 3);
			break;
		case STATUS_DECORATOR_NONE:
			mapAnimationGui(type, 10, 1);
			break;
		case STATUS_DECORATOR_DEAD:
			mapAnimationGui(type, 11, 3);
			break;
		case STATUS_DECORATOR_SLEEPING:
			mapAnimationGui(type, 10, 4);
			break;
		case STATUS_DECORATOR_WAITING:
			mapAnimationGui(type, 13, 4);
			break;
		default:
			break;
		}
	}

	private void mapAnimationPlayer(final EEntity type, final int x, final int y) {
		mapAnimation(FF_PLAYER0, FF_PLAYER1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationPest(final EEntity type, final int x, final int y) {
		mapAnimation(FF_PEST0, FF_PEST1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationRodent(final EEntity type, final int x, final int y) {
		mapAnimation(FF_RODENT0, FF_RODENT1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationAvian(final EEntity type, final int x, final int y) {
		mapAnimation(FF_AVIAN0, FF_AVIAN1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationHumanoid(final EEntity type, final int x, final int y) {
		mapAnimation(FF_HUMANOID0, FF_HUMANOID1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationUndead(final EEntity type, final int x, final int y) {
		mapAnimation(FF_UNDEAD0, FF_UNDEAD1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationElemental(final EEntity type, final int x, final int y) {
		mapAnimation(FF_ELEMENTAL0, FF_ELEMENTAL1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationReptile(final EEntity type, final int x, final int y) {
		mapAnimation(FF_REPTILE0, FF_REPTILE1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationSlime(final EEntity type, final int x, final int y) {
		mapAnimation(FF_SLIME0, FF_SLIME1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationPlant(final EEntity type, final int x, final int y) {
		mapAnimation(FF_PLANT0, FF_PLANT1, type, x, y, ANIMATION_DURATION_CREATURE);
	}

	private void mapAnimationDecor(final EEntity type, final int x, final int y) {
		mapAnimation(FF_DECOR0, FF_DECOR1, type, x, y, ANIMATION_DURATION_DECORATION);
	}

	private void mapAnimationGui(final EEntity type, final int x, final int y) {
		mapAnimation(FF_GUI0, FF_GUI1, type, x, y, ANIMATION_DURATION_DECORATION);
	}

	private void mapAnimation(final String fileName1, final String fileName2, final EEntity type, final int x,
			final int y, final float frameDuration) {
		final TextureRegion[] frames = new TextureRegion[2];
		frames[0] = loadFrame(getTexture(fileName1), x, y);
		frames[1] = loadFrame(getTexture(fileName2), x, y);
		mappedAnimations.put(type, new Animation(frameDuration, frames));
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
			final Texture t = getTexture(FF_WARRIOR);
			for (int i = 0; i < 4; i++) {
				framesDown[i] = loadFrame(t, i, 0);
				framesLeft[i] = loadFrame(t, i, 1);
				framesRight[i] = loadFrame(t, i, 2);
				framesUp[i] = loadFrame(t, i, 3);
			}
			final Map<Integer, Animation> animations = new HashMap<>();
			animations.put(Move.UP, new Animation(ANIMATION_DURATION_CREATURE, framesUp));
			animations.put(Move.DOWN, new Animation(ANIMATION_DURATION_CREATURE, framesDown));
			animations.put(Move.LEFT, new Animation(ANIMATION_DURATION_CREATURE, framesLeft));
			animations.put(Move.RIGHT, new Animation(ANIMATION_DURATION_CREATURE, framesRight));
			mappedDirectionalAnimations.put(type, animations);
			break;
		default:
			break;
		}
	}

	private void loadSprite(final EEntity type) {
		switch (type) {
		case POTION_A:
		case POTION_B:
		case POTION_C:
		case POTION_D:
		case POTION_E:
		case POTION_F:
		case POTION_G:
		case POTION_H:
		case POTION_I:
		case POTION_J:
			mapSpritePotion(type);
			break;
		case SCROLL_A:
		case SCROLL_B:
		case SCROLL_C:
		case SCROLL_D:
		case SCROLL_E:
		case SCROLL_F:
		case SCROLL_G:
		case SCROLL_H:
		case SCROLL_I:
		case SCROLL_J:
			mapSpriteScroll(type);
			break;
		case WAND_A:
		case WAND_B:
		case WAND_C:
		case WAND_D:
		case WAND_E:
		case WAND_F:
		case WAND_G:
		case WAND_H:
		case WAND_I:
		case WAND_J:
			mapSpriteWand(type);
			break;
		case RING_A:
		case RING_B:
		case RING_C:
		case RING_D:
		case RING_E:
		case RING_F:
		case RING_G:
		case RING_H:
		case RING_I:
		case RING_J:
			mapSpriteRing(type);
			break;
		case WEAPON_A:
			mapSpriteWeapon(type, 0, 0);
			break;
		case WEAPON_B:
			mapSpriteWeapon(type, 0, 1);
			break;
		case WEAPON_C:
			mapSpriteWeapon(type, 0, 2);
			break;
		case WEAPON_D:
			mapSpriteWeapon(type, 0, 3);
			break;
		case WEAPON_E:
			mapSpriteWeapon(type, 1, 0);
			break;
		case WEAPON_F:
			mapSpriteWeapon(type, 1, 1);
			break;
		case WEAPON_G:
			mapSpriteWeapon(type, 1, 2);
			break;
		case WEAPON_H:
			mapSpriteWeapon(type, 1, 3);
			break;
		case WEAPON_I:
			mapSpriteWeapon(type, 2, 0);
			break;
		case WEAPON_J:
			mapSpriteWeapon(type, 2, 1);
			break;
		case SHIELD_A:
			mapSpriteShield(type, 0, 0);
			break;
		case SHIELD_B:
			mapSpriteShield(type, 1, 0);
			break;
		case SHIELD_C:
			mapSpriteShield(type, 2, 0);
			break;
		case SHIELD_D:
			mapSpriteShield(type, 3, 0);
			break;
		case SHIELD_E:
			mapSpriteShield(type, 4, 0);
			break;
		case SHIELD_F:
			mapSpriteShield(type, 5, 0);
			break;
		case SHIELD_G:
			mapSpriteShield(type, 6, 0);
			break;
		default:
			break;
		}
	}

	/**
	 * tries to select a random sprite out of a pool of available sprite sheet
	 * positions
	 *
	 * @param type
	 * @param cache
	 * @param spriteSheetPositions
	 * @return null when no free sprite position could be found
	 */
	private Coord itemRandomSpriteSelection(final EEntity type, final Coord[] spriteSheetPositions,
			final Map<EEntity, Coord> cache) {
		Coord coord = cache.get(type);
		if (coord == null) {
			Coord tempCoord = null;
			final RNG rng = GameEngine.getRng();

			// try to find a random free position
			int tries = 0;
			final int maxTries = spriteSheetPositions.length;
			boolean found = false;
			while (!found && tries < maxTries) {
				tempCoord = spriteSheetPositions[rng.nextInt(spriteSheetPositions.length)];
				found = !cache.containsValue(tempCoord);
				tries += 1;
			}

			// get the next best free position
			tries = 0;
			while (!found && tries < maxTries) {
				tempCoord = spriteSheetPositions[tries];
				found = !cache.containsValue(tempCoord);
				tries += 1;
			}

			if (found) {
				coord = tempCoord;
				cache.put(type, coord);
			}
		}
		return coord;
	}

	private void mapSpritePotion(final EEntity type) {
		mapSprite(FF_POTION, type,
				itemRandomSpriteSelection(type, spriteSheetPositionPotions, spriteSheetSelectionPotions));
	}

	private void mapSpriteScroll(final EEntity type) {
		mapSprite(FF_SCROLL, type,
				itemRandomSpriteSelection(type, spriteSheetPositionScrolls, spriteSheetSelectionScrolls));
	}

	private void mapSpriteWand(final EEntity type) {
		mapSprite(FF_WAND, type,
				itemRandomSpriteSelection(type, spriteSheetPositionWands, spriteSheetSelectionWands));
	}

	private void mapSpriteRing(final EEntity type) {
		mapSprite(FF_RING, type,
				itemRandomSpriteSelection(type, spriteSheetPositionRings, spriteSheetSelectionRings));
	}

	private void mapSpriteWeapon(final EEntity type, final int x, final int y) {
		mapSprite(FF_WEAPON, type, x, y);
	}

	private void mapSpriteShield(final EEntity type, final int x, final int y) {
		mapSprite(FF_SHIELD, type, x, y);
	}

	private void mapSprite(final String fileName, final EEntity type, final Coord c) {
		if (c != null) {
			mapSprite(fileName, type, c.x, c.y);
		} else {
			Gdx.app.error("SpriteMapper.mapSprite",
					"couldn't find sprite position for entity " + type.toString());
		}
	}

	private void mapSprite(final String fileName, final EEntity type, final int x, final int y) {
		mappedSprites.put(type, loadFrame(getTexture(fileName), x, y));
	}

	private Texture getTexture(final String fileName) {
		Texture t = null;
		if (!textures.containsKey(fileName)) {
			t = new Texture(Gdx.files.internal(fileName), false);
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			t.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
			toDispose.add(t);
			textures.put(fileName, t);
		} else {
			t = textures.get(fileName);
		}
		return t;
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
