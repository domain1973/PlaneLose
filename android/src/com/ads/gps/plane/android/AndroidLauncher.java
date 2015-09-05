package com.ads.gps.plane.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.ads.gps.plane.Answer;
import com.ads.gps.plane.AppGame;
import com.ads.gps.plane.Settings;
import com.ads.gps.plane.android.util.IabHelper;
import com.ads.gps.plane.android.util.IabResult;
import com.ads.gps.plane.android.util.Inventory;
import com.ads.gps.plane.android.util.Purchase;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    private int helpNum;
    private PEventImpl pEvent;
    private IabHelper mHelper;
    private static final int RC_REQUEST = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        pEvent = new PEventImpl(AndroidLauncher.this);
        initialize(new AppGame(pEvent), config);
        loadGameConfig();

        mHelper = new IabHelper(this, Constant.base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }
                if (mHelper == null) return;
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (mHelper == null) return;
            if (result.isFailure()) {
                return;
            }
            Purchase purchase = inventory.getPurchase(Constant.HELP_2);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                mHelper.consumeAsync(inventory.getPurchase(Constant.HELP_2), mConsumeFinishedListener);
                return;
            }
            purchase = inventory.getPurchase(Constant.HELP_5);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                mHelper.consumeAsync(inventory.getPurchase(Constant.HELP_5), mConsumeFinishedListener);
                return;
            }
            purchase = inventory.getPurchase(Constant.HELP_12);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                mHelper.consumeAsync(inventory.getPurchase(Constant.HELP_12), mConsumeFinishedListener);
                return;
            }
            purchase = inventory.getPurchase(Constant.HELP_N);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                mHelper.consumeAsync(inventory.getPurchase(Constant.HELP_N), mConsumeFinishedListener);
                return;
            }
        }
    };

    public void buy(int num) {
        String payload = "";
        helpNum = num;
        String sku = Constant.HELP_2;
        if (num == 2) {
            sku = Constant.HELP_2;
        } else if (num == 5) {
            sku = Constant.HELP_5;
        } else if (num == 12) {
            sku = Constant.HELP_12;
        } else {
            sku = Constant.HELP_N;
        }
        mHelper.launchPurchaseFlow(this, sku, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper == null) return;
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d("ADS", "onActivityResult handled by IABUtil.");
        }
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (mHelper == null) return;
            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                return;
            }
            mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (mHelper == null) return;
            if (result.isSuccess()) {
                int t = helpNum;
                if (Settings.helpNum == -1) {
                } else if (t == -1) {
                    Settings.helpNum = -1;
                } else {
                    Settings.helpNum = Settings.helpNum + t;
                }
                Settings.unlockEnabled = true;
            }
            saveData();
        }
    };

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
        super.onDestroy();
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    public void saveData() {
        SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();
        sharedata.putBoolean("music", Settings.musicEnabled);
        sharedata.putBoolean("sound", Settings.soundEnabled);
        sharedata.putInt("passNum", Settings.unlockGateNum);
        sharedata.putInt("helpNum", Settings.helpNum);
        StringBuffer sb = new StringBuffer();
        for (Integer starNum : Answer.gateStars) {
            sb.append(starNum).append(",");
        }
        sharedata.putString("starNum", sb.substring(0, sb.length() - 1));
        sharedata.putBoolean("unlock", Settings.unlockEnabled);
        sharedata.commit();
    }

    private void loadGameConfig() {
        SharedPreferences sharedata = getSharedPreferences("data", Context.MODE_PRIVATE);
        Settings.musicEnabled = sharedata.getBoolean("music", true);
        Settings.soundEnabled = sharedata.getBoolean("sound", true);
        Settings.unlockGateNum = sharedata.getInt("passNum", 0);
        Settings.helpNum = sharedata.getInt("helpNum", 3);//TODO
        Settings.unlockEnabled = sharedata.getBoolean("unlock", false);
        Answer.gateStars.clear();
        String[] split = sharedata.getString("starNum", "0").split("[,]");
        for (String starNum : split) {
            if (!"".equals(starNum)) {
                Answer.gateStars.add(Integer.parseInt(starNum));
            }
        }
        int sub = 48 - Answer.gateStars.size();
        for (int i=0; i<sub;i++) {
            Answer.gateStars.add(0);
        }
    }
}
