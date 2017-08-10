package com.witlife.beijinnews.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by bruce on 1/08/2017.
 */

public abstract class DetailBasePager {

    public final Context context;
    public View rootView;

    public DetailBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public abstract View initView();

    public void initData(){

    }

}
