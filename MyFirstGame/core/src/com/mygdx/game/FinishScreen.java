package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class FinishScreen implements Screen {

    final MyFrstGame game;
    OrthographicCamera camera;
    private Texture background;
    private int lives;
    private int finish;
    private int timer;
    private int speed;

    public FinishScreen(MyFrstGame game, int lives, int finish, int timer, int speed) {
        this.game = game;
        this.lives = lives;
        this.finish = finish;
        this.timer = timer;
        this.speed = speed;

        background = new Texture(Gdx.files.internal("menu.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        game.batch.begin();
        game.batch.draw(background, 0, 0, 800, 700);
        game.batch.end();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch,  "You Win!", 350, 150);
        game.font.draw(game.batch, "You ate all apples!", 325, 100);
        game.font.draw(game.batch, "Touch the screen to restart", 302, 50);
        //game.font.draw(game.batch, "Touch the screen to exit", 315, 100);
        game.batch.end();


        if(Gdx.input.isTouched()){
            game.setScreen(new MyFrstGameScreen(game, lives, finish, timer, speed));
            dispose();
            //Gdx.app.exit();
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

    }
}
