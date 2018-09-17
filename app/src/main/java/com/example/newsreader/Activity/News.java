package com.example.newsreader.Activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newsreader.R;

import dmax.dialog.SpotsDialog;

public class News extends AppCompatActivity {

    private WebView webView;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        initUI();
        initDialog();
        dialog.show();
        configureWebView();
        loadPage();
    }

    private void initUI() {
        webView = findViewById(R.id.webView);
    }

    private void initDialog() {
        dialog = new SpotsDialog(this);
    }

    private void configureWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }

    private void loadPage() {
        if (hasIntent()) {
            webView.loadUrl(getIntent().getStringExtra("newsPageURL"));
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    dialog.dismiss();
                }
            });
        }
    }

    private boolean hasIntent() {
        return getIntent() != null && !getIntent().getStringExtra("newsPageURL").isEmpty();
    }
}
