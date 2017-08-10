package com.witlife.beijinnews.pager.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.witlife.beijinnews.R;
import com.witlife.beijinnews.adapter.PhotosDetailListAdapter;
import com.witlife.beijinnews.base.DetailBasePager;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.bean.PhotosMenuDetailPagerBean;
import com.witlife.beijinnews.util.BitmapCacheUtils;
import com.witlife.beijinnews.util.CacheUtils;
import com.witlife.beijinnews.util.Contants;
import com.witlife.beijinnews.util.NetCacheUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by bruce on 1/08/2017.
 */

public class InteractDetailPager extends DetailBasePager {

    private final NewsCenterPagerBean.DataEntity detailPagerData;
    @ViewInject(R.id.listview)
    private ListView listView;
    @ViewInject(R.id.gridview)
    private GridView gridView;

    PhotosDetailListAdapter adapter;
    PhotosMenuDetailPagerBean bean;

    private String url;
    private int prePosition;
    private boolean isListView = true;
    private BitmapCacheUtils bitmapCacheUtils;
    //private List<NewsCenterPagerBean> bean;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case NetCacheUtils.SUCCESS:

                    int position = msg.arg1;
                    Bitmap bitmap = (Bitmap) msg.obj;

                    if (listView.isShown()){
                        ImageView iv = (ImageView) listView.findViewWithTag(position);
                        if (bitmap != null && iv != null) {
                            iv.setImageBitmap(bitmap);
                        }
                    }

                    if (gridView.isShown()){
                        ImageView iv = (ImageView) gridView.findViewWithTag(position);
                        if (bitmap != null && iv != null) {
                            iv.setImageBitmap(bitmap);
                        }
                    }
                    break;
                case NetCacheUtils.FAIL:
                    break;
            }
        }
    };

    public InteractDetailPager(Context context, NewsCenterPagerBean.DataEntity detailPagerData) {
        super(context);
        this.detailPagerData = detailPagerData;
        bitmapCacheUtils = new BitmapCacheUtils(context, handler);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.photos_detail_pager, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Contants.BASE_URL + detailPagerData.getUrl();

        String savedJson = CacheUtils.getString(context, url);

        if (!TextUtils.isEmpty(savedJson)){
            processData(savedJson);
        }
        getDataFromInternet(url);

    }

    private void getDataFromInternet(final String url) {

        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context, url, result);

                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {

        bean = new Gson().fromJson(result, PhotosMenuDetailPagerBean.class);

        adapter = new PhotosDetailListAdapter(context, bean.getData().getNews(), bitmapCacheUtils);
        listView.setAdapter(adapter);
    }

    public void switchListAndGrid(ImageButton imageButton){
        if (isListView) {
            isListView = false;
            gridView.setVisibility(View.VISIBLE);
            adapter = new PhotosDetailListAdapter(context, bean.getData().getNews());
            gridView.setAdapter(adapter);
            listView.setVisibility(View.GONE);
            imageButton.setImageResource(R.drawable.icon_pic_list_type);

        } else {
            isListView = true;
            listView.setVisibility(View.VISIBLE);
            adapter = new PhotosDetailListAdapter(context, bean.getData().getNews());
            listView.setAdapter(adapter);
            gridView.setVisibility(View.GONE);
            imageButton.setImageResource(R.drawable.icon_pic_grid_type );
        }

    }
}