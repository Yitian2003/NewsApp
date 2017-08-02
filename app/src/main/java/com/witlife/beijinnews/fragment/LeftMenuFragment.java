package com.witlife.beijinnews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.witlife.beijinnews.activity.MainActivity;
import com.witlife.beijinnews.adapter.LeftMenuListAdapter;
import com.witlife.beijinnews.base.BaseFragment;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.util.DensityUtil;

import java.util.List;

/**
 * Created by bruce on 31/07/2017.
 */

public class LeftMenuFragment extends BaseFragment{

    private List<NewsCenterPagerBean.DataEntity> data;
    private ListView listview;
    private LeftMenuListAdapter adapter;


    @Override
    public View initView() {
        listview = new ListView(context);
        listview.setPadding(0, DensityUtil.dip2px(context, 40), 0, 0);
        listview.setDividerHeight(0);
        listview.setCacheColorHint(Color.TRANSPARENT);
        listview.setSelector(android.R.color.transparent);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.prePosition = position;
                adapter.notifyDataSetChanged();

                MainActivity main = (MainActivity)context;
                main.getSlidingMenu().toggle();

                main.getContentFragment().getNewsPager().switchPager(adapter.prePosition);
            }
        });
        return listview;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void setData(List<NewsCenterPagerBean.DataEntity> data) {
        this.data = data;

        adapter = new LeftMenuListAdapter(context, data);
        listview.setAdapter(adapter);

        MainActivity main = (MainActivity)context;
        main.getContentFragment().getNewsPager().switchPager(adapter.prePosition);
    }
}
