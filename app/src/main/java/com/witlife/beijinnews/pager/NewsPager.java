package com.witlife.beijinnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.witlife.beijinnews.activity.MainActivity;
import com.witlife.beijinnews.base.BasePager;
import com.witlife.beijinnews.base.DetailBasePager;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.fragment.LeftMenuFragment;
import com.witlife.beijinnews.util.CacheUtils;
import com.witlife.beijinnews.util.Contants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 1/08/2017.
 */

public class NewsPager extends BasePager{

    private List<NewsCenterPagerBean.DataEntity> data;
    private List<DetailBasePager> detailBasePagers;

    public NewsPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        tvTitle.setText("News");
        ib_menu.setVisibility(View.VISIBLE);

        TextView textView = new TextView(context);
        textView.setText("News Content");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);

        fl_content.addView(textView);

        String savedJson = CacheUtils.getString(context, Contants.NEWS_URL);

        if (!TextUtils.isEmpty(savedJson)) {
            processData(savedJson);
        }
        
        getDataFromInternet();

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity)context;
                main.getSlidingMenu().toggle();
            }
        });
    }

    private void getDataFromInternet() {

        RequestParams params = new RequestParams(Contants.NEWS_URL);
        
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context, Contants.NEWS_URL, result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("Error--", ex.getMessage());
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

        NewsCenterPagerBean newsBean = parseJson(result);

        String title = newsBean.getData().get(0).getChildren().get(1).getTitle();

        data = newsBean.getData();

        MainActivity mainActivity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();

        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsDetailPager(context, data.get(0)));
        detailBasePagers.add(new TopicDetailPager(context));
        detailBasePagers.add(new PhotosDetailPager(context));
        detailBasePagers.add(new InteractDetailPager(context));

        leftMenuFragment.setData(data);


    }

    private NewsCenterPagerBean parseJson(String result) {

        Gson gson = new Gson();
        NewsCenterPagerBean bean = gson.fromJson(result, NewsCenterPagerBean.class);
        return bean;
    }

    public void switchPager(int position) {
        tvTitle.setText(data.get(position).getTitle());

        fl_content.removeAllViews();
        DetailBasePager detailPager = detailBasePagers.get(position);
        detailPager.initData();
        fl_content.addView(detailPager.rootView);
    }
}
