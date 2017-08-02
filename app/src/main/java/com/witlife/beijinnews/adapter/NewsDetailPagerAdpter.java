package com.witlife.beijinnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.witlife.beijinnews.pager.details.tab.TabNewsDetailPager;

import java.util.List;

/**
 * Created by bruce on 2/08/2017.
 */

public class NewsDetailPagerAdpter extends PagerAdapter{

    private List<TabNewsDetailPager> tabNewsDetailPagers;

    public NewsDetailPagerAdpter(List<TabNewsDetailPager> tabNewsDetailPagers) {
       this.tabNewsDetailPagers = tabNewsDetailPagers;
    }

    @Override
    public int getCount() {
        return tabNewsDetailPagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = tabNewsDetailPagers.get(position).rootView;
        tabNewsDetailPagers.get(position).initData();
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
        return tabNewsDetailPagers.get(position).getChildrenData().getTitle();
    }
}
