package com.astech.movsee.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.astech.movsee.R;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.ui.ads.FullAdsActivity;
import com.astech.movsee.ui.detail.video.VideoActivity;
import com.astech.movsee.viewmodel.ViewModelFactory;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_IDENTITY = "extra_identity";
    public static final String EXTRA_FAVORITE = "extra_favorite";

    private ImageView imgDetail;
    private TextView tvTitle;
    private TextView tvRelease;
    private TextView tvScore;
    private TextView tvMinutes;
    private TextView tvOverview;
    private TextView tvGenres;
    private ProgressBar pbDetail;
    private DetailViewModel viewModel;
    private String identity;
    private Menu menu;
    private String id;
    private String adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        startActivity(new Intent(this, FullAdsActivity.class));

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this,factory).get(DetailViewModel.class);
        identity = getIntent().getStringExtra(EXTRA_IDENTITY);
        id = getIntent().getStringExtra(EXTRA_ID);

        imgDetail = findViewById(R.id.img_poster_detail);
        tvTitle = findViewById(R.id.tv_title);
        tvRelease = findViewById(R.id.tv_release);
        tvScore = findViewById(R.id.tv_score_value);
        tvMinutes = findViewById(R.id.tv_minute_value);
        tvOverview = findViewById(R.id.tv_overview);
        tvGenres = findViewById(R.id.tv_genre);
        Button btnPlay = findViewById(R.id.btn_trailer);
        pbDetail = findViewById(R.id.progress_bar);
        pbDetail.setVisibility(View.VISIBLE);

        if (identity.equals("movie")){

            if (!id.equals("0")){
                viewModel.setCatalogId(id);
                MovieDetailEntity movieDetailEntity = getIntent().getParcelableExtra(EXTRA_FAVORITE);
                if (movieDetailEntity != null){
                    adress = "favorite";
                    pbDetail.setVisibility(View.GONE);
                    populateMovieLocal(movieDetailEntity);
                }else {
                    adress = "remote";
                    viewModel.getDetailMovieRemote.observe(this, remote -> {
                        if (remote != null){
                            switch (remote.status){
                                case SUCCESS:
                                    pbDetail.setVisibility(View.GONE);
                                    if (remote.data != null){
                                        DetailResponse movie = remote.data;
                                        populateMovieRemote(movie);
                                    }
                                    break;
                                case ERROR:
                                    pbDetail.setVisibility(View.GONE);
                                    populateBlank();
                                    break;
                            }
                        }
                    });
                }
            }else {
                pbDetail.setVisibility(View.GONE);
                populateBlank();
            }
        }else {
            if (!id.equals("0")){
                viewModel.setCatalogId(id);
                TvDetailEntity tvDetailEntity = getIntent().getParcelableExtra(EXTRA_FAVORITE);
                if (tvDetailEntity != null){
                    adress = "favorite";
                    pbDetail.setVisibility(View.GONE);
                    populateTvLocal(tvDetailEntity);
                }else {
                    adress = "remote";
                    viewModel.getDetailTvRemote.observe(this, remote -> {
                        if (remote != null){
                            switch (remote.status){
                                case SUCCESS:
                                    pbDetail.setVisibility(View.GONE);
                                    if (remote.data != null){
                                        DetailResponse tv = remote.data;
                                        populateTvRemote(tv);
                                    }
                                    break;
                                case ERROR:
                                    pbDetail.setVisibility(View.GONE);
                                    populateBlank();
                                    break;
                            }
                        }
                    });
                }
            }else {
                pbDetail.setVisibility(View.GONE);
                populateBlank();
            }

        }

        btnPlay.setOnClickListener(view -> {
            if (isOnline() && !id.equals("0")){
                Intent intent = new Intent(DetailActivity.this,VideoActivity.class);
                intent.putExtra(VideoActivity.EXTRA_ID,id);
                intent.putExtra(VideoActivity.EXTRA_ADRESS,adress);
                intent.putExtra(VideoActivity.EXTRA_IDENTITY,identity);
                startActivity(intent);
            }else if (id.equals("0")){
                Toast.makeText(DetailActivity.this,R.string.no_video,Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(DetailActivity.this,R.string.check_internet,Toast.LENGTH_LONG).show();
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void populateMovieRemote(DetailResponse movie){
        if(movie.getPoster().isEmpty()){
            imgDetail.setImageResource(R.color.background);
        }else {
            String url = "https://image.tmdb.org/t/p/w342" + movie.getPoster();

            Glide.with(DetailActivity.this)
                    .load(url)
                    .into(imgDetail);
        }

        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease());
        tvGenres.setText(movie.getGenre());
        tvScore.setText(movie.getScore());
        tvMinutes.setText(movie.getMinute());
        tvOverview.setText(movie.getOverview());
    }

    private void populateTvRemote(DetailResponse tv){
        if(tv.getPoster().isEmpty()){
            imgDetail.setImageResource(R.color.background);
        }else {
            String url = "https://image.tmdb.org/t/p/w342" + tv.getPoster();

            Glide.with(DetailActivity.this)
                    .load(url)
                    .into(imgDetail);
        }

        tvTitle.setText(tv.getTitle());
        tvRelease.setText(tv.getRelease());
        tvGenres.setText(tv.getGenre());
        tvScore.setText(tv.getScore());
        tvMinutes.setText(tv.getMinute());
        tvOverview.setText(tv.getOverview());
    }

    private void populateMovieLocal(MovieDetailEntity movie){

        if(movie.getPoster().isEmpty()){
            imgDetail.setImageResource(R.color.background);
        }else {
            String url = "https://image.tmdb.org/t/p/w342" + movie.getPoster();

            Glide.with(DetailActivity.this)
                    .load(url)
                    .into(imgDetail);
        }



        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease());
        tvGenres.setText(movie.getGenre());
        tvScore.setText(movie.getScore());
        tvMinutes.setText(movie.getMinute());
        tvOverview.setText(movie.getOverview());
    }

    private void populateTvLocal(TvDetailEntity tv){

        if(tv.getPoster().isEmpty()){
            imgDetail.setImageResource(R.color.background);
        }else {
            String url = "https://image.tmdb.org/t/p/w342" + tv.getPoster();

            Glide.with(DetailActivity.this)
                    .load(url)
                    .into(imgDetail);
        }



        tvTitle.setText(tv.getTitle());
        tvRelease.setText(tv.getRelease());
        tvGenres.setText(tv.getGenre());
        tvScore.setText(tv.getScore());
        tvMinutes.setText(tv.getMinute());
        tvOverview.setText(tv.getOverview());
    }

    private void populateBlank(){
        imgDetail.setImageResource(R.color.background);

        tvTitle.setText("");
        tvRelease.setText("");
        tvGenres.setText("");
        tvScore.setText("");
        tvMinutes.setText("");
        tvOverview.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        this.menu = menu;

        if (identity.equals("movie")){
            viewModel.getDetailMovieLocal.observe(this, local -> {
                if (local != null){
                    populateFavorite(true);
                }else {
                    populateFavorite(false);
                }
            });
        }else {
            viewModel.getDetailTvLocal.observe(this, local -> {
                if (local != null){
                    populateFavorite(true);
                }else {
                    populateFavorite(false);
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            finish();
        }else if (item.getItemId() == R.id.action_favorite){
            if (identity.equals("movie")){
                viewModel.setMovieFavorite();
            }else {
                viewModel.setTvFavorite();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateFavorite(boolean state){
        if (state){
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorited);
        }else {
            menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite);
        }
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null){
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        return false;
    }
}