package com.ads.gps.plane;

import com.ads.gps.plane.screen.LevelScreen;
import com.ads.gps.plane.screen.LoadingScreen;
import com.ads.gps.plane.screen.MainScreen;
import com.badlogic.gdx.Game;

public class AppGame extends Game {
    private LoadingScreen loadingScreen;
    private PEvent pEvent;

    public AppGame() {
    }

    public AppGame(PEvent pe) {
        pEvent = pe;
    }

    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    public MainScreen getMainScreen() {
        return loadingScreen.getMainScreen();
    }

    public LevelScreen getLevelScreen() {
        return getMainScreen().getLevelScreen();
    }

    public PEvent getPEvent() {
        return pEvent;
    }

    public void convert2MainScreen() {
        setScreen(loadingScreen.getMainScreen());
    }
}
