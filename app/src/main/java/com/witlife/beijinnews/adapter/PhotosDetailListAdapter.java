package com.witlife.beijinnews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.witlife.beijinnews.R;
import com.witlife.beijinnews.bean.NewsCenterPagerBean;
import com.witlife.beijinnews.bean.PhotosMenuDetailPagerBean;
import com.witlife.beijinnews.util.BitmapCacheUtils;
import com.witlife.beijinnews.util.Contants;

import org.xutils.x;

import java.util.List;

/**
 * Created by bruce on 6/08/2017.
 */

public class PhotosDetailListAdapter extends BaseAdapter {

    private final List<PhotosMenuDetailPagerBean.DataEntity.NewsEntity> data;
    private Context context;
    private ImageView imageview;
    private TextView tvTitle;
    private BitmapCacheUtils bitmapCacheUtils;

    public PhotosDetailListAdapter(Context context, List<PhotosMenuDetailPagerBean.DataEntity.NewsEntity> data) {
        this(context, data, null);
    }

    public PhotosDetailListAdapter(Context context, List<PhotosMenuDetailPagerBean.DataEntity.NewsEntity> data, BitmapCacheUtils bitmapCacheUtils) {
        this.context = context;
        this.data = data;
        this.bitmapCacheUtils = bitmapCacheUtils;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PhotosMenuDetailPagerBean.DataEntity.NewsEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_photos_detail_list, null);
            tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            imageview = (ImageView) convertView.findViewById(R.id.imageview);

            tvTitle.setText(data.get(position).getTitle());
            String imageUrl = Contants.BASE_URL + data.get(position).getSmallimage();
            //x.image().bind(imageview, Contants.BASE_URL + data.get(position).getSmallimage());

            imageview.setTag(position);
            Bitmap bitmap = bitmapCacheUtils.getBitmap(imageUrl, position);

            if (bitmap != null) {
                imageview.setImageBitmap(bitmap);
            }
        } else {

        }

        return convertView;
    }
}
