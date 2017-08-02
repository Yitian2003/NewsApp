package com.witlife.beijinnews.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.witlife.beijinnews.R;
import com.witlife.beijinnews.fragment.ContentFragment;
import com.witlife.beijinnews.fragment.LeftMenuFragment;
import com.witlife.beijinnews.util.DensityUtil;

public class MainActivity extends SlidingFragmentActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFT_MENU_TAG = "left_menu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSlidingMenu();

        initFragment();

    }

    private void initSlidingMenu() {
        setBehindContentView(R.layout.activity_leftmenu);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);

        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 200));
    }

    private void initFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, new ContentFragment(), MAIN_CONTENT_TAG);
        fragmentTransaction.replace(R.id.left_menu, new LeftMenuFragment(), LEFT_MENU_TAG);

        fragmentTransaction.commit();

    }

    public LeftMenuFragment getLeftMenuFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        return (LeftMenuFragment)fragmentManager.findFragmentByTag(LEFT_MENU_TAG);
    }

    public ContentFragment getContentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (ContentFragment)fragmentManager.findFragmentByTag(MAIN_CONTENT_TAG);
    }
}
