package com.ads.gps.plane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/6/24.
 */
public class Answer {
    public final static int LEVEL_GATE_MAX = 12;
    public final static List<String[]> CHALLENGES = new ArrayList<String[]>();
    public final static String[] TITLES = new String[]{"时间已到!", "恭喜您过关了!", "您真牛!", "您真棒!"};
    public final static List<int[]> timeLevels = new ArrayList<int[]>();

    private final static int[] time0 = {0, 25, 35, 800};
    private final static int[] time1 = {0, 40, 50, 60};
    private final static int[] time2 = {0, 60, 70, 80};
    private final static int[] time3 = {0, 40, 60, 110};

    static {
        timeLevels.add(time0);
        timeLevels.add(time1);
        timeLevels.add(time2);
        timeLevels.add(time3);
    }

    private final static String[] C1 = {"1,1,0", "2,2,0", "3,2,2", "1,1,2", "1,-1,2","1,-1,0"};
    private final static String[] C2 = {"2,2,0", "3,2,1", "3,0,1", "1,0,0", "2,2,2", "2,0,2"};
    private final static String[] C3 = {"2,1,0", "3,0,2", "2,1,2", "0,0,0", "1,2,0", "1,2,2"};
    private final static String[] C4 = {"3,2,2", "3,0,1", "2,1,1", "2,2,0", "2,0,-1", "0,0,2"};
    private final static String[] C5 = {"1,0,1", "2,0,2", "1,2,1", "3,2,2", "2,0,-1", "0,2,0"};
    private final static String[] C6 = {"2,2,0", "3,1,0", "2,2,2", "0,1,2", "1,-1,2","2,0,-1"};
    private final static String[] C7 = {"3,2,2", "3,0,0", "2,1,0", "1,1,2", "1,-1,2", "1,2,0"};
    private final static String[] C8 = {"1,0,0", "2,0,1", "0,1,2", "2,2,0", "0,2,3", "2,0,2"};
    private final static String[] C9 = {"1,0,0", "3,0,2", "2,1,2", "3,1,0", "3,3,0", "1,2,2"};
    private final static String[] C10 = {"3,2,2", "3,0,2", "3,1,1", "3,1,1", "2,0,-1", "0,0,1"};
    private final static String[] C11 = {"1,0,1", "0,2,1", "3,2,2", "3,0,2", "0,0,0", "0,2,0"};
    private final static String[] C12 = {"3,2,2", "1,2,0", "0,1,0", "1,1,2", "1,-1,2", "3,0,0"};
    static {
        CHALLENGES.add(C1);
        CHALLENGES.add(C2);
        CHALLENGES.add(C3);
        CHALLENGES.add(C4);
        CHALLENGES.add(C5);
        CHALLENGES.add(C6);
        CHALLENGES.add(C7);
        CHALLENGES.add(C8);
        CHALLENGES.add(C9);
        CHALLENGES.add(C10);
        CHALLENGES.add(C11);
        CHALLENGES.add(C12);
    }

    public static final int GATE_MAX = 12;

    public static List<Integer> gateStars = new ArrayList<Integer>();

    public static boolean isLasterSmallGate(int nextGateNum) {
        return nextGateNum == LEVEL_GATE_MAX || nextGateNum == LEVEL_GATE_MAX*2 || nextGateNum == LEVEL_GATE_MAX*3 || nextGateNum == LEVEL_GATE_MAX*4;
    }
}
