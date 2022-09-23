package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyFrstGame extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
	private Texture background;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle snake;
	private Array<Rectangle> appledrops;
	private long lastDropTime;
	private int score;

	@Override
	public void create() {
		dropImage = new Texture(Gdx.files.internal("apple.png"));
		bucketImage = new Texture(Gdx.files.internal("snake.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("move.mp3"));

		background = new Texture(Gdx.files.internal("fon.jpg"));
		batch = new SpriteBatch();

		rainMusic.setLooping(true);
		rainMusic.play();


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the bucket
		snake = new Rectangle();
		snake.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		snake.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		snake.width = 64;
		snake.height = 64;

		// create the raindrops array and spawn the first raindrop
		appledrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 700;
		raindrop.width = 64;
		raindrop.height = 64;
		appledrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {

		batch.begin();
		batch.draw(background, 0, 0, 800, 700);
		batch.end();

		camera.update();


		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.draw()
		batch.draw(bucketImage, snake.x, snake.y);
		for(Rectangle appledrop: appledrops) {
			batch.draw(dropImage, appledrop.x, appledrop.y);
		}
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			snake.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) snake.x -= 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snake.x += 300 * Gdx.graphics.getDeltaTime();

		if(snake.x < 0) snake.x = 0;
		if(snake.x > 800 - 64) snake.x = 800 - 64;


		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();


		for (Iterator<Rectangle> iter = appledrops.iterator(); iter.hasNext(); ) {
			Rectangle appledrop = iter.next();
			appledrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(appledrop.y + 64 < 0) iter.remove();
			if(appledrop.overlaps(snake)) {
				dropSound.play();
				iter.remove();
			}
		}
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}