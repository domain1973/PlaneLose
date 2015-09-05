package com.ads.gps.plane.actors;

import com.ads.gps.plane.Assets;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Administrator on 2014/7/5.
 */
public class GateImageBtn extends ImageButton {

    public GateImageBtn(Drawable imageUp, int id) {
        super(imageUp);
        float gateBtnSize = Assets.WIDTH / 5;
        float gateBtnSpace = Assets.WIDTH / 25;
        float y_off = Assets.HEIGHT / 3;
        float hspace = Assets.HEIGHT / 6;
        setPosition((id % 4 + 1) * gateBtnSpace + id % 4 * gateBtnSize, Assets.HEIGHT - y_off - id / 4 * hspace);
    }
}
