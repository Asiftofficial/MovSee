package com.astech.movsee.ui.detail.video;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.astech.movsee.R;
import com.astech.movsee.viewmodel.ViewModelFactory;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_IDENTITY = "extra_identity";
    public static final String EXTRA_ADRESS = "extra_adress";
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int) (height*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        youTubePlayerView = findViewById(R.id.youtube_play);
        getLifecycle().addObserver(youTubePlayerView);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        VideoViewModel videoViewModel = new ViewModelProvider(this,factory).get(VideoViewModel.class);

        String id = getIntent().getStringExtra(EXTRA_ID);
        String identity = getIntent().getStringExtra(EXTRA_IDENTITY);
        String adress= getIntent().getStringExtra(EXTRA_ADRESS);

        if (identity.equals("movie")){
            videoViewModel.setCatalogId(id);
            if (adress.equals("favorite")){
                videoViewModel.getMovieVideo.observe(this, movie -> {
                    if (movie != null){
                        switch (movie.status){
                            case SUCCESS:
                                if (movie.data != null){
                                    playVideo(movie.data.videoEntity.getVideo());
                                }
                                break;
                            case ERROR:
                                Toast.makeText(getApplicationContext(),movie.msg,Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }else {
                videoViewModel.getMovieVideoRemote.observe(this, remote -> {
                    if (remote != null){
                        switch (remote.status){
                            case SUCCESS:
                                if (remote.data != null){
                                    playVideo(remote.data.getVideo());
                                }
                                break;
                            case ERROR:
                                Toast.makeText(getApplicationContext(),remote.msg,Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }

        }else {
            videoViewModel.setCatalogId(id);
            if (adress.equals("favorite")){
                videoViewModel.getTvVideo.observe(this, tv -> {
                    if (tv != null){
                        switch (tv.status){
                            case SUCCESS:
                                if (tv.data != null){
                                    playVideo(tv.data.videoEntity.getVideo());
                                }
                                break;
                            case ERROR:
                                Toast.makeText(getApplicationContext(),tv.msg,Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }else {
                videoViewModel.getTvVideoRemote.observe(this, remote -> {
                    if (remote != null){
                        switch (remote.status){
                            case SUCCESS:
                                if (remote.data != null){
                                    playVideo(remote.data.getVideo());
                                }else {
                                    Toast.makeText(VideoActivity.this,R.string.no_video,Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                break;
                            case ERROR:
                                Toast.makeText(getApplicationContext(),remote.msg,Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }

        }

    }

    private void playVideo(String video){
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                if (video == null){
                    Toast.makeText(VideoActivity.this,R.string.no_video,Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    youTubePlayer.loadVideo(video,0);
                }

            }
        });
    }
}