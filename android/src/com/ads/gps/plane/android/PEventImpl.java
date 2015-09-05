package com.ads.gps.plane.android;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import com.ads.gps.plane.PEvent;
import com.ads.gps.plane.screen.MainScreen;

/**
 * Created by Administrator on 2014/10/2.
 */
public class PEventImpl extends PEvent {
    private AndroidLauncher launcher;
    private Handler handler;

    public PEventImpl(AndroidLauncher androidLauncher) {
        launcher = androidLauncher;
        handler = new Handler();
    }

    @Override
    public void exit(final MainScreen ms) {
        save();
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launcher.startActivity(startMain);
            System.exit(0);
        } else {// android2.1
            ActivityManager am = (ActivityManager) launcher.getSystemService(launcher.ACTIVITY_SERVICE);
            am.restartPackage(launcher.getPackageName());
        }
    }

    @Override
    public void save() {
        launcher.saveData();
    }

    @Override
    public void help(final int level) {
        handler.post(new Runnable() {
            public void run() {
                int readme = 0;
                switch (level) {
                    case 0:
                        readme = R.string.readme1;
                        break;
                    case 1:
                        readme = R.string.readme2;
                        break;
                    case 2:
                        readme =  R.string.readme3;
                        break;
                    case 3:
                        readme = R.string.readme4;
                }
                new AlertDialog.Builder(launcher).setTitle(Constant.SHARE_TITLE).setMessage(readme)
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setIcon(R.drawable.xiaozi).create().show();
            }
        });
    }

    @Override
    public void pass() {
        handler.post(new Runnable() {
            public void run() {
                new AlertDialog.Builder(launcher).setTitle(Constant.SHARE_TITLE).setMessage("You are great! Games have all passed, thanks for using!")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setIcon(R.drawable.xiaozi).create().show();
            }
        });
    }

    @Override
    public void buy(int num) {
        launcher.buy(num);
    }
}