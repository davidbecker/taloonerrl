package de.brainstormsoftworks.taloonerrl.core;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class TaloonerRl implements ApplicationListener {
	Texture texture;
	Texture font;
	SpriteBatch batch;
	float elapsed;
	TextureRegion sTopLeft;
	TextureRegion sTop;
	TextureRegion sTopRight;
	TextureRegion at;
	int tileSize = 16;
	float scale = 16f;
	float mouseX;
	float mouseY;
	private OrthographicCamera camera;
	private Rectangle player;

	@Override
	public void create() {
		texture = new Texture(Gdx.files.internal("Floor.png"));
		sTopLeft = new TextureRegion(texture, 0 * tileSize, 3 * tileSize,
				tileSize, tileSize);
		sTop = new TextureRegion(texture, 1 * tileSize, 3 * tileSize, tileSize,
				tileSize);
		sTopRight = new TextureRegion(texture, 2 * tileSize, 3 * tileSize,
				tileSize, tileSize);
		font = new Texture(Gdx.files.internal("dejavu16x16_gs_tc.png"));
		at = new TextureRegion(font, 0 * tileSize, 1 * tileSize, tileSize,
				tileSize);
		batch = new SpriteBatch();
		mouseX = Gdx.graphics.getWidth() / 2;
		mouseY = Gdx.graphics.getHeight() / 2;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30 * tileSize, 20 * tileSize);
		player = new Rectangle();

		player.x = 30 * tileSize / 2 - tileSize / 2;
		player.y = 20 * tileSize / 2 - tileSize / 2;
		player.width = tileSize;
		player.height = tileSize;
		// TiledMap t = new TiledMap();
		// TiledMapTileLayer layer = (TiledMapTileLayer)t.getLayers().get(0);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.graphics.setTitle("current fps: "
				+ Gdx.graphics.getFramesPerSecond());
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.setBlendFunction(GL30.GL_SRC_ALPHA_SATURATE,
				GL30.GL_SRC_ALPHA_SATURATE);
		batch.draw(texture, 100 + 100 * (float) Math.cos(elapsed),
				100 + 25 * (float) Math.sin(elapsed));
		for (int i = 0; i < 10; i++) {
			batch.draw(sTopLeft, i * scale, i * scale);
			batch.draw(sTop, i * scale + scale, i * scale);
			batch.draw(sTopRight, i * scale + 2 * scale, i * scale);
		}
		batch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_SRC_ALPHA);

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			player.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			player.x += 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			player.y -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.UP))
			player.y += 200 * Gdx.graphics.getDeltaTime();
		if (player.x < 0)
			player.x = 0;
		if (player.x > 30 * tileSize - tileSize)
			player.x = 30 * tileSize - tileSize;
		if (player.y < 0)
			player.y = 0;
		if (player.y > 20 * tileSize - tileSize)
			player.y = 20 * tileSize - tileSize;
		batch.draw(at, player.x, player.y);

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		mouseX = touchPos.x - tileSize / 2;
		mouseY = touchPos.y - tileSize / 2;

		batch.draw(at, mouseX, mouseY);
		batch.end();
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
