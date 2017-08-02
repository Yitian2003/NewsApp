package com.witlife.beijinnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.witlife.beijinnews.base.BasePager;

/**
 * Created by bruce on 1/08/2017.
 */

public class SmartPager extends BasePager {

    public SmartPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        tvTitle.setText("Smart");

        TextView textView = new TextView(context);
        textView.setText("Smart Content");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);
    }
}
