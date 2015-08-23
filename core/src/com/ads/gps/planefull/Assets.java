/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.ads.gps.planefull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Assets {
    public static AssetManager assetManager;
    public static Music musicbg;
    public static Sound btnSound;
    public static Sound starSound;
    public static TextureRegion startBg;
    public static TextureRegion gameBg;
    public static TextureRegion netBg;
    public static TextureRegion winBg;
    public static TextureRegion layerBg;
    public static TextureRegion areaBg;
    public static TextureRegion star;
    public static TextureRegion star_null;
    public static TextureRegion help;
    public static TextureRegion reset;
    public static TextureRegion refresh;
    public static TextureRegion returnTr;
    public static TextureRegion light;
    public static TextureRegion gate;
    public static TextureRegion next;
    public static TextureRegion music;
    public static TextureRegion sound;
    public static TextureRegion forbid;
    public static TextureRegion playBtn;
    public static TextureRegion levelPreBtn;
    public static TextureRegion levelNextBtn;
    public static TextureRegion gate_0star;
    public static TextureRegion gate_1star;
    public static TextureRegion gate_2star;
    public static TextureRegion gate_3star;
    public static TextureRegion gate_lock;
    public static TextureRegion lock;
    public static TextureRegion[] planes;
    public static TextureRegion flash120_0;
    public static TextureRegion flash120_1;
    public static TextureRegion flash120_2;
    public static TextureRegion flash120_3;
    public static TextureRegion flash240;
    public static TextureRegion help2;
    public static TextureRegion help2Down;
    public static TextureRegion help5;
    public static TextureRegion help5Down;
    public static TextureRegion help12;
    public static TextureRegion help12Down;
    public static TextureRegion helpN;
    public static TextureRegion helpNDown;
    public static int LEVEL_GATE_MAX = 12;
    public static int LEVEL_MAX = 4;
    public static int PLANE_NUM = 6;
    public static float TOPBAR_HEIGHT;//顶部按钮条的高度
    public static float WIDTH;
    public static float HEIGHT;
    public static float GAME_HEIGHT;
    public static float GAME_WIDTH;
    public static float POOL_SIZE;
    public static float GAME_README_H;
    public static float LEVEL_IMAGE_SIZE;
    public static float LEVEL_IMAGE_OFF_SIZE;
    public static float PLANE_SIZE;
    public static float PLANE_BIG_SIZE;
    public static float ANSWER_X;
    public static float GATENUM_X;
    public static float GATENUM_Y;
    public static float AREA_Y;
    public static List<Sprite> levels;
    public static BitmapFont gameFont;
    public static BitmapFont otherFont;
    public static BitmapFont readmeFont;
    public static BitmapFont quizFont;
    public static List<TextureRegion> gateBmpList;

    public static void load() {
        assetManager = new AssetManager();
        assetManager.load("p.atlas", TextureAtlas.class);
        assetManager.load("puzzle.fnt", BitmapFont.class);
        assetManager.load("game.fnt", BitmapFont.class);
        assetManager.load("readme.fnt", BitmapFont.class);
        assetManager.load("quiz.fnt", BitmapFont.class);
        assetManager.load("data/musicbg.wav", Music.class); //加载背景音乐
        assetManager.load("data/btn.wav", Sound.class);
        assetManager.load("data/star.mp3", Sound.class);
    }

    public static boolean isFinishLoading() {
        return assetManager.update();
    }

    public static void initData() {
        gameFont = assetManager.get("puzzle.fnt", BitmapFont.class);
        otherFont = assetManager.get("game.fnt", BitmapFont.class);
        readmeFont = assetManager.get("readme.fnt", BitmapFont.class);
        quizFont = assetManager.get("quiz.fnt", BitmapFont.class);
        btnSound = assetManager.get("data/btn.wav", Sound.class);
        starSound = assetManager.get("data/star.mp3", Sound.class);
        musicbg = assetManager.get("data/musicbg.wav", Music.class);    //加载背景音乐
        initConstants();
        TextureAtlas atlas = assetManager.get("p.atlas", TextureAtlas.class);
        loadBmps(atlas);
        createLevels(atlas);
        creteMagicCubes(atlas);
        createGateImages(atlas);
        loadMusic();
    }

    private static void initConstants() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        TOPBAR_HEIGHT = HEIGHT / 16;
        GAME_HEIGHT = 13 * HEIGHT / 16;
        GAME_WIDTH = WIDTH;
        GAME_README_H = HEIGHT / 8;
        LEVEL_IMAGE_SIZE = WIDTH;
        LEVEL_IMAGE_OFF_SIZE = WIDTH / 7;

        int GAMEBG_SIZE = 800;
        int RIGHT_X = 680;//最右边的位置 800
        int RIGHT_Y = 70;//最右边的位置 800
        GATENUM_X = 640 * Assets.WIDTH / GAMEBG_SIZE;
        GATENUM_Y = HEIGHT - RIGHT_Y * WIDTH / GAMEBG_SIZE - TOPBAR_HEIGHT;
        POOL_SIZE = WIDTH;
        PLANE_SIZE = WIDTH / 4;
        PLANE_BIG_SIZE = WIDTH / 2;
        ANSWER_X = RIGHT_X * Assets.WIDTH / GAMEBG_SIZE;
        AREA_Y = HEIGHT - WIDTH - TOPBAR_HEIGHT;
    }

    private static void loadBmps(TextureAtlas atlas) {
        startBg = atlas.findRegion("startbg");
        gameBg = atlas.findRegion("gamebg");
        netBg = atlas.findRegion("netbg");
        layerBg = atlas.findRegion("layerbg");//图层背景,对话框使用
        winBg = atlas.findRegion("winbg");
        areaBg = atlas.findRegion("area");
        flash120_0 = atlas.findRegion("flash120-0");
        flash120_1 = atlas.findRegion("flash120-1");
        flash120_2 = atlas.findRegion("flash120-2");
        flash120_3 = atlas.findRegion("flash120-3");
        flash240 = atlas.findRegion("flash240");

        light = atlas.findRegion("light");
        next = atlas.findRegion("next");
        returnTr = atlas.findRegion("return");
        help = atlas.findRegion("help");
        refresh = atlas.findRegion("refresh");
        gate = atlas.findRegion("gate");
        music = atlas.findRegion("muisc");
        sound = atlas.findRegion("sound");
        reset = atlas.findRegion("reset");
        star = atlas.findRegion("star");
        star_null = atlas.findRegion("star_null");
        forbid = atlas.findRegion("forbid");
        lock = atlas.findRegion("lock");
        playBtn = atlas.findRegion("playbtn");
        levelPreBtn = atlas.findRegion("levelpre");
        levelNextBtn = atlas.findRegion("levelnext");

        gate_0star = atlas.findRegion("gate_0star");
        gate_1star = atlas.findRegion("gate_1star");
        gate_2star = atlas.findRegion("gate_2star");
        gate_3star = atlas.findRegion("gate_3star");
        gate_lock = atlas.findRegion("gate_lock");

        help2 = atlas.findRegion("help2");
        help2Down = atlas.findRegion("help2Down");
        help5 = atlas.findRegion("help5");
        help5Down = atlas.findRegion("help5Down");
        help12 = atlas.findRegion("help12");
        help12Down = atlas.findRegion("help12Down");
        helpN = atlas.findRegion("helpN");
        helpNDown = atlas.findRegion("helpNDown");
    }

    private static void creteMagicCubes(TextureAtlas atlas) {
        planes = new TextureRegion[PLANE_NUM];
        for (int i = 0; i < PLANE_NUM; i++) {
            String name = "plane" + i;
            planes[i] = atlas.findRegion(name);
        }
    }

    private static void createLevels(TextureAtlas atlas) {
        levels = new ArrayList<Sprite>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Sprite s = atlas.createSprite("s" + i);
            if (s == null) {
                break;
            }
            levels.add(s);
        }
    }

    private static void createGateImages(TextureAtlas atlas) {
        gateBmpList = new ArrayList<TextureRegion>();
        int gateTotal = LEVEL_MAX * LEVEL_GATE_MAX;
        for (int i = 1; i <= gateTotal; i++) {
            gateBmpList.add(atlas.findRegion(i+""));
        }
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }

    public static void soundBtn() {
        playSound(btnSound);
    }

    public static void soundStar() {
        playSound(starSound);
    }

    private static void loadMusic() {
        musicbg.setLooping(true);  //设置背景音乐循环播放
        musicbg.setVolume(0.5f);
        if (Settings.musicEnabled) {
            musicbg.play();        //播放背景音乐
        }
    }
}
