package com.ads.gps.plane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/6/24.
 */
public class Answer {
    public final static List<String[]> CHALLENGES = new ArrayList<String[]>();
    public final static String[] TITLES = new String[]{"时间已到,要继续努力哦!", "您是合格的领航员!", "您是一流的领航员!", "您是专家级的领航员!"};
    public final static List<int[]> timeLevels = new ArrayList<int[]>();

    private final static int[] time0 = {0, 150, 160, 180};
    private final static int[] time1 = {0, 200, 250, 300};
    private final static int[] time2 = {0, 250, 300, 360};
    private final static int[] time3 = {0, 300, 360, 420};

    static {
        timeLevels.add(time0);
        timeLevels.add(time1);
        timeLevels.add(time2);
        timeLevels.add(time3);
    }

    private final static String[] C1 = {"1,1,0", "3,2,0", "3,2,2", "1,1,2", "1,-1,2","1,-1,0"};
    private final static String[] C2 = {"2,2,0", "0,2,1", "3,0,1", "1,0,0", "2,2,2", "2,0,2"};
    private final static String[] C3 = {"2,1,0", "0,0,2", "2,1,2", "0,0,0", "1,2,0", "1,2,2"};
    private final static String[] C4 = {"3,2,2", "0,0,1", "2,1,1", "2,2,0", "2,0,-1", "2,0,2"};
    private final static String[] C5 = {"1,0,1", "3,0,2", "1,2,1", "3,2,2", "2,0,-1", "0,2,0"};
    private final static String[] C6 = {"2,2,0", "0,1,0", "2,2,2", "0,1,2", "1,-1,2","3,0,0"};
    private final static String[] C7 = {"3,2,2", "0,0,0", "2,1,0", "1,1,2", "1,-1,2", "1,2,0"};
    private final static String[] C8 = {"1,0,0", "3,0,1", "0,2,1", "2,2,0", "0,2,3", "2,0,2"};
    private final static String[] C9 = {"1,0,0", "0,0,2", "2,1,2", "3,1,0", "3,3,0", "1,2,2"};
    private final static String[] C10 = {"3,2,2", "0,0,2", "3,1,1", "2,2,0", "2,0,-1", "0,0,1"};
    private final static String[] C11 = {"1,0,1", "1,2,1", "3,2,2", "3,0,2", "0,0,0", "0,2,0"};
    private final static String[] C12 = {"3,2,2", "2,2,0", "0,1,0", "1,1,2", "1,-1,2", "3,0,0"};
    private final static String[] C13 = {"1,0,0", "1,1,1", "2,2,0", "3,1,2", "1,-1,2", "3,3,2"};
    private final static String[] C14 = {"3,2,2", "1,0,1", "2,2,0", "3,0,2", "0,0,0", "3,2,1"};
    private final static String[] C15 = {"0,0,1", "2,0,0", "3,2,1", "1,2,0", "0,2,3", "2,0,2"};
    private final static String[] C16 = {"0,0,2", "2,1,0", "0,1,1", "3,2,2", "1,-1,0", "1,2,0"};
    private final static String[] C17 = {"0,0,2", "2,2,0", "3,2,2", "0,1,0", "1,-1,0", "0,1,2"};
    private final static String[] C18 = {"1,0,0", "2,2,0", "1,1,1", "0,0,2", "1,1,2", "3,3,2"};
    private final static String[] C19 = {"0,0,0", "3,2,0", "3,2,2", "1,1,2", "1,-1,2", "2,1,-1"};
    private final static String[] C20 = {"2,2,0", "1,0,0", "3,2,2", "1,1,2", "1,-1,2", "0,1,1"};
    private final static String[] C21 = {"1,0,0", "2,1,1", "2,2,0", "0,1,2", "1,-1,2", "1,2,2"};
    private final static String[] C22 = {"0,1,1", "2,1,0", "0,0,2", "3,2,2", "1,2,0", "1,-1,0"};
    private final static String[] C23 = {"3,1,2", "1,0,2", "1,0,0", "3,1,0", "3,3,0", "3,3,2"};
    private final static String[] C24 = {"0,0,2", "1,2,0", "0,1,1", "1,0,0", "3,3,1", "2,2,2"};
    private final static String[] C25 = {"2,2,0", "3,2,2", "1,0,1", "3,0,2", "1,1,1", "0,0,0"};
    private final static String[] C26 = {"3,2,2", "1,0,1", "3,1,1", "2,2,0", "0,0,3", "2,0,-1"};
    private final static String[] C27 = {"0,2,1", "1,0,1", "2,2,0", "3,0,2", "0,0,0", "2,2,2"};
    private final static String[] C28 = {"0,0,2", "1,0,0", "0,2,1", "2,2,0", "3,1,1", "0,2,3"};
    private final static String[] C29 = {"0,2,1", "2,2,0", "1,0,1", "3,0,2", "2,0,-1", "2,2,2"};
    private final static String[] C30 = {"0,0,2", "1,0,0", "0,1,1", "3,2,2", "1,1,0", "3,3,0"};
    private final static String[] C31 = {"0,2,2", "1,2,0", "1,0,1", "3,0,2", "1,2,1", "0,0,0"};
    private final static String[] C32 = {"1,2,0", "0,2,2", "1,0,1", "3,0,2", "2,0,-1", "3,3,1"};
    private final static String[] C33 = {"0,2,1", "2,2,0", "1,0,1", "3,0,2", "2,0,-1", "2,2,2"};
    private final static String[] C34 = {"3,2,2", "0,0,2", "3,1,1", "2,2,0", "3,1,0", "1,-1,0"};
    private final static String[] C35 = {"2,2,0", "0,1,0", "0,1,2", "2,2,2", "1,-1,2", "1,-1,0"};
    private final static String[] C36 = {"2,2,0", "3,2,2", "2,1,1", "1,0,0", "1,-1,2", "3,1,2"};
    private final static String[] C37 = {"0,0,0", "0,0,2", "2,1,0", "2,1,2", "3,3,0", "1,2,2"};
    private final static String[] C38 = {"2,1,0", "2,2,2", "0,0,0", "0,1,2", "1,2,0", "1,-1,2"};
    private final static String[] C39 = {"0,0,2", "3,2,2", "0,1,1", "1,0,0", "1,1,0", "3,3,0"};
    private final static String[] C40 = {"3,2,0", "2,1,2", "1,1,0", "0,0,2", "1,2,2", "1,-1,0"};
    private final static String[] C41 = {"0,1,2", "3,2,0", "0,0,0", "2,2,2", "3,0,2", "2,1,-1"};
    private final static String[] C42 = {"2,2,0", "3,0,2", "2,1,1", "3,2,2", "3,0,1", "0,0,0"};
    private final static String[] C43 = {"2,2,0", "3,2,2", "2,0,0", "3,1,1", "1,-1,1", "2,0,2"};
    private final static String[] C44 = {"2,2,0", "3,2,2", "3,0,2", "1,0,1", "1,1,1", "2,0,-1"};
    private final static String[] C45 = {"2,2,2", "3,2,0", "1,0,2", "1,1,0", "2,1,2", "1,-1,0"};
    private final static String[] C46 = {"1,0,0", "2,2,0", "1,1,1", "2,2,2", "0,1,3", "1,-1,2"};
    private final static String[] C47 = {"0,0,2", "1,1,0", "3,2,2", "3,1,1", "1,-1,0", "1,2,0"};
    private final static String[] C48 = {"3,1,1", "2,2,0", "3,2,2", "2,0,0", "2,0,2", "1,-1,1"};
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
        CHALLENGES.add(C13);
        CHALLENGES.add(C14);
        CHALLENGES.add(C15);
        CHALLENGES.add(C16);
        CHALLENGES.add(C17);
        CHALLENGES.add(C18);
        CHALLENGES.add(C19);
        CHALLENGES.add(C20);
        CHALLENGES.add(C21);
        CHALLENGES.add(C22);
        CHALLENGES.add(C23);
        CHALLENGES.add(C24);
        CHALLENGES.add(C25);
        CHALLENGES.add(C26);
        CHALLENGES.add(C27);
        CHALLENGES.add(C28);
        CHALLENGES.add(C29);
        CHALLENGES.add(C30);
        CHALLENGES.add(C31);
        CHALLENGES.add(C32);
        CHALLENGES.add(C33);
        CHALLENGES.add(C34);
        CHALLENGES.add(C35);
        CHALLENGES.add(C36);
        CHALLENGES.add(C37);
        CHALLENGES.add(C38);
        CHALLENGES.add(C39);
        CHALLENGES.add(C40);
        CHALLENGES.add(C41);
        CHALLENGES.add(C42);
        CHALLENGES.add(C43);
        CHALLENGES.add(C44);
        CHALLENGES.add(C45);
        CHALLENGES.add(C46);
        CHALLENGES.add(C47);
        CHALLENGES.add(C48);
    }

    public static List<Integer> gateStars = new ArrayList<Integer>();

    public static boolean isLasterSmallGate(int nextGateNum) {
        return nextGateNum == Assets.LEVEL_GATE_MAX || nextGateNum == Assets.LEVEL_GATE_MAX*2
                || nextGateNum == Assets.LEVEL_GATE_MAX*3 || nextGateNum == Assets.LEVEL_GATE_MAX*4;
    }

    public static boolean isAllPass(int nextGateNum) {
        return nextGateNum == Assets.LEVEL_GATE_MAX*Assets.LEVEL_MAX;
    }
}
