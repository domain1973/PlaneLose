package com.ads.gps.plane.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.ads.gps.plane.Answer;
import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Settings;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import cn.domob.android.ads.AdManager;
import cn.domob.android.ads.InterstitialAd;
import cn.domob.android.ads.InterstitialAdListener;

public class AndroidLauncher extends AndroidApplication {
    public static final String PUBLISHER_ID = "56OJxbJIuN06I9QHG4";
    public static final String InterstitialPPID = "16TLeSFoApHXkNUdeaHyCEjk";

    private InterstitialAd mInterstitialAd;
    private PEventImpl pEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        pEvent = new PEventImpl(AndroidLauncher.this);
        initialize(new AppGame(pEvent), config);
        loadGameConfig();
        mInterstitialAd = new InterstitialAd(this, PUBLISHER_ID,
                InterstitialPPID);
        mInterstitialAd.setInterstitialAdListener(new DobomAdListenerImpl());
        mInterstitialAd.loadInterstitialAd();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void spot() {
        if (mInterstitialAd.isInterstitialAdReady()){
            mInterstitialAd.showInterstitialAd(this);
        } else {
            Log.i("DomobSDKDemo", "Interstitial Ad is not ready");
            mInterstitialAd.loadInterstitialAd();
        }
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

    public class DobomAdListenerImpl implements InterstitialAdListener {

        @Override
        public void onInterstitialAdReady() {
            Log.i("DomobSDKDemo", "onAdReady");
        }

        @Override
        public void onLandingPageOpen() {
            Log.i("DomobSDKDemo", "onLandingPageOpen");
        }

        @Override
        public void onLandingPageClose() {
            Log.i("DomobSDKDemo", "onLandingPageClose");
        }

        @Override
        public void onInterstitialAdPresent() {
            Log.i("DomobSDKDemo", "onInterstitialAdPresent");
        }

        @Override
        public void onInterstitialAdDismiss() {
            // Request new ad when the previous interstitial ad was closed.
            mInterstitialAd.loadInterstitialAd();
            Log.i("DomobSDKDemo", "onInterstitialAdDismiss");
        }

        @Override
        public void onInterstitialAdFailed(AdManager.ErrorCode arg0) {
            Log.i("DomobSDKDemo", "onInterstitialAdFailed");
        }

        @Override
        public void onInterstitialAdLeaveApplication() {
            Log.i("DomobSDKDemo", "onInterstitialAdLeaveApplication");
        }

        @Override
        public void onInterstitialAdClicked(InterstitialAd arg0) {
            Log.i("DomobSDKDemo", "onInterstitialAdClicked");
        }
    }
}
