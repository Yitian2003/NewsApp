package com.witlife.beijinnews.pager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.witlife.beijinnews.R;
import com.witlife.beijinnews.activity.MainActivity;
import com.witlife.beijinnews.adapter.NewsDetailPagerAdpter;
import com.witlife.beijinnews.base.DetailBasePager;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 1/08/2017.
 */

public class NewsDetailPager extends DetailBasePager {

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    @ViewInject(R.id.tab_layout)
    private TabLayout tabLayout;

    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;

    private List<NewsCenterPagerBean.DataEntity.ChildrenData> children;
    private List<TabDetailPager> tabDetailPagers;

    public NewsDetailPager(Context context, NewsCenterPagerBean.DataEntity dataEntity) {
        super(context);

        children = dataEntity.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.news_detail_pager, null);
        x.view().inject(NewsDetailPager.this, view);

        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               if (position == 0) {
                    ((MainActivity) context).getSlidingMenu().setSlidingEnabled(true);
                } else {
                    ((MainActivity) context).getSlidingMenu().setSlidingEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        tabDetailPagers = new ArrayList<>();
        for (int i = 0; i< children.size(); i++){
            tabDetailPagers.add(new TabDetailPager(context, children.get(i)));
        }

        viewPager.setAdapter(new NewsDetailPagerAdpter(tabDetailPagers));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}