package com.buzz.unity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.buzz.unity.ShakeListener.OnShakeListener;

public class SlotActivity extends Activity {

    long lastShakeTime;
    long currentShakeTime;
    boolean gameReady;
    boolean gameOver;

    ShakeListener mShakeListener = null;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    final static String TAG = SlotActivity.class.getSimpleName();
    MyApplication myApp;

    Thread shakeThread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);
        myApp = (MyApplication) getApplication();

        shakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    gameReady = myApp.jsonServer.getServer().equals("01");

                    if (lastShakeTime > 0) {
                        long shakeInterval = System.currentTimeMillis() - lastShakeTime;
                        if (shakeInterval >= 1000) {
                            gameReady = false;
                            gameOver = true;
                        }

                        if (gameOver) {
                            lastShakeTime = 0;
                            gameOver = false;
                            gameOverAction();
                        }
                    }

                    if (!myApp.rangeIn) {
                        if (!myApp.back) {
                            gameOverAction();
                            myApp.back = true;
                        }
                    }
                }
            }
        });

        shakeThread.start();

        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp_slot);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown_slot);

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
                        mShakeListener.start();
                    }
                }
            }
        });
    }

    private void gameOverAction() {
        myApp.socketHelper.Send(String.format("05 %s,02", myApp.userId));
        myApp.jsonServer.setServer("02");
        myApp.jsonServer.setUserid("");
        myApp.jsonUser.setValue("");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        });
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            return true;//返回true，把事件消费掉，不会继续调用onBackPressed
        }
        return super.dispatchKeyEvent(event);
    }
}