package com.example.kys_8.easymeetings.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kys_8.easymeetings.R;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url = "";
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_web;
//    }
//
//    @Override
//    protected void initView() {
//
//        url = getIntent().getStringExtra("url_web");
//        mWebView = (WebView)findViewById(R.id.web_view);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.setWebViewClient (new WebViewClient());
//        mWebView.loadUrl(url);
//    }
//
//    @Override
//    protected void initListener() {
//
//    }
//
//    @Override
//    protected String getTitleText() {
//        return "网页浏览";
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        url = getIntent().getStringExtra("url_web");
        mWebView = (WebView)findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient (new WebViewClient());
        mWebView.loadUrl(url);

    }
}
