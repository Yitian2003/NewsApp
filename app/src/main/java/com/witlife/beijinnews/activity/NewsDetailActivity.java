package com.witlife.beijinnews.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.witlife.beijinnews.R;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibTextsize;
    private ImageButton ibShare;

    private WebView webview;
    private ProgressBar pbLoading;
    private String url;
    private WebSettings webSettings;

    private int tempSize = 2;
    private int realSize = tempSize;

    private void findViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ibMenu = (ImageButton) findViewById(R.id.ib_menu);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        ibTextsize = (ImageButton) findViewById(R.id.ib_size);
        ibShare = (ImageButton) findViewById(R.id.ib_share);
        webview = (WebView) findViewById(R.id.webview);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        tvTitle.setVisibility(View.GONE);
        ibMenu.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        ibBack.setOnClickListener(this);
        ibTextsize.setOnClickListener(this);
        ibShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ibBack) {
            finish();
        } else if (v == ibTextsize) {
            showChangTextSizeDialog();
        } else if (v == ibShare) {

        }
    }

    private void showChangTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Text Size");
        String[] items = {"Extra Big Size", "Big Size", "Normal Size", "Small Size", "Extra Small Size"};
        builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempSize = which;
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realSize = tempSize;
                changeTextSize(realSize);
            }
        });
        builder.show();
    }

    private void changeTextSize(int realSize) {

        switch (realSize){
            case 0:
                webSettings.setTextZoom(200);
                break;
            case 1:
                webSettings.setTextZoom(150);
                break;
            case 2:
                webSettings.setTextZoom(100);
                break;
            case 3:
                webSettings.setTextZoom(75);
                break;
            case 4:
                webSettings.setTextZoom(50);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        findViews();

        getData();
    }

    private void getData() {

        url = getIntent().getStringExtra("url");

        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });

        webview.loadUrl(url);
    }
}
