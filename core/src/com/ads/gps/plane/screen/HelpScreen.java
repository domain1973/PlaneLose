package com.ads.gps.plane.screen;

import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2014/9/1.
 */
public class HelpScreen extends OtherScreen {
    private Image readmeImage;
    private BaseScreen baseScreen;

    private HelpScreen(AppGame game) {
        super(game);
        readmeImage = new Image(Assets.readme);
        float imageW = Assets.WIDTH * 20 / 21;
        float height = imageW * 1.6f;
        float maxH = Assets.HEIGHT - 2 * Assets.TOPBAR_HEIGHT;
        readmeImage.setBounds((Assets.WIDTH - imageW) / 2, Assets.TOPBAR_HEIGHT, imageW, Math.min(maxH, height));
    }

    public HelpScreen(AppGame game, BaseScreen ms) {
        this(game);
        baseScreen = ms;
    }

    @Override
    public void show() {
        if (!isShow()) {
            super.show();
            addActor(readmeImage);
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
            setShow(true);
        } else {
            Gdx.input.setInputProcessor(getStage());
            setStarNum();
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            baseScreen.setBackFlag(true);
            if (baseScreen instanceof MainScreen) {
                ((MainScreen)baseScreen).setTouchBack(false);
            }
            getAppGame().setScreen(baseScreen);
        } else {
            setBackFlag(false);
        }
        super.render(delta);
    }

    public void setBaseScreen(BaseScreen baseScreen) {
        this.baseScreen = baseScreen;
    }
}
