package com.lambton.capstone_wic_fitandfine.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lambton.capstone_wic_fitandfine.R;

public class BlogActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
     //   ActionBar actionBar =  getSupportActionBar();
       // actionBar.setTitle("About Us");
        //actionBar.show();
        webView=findViewById(R.id.web);
        WebViewClient mWebViewClient = new WebViewClient();
        webView.setWebViewClient(mWebViewClient);
        webView.loadUrl("https://breakingmuscle.com");

        TextView ca = findViewById(R.id.textview_log_pain_cancel);
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) this;
      //  ActionBar actionBar = activity.getSupportActionBar();
        //if(actionBar!=null) {
          //  actionBar.setTitle("BLOG");
        //}

    }
}