package com.astech.movsee.ui.tv.genre;

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
import com.astech.movsee.ui.tv.TvAdapter;
import com.astech.movsee.viewmodel.ViewModelFactory;

public class TvGenreActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";
    private RecyclerView rvTv;
    private TvAdapter adapter;
    private TvGenreViewModel viewModel;
    private ProgressBar pbTv;
    private TextView tvCheckInternet;
    private int page = 1;
    private String id;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_genre);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        viewModel = new ViewModelProvider(this,factory).get(TvGenreViewModel.class);
        rvTv = findViewById(R.id.rv_choose_genre_tv);
        adapter = new TvAdapter();
        rvTv.setLayoutManager(new GridLayoutManager(this,3));
        rvTv.setHasFixedSize(true);
        rvTv.setAdapter(adapter);
        pbTv = findViewById(R.id.progress_bar);
        pbTv.setVisibility(View.VISIBLE);
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

            viewModel.getGenreTvs(id,String.valueOf(page)).observe(this, newData -> {
                if (newData != null){
                    switch (newData.status){
                        case SUCCESS:
                            if (newData.data != null){
                                pbTv.setVisibility(View.GONE);
                                adapter.setListTvs(newData.data);
                            }
                            break;
                        case ERROR:
                            pbTv.setVisibility(View.GONE);
                            break;
                    }
                }
            });

            adapter.setTvCallback(new TvAdapter.TvCallback() {
                @Override
                public void pageCallback() {
                    page++;
                    viewModel.getGenreTvs(id,String.valueOf(page)).observe(TvGenreActivity.this, newData -> {
                        if (newData != null){
                            switch (newData.status){
                                case SUCCESS:
                                    if (newData.data != null){
                                        pbTv.setVisibility(View.GONE);
                                        adapter.setListTvs(newData.data);
                                    }
                                    break;
                                case ERROR:
                                    pbTv.setVisibility(View.GONE);
                                    break;
                            }
                        }
                    });
                }
            });

        }else {
            pbTv.setVisibility(View.GONE);
            tvCheckInternet.setVisibility(View.VISIBLE);
            rvTv.setVisibility(View.GONE);
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