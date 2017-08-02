package com.witlife.beijinnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.witlife.beijinnews.base.BasePager;

/**
 * Created by bruce on 1/08/2017.
 */

public class SettingPager extends BasePager {

    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        tvTitle.setText("Setting");

        TextView textView = new TextView(context);
        textView.setText("Setting Content");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.WHITE);

        fl_content.addView(textView);
    }
}
