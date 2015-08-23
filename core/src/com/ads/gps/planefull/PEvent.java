package com.ads.gps.planefull;

import com.ads.gps.planefull.screen.MainScreen;

/**
 * Created by Administrator on 2014/9/10.
 */
public abstract class PEvent {

    public abstract void exit(MainScreen ms);

    public abstract void save();

    public abstract void help(int level);

    public abstract void pass();

    public abstract void buy(int num);


}
