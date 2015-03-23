package com.ads.gps.plane.controller;

import com.ads.gps.plane.Assets;
import com.ads.gps.plane.actors.PlaneImage;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by Administrator on 2014/7/4.
 */
public class PlaneController extends IController {
    private Rectangle bounds;

    public PlaneController(String name) {
        setName(name);
        for (int i = 0; i < Assets.PLANE_NUM; i++) {
            PlaneImage net = new PlaneImage(i);
            addActor(net);
            if (i == 4) {
                bounds = new Rectangle(0, 0, Assets.WIDTH, Assets.WIDTH / 2);
            }
        }
    }

    @Override
    public void handler() {
        SnapshotArray<Actor> actors = getChildren();
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            PlaneImage piece = (PlaneImage) actor;
            piece.return2BeginArea();
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
