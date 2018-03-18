package com.ntnu.henrik.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ArticleContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        //Gets url from listView activity
        String webPageUrlFromRSS = getIntent().getStringExtra("WORD");

        //Starts a new web view with the link sent from listView
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl(webPageUrlFromRSS);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(ArticleContent.this, RSSview.class));
    }
}
