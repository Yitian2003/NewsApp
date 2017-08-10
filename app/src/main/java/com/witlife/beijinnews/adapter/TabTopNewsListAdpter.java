package com.witlife.beijinnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.witlife.beijinnews.R;
import com.witlife.beijinnews.base.BasePager;
import com.witlife.beijinnews.bean.TabDetailPagerBean;
import com.witlife.beijinnews.util.CacheUtils;
import com.witlife.beijinnews.util.Contants;
import com.witlife.beijinnews.util.DensityUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import static com.witlife.beijinnews.pager.details.tab.TabNewsDetailPager.READ_ARRAY_ID;

/**
 * Created by bruce on 2/08/2017.
 */

public class TabTopNewsListAdpter extends BaseAdapter {

    private List<TabDetailPagerBean.DataEntity.NewsData> listNews;
    private Context context;
    private TextView tvTitle;
    private TextView tvTime;
    private ImageView imageView;
    private ImageOptions imageOptions;
    private Handler internalHandler;

    public TabTopNewsListAdpter(Context context, List<TabDetailPagerBean.DataEntity.NewsData> listNews) {
        this(context, listNews, null);
    }

    public TabTopNewsListAdpter(Context context, List<TabDetailPagerBean.DataEntity.NewsData> listNews, Handler handler) {
        this.listNews = listNews;
        this.context = context;
        internalHandler = handler;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(context, 110), DensityUtil.dip2px(context, 100))
                .setRadius(DensityUtil.dip2px(context, 5))
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_listview, null);
        }
        imageView = (ImageView) convertView.findViewById(R.id.imageview);
        imageView.setBackgroundResource(R.drawable.home_scroll_default);
        tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        TabDetailPagerBean.DataEntity.NewsData newsData = listNews.get(position);
        String imageUrl = Contants.BASE_URL + newsData.getListimage();
        x.image().bind(imageView, imageUrl, imageOptions);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch  (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        internalHandler.removeCallbacksAndMessages(null);
                        break;

                    case MotionEvent.ACTION_UP:
                        internalHandler.removeCallbacksAndMessages(null);
                        internalHandler.sendEmptyMessageDelayed(0, 2000);
                        break;
                };
                return true; //only touch event
            }
        });

        tvTitle.setText(newsData.getTitle());
        tvTime.setText(newsData.getPubdate());

        String idArray = CacheUtils.getString(context, READ_ARRAY_ID);

        if (idArray.contains(newsData.getId()+ "")){
            tvTitle.setTextColor(Color.LTGRAY);
        } else tvTitle.setTextColor(Color.BLACK);

        return convertView;
    }
}
