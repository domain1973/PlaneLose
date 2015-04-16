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
import com.five.adwoad.AdDisplay;
import com.five.adwoad.ErrorCode;
import com.five.adwoad.FullScreenAdListener;

public class AndroidLauncher extends AndroidApplication implements FullScreenAdListener {
    private String LOG_TAG = "Adwo full-screen ad";
    private AdDisplay ad;
    private PEventImpl pEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        pEvent = new PEventImpl(AndroidLauncher.this);
        initialize(new AppGame(pEvent), config);
        loadGameConfig();
        ad = new AdDisplay(this,"61d2c68101c2411c9520b96e2c6fdacd",false,this);
        // 设置全屏格式
        ad.setDesireAdForm(AdDisplay.ADWO_FS_INTERCEPT);
        // 设置请求广告类型 0（默认值）：表示插屏模式，即App Fun与插屏广告都能接收 * 1：表示仅接受App	Fun广告；* 2：表示仅接受插屏广告。
        ad.setDesireAdType(AdDisplay.ADWO_FS_TYPE_NO_APP_FUN);
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
        // 请求全屏广告
        ad.prepareAd();
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

    @Override
    public void onReceiveAd() {
        Log.e(LOG_TAG, "onReceiveAd");
    }

    @Override
    public void onLoadAdComplete() {
        Log.e(LOG_TAG, "onLoadAdComplete");
        // 成功完成下载后，展示广告
        ad.displayAd();
    }

    @Override
    public void onFailedToReceiveAd(ErrorCode errorCode) {
        Log.e(LOG_TAG, "onFailedToReceiveAd");
    }

    @Override
    public void onAdDismiss() {
        Log.e(LOG_TAG, "onAdDismiss");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(LOG_TAG, "onDestroy");
        // 请在这里释放全屏广告资源
        if (ad != null) {
            ad.dismiss();
            ad = null;
        }
    }
}
