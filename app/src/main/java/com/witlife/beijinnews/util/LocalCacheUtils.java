package com.witlife.beijinnews.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bruce on 6/08/2017.
 */

class LocalCacheUtils {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Activity activity;

    public LocalCacheUtils(Context context) {
        activity = (Activity)context;
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        String fileName = null;
        try {
            fileName = MD5Encoder.encode(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //File file = new File(Environment.getExternalStorageDirectory() + "/beijinNews", fileName);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/beijinNews", fileName);
        Log.i("getfilepath:", file.toString());

        if (file.exists()){
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Log.i("read success!!", file.toString());
            return bitmap;
        }
       return null;
    }

    public void putBitmap(String imageUrl, Bitmap bitmap) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            verifyStoragePermissions(activity);

            String fileName = null;
            try {
                fileName = MD5Encoder.encode(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //File file = new File(Environment.getExternalStorageDirectory() + "/beijinNews", fileName);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/beijinNews", fileName);
            Log.i("file:", file.toString());

            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            Log.i("parentFile", parentFile.toString());

            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Log.i("File create success", file.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
