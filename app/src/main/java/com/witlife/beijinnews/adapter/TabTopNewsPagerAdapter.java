package com.witlife.beijinnews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.witlife.beijinnews.R;
import com.witlife.beijinnews.bean.TabDetailPagerBean;
import com.witlife.beijinnews.util.Contants;
import com.witlife.beijinnews.util.DensityUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by bruce on 2/08/2017.
 */

public class TabTopNewsPagerAdapter extends PagerAdapter{

    private List<TabDetailPagerBean.DataEntity.TopnewsData> topNews;
    private Context context;

    public TabTopNewsPagerAdapter(Context context, List<TabDetailPagerBean.DataEntity.TopnewsData> topNews) {
        this.topNews = topNews;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setBackgroundResource(R.drawable.home_scroll_default);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);

        TabDetailPagerBean.DataEntity.TopnewsData topnewsData = topNews.get(position);
        String imageUrl = Contants.BASE_URL + topnewsData.getTopimage();

        x.image().bind(imageView, imageUrl);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return topNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== object;
    }
}
