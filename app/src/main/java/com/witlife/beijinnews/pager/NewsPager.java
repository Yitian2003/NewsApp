package com.witlife.beijinnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.witlife.beijinnews.activity.MainActivity;
import com.witlife.beijinnews.base.BasePager;
import com.witlife.beijinnews.base.DetailBasePager;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.fragment.LeftMenuFragment;
import com.witlife.beijinnews.pager.details.InteractDetailPager;
import com.witlife.beijinnews.pager.details.NewsDetailPager;
import com.witlife.beijinnews.pager.details.PhotosDetailPager;
import com.witlife.beijinnews.pager.details.TopicDetailPager;
import com.witlife.beijinnews.util.CacheUtils;
import com.witlife.beijinnews.util.Contants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
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
    private long startTime;

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

        startTime = SystemClock.uptimeMillis();

        getDataFromInternet();


        //getDataFromInternetByVolley();

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity)context;
                main.getSlidingMenu().toggle();
            }
        });


    }

    private void getDataFromInternetByVolley() {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, Contants.NEWS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CacheUtils.putString(context, Contants.NEWS_URL, response);
                processData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String parsed = new String(response.data, "UTF-8");
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    private void getDataFromInternet() {

        RequestParams params = new RequestParams(Contants.NEWS_URL);
        
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                long endTime = SystemClock.uptimeMillis();
                long duration = endTime =startTime;

                Log.i("xutil request time", duration + "");
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
        detailBasePagers.add(new TopicDetailPager(context, data.get(0)));
        detailBasePagers.add(new PhotosDetailPager(context, data.get(2)));
        detailBasePagers.add(new InteractDetailPager(context, data.get(2)));

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
        final DetailBasePager detailPager = detailBasePagers.get(position);
        detailPager.initData();
        fl_content.addView(detailPager.rootView);

        if (position == 2){
            ib_switch_list.setVisibility(View.VISIBLE);

            ib_switch_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotosDetailPager photosDetailPager = (PhotosDetailPager) detailBasePagers.get(2);
                    photosDetailPager.switchListAndGrid(ib_switch_list);
                }
            });
        } else {
            ib_switch_list.setVisibility(View.GONE);
        }

        if (position == 3){
            ib_switch_list.setVisibility(View.VISIBLE);

            ib_switch_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InteractDetailPager interactDetailPager = (InteractDetailPager) detailBasePagers.get(3);
                    interactDetailPager.switchListAndGrid(ib_switch_list);
                }
            });
        } else {
            ib_switch_list.setVisibility(View.GONE);
        }
    }
}
