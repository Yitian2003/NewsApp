package com.witlife.beijinnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.witlife.beijinnews.activity.GuideActivity;
import com.witlife.beijinnews.activity.MainActivity;
import com.witlife.beijinnews.util.CacheUtils;

public class SplashActivity extends AppCompatActivity {

    public static final String START_MAIN = "start_main";
    private RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlSplash = (RelativeLayout) findViewById(R.id.rlSplash);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);

        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);
        set.setDuration(2000);

        rlSplash.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());
    }

    public class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);

            if (isStartMain) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);

            }
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}