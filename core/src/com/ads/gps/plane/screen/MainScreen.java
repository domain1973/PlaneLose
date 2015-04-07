package com.ads.gps.plane.screen;

import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Assets;
import com.ads.gps.plane.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Created by Ads on 2014/6/22.
 */
public class MainScreen extends BaseScreen {
    private AppGame appGame;
    private LevelScreen levelScreen;
    private boolean touchBack = true;
    private Image noMusic;
    private Image noSound;

    public MainScreen(AppGame game) {
        super(game);
        appGame = game;
        levelScreen = new LevelScreen(game);
    }

    @Override
    public void show() {
        if (!isShow()) {
            super.show();
            Image startBg = new Image(Assets.startBg);
            startBg.setBounds(0, 0, Assets.WIDTH, Assets.HEIGHT);
            float playSize = Assets.WIDTH / 3;
            float spaceSize = Assets.WIDTH / 12;
            float btnPlayX = (Assets.WIDTH - playSize) / 2;
            float otherX = spaceSize;
            float otherSize = spaceSize * 2;
            float btnPlayY = Assets.HEIGHT / 3;
            final Image playBtn = new Image(new TextureRegionDrawable(Assets.playBtn));
            playBtn.addAction(Actions.repeat(2000, Actions.rotateBy(360, 3f)));
            playBtn.setBounds(btnPlayX, btnPlayY, playSize, playSize);
            playBtn.setOrigin(playBtn.getWidth() / 2, playBtn.getHeight() / 2);
            final ImageButton aboutBtn = new ImageButton(new TextureRegionDrawable(Assets.about), new TextureRegionDrawable(Assets.about));
            float y = btnPlayY - 1.5f * otherSize;
            aboutBtn.setBounds(otherX * 2, y, otherSize, otherSize);

            final ImageButton music = new ImageButton(new TextureRegionDrawable(Assets.music), new TextureRegionDrawable(Assets.music));
            music.setBounds(5 * otherX, y, otherSize, otherSize);
            noMusic = new Image(Assets.forbid);
            noMusic.setBounds(music.getX(), music.getY(), otherSize, otherSize);
            final ImageButton sound = new ImageButton(new TextureRegionDrawable(Assets.sound), new TextureRegionDrawable(Assets.sound));
            sound.setBounds(8 * otherX, y, otherSize, otherSize);
            noSound = new Image(Assets.forbid);
            noSound.setBounds(sound.getX(), sound.getY(), otherSize, otherSize);
            aboutBtn.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, music.getWidth(), music.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        appGame.getPEvent().about();
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            music.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, music.getWidth(), music.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        addActor(noMusic);
                        Settings.musicEnabled = false;
                        Assets.musicbg.stop();
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            noMusic.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, noMusic.getWidth(), noMusic.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        noMusic.remove();
                        Settings.musicEnabled = true;
                        Assets.musicbg.play();
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            sound.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, sound.getWidth(), sound.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        addActor(noSound);
                        Settings.soundEnabled = false;
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            noSound.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, noSound.getWidth(), noSound.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        noSound.remove();
                        Settings.soundEnabled = true;
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
            playBtn.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Rectangle bound = new Rectangle(0, 0, playBtn.getWidth(), playBtn.getHeight());
                    if (bound.contains(x, y)) {
                        Assets.playSound(Assets.btnSound);
                        levelScreen.setLevel(Settings.unlockGateNum / 12);
                        appGame.setScreen(levelScreen);
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });

            addActor(startBg);
            addActor(playBtn);
            addActor(aboutBtn);
            addActor(music);
            addActor(sound);
            removeLayerBg();
            setShow(true);
        } else {
            Gdx.input.setInputProcessor(getStage());
        }
        if (!Settings.musicEnabled) {
            addActor(noMusic);
        }
        if (!Settings.soundEnabled) {
            addActor(noSound);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        getStage().dispose();
        Assets.assetManager.dispose();
    }

    @Override
    public void render(float delta) {
        if (touchBack && Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            appGame.getPEvent().exit(MainScreen.this);
            touchBack = false;
        } else {
            if (!Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                touchBack = true;
            }
        }
        super.render(delta);
    }

    public LevelScreen getLevelScreen() {
        return levelScreen;
    }

    public void setTouchBack(boolean touchBack) {
        this.touchBack = touchBack;
    }
}
