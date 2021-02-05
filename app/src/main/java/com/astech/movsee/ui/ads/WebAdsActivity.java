package com.astech.movsee.ui.ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.astech.movsee.R;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WebAdsActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_ads);

        viewPager = findViewById(R.id.view_pager_web_ads);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        ShimmerFrameLayout pbWeb = findViewById(R.id.pb_web_ads);
        pbWeb.setVisibility(View.VISIBLE);

        ImageView btnBack = findViewById(R.id.btn_back_web);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String url = getIntent().getStringExtra(EXTRA_DATA);

        WebView webView = findViewById(R.id.webView_ads);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (!url.isEmpty()){
            webView.loadUrl(url);
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view,String url){
                pbWeb.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });


    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            WebAdsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}