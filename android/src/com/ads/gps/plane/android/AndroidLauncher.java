package com.ads.gps.plane.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;

import com.ads.gps.plane.Answer;
import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Settings;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.otomod.ad.AdView;
import com.otomod.ad.listener.O2OAdListener;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

public class AndroidLauncher extends AndroidApplication {
    private static String APP_KEY = "63d351c2d09911e49082f8bc123d7e98";
    private PEventImpl pEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        pEvent = new PEventImpl(AndroidLauncher.this);
        initialize(new AppGame(pEvent), config);
        loadGameConfig();
        AdManager.getInstance(this).init("fc9c3f69189817a0", "a28237054af6846e", false);
        SpotManager.getInstance(this).loadSpotAds();
        SpotManager.getInstance(this).setSpotOrientation(
                SpotManager.ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        //如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
        SpotManager.getInstance(this).onStop();
        super.onStop();
    }

    public void spot() {
        AdView adView = AdView.createPopup(this, APP_KEY);
        adView.setAdListener(new O2OAdListenerImpl(this));
        adView.request();
    }

    private void loadGameConfig() {
        SharedPreferences sharedata = getSharedPreferences("data", Context.MODE_PRIVATE);
        Settings.musicEnabled = sharedata.getBoolean("music", true);
        Settings.soundEnabled = sharedata.getBoolean("sound", true);
        Settings.unlockGateNum = sharedata.getInt("passNum", 0);
        Settings.helpNum = sharedata.getInt("helpNum", 3);//TODO
        Answer.gateStars.clear();
        String[] split = sharedata.getString("starNum", "0").split("[,]");
        for (String starNum : split) {
            if (!"".equals(starNum)) {
                Answer.gateStars.add(Integer.parseInt(starNum));
            }
        }
    }

    public class O2OAdListenerImpl implements O2OAdListener {
        private AndroidLauncher androidLauncher;

        public O2OAdListenerImpl(AndroidLauncher launcher) {
            androidLauncher = launcher;
        }

        @Override
        public void onClick() {
        }

        @Override
        public void onClose() {
        }

        @Override
        public void onAdFailed() {
            SpotManager.getInstance(androidLauncher).showSpotAds(androidLauncher);
        }

        @Override
        public void onAdSuccess() {
        }
    }
}
