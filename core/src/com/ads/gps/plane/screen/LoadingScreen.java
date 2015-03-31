package com.ads.gps.plane.screen;

/**
 * Created by Administrator on 2015/3/17.
 */

import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class LoadingScreen extends ScreenAdapter {
    private AppGame game;
    private Stage stage;
    private MainScreen mainScreen;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Assets.isFinishLoading()) {
            Assets.initData();
            mainScreen = new MainScreen(game);
            game.setScreen(mainScreen);
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        stage = new Stage();
        Image image = new Image(new Texture(Gdx.files.internal("loading/default.png")));
        float w = Gdx.graphics.getWidth()/2;
        float h = w * 230 / 455;
        image.setWidth(w);
        image.setHeight(h);
        image.setPosition((Gdx.graphics.getWidth() - w) / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(image);
        Assets.load();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public LoadingScreen(AppGame game) {
        this.game = game;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }
}
