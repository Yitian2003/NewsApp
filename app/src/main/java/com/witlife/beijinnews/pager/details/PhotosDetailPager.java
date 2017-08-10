package com.witlife.beijinnews.pager.details;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.witlife.beijinnews.R;
import com.witlife.beijinnews.adapter.PhotosDetailListAdapter;
import com.witlife.beijinnews.base.DetailBasePager;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.bean.PhotosMenuDetailPagerBean;
import com.witlife.beijinnews.util.CacheUtils;
import com.witlife.beijinnews.util.Contants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by bruce on 1/08/2017.
 */

public class PhotosDetailPager extends DetailBasePager {

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
    //private List<NewsCenterPagerBean> bean;

    public PhotosDetailPager(Context context, NewsCenterPagerBean.DataEntity detailPagerData) {
        super(context);
        this.detailPagerData = detailPagerData;
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

        adapter = new PhotosDetailListAdapter(context, bean.getData().getNews());
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