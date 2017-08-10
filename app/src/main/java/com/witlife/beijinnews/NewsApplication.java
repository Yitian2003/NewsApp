package com.witlife.beijinnews;

import android.app.Application;

import org.xutils.x;

/**
 * Created by bruce on 31/07/2017.
 */

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }
}
