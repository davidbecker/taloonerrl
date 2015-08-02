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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import de.brainstormsoftworks.taloonerrl.actors.ActorFactory;
import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;
import de.brainstormsoftworks.taloonerrl.actors.IActor;
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
	Texture warrior;
	Texture cursor;

	float elapsed;

	TextureRegion[] walkFramesUp = new TextureRegion[4];
	TextureRegion[] walkFramesDown = new TextureRegion[4];
	TextureRegion[] walkFramesLeft = new TextureRegion[4];
	TextureRegion[] walkFramesRight = new TextureRegion[4];
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
	Animation walkUp;
	Animation walkDown;
	Animation walkLeft;
	Animation walkRight;
	EDirection walkingDirection = EDirection.RIGHT;
	private static final float scale = 16f;
	float mouseX;
	float mouseY;

	private boolean isPlayerTurn = false;
	private float delayToNextTurn = 0f;
	/** minimum delay between player turns */
	private static final float delayBetweenTurns = 0.03f;
	private static final float delayToNextAnimation = 0f;
	/** minimum delay between player turns */
	private static final float delayBetweenAnimation = 0.03f;

	private final IActor player = ActorFactory.createActor(EActorTypes.PLAYER);
	public static IMap map = null;
	private static final String fontPath = "font/";
	private Entity playerEntity;
	private HealthComponent playerHealthComponent;
	private PositionComponent playerPositionComponent;

	@Override
	public void create() {
		map = MapFactory.createMap(Renderer.TILES_HORIZONTAL, Renderer.TILES_VERTICAL);

		warrior = new Texture(Gdx.files.internal("character/Warrior.png"));
		for (int i = 0; i < 4; i++) {
			walkFramesDown[i] = new TextureRegion(warrior, i * Renderer.tileSize, 0 * Renderer.tileSize,
					Renderer.tileSize, Renderer.tileSize);
			walkFramesLeft[i] = new TextureRegion(warrior, i * Renderer.tileSize, 1 * Renderer.tileSize,
					Renderer.tileSize, Renderer.tileSize);
			walkFramesRight[i] = new TextureRegion(warrior, i * Renderer.tileSize, 2 * Renderer.tileSize,
					Renderer.tileSize, Renderer.tileSize);
			walkFramesUp[i] = new TextureRegion(warrior, i * Renderer.tileSize, 3 * Renderer.tileSize,
					Renderer.tileSize, Renderer.tileSize);
		}
		walkUp = new Animation(0.25f, walkFramesUp);
		walkDown = new Animation(0.25f, walkFramesDown);
		walkLeft = new Animation(0.25f, walkFramesLeft);
		walkRight = new Animation(0.25f, walkFramesRight);

		cursor = new Texture(Gdx.files.internal("cursor.png"));
		cursorTopLeft = new TextureRegion(cursor, 0, 0, 8, 8);
		cursorTopRight = new TextureRegion(cursor, 8, 0, 8, 8);
		cursorBottomLeft = new TextureRegion(cursor, 0, 8, 8, 8);
		cursorBottomRight = new TextureRegion(cursor, 8, 8, 8, 8);

		mouseX = Gdx.graphics.getWidth() / 2;
		mouseY = Gdx.graphics.getHeight() / 2;

		player.getMovementComponent().move(4, 4);
		// forces the engine to initialize
		final GameEngine gameEngine = GameEngine.getInstance();
		playerEntity = gameEngine.createNewEntity(EEntity.PLAYER);
		playerHealthComponent = playerEntity.getComponent(HealthComponent.class);
		playerPositionComponent = playerEntity.getComponent(PositionComponent.class);
		// TODO only use one player
		playerPositionComponent.setX(player.getMovementComponent().getXPosition());
		playerPositionComponent.setY(player.getMovementComponent().getYPosition());
		final Entity createNewEntity = gameEngine.createNewEntity(EEntity.SQUIRREL, 1, 1);
		createNewEntity.getComponent(HealthComponent.class).setHealthPercent(0.75f);

		gameEngine.createNewEntity(EEntity.BLOB, 2, 2);
		for (int i = 1; i < Renderer.TILES_HORIZONTAL - 1; i++) {
			gameEngine.createNewEntity(EEntity.TORCH, i, Renderer.TILES_VERTICAL - 1);
		}

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

		switch (((int) GameEngine.getInstance().getStateTime()) % 4) {
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
				isPlayerTurn = true;

				if (keyPressedLeft) {
					player.getMovementComponent().move(-1, 0);
					walkingDirection = EDirection.LEFT;
				}
				if (keyPressedRight) {
					player.getMovementComponent().move(1, 0);
					walkingDirection = EDirection.RIGHT;
				}
				if (keyPressedDown) {
					player.getMovementComponent().move(0, -1);
					walkingDirection = EDirection.DOWN;
				}
				if (keyPressedUp) {
					player.getMovementComponent().move(0, 1);
					walkingDirection = EDirection.UP;
				}
				// TODO only use one player
				playerPositionComponent.setX(player.getMovementComponent().getXPosition());
				playerPositionComponent.setY(player.getMovementComponent().getYPosition());
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

		DungeonRenderer.getInstance().render(map.getMap(), Renderer.TILES_HORIZONTAL, Renderer.TILES_VERTICAL);
		GuiRenderer.getInstance().render(Renderer.TILES_HORIZONTAL, Renderer.TILES_VERTICAL);

		TextureRegion currentFrame = null;
		switch (walkingDirection) {
		case UP:
			currentFrame = RenderUtil.getKeyFrame(walkUp);
			break;
		case DOWN:
			currentFrame = RenderUtil.getKeyFrame(walkDown);
			break;
		case LEFT:
			currentFrame = RenderUtil.getKeyFrame(walkLeft);
			break;
		case RIGHT:
			currentFrame = RenderUtil.getKeyFrame(walkRight);
			break;
		default:
			break;
		}
		Renderer.getInstance().BATCH.draw(currentFrame, player.getMovementComponent().getXPosition() * scale,
				player.getMovementComponent().getYPosition() * scale);
		// drawActor(Renderer.getInstance().BATCH, player);

		final Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		Renderer.getInstance().unprojectFromCamera(touchPos);
		mouseX = touchPos.x - Renderer.tileSize / 2;
		mouseY = touchPos.y - Renderer.tileSize / 2;

		Renderer.getInstance().BATCH.draw(cursor, mouseX, mouseY);
		// FIXME WIP
		// // translate mouse coordinates to selected tile
		// final float tileX = touchPos.x /Renderer.TILES_HORIZONTAL; // 0-18
		// final float tileY = touchPos.y / Renderer.TILES_VERTICAL;
		// Renderer.getInstance().BATCH.draw(cursor, tileX, tileY);
		// System.out.println(tileX + " " + tileY);
		highlightPlayerTile(player);

		GameEngine.getInstance().update(deltaTime);
		Renderer.getInstance().endRendering();

		isPlayerTurn = false;
	}

	private static void highlightPlayerTile(final IActor player) {
		final int x = player.getMovementComponent().getXPosition();
		final int y = player.getMovementComponent().getYPosition();
		Renderer.getInstance().BATCH.draw(cursorTopLeft, x * scale + cursorTopLeftOffsetX - cursorAnimationOffset,
				y * scale + cursorTopLeftOffsetY + cursorAnimationOffset);
		Renderer.getInstance().BATCH.draw(cursorTopRight, x * scale + cursorTopRightOffsetX + cursorAnimationOffset,
				y * scale + cursorTopRightOffsetY + cursorAnimationOffset);
		Renderer.getInstance().BATCH.draw(cursorBottomLeft, x * scale + cursorBottomLeftOffsetX - cursorAnimationOffset,
				y * scale + cursorBottomLeftOffsetY - cursorAnimationOffset);
		Renderer.getInstance().BATCH.draw(cursorBottomRight,
				x * scale + cursorBottomRightOffsetX + cursorAnimationOffset,
				y * scale + cursorBottomRightOffsetY - cursorAnimationOffset);

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		Renderer.getInstance().BATCH.dispose();
		warrior.dispose();
		cursor.dispose();
		RenderUtil.disposeInstances();
	}
}
