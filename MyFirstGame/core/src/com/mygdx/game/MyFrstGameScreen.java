package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import java.util.Timer;

public class MyFrstGameScreen implements Screen {


	final MyFrstGame game;
	private Texture dropImage;
	private Texture bucketImage;
	private Texture background;
	private Sound dropSound;
	private Music backgroundMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle snake;
	private Array<Rectangle> appledrops;
	private long lastDropTime;
	private int score;
	private int lives;
	private int finish;
	private int life;
	private int timer;
	private int speed;


	public MyFrstGameScreen(final MyFrstGame game) {
		this.game = game;

		dropImage = new Texture(Gdx.files.internal("apple.png"));
		bucketImage = new Texture(Gdx.files.internal("snake.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("move.mp3"));

		background = new Texture(Gdx.files.internal("fon.jpg"));
		batch = new SpriteBatch();

		backgroundMusic.setLooping(true);
		backgroundMusic.play();


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		snake = new Rectangle();
		snake.x = 800 / 2 - 64 / 2;
		snake.y = 20;
		snake.width = 64;
		snake.height = 64;

		appledrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	public MyFrstGameScreen(final MyFrstGame game, int lives, int finish, int timer, int speed) {
		this.game = game;
		this.lives = lives;
		this.finish = finish;
		this.timer = timer;
		this.speed = speed;
		life = lives;

		dropImage = new Texture(Gdx.files.internal("apple.png"));
		bucketImage = new Texture(Gdx.files.internal("snake.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("move.mp3"));

		background = new Texture(Gdx.files.internal("fon.jpg"));
		batch = new SpriteBatch();

		backgroundMusic.setLooping(true);
		backgroundMusic.play();


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		snake = new Rectangle();
		snake.x = 800 / 2 - 64 / 2;
		snake.y = 20;
		snake.width = 64;
		snake.height = 64;

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
	public void render(float delta) {


		game.batch.begin();
		game.batch.draw(background, 0, 0, 800, 700);
		game.batch.end();

		camera.update();


		batch.setProjectionMatrix(camera.combined);



		game.batch.begin();
		game.font.setColor( Color.GOLDENROD);
		game.font.draw(game.batch, "Apples was ate: " + score, 0, 480);
		game.font.draw(game.batch, "Lives: " + life, 0, 460);
		game.batch.draw(bucketImage, snake.x, snake.y);
		for(Rectangle appledrop: appledrops) {
			game.batch.draw(dropImage, appledrop.x, appledrop.y);
		}
		game.batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			snake.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) snake.x -= 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snake.x += 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) snake.x -= 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) snake.x += 300 * Gdx.graphics.getDeltaTime();

		if(snake.x < 0) snake.x = 0;
		if(snake.x > 800 - 64) snake.x = 800 - 64;


		if(TimeUtils.nanoTime() - lastDropTime > timer) spawnRaindrop();


		for (Iterator<Rectangle> iter = appledrops.iterator(); iter.hasNext(); ) {
			Rectangle appledrop = iter.next();
			appledrop.y -= speed * Gdx.graphics.getDeltaTime();
			if(appledrop.y + 64 < 0){
				iter.remove();
				life--;
				if(life == 0){
					game.setScreen(new DeathScreen(game, lives, finish, timer, speed));
					dispose();
				}
			}
			if(appledrop.overlaps(snake)) {
				score++;
				dropSound.play();
				iter.remove();
				if(score == finish){
					game.setScreen(new FinishScreen(game, lives, finish, timer, speed));
					dispose();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		backgroundMusic.dispose();
	}
	public void show(){
		backgroundMusic.play();
	}
}