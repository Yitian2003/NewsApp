package com.witlife.beijinnews.view;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.witlife.beijinnews.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bruce on 3/08/2017.
 */

public class RefreshListView extends ListView {

    private LinearLayout headerView;
    private View pullToRefrsh;
    private ImageView ivArrow;
    private TextView tvTime;
    private TextView tvStatus;
    private ProgressBar pb_status;
    private int refreshHeight;

    private float startY = -1;

    public static final int PULLDOWM = 0;
    public static final int RELEASE = 1;
    public static final int REFRESHING = 2;

    private int currentStatus = PULLDOWM;

    private Animation upAnimation;
    private Animation downAnimation;
    private View footerView;
    private int footerViewHeight;

    private boolean isLoadMore = false;
    private View topNewsHeaderView;
    private int listViewOnScreenY = -1;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initFooterView(context);

        initAnimation();
    }

    private void initFooterView(Context context) {
        footerView = View.inflate(context, R.layout.refresh_footer, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();

        footerView.setPadding(0, -footerViewHeight, 0, 0);

        addFooterView(footerView);

        setOnScrollListener(new MyOnScrollListener());
    }

    public void addTopNewsView(View topNewsHeaderView) {
        if (topNewsHeaderView != null) {

            this.topNewsHeaderView = topNewsHeaderView;
            headerView.addView(topNewsHeaderView);
        }

    }

    class MyOnScrollListener implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING){
                if (getLastVisiblePosition() >= getCount() -1){
                    footerView.setPadding(8,8,8,8);

                    isLoadMore = true;

                    if (mOnRefreshListener != null){
                        mOnRefreshListener.onLoadMore();
                    }
                }
            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(800);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context){
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        ivArrow = (ImageView) headerView.findViewById(R.id.ivArrow);
        tvTime = (TextView) headerView.findViewById(R.id.tvTime);
        tvStatus = (TextView) headerView.findViewById(R.id.tvStatus);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        pullToRefrsh = headerView.findViewById(R.id.pull_refresh);

        pullToRefrsh.measure(0,0);
        refreshHeight = pullToRefrsh.getMeasuredHeight();

        pullToRefrsh.setPadding(0, -refreshHeight, 0, 0);

        addHeaderView(headerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if(startY == -1) {
                    startY = ev.getY();
                }
                //check the top news layout show completely
                boolean isDisplayTopNews = isDisplayTopNews();
                if(!isDisplayTopNews) {
                    //load more
                    break;
                }

                //
                if (currentStatus == REFRESHING){
                    break;
                }

                float endY = ev.getY();

                float distanceY = endY - startY;

                if (distanceY > 0) {

                    int paddingTop = (int)(distanceY - refreshHeight);

                    if (paddingTop < 0 && currentStatus != PULLDOWM) {
                        currentStatus = PULLDOWM;
                        refreshViewState();
                    } else if (paddingTop > 0 && currentStatus != RELEASE){
                        currentStatus = RELEASE;
                        refreshViewState();
                    }
                    pullToRefrsh.setPadding(0, paddingTop, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;

                if (currentStatus == PULLDOWM) {
                     pullToRefrsh.setPadding(0, -refreshHeight, 0, 0);
                } else if (currentStatus == RELEASE) {
                    currentStatus = REFRESHING;
                    refreshViewState();
                    pullToRefrsh.setPadding(0,0,0,0);

                    if(mOnRefreshListener != null){
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isDisplayTopNews() {

        if(topNewsHeaderView != null){
            int[] location = new int[2];
            if (listViewOnScreenY == -1){
                getLocationOnScreen(location);
                listViewOnScreenY = location[1];
            }

            topNewsHeaderView.getLocationOnScreen(location);
            int topNewsViewOnScreenY = location[1];

            return listViewOnScreenY <= topNewsViewOnScreenY;

        } else  return true;
    }

    private void refreshViewState() {

        switch (currentStatus){
            case PULLDOWM:
                ivArrow.startAnimation(downAnimation);
                tvStatus.setText("Pull to refresh...");
                break;
            case RELEASE:
                ivArrow.startAnimation(upAnimation);
                tvStatus.setText("Release to refresh...");
                break;
            case REFRESHING:
                tvStatus.setText("Refreshing...");
                pb_status.setVisibility(VISIBLE);
                ivArrow.clearAnimation();
                ivArrow.setVisibility(GONE);
                break;
        }
    }

    public void onRefreshFinish(boolean success) {

        if (isLoadMore){

            isLoadMore = false;
            footerView.setPadding(0,-footerViewHeight, 0, 0);

        } else {
            tvStatus.setText("Pull to refresh...");
            currentStatus = PULLDOWM;
            ivArrow.clearAnimation();
            pb_status.setVisibility(GONE);
            ivArrow.setVisibility(VISIBLE);
            pullToRefrsh.setPadding(0, -refreshHeight, 0, 0);

            if (success)  {
                tvTime.setText("Next update:" + getSystemTime());
            }
        }

    }

    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public interface OnRefreshListener{
        public void onPullDownRefresh();

        public void onLoadMore();


    }

    private OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener l) {
        this.mOnRefreshListener = l;
    }
}
