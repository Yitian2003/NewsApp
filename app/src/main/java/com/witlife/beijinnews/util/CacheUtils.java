package com.witlife.beijinnews.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bruce on 31/07/2017.
 */

public class CacheUtils {

    public static final String WITLIFE_NEWS = "witlifeNews";

    public static boolean getBoolean(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(WITLIFE_NEWS, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(WITLIFE_NEWS, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(WITLIFE_NEWS, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(WITLIFE_NEWS, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
}
