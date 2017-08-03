package com.witlife.beijinnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.witlife.beijinnews.R;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;

import java.util.List;

/**
 * Created by bruce on 1/08/2017.
 */

public class LeftMenuListAdapter extends BaseAdapter {

    List<NewsCenterPagerBean.DataEntity> data;
    Context context;
    public int prePosition = 0;

    public LeftMenuListAdapter(Context context, List<NewsCenterPagerBean.DataEntity> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);
        textView.setText(data.get(position).getTitle());

        if (prePosition == position) {
            textView.setEnabled(true);
        } else {
            textView.setEnabled(false);
        }

        return textView;
    }

}
