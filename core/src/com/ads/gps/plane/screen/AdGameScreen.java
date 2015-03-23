package com.ads.gps.plane.screen;

import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Assets;
import com.ads.gps.plane.Series;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


/**
 * Created by Administrator on 2014/8/10.
 */
public class AdGameScreen extends BaseScreen {
    private float margin = Assets.WIDTH / 15;
    private float seriesW = margin * 2.5f;
    private float seriesH = seriesW;
    private BaseScreen baseScreen;

    public AdGameScreen(AppGame game, BaseScreen bs) {
        super(game);
        baseScreen = bs;
    }

    @Override
    public void show() {
        if (!isShow()) {
            super.show();
            createBtns();
            createAdLabel();
            for (int i = 0; i < Assets.seriesList.size(); i++) {
                final Series series = Assets.seriesList.get(i);
                Image image = series.getImage();
                ImageButton seriesBtn = new ImageButton(image.getDrawable(), image.getDrawable(), image.getDrawable());
                seriesBtn.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y,
                                             int pointer, int button) {
                        Assets.playSound(Assets.btnSound);
                        getAppGame().getPEvent().install(series);
                        return true;
                    }
                });
                float x = i % 4 * seriesW + (i % 4 + 1) * margin;
                float y = getY_bar() - (i / 4 + 1) * seriesH - (i / 4 + 2) * margin;
                seriesBtn.setBounds(x, y, seriesW, seriesH);
                addActor(seriesBtn);
            }
            setListens();
            setShow(true);
        } else {
            Gdx.input.setInputProcessor(getStage());
        }
        if (Assets.seriesList.size() == 0) {
            getAppGame().getPEvent().netSlowInfo();
        }
    }

    private void createAdLabel() {
        String str = "爱迪精品";
        BitmapFont font = getOtherFont();
        Label l = new Label(str, new Label.LabelStyle(font, Color.WHITE));
        float w = font.getBounds(str).width;
        l.setBounds((Assets.WIDTH - w) / 2, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        addActor(l);
    }

    private void setListens() {
        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, returnBtn.getWidth(), returnBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    getAppGame().setScreen(baseScreen);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            if (!isBackFlag()) {
                baseScreen.setBackFlag(true);
                getAppGame().setScreen(baseScreen);
                return;
            }
        } else {
            setBackFlag(false);
        }
        super.render(delta);
    }
}
