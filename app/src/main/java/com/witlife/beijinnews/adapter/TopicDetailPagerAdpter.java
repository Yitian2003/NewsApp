package com.witlife.beijinnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.witlife.beijinnews.pager.details.tab.TabTopicDetailPager;

import java.util.List;

/**
 * Created by bruce on 2/08/2017.
 */

public class TopicDetailPagerAdpter extends PagerAdapter{

    private List<TabTopicDetailPager> tabTopicDetailPagers;

    public TopicDetailPagerAdpter(List<TabTopicDetailPager> tabNewsDetailPagers) {
       this.tabTopicDetailPagers = tabNewsDetailPagers;
    }

    @Override
    public int getCount() {
        return tabTopicDetailPagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = tabTopicDetailPagers.get(position).rootView;
        tabTopicDetailPagers.get(position).initData();
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
        return tabTopicDetailPagers.get(position).getChildrenData().getTitle();
    }
}
