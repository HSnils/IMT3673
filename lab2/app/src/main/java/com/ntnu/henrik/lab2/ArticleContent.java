package com.ntnu.henrik.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ArticleContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(ArticleContent.this, RSSview.class));
    }
}
