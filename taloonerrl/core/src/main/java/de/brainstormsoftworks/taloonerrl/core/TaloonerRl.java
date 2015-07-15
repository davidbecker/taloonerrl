package de.brainstormsoftworks.taloonerrl.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.brainstormsoftworks.taloonerrl.actors.ActorFactory;
import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;
import de.brainstormsoftworks.taloonerrl.actors.IActor;
import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.ITile;
import de.brainstormsoftworks.taloonerrl.dungeon.MapFactory;

public class TaloonerRl implements ApplicationListener {
	Texture texture;
	Texture font;
	SpriteBatch batch;
	float elapsed;
	TextureRegion sTopLeft;
	TextureRegion sTop;
	TextureRegion sTopRight;
	TextureRegion sLeft;
	TextureRegion sCenter;
	TextureRegion sRight;
	TextureRegion sBottomLeft;
	TextureRegion sBottom;
	TextureRegion sBottomRight;
	TextureRegion at;
	private static final int tileSize = 16;
	float scale = 16f;
	float mouseX;
	float mouseY;
	private static final int TILES_HORIZONTAL = 30;
	private static final int TILES_VERTICAL = 20;
	private static final int VIRTUAL_WIDTH = TILES_HORIZONTAL * tileSize;
	private static final int VIRTUAL_HEIGHT = TILES_VERTICAL * tileSize;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH
			/ (float) VIRTUAL_HEIGHT;
	private OrthographicCamera camera;
	private Rectangle viewport;
	private Rectangle playerOld;

	private IMap map = MapFactory.createMap(TILES_HORIZONTAL, TILES_VERTICAL);

	private IActor player = ActorFactory.createActor(EActorTypes.PLAYER);

	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("Floor.png"), false);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		// 1 + yyy was added since there seems to be an offset in the tileset
		sTopLeft = new TextureRegion(texture, 0 * tileSize, 1 + 3 * tileSize,
				tileSize, tileSize);
		sTop = new TextureRegion(texture, 1 * tileSize, 1 + 3 * tileSize,
				tileSize, tileSize);
		sTopRight = new TextureRegion(texture, 2 * tileSize, 1 + 3 * tileSize,
				tileSize, tileSize);
		sLeft = new TextureRegion(texture, 0 * tileSize, 1 + 4 * tileSize,
				tileSize, tileSize);
		sCenter = new TextureRegion(texture, 1 * tileSize, 1 + 4 * tileSize,
				tileSize, tileSize);
		sRight = new TextureRegion(texture, 2 * tileSize, 1 + 4 * tileSize,
				tileSize, tileSize);
		sBottomLeft = new TextureRegion(texture, 0 * tileSize,
				1 + 5 * tileSize, tileSize, tileSize);
		sBottom = new TextureRegion(texture, 1 * tileSize, 1 + 5 * tileSize,
				tileSize, tileSize);
		sBottomRight = new TextureRegion(texture, 2 * tileSize,
				1 + 5 * tileSize, tileSize, tileSize);
		font = new Texture(Gdx.files.internal("dejavu16x16_gs_tc.png"));
		at = new TextureRegion(font, 0 * tileSize, 1 * tileSize, tileSize,
				tileSize);
		batch = new SpriteBatch();
		mouseX = Gdx.graphics.getWidth() / 2;
		mouseY = Gdx.graphics.getHeight() / 2;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		playerOld = new Rectangle();

		playerOld.x = 30 * tileSize / 2 - tileSize / 2;
		playerOld.y = 20 * tileSize / 2 - tileSize / 2;
		playerOld.width = tileSize;
		playerOld.height = tileSize;

		// TiledMap t = new TiledMap();
		// TiledMapTileLayer layer = (TiledMapTileLayer)t.getLayers().get(0);
	}

	@Override
	public void resize(int width, int height) {
		// calculate new viewport
		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
		if (aspectRatio > ASPECT_RATIO) {
			scale = (float) height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
		} else if (aspectRatio < ASPECT_RATIO) {
			scale = (float) width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) VIRTUAL_WIDTH;
		}

		float w = VIRTUAL_WIDTH * scale;
		float h = VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);
	}

	@Override
	public void render() {
		// update camera
		camera.update();

		// see http://codebin.co.uk/blog/pixelated-rendering-in-libgdx/

		// set viewport
		Gdx.gl.glViewport(Math.round(viewport.x * tileSize) / tileSize,
				Math.round(viewport.y * tileSize) / tileSize,
				Math.round(viewport.width * tileSize) / tileSize,
				Math.round(viewport.height * tileSize) / tileSize);
		Gdx.graphics.setTitle("current fps: "
				+ Gdx.graphics.getFramesPerSecond());
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		// camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		// batch.draw(texture, 100 + 100 * (float) Math.cos(elapsed),
		// 100 + 25 * (float) Math.sin(elapsed));
		for (int x = 0; x < TILES_HORIZONTAL; x++) {
			for (int y = 0; y < TILES_VERTICAL; y++) {
				TextureRegion tile = getTile(map.getMap()[x][y]);
				if (tile != null) {
					batch.draw(tile, x * scale, y * scale);
				}
			}
		}
		// for (int i = 0; i < 10; i++) {
		// batch.draw(sTopLeft, i * scale, i * scale);
		// batch.draw(sTop, i * scale + scale, i * scale);
		// batch.draw(sTopRight, i * scale + 2 * scale, i * scale);
		// }
		// batch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_SRC_ALPHA);

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			playerOld.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			playerOld.x += 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			playerOld.y -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.UP))
			playerOld.y += 200 * Gdx.graphics.getDeltaTime();
		if (playerOld.x < 0)
			playerOld.x = 0;
		if (playerOld.x > VIRTUAL_WIDTH - tileSize)
			playerOld.x = VIRTUAL_WIDTH - tileSize;
		if (playerOld.y < 0)
			playerOld.y = 0;
		if (playerOld.y > VIRTUAL_HEIGHT - tileSize)
			playerOld.y = VIRTUAL_HEIGHT - tileSize;
		batch.draw(at, playerOld.x, playerOld.y);

		drawActor(batch, player);

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		mouseX = touchPos.x - tileSize / 2;
		mouseY = touchPos.y - tileSize / 2;

		batch.draw(at, mouseX, mouseY);
		batch.end();
	}

	private void drawActor(SpriteBatch spriteBatch, IActor actor) {
		spriteBatch.draw(getSprite(actor), actor.getMovementComponent()
				.getXPosition() * scale, actor.getMovementComponent()
				.getYPosition() * scale);
	}

	private TextureRegion getTile(ITile tile) {
		switch (tile.getFloor()) {
		case ROOM_BOTTOM:
			return sBottom;
		case ROOM_BOTTOMLEFT_CORNER:
			return sBottomLeft;
		case ROOM_BOTTOMRIGHT_CORNER:
			return sBottomRight;
		case ROOM_CENTER:
			return sCenter;
		case ROOM_LEFT:
			return sLeft;
		case ROOM_RIGHT:
			return sRight;
		case ROOM_TOP:
			return sTop;
		case ROOM_TOPLEFT_CORNER:
			return sTopLeft;
		case ROOM_TOPRIGHT_CORNER:
			return sTopRight;
		case NOTHING:
			// same as default for now
		default:
			// keep tile blank
			return null;
		}
	}

	private TextureRegion getSprite(IActor actor) {
		switch (actor.getSpriteComponent().getSprite()) {
		case AT:
			return at;
		default:
			return null;
		}
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
		font.dispose();
		texture.dispose();
	}
}
