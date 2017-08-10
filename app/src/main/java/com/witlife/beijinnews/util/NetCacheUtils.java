package com.witlife.beijinnews.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by bruce on 6/08/2017.
 */

public class NetCacheUtils {

    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    private final LocalCacheUtils localCacheUtils;
    private Handler handler;
    private ExecutorService service;

    public NetCacheUtils(Handler handler, LocalCacheUtils localCacheUtils) {
        this.handler = handler;
        service = Executors.newFixedThreadPool(10);
        this.localCacheUtils = localCacheUtils;
    }

    public void getBitmapFromInterNet(final String imageUrl, final int position) {


        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(4000);
                    connection.setReadTimeout(4000);
                    connection.connect();
                    int code = connection.getResponseCode();

                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);

                        Message message = Message.obtain();
                        message.what = SUCCESS;
                        message.arg1 = position;
                        message.obj = bitmap;
                        handler.sendMessage(message);

                        localCacheUtils.putBitmap(imageUrl, bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = FAIL;
                    message.arg1 = position;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
