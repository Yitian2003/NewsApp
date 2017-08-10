package com.witlife.beijinnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.witlife.beijinnews.R;

/**
 * Created by bruce on 1/08/2017.
 */

public class BasePager {

    public final Context context;
    public View rootView;
    public TextView tvTitle;
    public ImageButton ib_menu;
    public FrameLayout fl_content;

    public ImageButton ib_switch_list;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    private View initView(){
        View view = View.inflate(context, R.layout.base_pager, null);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        ib_switch_list = (ImageButton)view.findViewById(R.id.ib_switch_list);

        return view;
    }

    public void initData(){

    }

}
