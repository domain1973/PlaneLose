package com.ads.gps.plane.window;

import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Assets;
import com.ads.gps.plane.Settings;
import com.ads.gps.plane.screen.GameScreen;
import com.ads.gps.plane.screen.HelpScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Created by Administrator on 2014/7/20.
 */
public class SupsendWin extends BaseWin {
    private AppGame puzzle;
    private GameScreen gameScreen;
    private float win_H;
    private float win_Y;
    private int level;
    private Image noMusic;
    private Image noSound;

    public SupsendWin(GameScreen gs, int lv) {
        super("", new Window.WindowStyle(gs.getGameFont(), Color.WHITE, new TextureRegionDrawable(
                Assets.gameBg)));
        puzzle = gs.getAppGame();
        gameScreen = gs;
        level = lv;
        create();
    }

    public void create() {
        final float btnSize = Assets.WIDTH / 6;
        gameScreen.getStage().addActor(layerBg);
        win_H = btnSize * 4;
        win_Y = (Assets.HEIGHT - win_H) / 2;
        setBounds(0, win_Y, Assets.WIDTH, win_H);
        float y2 = win_H / 5;
        float y1 = win_H / 5 * 3;
        float x = Assets.WIDTH / 5;

        noSound = new Image(Assets.forbid);
        final ImageButton gate = new ImageButton(new TextureRegionDrawable(Assets.gate), new TextureRegionDrawable(Assets.gate));
        gate.setBounds(x, y1, btnSize, btnSize);
        final ImageButton share = new ImageButton(new TextureRegionDrawable(Assets.share), new TextureRegionDrawable(Assets.share));
        share.setBounds(x * 2, y1, btnSize, btnSize);
        final ImageButton continues = new ImageButton(new TextureRegionDrawable(Assets.continueTr), new TextureRegionDrawable(Assets.continueTr));
        continues.setBounds(x * 3, y1, btnSize, btnSize);

        final ImageButton music = new ImageButton(new TextureRegionDrawable(Assets.music), new TextureRegionDrawable(Assets.music));
        music.setBounds(x, y2, btnSize, btnSize);
        noMusic = new Image(Assets.forbid);
        noMusic.setBounds(music.getX(), music.getY(), btnSize, btnSize);
        final ImageButton sound = new ImageButton(new TextureRegionDrawable(Assets.sound), new TextureRegionDrawable(Assets.sound));
        sound.setBounds(x * 2, y2, btnSize, btnSize);
        noSound = new Image(Assets.forbid);
        noSound.setBounds(sound.getX(), sound.getY(), btnSize, btnSize);
        final ImageButton help = new ImageButton(new TextureRegionDrawable(Assets.help), new TextureRegionDrawable(Assets.help));
        help.setBounds(x * 3, y2, btnSize, btnSize);
        Gdx.input.setInputProcessor(gameScreen.getStage());

        share.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, share.getWidth(), share.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    puzzle.getPEvent().share();
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
        help.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, help.getWidth(), help.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    HelpScreen helpScreen = puzzle.getMainScreen().getHelpScreen();
                    helpScreen.setBaseScreen(gameScreen);
                    puzzle.setScreen(helpScreen);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        gate.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, gate.getWidth(), gate.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    layerBg.remove();
                    SupsendWin.this.remove();
                    gameScreen.resumeTimer();
                    gameScreen.getGateScreen().buildGateImage(level);
                    puzzle.setScreen(gameScreen.getGateScreen());
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        continues.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, continues.getWidth(), continues.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    gameScreen.resumeTimer();
                    layerBg.remove();
                    SupsendWin.this.remove();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(music);
        addActor(sound);
        if (!Settings.musicEnabled) {
            addActor(noMusic);
        }
        if (!Settings.soundEnabled) {
            addActor(noSound);
        }
        addActor(help);
        addActor(gate);
        addActor(share);
        addActor(continues);
    }
}
