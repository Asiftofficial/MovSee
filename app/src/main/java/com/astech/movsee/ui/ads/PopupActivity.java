package com.astech.movsee.ui.ads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.astech.movsee.R;
import com.bumptech.glide.Glide;

import java.util.Random;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        ImageView popAds = findViewById(R.id.popup_ads);

        Random random = new Random();
        int ads = random.nextInt(4);

        switch (ads){
            case 0:
                popAds.setImageDrawable(getDrawable(R.drawable.popup));
                popAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mini.co.id/"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case 1:
                popAds.setImageDrawable(getDrawable(R.drawable.popup2));
                popAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dunhill.co.id/"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case 2:
                popAds.setImageDrawable(getDrawable(R.drawable.popup3));
                popAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dunhill.co.id/"));
                        startActivity(browserIntent);
                    }
                });
                break;
            case 3:
                Glide.with(this)
                        .asGif()
                        .load(R.drawable.popup4)
                        .into(popAds);

                popAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.honda-indonesia.com/"));
                        startActivity(browserIntent);
                    }
                });
                break;
        }

        ImageButton btnClose = findViewById(R.id.btn_close_popup);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.6), (int) (height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

         */
    }
}