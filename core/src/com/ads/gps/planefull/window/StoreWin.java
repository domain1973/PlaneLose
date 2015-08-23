package com.ads.gps.planefull.window;

import com.ads.gps.planefull.Assets;
import com.ads.gps.planefull.screen.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/20.
 */
public class StoreWin extends BaseWin {
    private GameScreen gameScreen;

    public StoreWin(GameScreen gs) {
        super("STORE", new WindowStyle(gs.getGameFont(), Color.WHITE, new TextureRegionDrawable(
                Assets.winBg)));
        gs.getStage().addActor(layerBg);
        gameScreen = gs;
        create();
    }

    public void create() {
        float off = 10 * Assets.HEIGHT/854;
        float off1 = 60 * Assets.HEIGHT/854;
        setBounds(0, (Assets.HEIGHT - Assets.WIDTH) / 2, Assets.WIDTH, Assets.WIDTH+off1);
        int c = 0;
        float gateBtnSize = Assets.WIDTH / 2;
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
            imageButton.setBounds(i % 2 * gateBtnSize, (3-i) / 2 * gateBtnSize + off,gateBtnSize,gateBtnSize);
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
                        gameScreen.getAppGame().getPEvent().buy(t);
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            addActor(imageButton);
        }
    }
}