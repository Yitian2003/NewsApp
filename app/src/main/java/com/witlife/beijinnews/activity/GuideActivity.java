package com.witlife.beijinnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.witlife.beijinnews.R;
import com.witlife.beijinnews.SplashActivity;
import com.witlife.beijinnews.adapter.GuideViewPagerAdapter;
import com.witlife.beijinnews.util.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button startBtn;
    private GuideViewPagerAdapter adapter;
    private LinearLayout indicatorLayout;
    private ImageView ivRedpoint;

    private List<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        startBtn = (Button) findViewById(R.id.startBtn);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicatorLayout);
        ivRedpoint = (ImageView) findViewById(R.id.ivRedpoint);

        initData();

        setupViewPager();

        setListener();
    }

    private void setListener(){
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN, true);

                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void setupViewPager(){
        adapter = new GuideViewPagerAdapter(imageViews);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == imageViews.size() - 1) {
                    startBtn.setVisibility(View.VISIBLE);
                } else {
                    startBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData(){

        int[] ids = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3,
        };

        imageViews = new ArrayList<>();

        for (int i = 0; i < ids.length; i++){
            ImageView imageView = new ImageView(this);

            imageView.setImageResource(ids[i]);
            imageViews.add(imageView);

            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);

            if(i != 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);

            indicatorLayout.addView(point);
        }
    }

}
