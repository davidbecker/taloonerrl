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
package de.brainstormsoftworks.taloonerrl.core;

import com.artemis.Entity;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import de.brainstormsoftworks.taloonerrl.components.FacingComponent;
import de.brainstormsoftworks.taloonerrl.components.HealthComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.EEntity;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;
import de.brainstormsoftworks.taloonerrl.render.DungeonRenderer;
import de.brainstormsoftworks.taloonerrl.render.GuiRenderer;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

public class TaloonerRl implements ApplicationListener {
	Texture cursor;

	float elapsed;

	private static TextureRegion cursorTopLeft;
	private static TextureRegion cursorTopRight;
	private static TextureRegion cursorBottomLeft;
	private static TextureRegion cursorBottomRight;
	private static final int cursorTopLeftOffsetX = -2;
	private static final int cursorTopLeftOffsetY = 11;
	private static final int cursorTopRightOffsetX = 11;
	private static final int cursorTopRightOffsetY = 11;
	private static final int cursorBottomLeftOffsetX = -2;
	private static final int cursorBottomLeftOffsetY = -2;
	private static final int cursorBottomRightOffsetX = 11;
	private static final int cursorBottomRightOffsetY = -2;
	static int cursorAnimationOffset = 0;
	EDirection walkingDirection = EDirection.RIGHT;
	float mouseX;
	float mouseY;

	// private boolean isPlayerTurn = false;
	private float delayToNextTurn = 0f;
	/** minimum delay between player turns */
	private static final float delayBetweenTurns = 0.03f;
	// private static final float delayToNextAnimation = 0f;
	/** minimum delay between player turns */
	// private static final float delayBetweenAnimation = 0.03f;

	public static IMap map = null;
	// private static final String fontPath = "font/";
	private Entity playerEntity;
	private HealthComponent playerHealthComponent;
	private PositionComponent playerPositionComponent;
	private FacingComponent playerFacingComponent;

	@Override
	public void create() {
		map = MapFactory.createMap(Renderer.TILES_HORIZONTAL, Renderer.TILES_VERTICAL);

		cursor = new Texture(Gdx.files.internal("cursor.png"));
		cursorTopLeft = new TextureRegion(cursor, 0, 0, 8, 8);
		cursorTopRight = new TextureRegion(cursor, 8, 0, 8, 8);
		cursorBottomLeft = new TextureRegion(cursor, 0, 8, 8, 8);
		cursorBottomRight = new TextureRegion(cursor, 8, 8, 8, 8);

		mouseX = Gdx.graphics.getWidth() / 2;
		mouseY = Gdx.graphics.getHeight() / 2;

		// forces the engine to initialize
		final GameEngine gameEngine = GameEngine.getInstance();
		playerEntity = gameEngine.createNewEntity(EEntity.PLAYER);
		playerHealthComponent = playerEntity.getComponent(HealthComponent.class);
		playerPositionComponent = playerEntity.getComponent(PositionComponent.class);
		playerFacingComponent = playerEntity.getComponent(FacingComponent.class);
		// TODO only use one player
		playerPositionComponent.setX(4);
		playerPositionComponent.setY(4);
		final Entity createNewEntity = gameEngine.createNewEntity(EEntity.SQUIRREL, 1, 1);
		createNewEntity.getComponent(HealthComponent.class).setHealthPercent(0.75f);

		gameEngine.createNewEntity(EEntity.BLOB, 2, 2);
		for (int i = 1; i < Renderer.TILES_HORIZONTAL - 1; i++) {
			gameEngine.createNewEntity(EEntity.TORCH, i, Renderer.TILES_VERTICAL - 1);
		}
		gameEngine.createNewEntity(EEntity.BAT, 1, 2);
		gameEngine.createNewEntity(EEntity.SLUG, 1, 3);
		gameEngine.createNewEntity(EEntity.MAGICIAN, 1, 4);
		gameEngine.createNewEntity(EEntity.GHOST, 1, 5);
		gameEngine.createNewEntity(EEntity.SHADOW, 1, 6);
		gameEngine.createNewEntity(EEntity.EYEBALL, 1, 7);
		gameEngine.createNewEntity(EEntity.GOLEM, 1, 8);
		gameEngine.createNewEntity(EEntity.ARCHER, 1, 9);
		gameEngine.createNewEntity(EEntity.BOMB, 1, 10);
		gameEngine.createNewEntity(EEntity.WARPER, 1, 11);
		gameEngine.createNewEntity(EEntity.STEALER, 1, 12);
		gameEngine.createNewEntity(EEntity.DRAGON, 1, 13);
		gameEngine.createNewEntity(EEntity.ACID, 1, 14);
		gameEngine.createNewEntity(EEntity.MUSHROOM, 1, 15);

		gameEngine.createNewEntity(EEntity.POTION_A, Renderer.TILES_HORIZONTAL - 2, 1);
		gameEngine.createNewEntity(EEntity.POTION_B, Renderer.TILES_HORIZONTAL - 2, 2);
		gameEngine.createNewEntity(EEntity.POTION_C, Renderer.TILES_HORIZONTAL - 2, 3);
		gameEngine.createNewEntity(EEntity.POTION_D, Renderer.TILES_HORIZONTAL - 2, 4);
		gameEngine.createNewEntity(EEntity.POTION_E, Renderer.TILES_HORIZONTAL - 2, 5);
		gameEngine.createNewEntity(EEntity.POTION_F, Renderer.TILES_HORIZONTAL - 2, 6);
		gameEngine.createNewEntity(EEntity.POTION_G, Renderer.TILES_HORIZONTAL - 2, 7);
		gameEngine.createNewEntity(EEntity.POTION_H, Renderer.TILES_HORIZONTAL - 2, 8);
		gameEngine.createNewEntity(EEntity.POTION_I, Renderer.TILES_HORIZONTAL - 2, 9);
		gameEngine.createNewEntity(EEntity.POTION_J, Renderer.TILES_HORIZONTAL - 2, 10);
		gameEngine.createNewEntity(EEntity.SCROLL_A, Renderer.TILES_HORIZONTAL - 3, 1);
		gameEngine.createNewEntity(EEntity.SCROLL_B, Renderer.TILES_HORIZONTAL - 3, 2);
		gameEngine.createNewEntity(EEntity.SCROLL_C, Renderer.TILES_HORIZONTAL - 3, 3);
		gameEngine.createNewEntity(EEntity.SCROLL_D, Renderer.TILES_HORIZONTAL - 3, 4);
		gameEngine.createNewEntity(EEntity.SCROLL_E, Renderer.TILES_HORIZONTAL - 3, 5);
		gameEngine.createNewEntity(EEntity.SCROLL_F, Renderer.TILES_HORIZONTAL - 3, 6);
		gameEngine.createNewEntity(EEntity.SCROLL_G, Renderer.TILES_HORIZONTAL - 3, 7);
		gameEngine.createNewEntity(EEntity.SCROLL_H, Renderer.TILES_HORIZONTAL - 3, 8);
		gameEngine.createNewEntity(EEntity.SCROLL_I, Renderer.TILES_HORIZONTAL - 3, 9);
		gameEngine.createNewEntity(EEntity.SCROLL_J, Renderer.TILES_HORIZONTAL - 3, 10);
		gameEngine.createNewEntity(EEntity.WAND_A, Renderer.TILES_HORIZONTAL - 4, 1);
		gameEngine.createNewEntity(EEntity.WAND_B, Renderer.TILES_HORIZONTAL - 4, 2);
		gameEngine.createNewEntity(EEntity.WAND_C, Renderer.TILES_HORIZONTAL - 4, 3);
		gameEngine.createNewEntity(EEntity.WAND_D, Renderer.TILES_HORIZONTAL - 4, 4);
		gameEngine.createNewEntity(EEntity.WAND_E, Renderer.TILES_HORIZONTAL - 4, 5);
		gameEngine.createNewEntity(EEntity.WAND_F, Renderer.TILES_HORIZONTAL - 4, 6);
		gameEngine.createNewEntity(EEntity.WAND_G, Renderer.TILES_HORIZONTAL - 4, 7);
		gameEngine.createNewEntity(EEntity.WAND_H, Renderer.TILES_HORIZONTAL - 4, 8);
		gameEngine.createNewEntity(EEntity.WAND_I, Renderer.TILES_HORIZONTAL - 4, 9);
		gameEngine.createNewEntity(EEntity.WAND_J, Renderer.TILES_HORIZONTAL - 4, 10);
		gameEngine.createNewEntity(EEntity.RING_A, Renderer.TILES_HORIZONTAL - 5, 1);
		gameEngine.createNewEntity(EEntity.RING_B, Renderer.TILES_HORIZONTAL - 5, 2);
		gameEngine.createNewEntity(EEntity.RING_C, Renderer.TILES_HORIZONTAL - 5, 3);
		gameEngine.createNewEntity(EEntity.RING_D, Renderer.TILES_HORIZONTAL - 5, 4);
		gameEngine.createNewEntity(EEntity.RING_E, Renderer.TILES_HORIZONTAL - 5, 5);
		gameEngine.createNewEntity(EEntity.RING_F, Renderer.TILES_HORIZONTAL - 5, 6);
		gameEngine.createNewEntity(EEntity.RING_G, Renderer.TILES_HORIZONTAL - 5, 7);
		gameEngine.createNewEntity(EEntity.RING_H, Renderer.TILES_HORIZONTAL - 5, 8);
		gameEngine.createNewEntity(EEntity.RING_I, Renderer.TILES_HORIZONTAL - 5, 9);
		gameEngine.createNewEntity(EEntity.RING_J, Renderer.TILES_HORIZONTAL - 5, 10);
		gameEngine.createNewEntity(EEntity.WEAPON_A, Renderer.TILES_HORIZONTAL - 6, 1);
		gameEngine.createNewEntity(EEntity.WEAPON_B, Renderer.TILES_HORIZONTAL - 6, 2);
		gameEngine.createNewEntity(EEntity.WEAPON_C, Renderer.TILES_HORIZONTAL - 6, 3);
		gameEngine.createNewEntity(EEntity.WEAPON_D, Renderer.TILES_HORIZONTAL - 6, 4);
		gameEngine.createNewEntity(EEntity.WEAPON_E, Renderer.TILES_HORIZONTAL - 6, 5);
		gameEngine.createNewEntity(EEntity.WEAPON_F, Renderer.TILES_HORIZONTAL - 6, 6);
		gameEngine.createNewEntity(EEntity.WEAPON_G, Renderer.TILES_HORIZONTAL - 6, 7);
		gameEngine.createNewEntity(EEntity.WEAPON_H, Renderer.TILES_HORIZONTAL - 6, 8);
		gameEngine.createNewEntity(EEntity.WEAPON_I, Renderer.TILES_HORIZONTAL - 6, 9);
		gameEngine.createNewEntity(EEntity.WEAPON_J, Renderer.TILES_HORIZONTAL - 6, 10);
		gameEngine.createNewEntity(EEntity.SHIELD_A, Renderer.TILES_HORIZONTAL - 7, 1);
		gameEngine.createNewEntity(EEntity.SHIELD_B, Renderer.TILES_HORIZONTAL - 7, 2);
		gameEngine.createNewEntity(EEntity.SHIELD_C, Renderer.TILES_HORIZONTAL - 7, 3);
		gameEngine.createNewEntity(EEntity.SHIELD_D, Renderer.TILES_HORIZONTAL - 7, 4);
		gameEngine.createNewEntity(EEntity.SHIELD_E, Renderer.TILES_HORIZONTAL - 7, 5);
		gameEngine.createNewEntity(EEntity.SHIELD_F, Renderer.TILES_HORIZONTAL - 7, 6);
		gameEngine.createNewEntity(EEntity.SHIELD_G, Renderer.TILES_HORIZONTAL - 7, 7);

		DungeonRenderer.initInstance();
		GuiRenderer.initInstance();
	}

	@Override
	public void resize(final int width, final int height) {
		Renderer.getInstance().resizeViewPort(width, height);
	}

	@Override
	public void render() {
		// check if the player did something first
		// TODO change to a more general system

		final float deltaTime = Gdx.graphics.getDeltaTime();
		delayToNextTurn -= deltaTime;

		switch ((int) GameEngine.getInstance().getStateTime() % 4) {
		case 0:
			cursorAnimationOffset = 0;
			break;
		case 1:
			cursorAnimationOffset = 1;
			break;
		case 2:
			cursorAnimationOffset = 2;
			break;
		case 3:
			cursorAnimationOffset = 1;
			break;
		default:
			cursorAnimationOffset = 0;
		}

		boolean keyPressedLeft = false;
		boolean keyPressedRight = false;
		boolean keyPressedDown = false;
		boolean keyPressedUp = false;
		if (delayToNextTurn <= 0f) {
			// possible for player to make his turn
			keyPressedLeft = Gdx.input.isKeyPressed(Keys.LEFT);
			keyPressedRight = Gdx.input.isKeyPressed(Keys.RIGHT);
			keyPressedDown = Gdx.input.isKeyPressed(Keys.DOWN);
			keyPressedUp = Gdx.input.isKeyPressed(Keys.UP);
			if (keyPressedDown || keyPressedLeft || keyPressedRight || keyPressedUp) {
				// player did a move
				// isPlayerTurn = true;
				// TODO refactor into controller input system
				int dX = 0;
				int dY = 0;
				if (keyPressedLeft) {
					dX = -1;
					walkingDirection = EDirection.LEFT;
				}
				if (keyPressedRight) {
					dX = 1;
					walkingDirection = EDirection.RIGHT;
				}
				if (keyPressedDown) {
					dY = -1;
					walkingDirection = EDirection.DOWN;
				}
				if (keyPressedUp) {
					dY = 1;
					walkingDirection = EDirection.UP;
				}
				// TODO only use one player
				playerPositionComponent.setX(playerPositionComponent.getX() + dX);
				playerPositionComponent.setY(playerPositionComponent.getY() + dY);
				playerFacingComponent.setDirection(walkingDirection);
				delayToNextTurn = delayBetweenTurns;
			}
		}
		playerHealthComponent = playerEntity.getComponent(HealthComponent.class);
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			playerHealthComponent.setHealthPercent(0.00f);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			playerHealthComponent.setHealthPercent(0.25f);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			playerHealthComponent.setHealthPercent(0.5f);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			playerHealthComponent.setHealthPercent(0.75f);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
			playerHealthComponent.setHealthPercent(1.0f);
		}

		elapsed += deltaTime;
		Renderer.getInstance().beginRendering();

		DungeonRenderer.getInstance().render(map.getMap(), Renderer.TILES_HORIZONTAL,
				Renderer.TILES_VERTICAL);
		GuiRenderer.getInstance().render(Renderer.TILES_HORIZONTAL, Renderer.TILES_VERTICAL);

		final Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		Renderer.getInstance().unprojectFromCamera(touchPos);
		mouseX = touchPos.x - Renderer.tileSize / 2;
		mouseY = touchPos.y - Renderer.tileSize / 2;

		Renderer.getInstance().renderOnScreen(cursor, mouseX, mouseY);
		// FIXME WIP
		// // translate mouse coordinates to selected tile
		// final float tileX = touchPos.x /Renderer.TILES_HORIZONTAL; // 0-18
		// final float tileY = touchPos.y / Renderer.TILES_VERTICAL;
		// Renderer.getInstance().BATCH.draw(cursor, tileX, tileY);
		// System.out.println(tileX + " " + tileY);

		// highlightPlayerTile(player);

		GameEngine.getInstance().update(deltaTime);
		Renderer.getInstance().endRendering();

		// isPlayerTurn = false;
	}

	// private static void highlightPlayerTile(final IActor player) {
	// final int x = player.getMovementComponent().getXPosition();
	// final int y = player.getMovementComponent().getYPosition();
	// Renderer.getInstance().BATCH.draw(cursorTopLeft,
	// x * Renderer.scale + cursorTopLeftOffsetX - cursorAnimationOffset,
	// y * Renderer.scale + cursorTopLeftOffsetY + cursorAnimationOffset);
	// Renderer.getInstance().BATCH.draw(cursorTopRight,
	// x * Renderer.scale + cursorTopRightOffsetX + cursorAnimationOffset,
	// y * Renderer.scale + cursorTopRightOffsetY + cursorAnimationOffset);
	// Renderer.getInstance().BATCH.draw(cursorBottomLeft,
	// x * Renderer.scale + cursorBottomLeftOffsetX - cursorAnimationOffset,
	// y * Renderer.scale + cursorBottomLeftOffsetY - cursorAnimationOffset);
	// Renderer.getInstance().BATCH.draw(cursorBottomRight,
	// x * Renderer.scale + cursorBottomRightOffsetX + cursorAnimationOffset,
	// y * Renderer.scale + cursorBottomRightOffsetY - cursorAnimationOffset);
	// }

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		cursor.dispose();
		RenderUtil.disposeInstances();
	}
}
