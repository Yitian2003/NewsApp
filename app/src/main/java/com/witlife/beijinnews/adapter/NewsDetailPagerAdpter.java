package com.witlife.beijinnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.pager.TabDetailPager;

import java.util.List;

/**
 * Created by bruce on 2/08/2017.
 */

public class NewsDetailPagerAdpter extends PagerAdapter{

    private List<TabDetailPager> tabDetailPagers;

    public NewsDetailPagerAdpter(List<TabDetailPager> tabDetailPagers) {
       this.tabDetailPagers = tabDetailPagers;
    }

    @Override
    public int getCount() {
        return tabDetailPagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = tabDetailPagers.get(position).rootView;
        tabDetailPagers.get(position).initData();
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabDetailPagers.get(position).getChildrenData().getTitle();
    }
}
