package com.ads.gps.plane.screen;

import com.ads.gps.plane.Answer;
import com.ads.gps.plane.Assets;
import com.ads.gps.plane.Settings;
import com.ads.gps.plane.actors.PlaneImage;
import com.ads.gps.plane.controller.AreaController;
import com.ads.gps.plane.controller.IController;
import com.ads.gps.plane.controller.PlaneController;
import com.ads.gps.plane.listener.PlaneDetector;
import com.ads.gps.plane.listener.PlaneListener;
import com.ads.gps.plane.window.ResultWin;
import com.ads.gps.plane.window.SupsendWin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/6/24.
 */
public class GameScreen extends BaseScreen {
    private ScheduledExecutorService executorGateEnd;
    private ScheduledExecutorService executStarCount;
    private ScheduledExecutorService executTime;

    private Image tFlash;
    private ParticleEffect effect;
    private GateScreen gateScreen;
    private AreaController areaCtrl;
    private PlaneController planeCtrl;
    private PlaneDetector planeDetector;
    private SupsendWin supsendWin;
    private InputMultiplexer multiplexer;
    private String timeStr;
    private Label labTime;
    private Label labCount;

    private boolean openResultWin;
    private boolean isPass;
    private boolean isSuspend;
    private boolean isUsedHelp;
    private boolean isUsingHelp;
    private int level;
    private int gateNum;
    private int seconds;
    private int starNum;
    private int planeId = 0;
    private Image[] flashImages;
    private Label.LabelStyle yellowStyle;
    private Label.LabelStyle redStyle;
    private Label.LabelStyle quizStyle;
    private Label labGateNum;

    public GameScreen(GateScreen gs) {
        super(gs.getAppGame());
        gateNum = -1;
        gateScreen = gs;
        isUsingHelp = true;
        BitmapFont font = getOtherFont();
        font.setScale(Assets.WIDTH / 480 * (float)0.8);
        yellowStyle = new Label.LabelStyle(font, Color.YELLOW);
        redStyle = new Label.LabelStyle(font, Color.RED);
        quizStyle = new Label.LabelStyle(getQuizFont(), Color.RED);

        executorGateEnd = Executors.newSingleThreadScheduledExecutor();
        flashImages = new Image[5];
        flashImages[0] = (new Image(Assets.flash120_0));
        flashImages[1] = (new Image(Assets.flash120_1));
        flashImages[2] = (new Image(Assets.flash120_2));
        flashImages[3] = (new Image(Assets.flash120_3));
        flashImages[4] = (new Image(Assets.flash240));
    }

    @Override
    public void show() {
        if (!isShow()) {
            super.show();
            timeStr = "00:00";
            areaCtrl = new AreaController(IController.AREA_CTRL);
            addActor(areaCtrl);
            planeCtrl = new PlaneController(IController.PIECE_CTRL);
            addActor(planeCtrl); // 添加块组到舞台
            createTopBar();
            addLabels();
            initEffect();
            createTimer();
            removeLayerBg();
            areaCtrl.setGateNum(gateNum);
            areaCtrl.handler();
            Image netbg =  new Image(Assets.netBg);
            netbg.setBounds(0, Assets.AREA_Y, Assets.WIDTH, Assets.WIDTH);
            addActor(netbg);
            setShow(true);
        }
        multiplexer = new InputMultiplexer(); // 多输入接收器
        planeDetector = new PlaneDetector(getStage(), new PlaneListener(getStage(), GameScreen.this));
        multiplexer.addProcessor(planeDetector); // 添加手势识别
        multiplexer.addProcessor(getStage()); // 添加舞台
        Gdx.input.setInputProcessor(multiplexer); // 设置多输入接收器为接收器
    }

    private void createTopBar() {
        super.createBtns();
        final ImageButton sosBtn = new ImageButton(new TextureRegionDrawable(Assets.light));
        sosBtn.setBounds(Assets.WIDTH - 4 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        sosBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, sosBtn.getWidth(), sosBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    if (Settings.helpNum > 0) {
                        getAppGame().getPEvent().sos(GameScreen.this);
                    } else {
                        getAppGame().getPEvent().invalidateSos();
                    }
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(sosBtn);

        final ImageButton share = new ImageButton(new TextureRegionDrawable(Assets.barShare));
        share.setBounds(Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        share.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, share.getWidth(), share.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    getAppGame().getPEvent().share();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(share);

        final ImageButton resetBtn = new ImageButton(new TextureRegionDrawable(Assets.reset));
        resetBtn.setBounds(Assets.WIDTH - 2 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        resetBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, resetBtn.getWidth(), resetBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    areaCtrl.clearPlane();
                    planeCtrl.handler();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(resetBtn);

        final ImageButton suspendBtn = new ImageButton(new TextureRegionDrawable(Assets.suspend));
        suspendBtn.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        suspendBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, suspendBtn.getWidth(), suspendBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    suspendTimer();
                    supsendWin = new SupsendWin(GameScreen.this, level);
                    addActor(supsendWin);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(suspendBtn);

        final ImageButton helpBtn = new ImageButton(new TextureRegionDrawable(Assets.help));
        helpBtn.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, Assets.AREA_Y - Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        helpBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, helpBtn.getWidth(), helpBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    getAppGame().getPEvent().help(level);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(helpBtn);

        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, returnBtn.getWidth(), returnBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    suspendTimer();
                    gateScreen.buildGateImage(level);
                    getAppGame().setScreen(gateScreen);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    private void addLabels() {
        BitmapFont font = getOtherFont();
        BitmapFont.TextBounds bounds = font.getBounds("00");
        labTime = new Label("", yellowStyle);
        labTime.setPosition(Assets.TOPBAR_HEIGHT, Assets.HEIGHT - Assets.TOPBAR_HEIGHT / 2);
        addActor(labTime);
        float w = bounds.width;
        labCount = new Label("", redStyle);
        labCount.setPosition(Assets.WIDTH - 4 * Assets.TOPBAR_HEIGHT - w / 3, Assets.HEIGHT - Assets.TOPBAR_HEIGHT / 2);
        addActor(labCount);

        String s = "把下面的6个拼图片移到上面的图中,移动后点击\n拼图可变方向. 注意拼图是不能重叠的.";
        Label c = new Label(s, quizStyle);
        c.setPosition(0, Assets.PLANE_SIZE + Assets.PLANE_SIZE/2);
        addActor(c);
        labGateNum = new Label("", yellowStyle);
        labGateNum.setPosition(0, Assets.AREA_Y + bounds.height - 5);
        addActor(labGateNum);
    }

    private void initEffect() {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data/"));
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            if (!isSuspend && isClosedResultWin()) {
                gateScreen.setBackFlag(true);
                gateScreen.buildGateImage(level);
                getAppGame().setScreen(gateScreen);
                return;
            }
        }
        super.render(delta);
        handleHelp();
        handlePass();
        labTime.setText(timeStr);
        labCount.setText(Settings.helpNum + "");
        labGateNum.setText((gateNum + 1) + "/" + Answer.CHALLENGES.size());
    }

    private void handleHelp() {
        if (isUsedHelp) {
            if (isUsingHelp) {
                changeStar();
                planeId = 0;
                isUsingHelp = false;
            }
            if (planeId < 4) {
                int temp = planeId;//防止定时器修改值不同步
                String[] c = Answer.CHALLENGES.get(gateNum)[temp].split(",");
                PlaneImage plane = (PlaneImage)planeCtrl.getChildren().get(temp);
                plane.setBounds(Integer.parseInt(c[1]) * Assets.PLANE_SIZE,
                         Assets.HEIGHT - Assets.TOPBAR_HEIGHT - (4 - Integer.parseInt(c[2])) * Assets.PLANE_SIZE,
                         Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
                plane.setOrientation(Integer.parseInt(c[0]));
                areaCtrl.addPlane(plane);
                getBatch().begin();
                effect.setPosition(plane.getX() + Assets.PLANE_BIG_SIZE / 2, plane.getY() + Assets.PLANE_BIG_SIZE / 2);
                effect.draw(getBatch(), Gdx.graphics.getDeltaTime());
                getBatch().end();
            } else {
                isUsedHelp = false;
                resumeTimer();
                planeId = 0;
                executStarCount.shutdown();
                multiplexer.addProcessor(planeDetector); // 添加手势识别
                multiplexer.addProcessor(getStage());
            }
        }
    }

    private void changeStar() {
        executStarCount = Executors.newSingleThreadScheduledExecutor();
        executStarCount.scheduleAtFixedRate(new Runnable() {
            public void run() {
                planeId++;
                if (planeId < 4) {
                    Assets.soundStar();
                }
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }

    private void handlePass() {
        if (!isPass) {
            Gdx.input.setInputProcessor(multiplexer);
            handleGate();
        }
        if (openResultWin) {
            computerStarNum();
            addActor(new ResultWin(this, starNum));
            openResultWin = false;
            if (starNum == 0) {
                getAppGame().getPEvent().spotAd();
            }
            areaCtrl.clearPlane();
            planeCtrl.handler();
        }
    }

    private boolean isClosedResultWin() {
        Array<Actor> actors = getStage().getActors();
        for (Actor actor : actors) {
            if (actor instanceof ResultWin) {
                return false;
            }
        }
        return true;
    }

    private void computerStarNum() {
        int[] timeLevel = Answer.timeLevels.get(level);
        if (timeLevel[0] < seconds && timeLevel[1] >= seconds) {
            starNum = 1;
        } else if (timeLevel[1] < seconds && timeLevel[2] >= seconds) {
            starNum = 2;
        } else if (timeLevel[2] < seconds && timeLevel[3] >= seconds) {
            starNum = 3;
        } else {
            starNum = 0;
        }
        if (Answer.gateStars.size() > gateNum) {//可能重玩
            if (starNum > 0) {
                Answer.gateStars.set(gateNum, starNum);
            }
        } else {
            Answer.gateStars.add(starNum);
        }
    }

    private void handleGate() {
        if (seconds <= 0) {//game over
            Gdx.input.setInputProcessor(null);
            executTime.shutdown();
            openResultWin = true; //关卡结束
            areaCtrl.clearPlane();
            isPass = true;
        } else if (planeDetector.isPass(gateNum)) {
            Gdx.input.setInputProcessor(null);
            executTime.shutdown();
            Runnable runner = new Runnable() {
                public void run() {
                    openResultWin = true; //关卡结束
                }
            };
            executorGateEnd.schedule(runner, 1500, TimeUnit.MILLISECONDS);
            isPass = true;
        }
    }

    private void suspendTimer() {
        isSuspend = true;
    }

    public void resumeTimer() {
        isSuspend = false;
    }

    private void createTimer() {
        isSuspend = false;
        seconds = Answer.timeLevels.get(level)[3];
        labTime.setStyle(yellowStyle);
        executTime = Executors.newSingleThreadScheduledExecutor();
        executTime.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (!isSuspend) {
                    seconds--; //
                    if (seconds < 15) {
                        if (seconds % 2 == 0) {
                            labTime.setStyle(redStyle);
                        } else {
                            labTime.setStyle(yellowStyle);
                        }
                    }
                    buildTimeStr();
                }
            }

            private void buildTimeStr() {
                String str0 = "%d";
                String str1 = "%d";
                int minute = seconds / 60;
                int second = seconds % 60;
                if (seconds < 0) {
                    second = 0;
                }
                if (minute < 10) {
                    str0 = "0%d";
                }
                if (second < 10) {
                    str1 = "0%d";
                }
                timeStr = String.format("倒计时" + str0 + ":" + str1, minute, second);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void runFlash(final Vector2 vector2, final int flag) {
        tFlash = flashImages[flag];
        tFlash.setBounds(vector2.x, vector2.y, Assets.PLANE_BIG_SIZE, Assets.PLANE_BIG_SIZE);
        addActor(tFlash);
    }

    public void stopFlash() {
        if (tFlash != null) {
            tFlash.remove();
        }
    }

    @Override
    public void pause() {
    }

    public void return2init() {
        isPass = false;
        if (!executTime.isShutdown()) {
            executTime.shutdown();
        }
        createTimer();
        reset();
    }

    private void reset() {
        stopFlash();
        areaCtrl.clearPlane();
        planeCtrl.handler();
    }

    public void useSos() {
        isUsingHelp = true;
        isUsedHelp = true;
        multiplexer.removeProcessor(planeDetector);
        multiplexer.removeProcessor(getStage());
        reset();
    }

    public void handleNewGate(int num) {
        level = num / Assets.LEVEL_GATE_MAX;
        if (isShow()) {
            if (num != gateNum) {
                return2init();
                areaCtrl.setGateNum(num);
                areaCtrl.handler();
            } else {
                resumeTimer();
            }
        }
        gateNum = num;
    }

    public int getGateNum() {
        return gateNum;
    }

    public boolean isSuspend() {
        return isSuspend;
    }

    public GateScreen getGateScreen() {
        return gateScreen;
    }
}
