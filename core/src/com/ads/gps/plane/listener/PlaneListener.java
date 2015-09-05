package com.ads.gps.plane.listener;

import com.ads.gps.plane.Assets;
import com.ads.gps.plane.actors.PlaneImage;
import com.ads.gps.plane.controller.AreaController;
import com.ads.gps.plane.controller.IController;
import com.ads.gps.plane.controller.PlaneController;
import com.ads.gps.plane.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by Administrator on 2014/7/4.
 */
public class PlaneListener extends GestureDetector.GestureAdapter {
    private static final float Y1 = Assets.HEIGHT - Assets.PLANE_SIZE - Assets.TOPBAR_HEIGHT;
    private static final float THREE_PLANE_SIZE = Assets.PLANE_SIZE * 3;
    private static final float Y2 = Assets.HEIGHT - THREE_PLANE_SIZE - Assets.PLANE_BIG_SIZE - Assets.TOPBAR_HEIGHT;
    private static final float THREE_PLANE_SIZE_ADD_TOP_H = Assets.TOPBAR_HEIGHT + THREE_PLANE_SIZE;
    private Stage stage;
    private GameScreen gameScreen;
    private Vector3 touchPoint;
    private AreaController areaCtrl;
    private PlaneController planeCtrl;
    private SnapshotArray<Actor> planeImages;
    private Vector2 rawVector;
    private Vector2 lasterVector2;
    private PlaneImage downPlaneImage;

    public PlaneListener(Stage stage, GameScreen gs) {
        this.stage = stage;
        gameScreen = gs;
        touchPoint = new Vector3();
        rawVector = new Vector2();
        areaCtrl = (AreaController) stage.getRoot().findActor(IController.AREA_CTRL);
        planeCtrl = (PlaneController) stage.getRoot().findActor(IController.PIECE_CTRL);
        planeImages = planeCtrl.getChildren();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        downPlaneImage = null;
        if (!gameScreen.isSuspend()) {
            stage.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            PlaneImage planeImage = areaCtrl.getPlane(touchPoint);
            if (planeImage != null) {
                downPlaneImage = planeImage;
                rawVector.set(planeImage.getX(), planeImage.getY());
            }
            if (downPlaneImage == null) {
                for (int i = 0; i < 6; i++) {
                    planeImage = (PlaneImage) planeImages.get(i);
                    Rectangle bounds = new Rectangle(planeImage.getX(), planeImage.getY(), planeImage.getWidth(), planeImage.getHeight());
                    if (bounds.contains(touchPoint.x, touchPoint.y)) {
                        downPlaneImage = planeImage;
                        rawVector.set(planeImage.getX(), planeImage.getY());
                        break;
                    }
                }
            }
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (!gameScreen.isSuspend()) {
            PlaneImage planeImage = areaCtrl.getPlane(touchPoint);
            if (planeImage != null) {
                if (planeImage.getId() == 4 || planeImage.getId() == 5) {
                    if (Y1 - 4 * Assets.PLANE_SIZE == planeImage.getY() && planeImage.getOrientation() == 2) {
                        planeImage.changeOrientation();//快速点击改变块方位
                        planeImage.setBounds(planeImage.getX(), Y1 - 3*Assets.PLANE_SIZE, Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
                        areaCtrl.handle(planeImage);
                        return super.tap(x,y,count,button);
                    }
                    if ((Y1 == planeImage.getY() && planeImage.getOrientation() == 0)) {
                        planeImage.changeOrientation();//快速点击改变块方位
                        planeImage.setBounds(planeImage.getX(), Y1 - Assets.PLANE_SIZE, Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
                        areaCtrl.handle(planeImage);
                        return super.tap(x,y,count,button);
                    }
                    if (-Assets.PLANE_SIZE == planeImage.getX() && planeImage.getOrientation() == 1) {
                        planeImage.changeOrientation();//快速点击改变块方位
                        planeImage.setBounds(0, planeImage.getY(), Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
                        areaCtrl.handle(planeImage);
                        return super.tap(x,y,count,button);
                    }
                    if (3*Assets.PLANE_SIZE == planeImage.getX() && planeImage.getOrientation() == 3) {
                        planeImage.changeOrientation();//快速点击改变块方位
                        planeImage.setBounds(2*Assets.PLANE_SIZE, planeImage.getY(), Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
                        areaCtrl.handle(planeImage);
                        return super.tap(x,y,count,button);
                    }
                }
                planeImage.changeOrientation();//快速点击改变块方位
                areaCtrl.handle(planeImage);
                Assets.soundBtn();
            }
        }
        return super.tap(x,y,count,button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (downPlaneImage != null) {
            float x1 = downPlaneImage.getX() + deltaX;
            float y1 = downPlaneImage.getY() - deltaY;
            downPlaneImage.setPosition(x1, y1);
            lasterVector2 = getFlashVector(downPlaneImage);
            if (lasterVector2 != null) {
                int flag = 4;
                if (downPlaneImage.getId() == 4 || downPlaneImage.getId() == 5) {
                    flag = downPlaneImage.getOrientation();
                }
                gameScreen.runFlash(lasterVector2, flag);
            } else {
                gameScreen.stopFlash();
            }
        }
        return super.pan(x, y, deltaX, deltaY);
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        press();
        return super.panStop(x, y, pointer, button);
    }

    private void press() {
        if (downPlaneImage != null) {
            stage.getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0)); // 坐标转化
            Rectangle planeBound = planeCtrl.getBounds();
            if (planeBound.contains(touchPoint.x, touchPoint.y)) {
                areaCtrl.removePlane(downPlaneImage);
                downPlaneImage.return2Raw();
            } else {
                plane2pool(downPlaneImage);
            }
            Assets.soundBtn();
        }
        gameScreen.stopFlash();
    }

    private Vector2 getFlashVector(PlaneImage planeImage) {
        if (planeImage.getId() == 4 || planeImage.getId() == 5) {
            int y = getY(planeImage.getY(), planeImage.getWidth());
            float y1 = Assets.HEIGHT - y - Assets.PLANE_BIG_SIZE;
            if (planeImage.getX() < 0 && (planeImage.getOrientation() == 0 || planeImage.getOrientation() == 2)) {
                return null;
            }
            if (planeImage.getX() > 3 * Assets.PLANE_SIZE && (planeImage.getOrientation() == 0 || planeImage.getOrientation() == 2)) {
                return null;
            }
            if (planeImage.getX() < 0) {
                if (y == -1 || y >= (int) THREE_PLANE_SIZE_ADD_TOP_H) {
                    return null;
                }
                if (planeImage.getOrientation() == 1) {
                    return new Vector2(- Assets.PLANE_SIZE, y1);
                }
            }
            int x = getX(planeImage.getX());
            if (x == THREE_PLANE_SIZE && planeImage.getOrientation() == 3) {
                return new Vector2(x, y1);
            }
            if (y < 0) {
                if (planeImage.getOrientation() == 0) {
                    return new Vector2(x, Y1);
                }
                return null;
            }
            if (y >= (int) THREE_PLANE_SIZE_ADD_TOP_H) {
                if (planeImage.getOrientation() == 2) {
                    return new Vector2(x, Y2);
                }
                return null;
            }
            return new Vector2(x, y1);
        } else {
            int x = getX(planeImage.getX());
            if (planeImage.getX() < 0 || x == THREE_PLANE_SIZE) {
                return null;
            }
            int y = getY(planeImage.getY(), planeImage.getWidth());
            if (y < 0) {
                return null;
            }
            if (y >= (int) THREE_PLANE_SIZE_ADD_TOP_H) {
                return null;
            }
            return new Vector2(x, Assets.HEIGHT - y - Assets.PLANE_BIG_SIZE);
        }
    }

    private void plane2pool(PlaneImage planeImage) {
        if (lasterVector2 != null) {
            planeImage.setBounds(lasterVector2.x, lasterVector2.y, Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
            areaCtrl.addPlane(planeImage);
        } else if (planeImage.getWidth() == Assets.PLANE_SIZE) {
            areaCtrl.removePlane(planeImage);
            planeImage.return2Raw();
        } else {
            planeImage.setX(rawVector.x);
            planeImage.setY(rawVector.y);
        }
    }

    private int getX(float x) {
        float size = Assets.PLANE_SIZE;
        int x1 = (int) (x / size);
        return (int) (x1 * size);
    }

    private int getY(float y, float fs) {
        float size = Assets.PLANE_SIZE;
        float t = Assets.HEIGHT - y - fs - Assets.TOPBAR_HEIGHT;
        if (t < 0) {
            return -1;
        }
        int y1 =  (int)(t / size);
        return (int) (y1 * size + Assets.TOPBAR_HEIGHT);
    }
}
