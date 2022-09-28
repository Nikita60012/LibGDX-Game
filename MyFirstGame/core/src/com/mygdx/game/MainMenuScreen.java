package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MainMenuScreen implements Screen {

    final  MyFrstGame game;
    OrthographicCamera camera;
    private Texture background;
    private int lives;
    private int finish;
    private  int timer;
    private int speed;

    public MainMenuScreen(final MyFrstGame game){
        this.game = game;

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
        game.font.setColor( Color.GOLDENROD);
        game.font.draw(game.batch,  "Eat all apples!", 345, 150);
        game.font.draw(game.batch, "Choose difficult by pressing: E, M or H to begin!", 245, 100);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.T)){
            finish = 1;
            lives = 1;
            timer = 1000000000;
            speed = 200;
            game.setScreen(new MyFrstGameScreen(game, lives, finish, timer, speed));
            dispose();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            finish = 15;
            lives = 5;
            timer = 1000000000;
            speed = 200;
            game.setScreen(new MyFrstGameScreen(game, lives, finish, timer, speed));
            dispose();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            finish = 30;
            lives = 3;
            timer = 500000000;
            speed = 400;
            game.setScreen(new MyFrstGameScreen(game, lives, finish, timer, speed));
            dispose();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.H)){
            finish = 50;
            lives = 1;
            timer = 250000000;
            speed = 400;
            game.setScreen(new MyFrstGameScreen(game, lives, finish, timer, speed));
            dispose();
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
