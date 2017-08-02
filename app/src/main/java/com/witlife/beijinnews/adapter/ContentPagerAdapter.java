package com.witlife.beijinnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.witlife.beijinnews.base.BasePager;

import java.util.List;

/**
 * Created by bruce on 1/08/2017.
 */

public class ContentPagerAdapter extends PagerAdapter {

    private List<BasePager> basePagers;

    public ContentPagerAdapter(List<BasePager> basePagers) {
        this.basePagers = basePagers;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        BasePager basePager = basePagers.get(position);
        View view = basePager.rootView;
        container.addView(view);
        //basePager.initData();
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
