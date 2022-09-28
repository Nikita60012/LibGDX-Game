package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class DeathScreen implements Screen {

    final  MyFrstGame game;
    OrthographicCamera camera;
    private int lives;
    private int finish;
    private int timer;
    private int speed;

    public DeathScreen(MyFrstGame game, int lives, int finish, int timer, int speed) {
        this.game = game;
        this.lives = lives;
        this.finish = finish;
        this.timer = timer;
        this.speed = speed;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch,  "Game Over", 350, 275);
        game.font.draw(game.batch, "Touch the screen to restart", 305, 100);
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
