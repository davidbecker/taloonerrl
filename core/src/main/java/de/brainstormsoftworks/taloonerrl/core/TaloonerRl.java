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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.brainstormsoftworks.taloonerrl.actors.ActorFactory;
import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;
import de.brainstormsoftworks.taloonerrl.actors.IActor;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;
import de.brainstormsoftworks.taloonerrl.render.DungeonRenderer;
import de.brainstormsoftworks.taloonerrl.render.GuiRenderer;

public class TaloonerRl implements ApplicationListener {
	Texture warrior;
	Texture cursor;
	SpriteBatch batch;
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
	private static final int tileSize = 16;
	private static final float scale = 16f;
	float mouseX;
	float mouseY;
	private static final int TILES_HORIZONTAL = 30;
	private static final int TILES_VERTICAL = 20;
	private static final int VIRTUAL_WIDTH = TILES_HORIZONTAL * tileSize + 4 * tileSize;
	private static final int VIRTUAL_HEIGHT = TILES_VERTICAL * tileSize;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;
	private OrthographicCamera camera;
	private Rectangle viewport;

	private boolean isPlayerTurn = false;
	private float delayToNextTurn = 0f;
	/** minimum delay between player turns */
	private static final float delayBetweenTurns = 0.03f;
	private static final float delayToNextAnimation = 0f;
	/** minimum delay between player turns */
	private static final float delayBetweenAnimation = 0.03f;

	private static float stateTime;

	private final IActor player = ActorFactory.createActor(EActorTypes.PLAYER);
	public static IMap map = null;
	private static final String fontPath = "font/";

	@Override
	public void create() {
		map = MapFactory.createMap(TILES_HORIZONTAL, TILES_VERTICAL);

		warrior = new Texture(Gdx.files.internal("character/Warrior.png"));
		for (int i = 0; i < 4; i++) {
			walkFramesDown[i] = new TextureRegion(warrior, i * tileSize, 0 * tileSize, tileSize, tileSize);
			walkFramesLeft[i] = new TextureRegion(warrior, i * tileSize, 1 * tileSize, tileSize, tileSize);
			walkFramesRight[i] = new TextureRegion(warrior, i * tileSize, 2 * tileSize, tileSize, tileSize);
			walkFramesUp[i] = new TextureRegion(warrior, i * tileSize, 3 * tileSize, tileSize, tileSize);
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

		batch = new SpriteBatch();
		mouseX = Gdx.graphics.getWidth() / 2;
		mouseY = Gdx.graphics.getHeight() / 2;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		player.getMovementComponent().move(4, 4);
		// forces the engine to initialize
		final GameEngine instance = GameEngine.instance;
		final Entity createNewEntity = instance.createNewEntity(EActorTypes.PLAYER);

		DungeonRenderer.initInstance();
		GuiRenderer.initInstance();
	}

	@Override
	public void resize(final int width, final int height) {
		// calculate new viewport
		final float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		final Vector2 crop = new Vector2(0f, 0f);
		if (aspectRatio > ASPECT_RATIO) {
			scale = (float) height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
		} else if (aspectRatio < ASPECT_RATIO) {
			scale = (float) width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) VIRTUAL_WIDTH;
		}

		final float w = VIRTUAL_WIDTH * scale;
		final float h = VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);
	}

	@Override
	public void render() {
		// check if the player did something first
		// TODO change to a more general system

		final float deltaTime = Gdx.graphics.getDeltaTime();
		delayToNextTurn -= deltaTime;
		stateTime += deltaTime;

		switch (((int) stateTime) % 4) {
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
				delayToNextTurn = delayBetweenTurns;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			GuiRenderer.playerHeath = 0.00f;
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			GuiRenderer.playerHeath = 0.25f;
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			GuiRenderer.playerHeath = 0.5f;
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			GuiRenderer.playerHeath = 0.75f;
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_5)) {

			GuiRenderer.playerHeath = 1.0f;
		}

		camera.update();

		// see http://codebin.co.uk/blog/pixelated-rendering-in-libgdx/

		final int viewPortX = Math.round(viewport.x * tileSize) / tileSize;
		final int viewPortY = Math.round(viewport.y * tileSize) / tileSize;
		final int viewPortWidth = Math.round(viewport.width * tileSize) / tileSize;
		final int viewPortHeight = Math.round(viewport.height * tileSize) / tileSize;
		Gdx.gl.glViewport(viewPortX, viewPortY, viewPortWidth, viewPortHeight);
		Gdx.graphics.setTitle("current fps: " + Gdx.graphics.getFramesPerSecond());
		elapsed += deltaTime;
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.enableBlending();

		DungeonRenderer.getInstance().render(batch, map.getMap(), TILES_HORIZONTAL, TILES_VERTICAL);
		GuiRenderer.getInstance().render(batch, TILES_HORIZONTAL, TILES_VERTICAL);

		TextureRegion currentFrame = null;
		switch (walkingDirection) {
		case UP:
			currentFrame = walkUp.getKeyFrame(stateTime, true);
			break;
		case DOWN:
			currentFrame = walkDown.getKeyFrame(stateTime, true);
			break;
		case LEFT:
			currentFrame = walkLeft.getKeyFrame(stateTime, true);
			break;
		case RIGHT:
			currentFrame = walkRight.getKeyFrame(stateTime, true);
			break;
		default:
			break;
		}
		batch.draw(currentFrame, player.getMovementComponent().getXPosition() * scale,
				player.getMovementComponent().getYPosition() * scale);
		// drawActor(batch, player);

		final Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos, viewPortX, viewPortY, viewPortWidth, viewPortHeight);
		mouseX = touchPos.x - tileSize / 2;
		mouseY = touchPos.y - tileSize / 2;

		batch.draw(cursor, mouseX, mouseY);
		// FIXME WIP
		// // translate mouse coordinates to selected tile
		// final float tileX = touchPos.x / TILES_HORIZONTAL; // 0-18
		// final float tileY = touchPos.y / TILES_VERTICAL;
		// batch.draw(cursor, tileX, tileY);
		// System.out.println(tileX + " " + tileY);
		highlightPlayerTile(batch, player);

		batch.end();

		isPlayerTurn = false;
	}

	private static void highlightPlayerTile(final SpriteBatch batch, final IActor player) {
		final int x = player.getMovementComponent().getXPosition();
		final int y = player.getMovementComponent().getYPosition();
		batch.draw(cursorTopLeft, x * scale + cursorTopLeftOffsetX - cursorAnimationOffset,
				y * scale + cursorTopLeftOffsetY + cursorAnimationOffset);
		batch.draw(cursorTopRight, x * scale + cursorTopRightOffsetX + cursorAnimationOffset,
				y * scale + cursorTopRightOffsetY + cursorAnimationOffset);
		batch.draw(cursorBottomLeft, x * scale + cursorBottomLeftOffsetX - cursorAnimationOffset,
				y * scale + cursorBottomLeftOffsetY - cursorAnimationOffset);
		batch.draw(cursorBottomRight, x * scale + cursorBottomRightOffsetX + cursorAnimationOffset,
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
		batch.dispose();
		warrior.dispose();
		cursor.dispose();
		DungeonRenderer.getInstance().disposeInstance();
		GuiRenderer.getInstance().disposeInstance();
	}
}
