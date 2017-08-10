package com.witlife.beijinnews.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * Created by bruce on 6/08/2017.
 */

public class BitmapCacheUtils {

    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;

    public BitmapCacheUtils(Context context, Handler handler) {
        localCacheUtils = new LocalCacheUtils(context);
        netCacheUtils = new NetCacheUtils(handler, localCacheUtils);

    }

    public Bitmap getBitmap(String imageUrl, int position) {

        if (localCacheUtils != null) {
           Bitmap bitmap = localCacheUtils.getBitmapFromUrl(imageUrl);
            if (bitmap != null){
                Log.i("local cache success  ", position+"");
                return bitmap;
            }
        }
        netCacheUtils.getBitmapFromInterNet(imageUrl, position);
        return null;
    }
}
