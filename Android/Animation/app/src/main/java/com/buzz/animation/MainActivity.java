package com.buzz.animation;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.nineoldandroids.animation.*;
import com.nineoldandroids.view.ViewPropertyAnimator;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnRotate = (Button) findViewById(R.id.btnRotate);
        final Button btnAlpha = (Button) findViewById(R.id.btnAlpha);
        final Button btnTranslate = (Button) findViewById(R.id.btnTranslate);
        final Button btnScale = (Button) findViewById(R.id.btnScale);

        ValueAnimator colorAnim = ObjectAnimator.ofInt(findViewById(R.id.relLayout), "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
        colorAnim.setDuration(2000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();


        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(btnRotate, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(btnRotate, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(btnRotate, "rotation", 0, -90),
                ObjectAnimator.ofFloat(btnRotate, "translationX", 0, 90),
                ObjectAnimator.ofFloat(btnRotate, "translationY", 0, 90),
                ObjectAnimator.ofFloat(btnRotate, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(btnRotate, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(btnRotate, "alpha", 1, 0.25f, 1)
        );
        set.setDuration(5 * 1000).start();



        /*
        ViewPropertyAnimator.animate(btnRotate).setDuration(3000).alpha(50).rotationYBy(780).x(10).y(100).setInterpolator(new BounceInterpolator());
        */
        ViewPropertyAnimator.animate(btnAlpha).setDuration(3000).rotationYBy(780).x(30).y(200).setInterpolator(new OvershootInterpolator());
        ViewPropertyAnimator.animate(btnTranslate).setDuration(3000).rotationYBy(780).x(50).y(300).setInterpolator(new LinearInterpolator());
        ViewPropertyAnimator.animate(btnScale).setDuration(3000).rotationYBy(780).x(70).y(400).setInterpolator(new AccelerateInterpolator());



        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RotateActivity.class));
            }
        });

        btnAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AlphaActivity.class));
            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TranslateActivity.class));
            }
        });

        btnScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScaleActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
