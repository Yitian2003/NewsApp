package com.witlife.beijinnews.pager.details.tab;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.witlife.beijinnews.R;
import com.witlife.beijinnews.adapter.TabTopNewsListAdpter;
import com.witlife.beijinnews.adapter.TabTopNewsPagerAdapter;
import com.witlife.beijinnews.base.DetailBasePager;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.bean.TabDetailPagerBean;
import com.witlife.beijinnews.util.CacheUtils;
import com.witlife.beijinnews.util.Contants;
import com.witlife.beijinnews.util.DensityUtil;
import com.witlife.beijinnews.view.HorizontalScrollViewPager;
import com.witlife.beijinnews.view.RefreshListView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruce on 2/08/2017.
 */

public class TabTopicDetailPager extends DetailBasePager {

    private HorizontalScrollViewPager viewPager;
    private TextView tvTitle;
    private LinearLayout linarLayout;
    private RefreshListView listview;
    private int prePosition = 0;

    private Handler handler;
    private String url;
    private String moreUrl;
    private boolean isLoadMore = false;

    private NewsCenterPagerBean.DataEntity.ChildrenData childrenData;
    private List<TabDetailPagerBean.DataEntity.TopnewsData> topNews;
    private List<TabDetailPagerBean.DataEntity.NewsData> listNews;
    private TabTopNewsListAdpter listAdpter;

    private OkHttpClient client;

    public TabTopicDetailPager(Context context, NewsCenterPagerBean.DataEntity.ChildrenData childrenData) {
        super(context);

        this.childrenData = childrenData;


    }

    public NewsCenterPagerBean.DataEntity.ChildrenData getChildrenData() {
        return childrenData;
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.tab_topic_detail_pager, null);
        listview = (RefreshListView) view.findViewById(R.id.listview);

        View topNewsHeaderView = View.inflate(context, R.layout.tab_detail_list_header, null);
        viewPager = (HorizontalScrollViewPager) topNewsHeaderView.findViewById(R.id.viewPager);
        tvTitle = (TextView) topNewsHeaderView.findViewById(R.id.tvTitle);
        linarLayout = (LinearLayout) topNewsHeaderView.findViewById(R.id.linarLayout);

        //listview.addHeaderView(topNewsHeaderView);
        listview.addTopNewsView(topNewsHeaderView);

        listview.setOnRefreshListener(new MyOnRefreshListener());

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());

        url = Contants.BASE_URL + childrenData.getUrl();

        String savedJson = CacheUtils.getString(context, url);

        if (!TextUtils.isEmpty(savedJson)){
            processData(savedJson);
        }
        getDataFromInternet(url);
    }

    private void processData(String json) {

        TabDetailPagerBean bean = parsedJson(json);

        moreUrl = "";
        if(!TextUtils.isEmpty(bean.getData().getMore())) {
            moreUrl = Contants.BASE_URL + bean.getData().getMore();
        }

        if(!isLoadMore) {

            topNews = bean.getData().getTopnews();

            viewPager.setAdapter(new TabTopNewsPagerAdapter(context, topNews));

            addPoints();

            tvTitle.setText(topNews.get(0).getTitle());

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tvTitle.setText(topNews.get(position).getTitle());
                    linarLayout.getChildAt(prePosition).setEnabled(false);
                    linarLayout.getChildAt(position).setEnabled(true);

                    prePosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            listNews = bean.getData().getNews();
            listAdpter = new TabTopNewsListAdpter(context, listNews);
            listview.setAdapter(listAdpter);
        } else {

            isLoadMore = false;

            listNews.addAll(bean.getData().getNews());
            listAdpter.notifyDataSetChanged();
        }
    }

    private void addPoints() {
        linarLayout.removeAllViews();

        for (int i = 0; i <topNews.size(); i++){
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 10),DensityUtil.dip2px(context, 10));

            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(context, 8);
            }

            imageView.setLayoutParams(params);

            linarLayout.addView(imageView);
        }
    }

    private TabDetailPagerBean parsedJson(String json) {

        Gson gson = new Gson();
        return gson.fromJson(json, TabDetailPagerBean.class);
    }

    private void getDataFromInternet(final String url) {

        client.setReadTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        client.setConnectTimeout(10, TimeUnit.SECONDS);

        Request request = new Request.Builder()
                .url(url)
                .build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //Log.i("TabNewsDetailPager--Failure", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    CacheUtils.putString(context, url, result);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            processData(result);
                            listview.onRefreshFinish(true);
                        }
                    });

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listview.onRefreshFinish(false);
                        }
                    });
                    //Log.i("TabNewsDetailPager--error", " "+ response.code());
                }
            }
        });
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener{
        @Override
        public void onPullDownRefresh() {
            getDataFromInternet(url);
        }

        @Override
        public void onLoadMore() {
            if(TextUtils.isEmpty(moreUrl)){
                Toast.makeText(context, "No more data", Toast.LENGTH_LONG).show();
                listview.onRefreshFinish(false);
            } else {
                getMoreDatatFromInternet();
            }

        }
    }

    private void getMoreDatatFromInternet() {

        client.setReadTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);
        client.setConnectTimeout(10, TimeUnit.SECONDS);

        Request request = new Request.Builder()
                .url(moreUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listview.onRefreshFinish(false);
                            isLoadMore = true;

                            processData(result);
                        }
                    });

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listview.onRefreshFinish(false);
                        }
                    });
                    //Log.i("TabNewsDetailPager--error", " "+ response.code());
                }
            }
        });
    }

}
