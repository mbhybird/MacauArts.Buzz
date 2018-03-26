package com.buzz.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AlphaActivity extends Activity implements AnimationListener {

    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alpha);

        splash = (ImageView) findViewById(R.id.splash);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.setAnimationListener(this);
        splash.startAnimation(anim);
    }

    public void alpha(View view) {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(3000);
        anim.setFillAfter(true);
        splash.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        Log.i("alpha", "onAnimationStart called.");
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Log.i("alpha", "onAnimationEnd called");
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Log.i("alpha", "onAnimationRepeat called");
    }
}
