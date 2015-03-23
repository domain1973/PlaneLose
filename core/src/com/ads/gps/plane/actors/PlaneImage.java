package com.ads.gps.plane.actors;

import com.ads.gps.plane.Assets;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2014/7/4.
 */
public class PlaneImage extends Image {
    private int orientation;
    private int id;
    private float x = 0;
    private float y = 0;
    private float planeRawSize;

    public PlaneImage(int planeId) {
        super(Assets.planes[planeId]);
        id = planeId;
        setOrigin(Assets.PLANE_SIZE, Assets.PLANE_SIZE);
        return2BeginArea();
    }

    public void return2BeginArea() {
        planeRawSize = Assets.PLANE_SIZE;
        float top = planeRawSize;
        float left = 0;
        switch (id) {
            case 0:
                x = left;
                y = top;
                break;
            case 1:
                x = left + planeRawSize;
                y = top;
                break;
            case 2:
                x = left + 2 * planeRawSize;
                y = top;
                break;
            case 3:
                x = left + 3 * planeRawSize;
                y = top;
                break;
            case 4:
                x = left;
                y = 0;
                break;
            case 5:
                x = left + planeRawSize;
                y = 0;
                break;
        }
        return2Raw();
    }

    public void return2Raw() {
        setBounds(x, y, planeRawSize, planeRawSize);
        setRotation(0);
        orientation = 0;
    }

    public void changeOrientation() {
        if (orientation == 3) {
            orientation = 0;
        } else {
            orientation++;
        }
        setRotation(orientation * 90);
    }

    public int getId() {
        return id;
    }

    public int getOrientation() {
        return orientation;
    }
}
