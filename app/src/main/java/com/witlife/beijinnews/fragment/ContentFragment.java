package com.witlife.beijinnews.fragment;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.witlife.beijinnews.activity.MainActivity;
import com.witlife.beijinnews.adapter.ContentPagerAdapter;
import com.witlife.beijinnews.base.BaseFragment;
import com.witlife.beijinnews.R;
import com.witlife.beijinnews.base.BasePager;
import com.witlife.beijinnews.pager.GovaPager;
import com.witlife.beijinnews.pager.HomePager;
import com.witlife.beijinnews.pager.NewsPager;
import com.witlife.beijinnews.pager.SettingPager;
import com.witlife.beijinnews.pager.SmartPager;
import com.witlife.beijinnews.view.NoScrollViewPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by bruce on 31/07/2017.
 */

public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.viewPager)
    private NoScrollViewPager viewPager;

    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    private List<BasePager> basePagers;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.content_fragment, null);

        x.view().inject(ContentFragment.this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();

        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));
        basePagers.add(new NewsPager(context));
        basePagers.add(new SmartPager(context));
        basePagers.add(new GovaPager(context));
        basePagers.add(new SettingPager(context));

        viewPager.setAdapter(new ContentPagerAdapter(basePagers));

        radioGroup.setOnCheckedChangeListener(new RGOnCheckChangeListener());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                basePagers.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radioGroup.check(R.id.rb_home);
        basePagers.get(0).initData();
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
    }

    public NewsPager getNewsPager() {
        return (NewsPager) basePagers.get(1);
    }

    class RGOnCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            switch (checkedId){
                case R.id.rb_home:
                    viewPager.setCurrentItem(0, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_news:
                    viewPager.setCurrentItem(1, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smart:
                    viewPager.setCurrentItem(2, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_gov:
                    viewPager.setCurrentItem(3, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    viewPager.setCurrentItem(4, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    private void isEnableSlidingMenu(int type) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(type);
    }


}
