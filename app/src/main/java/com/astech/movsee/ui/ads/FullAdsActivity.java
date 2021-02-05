package com.astech.movsee.ui.ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.astech.movsee.R;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FullAdsActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_ads);

        viewPager = findViewById(R.id.view_pager_ads);
        
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        CardView news1 = findViewById(R.id.news_full_1);
        CardView news2 = findViewById(R.id.news_full_2);
        CardView news3 = findViewById(R.id.news_full_3);
        CardView news4 = findViewById(R.id.news_full_4);
        CardView news5 = findViewById(R.id.news_full_5);
        CardView news6 = findViewById(R.id.news_full_6);

        news1.setOnClickListener(this);
        news2.setOnClickListener(this);
        news3.setOnClickListener(this);
        news4.setOnClickListener(this);
        news5.setOnClickListener(this);
        news6.setOnClickListener(this);



        ImageView btnClose = findViewById(R.id.btn_fullAds);

        ImageView footer = findViewById(R.id.fullAds_footer);


        Random random = new Random();
        int ads = random.nextInt(4);
        if (ads == 0){

            Glide.with(this)
                    .asGif()
                    .load(R.drawable.footer2)
                    .into(footer);
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mini.co.id/"));
                    startActivity(browserIntent);
                }
            });
        }else if (ads == 1){

            Glide.with(this)
                    .asGif()
                    .load(R.drawable.footer3)
                    .into(footer);
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.honda-indonesia.com/"));
                    startActivity(browserIntent);
                }
            });
        }else if (ads == 2){

            footer.setImageDrawable(getDrawable(R.drawable.footer1));
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dunhill.co.id/"));
                    startActivity(browserIntent);
                }
            });
        }else {
            footer.setImageDrawable(getDrawable(R.drawable.footer));
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dunhill.co.id/"));
                    startActivity(browserIntent);
                }
            });
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            FullAdsActivity.this.runOnUiThread(new Runnable() {
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

    @Override
    public void onClick(View view) {
        String url = "";
        if (view.getId() == R.id.news_full_1){
            url = "https://www.klikdokter.com/info-sehat/read/3647428/efek-merenung-saat-kasus-infeksi-virus-corona-semakin-banyak";
        }else if (view.getId() == R.id.news_full_2){
            url = "https://www.klikdokter.com/info-sehat/read/3634629/manfaat-tidur-siang-bagi-anak-apa-saja";
        }else if (view.getId() == R.id.news_full_3){
            url = "https://www.klikdokter.com/info-sehat/read/3159073/minyak-kelapa-lancarkan-persalinan-mitos-atau-fakta";
        }else if (view.getId() == R.id.news_full_4){
            url = "https://www.klikdokter.com/info-sehat/read/2698078/fakta-seputar-bau-mulut-yang-perlu-anda-tahu";
        }else if (view.getId() == R.id.news_full_5){
            url = "https://www.klikdokter.com/info-sehat/read/3171365/manfaat-penggunaan-krim-mata-yang-perlu-anda-tahu";
        }else if (view.getId() == R.id.news_full_6){
            url = "https://www.klikdokter.com/info-sehat/read/2696885/konsumsi-vitamin-e-bisa-mencerahkan-kulit-benarkah";
        }

        if (!url.isEmpty()){
            Intent intent = new Intent(FullAdsActivity.this,WebAdsActivity.class);
            intent.putExtra(WebAdsActivity.EXTRA_DATA,url);
            startActivity(intent);
        }

    }
}