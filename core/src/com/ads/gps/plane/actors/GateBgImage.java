package com.ads.gps.plane.actors;

import com.ads.gps.plane.Assets;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2014/7/6.
 */
public class GateBgImage extends Image {

    public GateBgImage(TextureRegion tr) {
        super(tr);
        setBounds(0, Assets.AREA_Y, Assets.WIDTH, Assets.WIDTH);
    }
}
