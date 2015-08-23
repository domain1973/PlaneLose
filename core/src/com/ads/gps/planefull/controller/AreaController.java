package com.ads.gps.planefull.controller;

import com.ads.gps.planefull.Assets;
import com.ads.gps.planefull.actors.GateBgImage;
import com.ads.gps.planefull.actors.PlaneImage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/6.
 */
public class AreaController extends IController {
    private int gateNum;
    private GateBgImage currGateBgImage;//当前关卡背景
    private Piece[][] pieces = new Piece[4][4];
    private List<GateBgImage> gateBgImages = new ArrayList<GateBgImage>();
    private List<PlaneImage> planeImages = new ArrayList<PlaneImage>();

    public AreaController(String name) {
        setName(name);
        buildGateImages();
        initSmallPiece();
    }

    public void buildGateImages() {
        for (TextureRegion tr : Assets.gateBmpList) {
            GateBgImage gateBgImage = new GateBgImage(tr);
            gateBgImages.add(gateBgImage);
        }
    }

    private void initSmallPiece() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i][j] = new Piece(i, j);
            }
        }
    }

    public void setGateNum(int num) {
        gateNum = num;
    }

    @Override
    public void handler() {
        if (currGateBgImage != null) {
            currGateBgImage.remove();
        }
        currGateBgImage = gateBgImages.get(gateNum);
        addActor(currGateBgImage);
    }

    public void clearPlane() {
        planeImages.clear();
        removeAllPlaneId();
    }

    public List<PlaneImage> getPlaneImages() {
        return planeImages;
    }

    public PlaneImage getPlane(Vector3 touchPoint) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece piece = pieces[i][j];
                if (piece.contains(touchPoint.x, touchPoint.y)) {
                    List<Integer> planeIds = piece.getPlaneIds();
                    if (planeIds.size() > 0) {
                        int planeId = planeIds.get(0);
                        for (PlaneImage planeImage : planeImages) {
                            if (planeImage.getId() == planeId) {
                                return planeImage;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void addPlane(PlaneImage planeImage) {
        if (!planeImages.contains(planeImage)) {
            planeImages.add(planeImage);
        }
        handle(planeImage);
    }

    public void handle(PlaneImage planeImage) {
        int planeId = planeImage.getId();
        removePlaneId(planeId);
        int col;
        int row = 0;
        if (planeImage.getX() < 0) {
            col = -1;
        } else {
            col = (int) (planeImage.getX() / Assets.PLANE_SIZE);
        }
        if (planeImage.getY() != Assets.PLANE_SIZE) {
            row = 3 - (int) ((planeImage.getY() - Assets.AREA_Y) / Assets.PLANE_SIZE);
        }
        resetPlaneId(planeImage, planeId, col, row);
    }

    private void resetPlaneId(PlaneImage planeImage, int planeId, int col, int row) {
        switch (planeImage.getOrientation()) {
            case 0:
                if (planeImage.getId() < 4) {
                    resetPlaneId(row, col, planeId);
                    resetPlaneId(row - 1, col, planeId);
                    resetPlaneId(row - 1, col + 1, planeId);
                } else {
                    resetPlaneId(row, col, planeId);
                    resetPlaneId(row, col + 1, planeId);
                }
                break;
            case 1:
                if (planeImage.getId() < 4) {
                    resetPlaneId(row, col, planeId);
                    resetPlaneId(row - 1, col, planeId);
                    resetPlaneId(row, col + 1, planeId);
                } else {
                    resetPlaneId(row, col + 1, planeId);
                    resetPlaneId(row - 1, col + 1, planeId);
                }
                break;
            case 2:
                if (planeImage.getId() < 4) {
                    resetPlaneId(row, col, planeId);
                    resetPlaneId(row, col + 1, planeId);
                    resetPlaneId(row - 1, col + 1, planeId);
                } else {
                    resetPlaneId(row - 1, col, planeId);
                    resetPlaneId(row - 1, col + 1, planeId);
                }
                break;
            case 3:
                if (planeImage.getId() < 4) {
                    resetPlaneId(row, col + 1, planeId);
                    resetPlaneId(row - 1, col, planeId);
                    resetPlaneId(row - 1, col + 1, planeId);
                } else {
                    resetPlaneId(row, col, planeId);
                    resetPlaneId(row - 1, col, planeId);
                }
        }
    }

    private void removeAllPlaneId() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               pieces[i][j].getPlaneIds().clear();
            }
        }
    }

    private void removePlaneId(int planeId) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                List<Integer> planeIds = pieces[i][j].getPlaneIds();
                int t = -1;
                for (int n = 0; n < planeIds.size(); n++) {
                    if (planeIds.get(n) == planeId) {
                        t = n;
                        break;
                    }
                }
                if (t > -1) {
                    planeIds.remove(t);
                }
            }
        }
    }

    private void resetPlaneId(int row, int column, int planeId) {
        if ((row > -1 && row < 4) && (column > -1 && column < 4)) {
            List<Integer> planeIds = pieces[row][column].getPlaneIds();
            if (planeIds.size() == 0) {
                planeIds.add(planeId);
            } else {
                planeIds.add(0, planeId);
            }
        }
    }

    public void removePlane(PlaneImage planeImage) {
        if (planeImages.contains(planeImage)) {
            removePlaneId(planeImage.getId());
            planeImages.remove(planeImage);
        }
    }

    public class Piece {

        private Rectangle bound;

        private List<Integer> planeIds;

        public Piece(int i, int j) {
            float y = (3 - i % 4) * Assets.PLANE_SIZE + Assets.AREA_Y;
            bound = new Rectangle(j % 4 * Assets.PLANE_SIZE, y, Assets.PLANE_SIZE, Assets.PLANE_SIZE);
        }

        public List<Integer> getPlaneIds() {
            if (planeIds == null) {
                planeIds = new ArrayList<Integer>();
            }
            return planeIds;
        }

        public boolean contains(float x, float y) {
            return bound.contains(x, y);
        }
    }
}
