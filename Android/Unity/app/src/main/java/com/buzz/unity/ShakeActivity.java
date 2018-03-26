package com.buzz.unity;

import java.io.IOException;
import java.util.HashMap;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

import com.buzz.unity.ShakeListener.OnShakeListener;

public class ShakeActivity extends Activity {

    long lastShakeTime;
    long currentShakeTime;
    boolean gameReady;
    boolean gameOver;

    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    private RelativeLayout mTitle;
    private RelativeLayout reLayout;
    private RelativeLayout reLayoutSub;
    private LinearLayout content;
    private ImageView shakeBg;

    private SlidingDrawer mDrawer;
    private Button mDrawerBtn;
    //private SoundPool sndPool;
    //private HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();
    final static String TAG = ShakeActivity.class.getSimpleName();
    MyApplication myApp;

    Thread shakeThread;
    CARD52OPENReceiver card52OPENReceiver;
    boolean autoOpen;
    boolean isOpened;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        //drawerSet ();设置drawer监听切换按钮的方向

        card52OPENReceiver = new CARD52OPENReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CARD52_OPEN");
        registerReceiver(card52OPENReceiver, intentFilter);

        autoOpen = false;
        isOpened = false;

        myApp = (MyApplication) getApplication();

        shakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    gameReady = myApp.jsonServer.getServer().equals("01") && (myApp.jsonUser.getValue().equals(""));

                    if (lastShakeTime > 0) {
                        long shakeInterval = System.currentTimeMillis() - lastShakeTime;
                        if (shakeInterval >= 1000) {
                            gameReady = false;
                            gameOver = true;
                        }

                        if (gameOver) {
                            lastShakeTime = 0;
                            gameOver = false;
                            myApp.socketHelper.Send(String.format("05 %s,02", myApp.userId));
                            myApp.jsonServer.setServer("02");
                            new Handler(getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //myApp.sndPool.play(myApp.soundPoolMap.get(1), (float) 1, (float) 1, 0, 0, (float) 1.0);
                                    reLayoutSub.setBackgroundResource(R.drawable.cards_back);
                                    mImgDn.setVisibility(View.GONE);
                                    mImgUp.setVisibility(View.GONE);
                                    shakeBg.setVisibility(View.GONE);
                                    //Toast.makeText(getApplicationContext(), String.format("You got the '%s'...", myApp.jsonUser.getValue()), Toast.LENGTH_LONG).show();
                                }
                            }, 2000);
                        }
                    }
                }
            }
        });
        shakeThread.start();


        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
        mTitle = (RelativeLayout) findViewById(R.id.shake_title_bar);
        reLayout = (RelativeLayout) findViewById(R.id.reLayout);
        reLayoutSub = (RelativeLayout) findViewById(R.id.reLayoutSub);
        content = (LinearLayout) findViewById(R.id.content);
        shakeBg = (ImageView) findViewById(R.id.shakeBg);


        if (!myApp.jsonServer.getColor().isEmpty()) {
            mImgUp.setBackgroundColor(Color.parseColor("#" + myApp.jsonServer.getColor()));
            mImgDn.setBackgroundColor(Color.parseColor("#" + myApp.jsonServer.getColor()));
            reLayout.setBackgroundColor(Color.parseColor("#" + myApp.jsonServer.getColor()));
        }

        mDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
        mDrawerBtn = (Button) findViewById(R.id.handle);
        mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
            public void onDrawerOpened() {
                mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_down));
                TranslateAnimation titleup = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.0f);
                titleup.setDuration(200);
                titleup.setFillAfter(true);
                mTitle.startAnimation(titleup);

                if (!isOpened) {
                    if (!autoOpen) {
                        myApp.socketHelper.Send(String.format("06 %s,03", myApp.userId));
                    }

                    int resID = getResources().getIdentifier(myApp.jsonUser.getValue().toLowerCase(), "drawable", getPackageName());
                    content.setBackgroundResource(resID);
                    reLayoutSub.setBackgroundColor(Color.parseColor("#" + myApp.jsonServer.getColor()));
                    myApp.jsonServer.setUserid("");
                    myApp.jsonUser.setValue("");
                }

                isOpened = true;
            }
        });
         /* 设定SlidingDrawer被关闭的事件处理 */
        mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
            public void onDrawerClosed() {
                mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_up));
                TranslateAnimation titledn = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0f);
                titledn.setDuration(200);
                titledn.setFillAfter(false);
                mTitle.startAnimation(titledn);
            }
        });
        //loadSound();
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new OnShakeListener() {
            public void onShake() {
                if (gameReady) {
                    currentShakeTime = System.currentTimeMillis();
                    if (lastShakeTime == 0) {
                        myApp.socketHelper.Send(String.format("04 %s,01", myApp.userId));
                        lastShakeTime = currentShakeTime;
                    }

                    long interval = currentShakeTime - lastShakeTime;
                    if (interval < 500) {
                        lastShakeTime = currentShakeTime;
                        startAnim();//开始摇一摇手掌动画
                        mShakeListener.stop();
                        //myApp.sndPool.play(myApp.soundPoolMap.get(0), (float) 1, (float) 1, 0, 0, (float) 1.2);
                        //mVibrator.cancel();
                        mShakeListener.start();
                    }
                }
            }
        });
    }

    /*
    private void loadSound() {
        sndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        new Thread() {
            public void run() {
                try {
                    soundPoolMap.put(
                            0,
                            sndPool.load(getAssets().openFd(
                                    "sound/shake_sound_male.mp3"), 1));

                    soundPoolMap.put(
                            1,
                            sndPool.load(getAssets().openFd(
                                    "sound/shake_match.mp3"), 1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }*/

    public void startAnim() {   //定义摇一摇动画动画
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(1000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);
    }

    public void startVibrato() {     //定义震动
        mVibrator.vibrate(new long[]{500, 300, 500, 300}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }

    public void shake_activity_back(View v) {     //标题栏 返回按钮
        if (!isOpened) {
            myApp.socketHelper.Send(String.format("06 %s,03", myApp.userId));
            myApp.jsonServer.setUserid("");
            myApp.jsonUser.setValue("");
        }
        this.finish();
    }

    public void linshi(View v) {     //标题栏
        //startAnim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }

        unregisterReceiver(card52OPENReceiver);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            return true;//返回true，把事件消费掉，不会继续调用onBackPressed
        }
        return super.dispatchKeyEvent(event);
    }

    public class CARD52OPENReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            autoOpen = true;
            mDrawer.open();
        }
    }
}