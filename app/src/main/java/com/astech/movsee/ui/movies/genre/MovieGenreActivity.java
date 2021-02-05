package com.astech.movsee.ui.movies.genre;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.ui.movies.MovieAdapter;
import com.astech.movsee.viewmodel.ViewModelFactory;

public class MovieGenreActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";
    private RecyclerView rvMovies;
    private MovieAdapter adapter;
    private MovieGenreViewModel viewModel;
    private ProgressBar pbMovies;
    private TextView tvCheckInternet;
    private int page = 1;
    private String id;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_genre);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this,factory).get(MovieGenreViewModel.class);
        rvMovies = findViewById(R.id.rv_choose_genre_movies);
        adapter = new MovieAdapter();
        rvMovies.setLayoutManager(new GridLayoutManager(this,3));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(adapter);
        pbMovies = findViewById(R.id.progress_bar);
        pbMovies.setVisibility(View.VISIBLE);
        tvCheckInternet = findViewById(R.id.check_internet);

        GenreResponse data = getIntent().getParcelableExtra(EXTRA_DATA);
        if (data != null){
            id = data.getGenreId();
            title = data.getName();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (isOnline()){

            viewModel.getGenreMovies(id,String.valueOf(page)).observe(this, newData -> {
                if (newData != null){
                    switch (newData.status){
                        case SUCCESS:
                            if (newData.data != null){
                                pbMovies.setVisibility(View.GONE);
                                adapter.setListMovie(newData.data);
                            }
                            break;
                        case ERROR:
                            pbMovies.setVisibility(View.GONE);
                            break;
                    }
                }
            });

            adapter.setMovieCallback(new MovieAdapter.MovieCallback() {
                @Override
                public void pageCallback() {
                    page++;
                    viewModel.getGenreMovies(id,String.valueOf(page)).observe(MovieGenreActivity.this, newData -> {
                        if (newData != null){
                            switch (newData.status){
                                case SUCCESS:
                                    if (newData.data != null){
                                        pbMovies.setVisibility(View.GONE);
                                        adapter.setListMovie(newData.data);
                                    }
                                    break;
                                case ERROR:
                                    pbMovies.setVisibility(View.GONE);
                                    break;
                            }
                        }
                    });
                }
            });


        }else {
            pbMovies.setVisibility(View.GONE);
            tvCheckInternet.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.GONE);
        }

    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null){
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}