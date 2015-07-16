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
	Texture floorTexture;
	Texture wallTexture;
	Texture font;
	SpriteBatch batch;
	float elapsed;
	TextureRegion sFloorTopLeft;
	TextureRegion sFloorTop;
	TextureRegion sFloorTopRight;
	TextureRegion sFloorLeft;
	TextureRegion sFloorCenter;
	TextureRegion sFloorRight;
	TextureRegion sFloorBottomLeft;
	TextureRegion sFloorBottom;
	TextureRegion sFloorBottomRight;
	TextureRegion sWallTopLeft;
	TextureRegion sWallHorizontal;
	TextureRegion sWallTopRight;
	TextureRegion sWallVertical;
	TextureRegion sWallBottomLeft;
	TextureRegion sWallBottomRight;
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

	private final IMap map = MapFactory.createMap(TILES_HORIZONTAL,
			TILES_VERTICAL);

	private final IActor player = ActorFactory.createActor(EActorTypes.PLAYER);

	@Override
	public void create() {
		floorTexture = new Texture(Gdx.files.internal("Floor.png"), false);
		floorTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		floorTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		wallTexture = new Texture(Gdx.files.internal("Wall.png"), false);
		wallTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		wallTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);

		// 1 + yyy was added since there seems to be an offset in the tileset
		// for the floor
		sFloorTopLeft = new TextureRegion(floorTexture, 0 * tileSize,
				1 + 3 * tileSize, tileSize, tileSize);
		sWallTopLeft = new TextureRegion(wallTexture, 0 * tileSize,
				3 * tileSize, tileSize, tileSize);
		sFloorTop = new TextureRegion(floorTexture, 1 * tileSize,
				1 + 3 * tileSize, tileSize, tileSize);
		sWallHorizontal = new TextureRegion(wallTexture, 1 * tileSize,
				3 * tileSize, tileSize, tileSize);
		sFloorTopRight = new TextureRegion(floorTexture, 2 * tileSize,
				1 + 3 * tileSize, tileSize, tileSize);
		sWallTopRight = new TextureRegion(wallTexture, 2 * tileSize,
				3 * tileSize, tileSize, tileSize);
		sFloorLeft = new TextureRegion(floorTexture, 0 * tileSize,
				1 + 4 * tileSize, tileSize, tileSize);
		sWallVertical = new TextureRegion(wallTexture, 0 * tileSize,
				4 * tileSize, tileSize, tileSize);
		sFloorCenter = new TextureRegion(floorTexture, 1 * tileSize,
				1 + 4 * tileSize, tileSize, tileSize);
		sFloorRight = new TextureRegion(floorTexture, 2 * tileSize,
				1 + 4 * tileSize, tileSize, tileSize);
		sFloorBottomLeft = new TextureRegion(floorTexture, 0 * tileSize,
				1 + 5 * tileSize, tileSize, tileSize);
		sWallBottomLeft = new TextureRegion(wallTexture, 0 * tileSize,
				5 * tileSize, tileSize, tileSize);
		sFloorBottom = new TextureRegion(floorTexture, 1 * tileSize,
				1 + 5 * tileSize, tileSize, tileSize);
		sFloorBottomRight = new TextureRegion(floorTexture, 2 * tileSize,
				1 + 5 * tileSize, tileSize, tileSize);
		sWallBottomRight = new TextureRegion(wallTexture, 2 * tileSize,
				5 * tileSize, tileSize, tileSize);
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

		player.getMovementComponent().move(4, 4);

		// TiledMap t = new TiledMap();
		// TiledMapTileLayer layer = (TiledMapTileLayer)t.getLayers().get(0);
	}

	@Override
	public void resize(int width, int height) {
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
				final TextureRegion tile = getTile(map.getMap()[x][y]);
				if (tile != null) {
					batch.draw(tile, x * scale, y * scale);
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			playerOld.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			playerOld.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			playerOld.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			playerOld.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if (playerOld.x < 0) {
			playerOld.x = 0;
		}
		if (playerOld.x > VIRTUAL_WIDTH - tileSize) {
			playerOld.x = VIRTUAL_WIDTH - tileSize;
		}
		if (playerOld.y < 0) {
			playerOld.y = 0;
		}
		if (playerOld.y > VIRTUAL_HEIGHT - tileSize) {
			playerOld.y = VIRTUAL_HEIGHT - tileSize;
		}
		batch.draw(at, playerOld.x, playerOld.y);

		drawActor(batch, player);

		final Vector3 touchPos = new Vector3();
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
		switch (tile.getDungeonSprite()) {
		case FLOOR_BOTTOM:
			return sFloorBottom;
		case FLOOR_BOTTOMLEFT_CORNER:
			return sFloorBottomLeft;
		case FLOOR_BOTTOMRIGHT_CORNER:
			return sFloorBottomRight;
		case FLOOR_CENTER:
			return sFloorCenter;
		case FLOOR_LEFT:
			return sFloorLeft;
		case FLOOR_RIGHT:
			return sFloorRight;
		case FLOOR_TOP:
			return sFloorTop;
		case FLOOR_TOPLEFT_CORNER:
			return sFloorTopLeft;
		case FLOOR_TOPRIGHT_CORNER:
			return sFloorTopRight;
		case WALL_TOPLEFT_CORNER:
			return sWallTopLeft;
		case WALL_HORIZONTAL:
			return sWallHorizontal;
		case WALL_TOPRIGHT_CORNER:
			return sWallTopRight;
		case WALL_VERTICAL:
			return sWallVertical;
		case WALL_BOTTOMLEFT_CORNER:
			return sWallBottomLeft;
		case WALL_BOTTOMRIGHT_CORNER:
			return sWallBottomRight;
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
		floorTexture.dispose();
		wallTexture.dispose();
	}
}
