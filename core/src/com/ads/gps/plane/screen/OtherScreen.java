package com.ads.gps.plane.screen;

import com.ads.gps.plane.Answer;
import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/27.
 */
public class OtherScreen extends BaseScreen {
    private Image star;
    private Label starLabel;

    public OtherScreen(AppGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        createBtns();
        removeLayerBg();
    }

    protected void createBtns() {
        star = new Image(new TextureRegionDrawable(Assets.star));
        star.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);

        BitmapFont font = getOtherFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE); // 创建一个Label样式，使用默认白色字体
        String str = getStarNumInfo();
        BitmapFont.TextBounds bounds = font.getBounds(str);
        starLabel = new Label(str, labelStyle);
        starLabel.setPosition(Assets.WIDTH - bounds.width - Assets.TOPBAR_HEIGHT, getY_bar());

        addActor(starLabel);
        addActor(star);
    }

    protected String getStarNumInfo() {
        return "Total:" + getStarNum();
    }

    protected int getStarNum() {
        int starNum = 0;
        for (int num : Answer.gateStars) {
            starNum = starNum + num;
        }
        return starNum;
    }

    protected void setStarNum() {
        starLabel.setText(getStarNumInfo());
    }
}
