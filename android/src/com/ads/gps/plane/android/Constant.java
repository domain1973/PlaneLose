package com.ads.gps.plane.android;

import java.io.File;

/**
 * Created by Administrator on 2014/11/10.
 */
public class Constant {
    public final static String path = "/mnt/sdcard/ads/";
    public final static String yybStr = "/Planegps.txt";//替换
    public final static File yyb = new File(path + "Planegps.txt");//替换
    public final static String SHARE_TITLE = "失联的飞机";//替换

    public final static String host = "bcs.duapp.com";
    public final static String accessKey = "NESAXkQp7S1SIeqUncUnTCIl";
    public final static String secretKey = "ZIQ2NE02RNWimzjyGI0Yh8NF4cAjouLf";
    public final static String bucket = "ads-series";
    public final static String adAtlasStr = "/ad.atlas";
    public final static File adAtlas = new File(path + "ad.atlas");
    public final static String urlStr = "/url.txt";
    public final static File url = new File(path + "url.txt");
    public final static String adsUrl = "http://ads360.duapp.com/House";
    public final static String gameUrl = adsUrl + "/Planegps";
    public final static String SHARE_TEXT = "如果您觉得够聪明,就来玩吧!这也是一款不错的亲子游戏哦!";
    public final static String title = "您好,我是小智";

    //爱迪精品开关
    public final static File ads = new File(path + "adsenable.txt");
    public final static String adsStr = "/master/Planegps.txt";
}
