package com.example.mynews.views;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynews.R;

public class WebViewActivity extends AppCompatActivity {

    public static String WebUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_view);

        String url=getIntent().getStringExtra(WebUrl);
        setTitle(url);

        WebView webView = findViewById(R.id.webView);
        webView.loadUrl(WebUrl);
    }
}
