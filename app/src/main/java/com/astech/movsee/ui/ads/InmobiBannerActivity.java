package com.astech.movsee.ui.ads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.astech.movsee.R;
import com.inmobi.ads.InMobiBanner;

public class InmobiBannerActivity extends AppCompatActivity {
    private InMobiBanner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmobi_banner);
        mBanner = (InMobiBanner) findViewById(R.id.banner);
        mBanner.load();
    }
}