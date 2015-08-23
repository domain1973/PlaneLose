package com.ads.gps.planefull.listener;

import com.ads.gps.planefull.Answer;
import com.ads.gps.planefull.Assets;
import com.ads.gps.planefull.actors.PlaneImage;
import com.ads.gps.planefull.controller.AreaController;
import com.ads.gps.planefull.controller.IController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.List;

/**
 * Created by Administrator on 2014/7/4.
 */
public class PlaneDetector extends GestureDetector {
    private Stage stage;

    /**
     * Creates a new GestureDetector with default values: halfTapSquareSize=20, tapCountInterval=0.4f, longPressDuration=1.1f,
     * maxFlingDelay=0.15f.
     *
     * @param listener
     */
    public PlaneDetector(Stage stage, GestureListener listener) {
        super(listener);
        this.stage = stage;
    }

    public boolean isPass(int gateNum) {
        AreaController areaCtrl = (AreaController) stage.getRoot().findActor(IController.AREA_CTRL);
        List<PlaneImage> planeImages = areaCtrl.getPlaneImages();
        if (planeImages.size() != 6) {
            return false;
        }
        String[] C = Answer.CHALLENGES.get(gateNum);
        for (int i = 0; i < 6; i++) {
            PlaneImage planeImage = planeImages.get(i);
            String[] answers = C[planeImage.getId()].split(",");
            if (Integer.parseInt(answers[0].trim()) == planeImage.getOrientation()) {
                int x = (int) (planeImage.getX() / Assets.PLANE_SIZE);
                int y = (int) ((planeImage.getY() - Assets.AREA_Y)/ Assets.PLANE_SIZE);
                if (x == Integer.parseInt(answers[1].trim()) && y == Integer.parseInt(answers[2].trim())) {
                    continue;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
