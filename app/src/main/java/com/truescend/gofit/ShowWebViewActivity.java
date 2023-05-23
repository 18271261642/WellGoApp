package com.truescend.gofit;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Admin
 * Date 2023/5/23
 */
public class ShowWebViewActivity extends AppCompatActivity {


    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_webview_layout);

        initView();
        initData();
    }

    private void initView() {
        webView = findViewById(R.id.webView);

        setWebSetting();
    }

    private void initData() {

        String url = getIntent().getStringExtra("url");
        String  title = getIntent().getStringExtra("title");

        setTitle(title);

        if (url != null) {
            webView.loadUrl(url);
        }
    }

    private void setWebSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
    }

}
