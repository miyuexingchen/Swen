package com.wcc.swen.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wcc.swen.R;
import com.wcc.swen.utils.LogUtils;

public class WebNewsActivity extends AppCompatActivity {

    private WebView wv_web_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_news);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(Color.WHITE);


        // 当版本大于5.0，调用方法更改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);   //这里动态修改颜色
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = getIntent().getStringExtra("url");
        LogUtils.e("NewsDetailPresenter", url);
        wv_web_news = (WebView) findViewById(R.id.wv_web_news);

        wv_web_news.getSettings().setDomStorageEnabled(true);
        wv_web_news.getSettings().setJavaScriptEnabled(true);
//        wv_web_news.getSettings().setBlockNetworkImage(true);
        wv_web_news.getSettings().setSupportZoom(true);  //支持缩放
        wv_web_news.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        wv_web_news.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        wv_web_news.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        wv_web_news.getSettings().setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
        wv_web_news.getSettings().setBuiltInZoomControls(true);
        wv_web_news.getSettings().setUseWideViewPort(true);
        wv_web_news.getSettings().setAppCacheEnabled(true);//是否使用缓存
        wv_web_news.setWebChromeClient(new WebChromeClient());

        wv_web_news.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv_web_news.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if (wv_web_news.canGoBack()) {
            wv_web_news.goBack();
            return;
        }
        finish();
    }
}
