package com.ads.gps.plane.screen;

import com.ads.gps.plane.Assets;
import com.ads.gps.plane.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/6/22.
 */
public class StoreScreen extends BaseScreen {
    private GameScreen gameScreen;

    public StoreScreen(GameScreen gs) {
        super(gs.getAppGame());
        gameScreen = gs;
    }

    @Override
    public void show() {
        super.show();
        createAdLabel();
        buildStore();
        Gdx.input.setInputProcessor(getStage());
    }
    private void createAdLabel() {
        String str = "STORE";
        BitmapFont font = getGameFont();
        Label l = new Label(str, new Label.LabelStyle(font, Color.WHITE));
        float w = font.getBounds(str).width;
        l.setPosition((Assets.WIDTH - w) / 2, Assets.HEIGHT / 2 + Assets.WIDTH / 2);
        addActor(l);
    }

    public void buildStore() {
        int c = 0;
        float gateBtnSize = Assets.WIDTH / 2;
        float y_off = Assets.HEIGHT / 2;
        for (int i = 0; i < 4; i++) {
            TextureRegion gateTRegion = null;
            TextureRegion gateTRegionDown = null;
            switch (i) {
                case 0:
                    gateTRegion = Assets.help2;
                    gateTRegionDown = Assets.help2Down;
                    c = 2;
                    break;
                case 1:
                    gateTRegion = Assets.help5;
                    gateTRegionDown = Assets.help5Down;
                    c = 5;
                    break;
                case 2:
                    gateTRegion = Assets.help12;
                    gateTRegionDown = Assets.help12Down;
                    c = 12;
                    break;
                case 3:
                    gateTRegion = Assets.helpN;
                    gateTRegionDown = Assets.helpNDown;
                    c = -1;
                    break;
            }
            final ImageButton imageButton = new ImageButton(new TextureRegionDrawable(gateTRegion), new TextureRegionDrawable(gateTRegionDown));
            final int t = c;
            imageButton.setBounds(i % 2 * gateBtnSize, Assets.HEIGHT - y_off - i / 2 * gateBtnSize,gateBtnSize,gateBtnSize);
            imageButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, imageButton.getWidth(), imageButton.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        if (Settings.helpNum == -1) {
                        } else if (t == -1) {
                            Settings.helpNum = -1;
                        } else {
                            Settings.helpNum = Settings.helpNum + t;
                        }
                        Settings.unlockEnabled = true;
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            addActor(imageButton);
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gameScreen.setBackFlag(true);
            getAppGame().setScreen(gameScreen);
            return;
        }
        super.render(delta);
    }
}
