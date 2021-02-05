package com.astech.movsee.ui.tv;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.ui.tv.genre.TvGenreActivity;
import com.astech.movsee.viewmodel.ViewModelFactory;


public class TvFragment extends Fragment {
    private ProgressBar pbTv;
    private TvGenreAdapter genreAdapter;
    private TvAdapter adapter;
    private int page = 1;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        RecyclerView rvTv = view.findViewById(R.id.rv_tv);
        RecyclerView rvTvGenre = view.findViewById(R.id.rv_tv_genre);
        pbTv = view.findViewById(R.id.progress_bar);
        pbTv.setVisibility(View.VISIBLE);
        View viewLine = view.findViewById(R.id.view_line);
        TextView tvCheckInternet = view.findViewById(R.id.check_internet);

        ViewModelFactory factory = ViewModelFactory.getInstance(getContext());
        TvViewModel viewModel = new ViewModelProvider(this,factory).get(TvViewModel.class);
        adapter = new TvAdapter();
        rvTv.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvTv.setHasFixedSize(true);
        rvTv.setAdapter(adapter);

        genreAdapter = new TvGenreAdapter();
        rvTvGenre.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvTvGenre.setHasFixedSize(true);
        rvTvGenre.setAdapter(genreAdapter);

        if (isOnline()){

            viewModel.setPage(String.valueOf(page));

            viewModel.getGenres().observe(getViewLifecycleOwner(), listResource -> {
                if (listResource != null){
                    switch (listResource.status){
                        case SUCCESS:
                            if (listResource.data != null){
                                genreAdapter.setListGenre(listResource.data);
                            }
                            break;
                        case ERROR:
                            Toast.makeText(getContext(),listResource.msg,Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            });

            adapter.setTvCallback(new TvAdapter.TvCallback() {
                @Override
                public void pageCallback() {
                    page++;
                    viewModel.setPage(String.valueOf(page));
                }
            });

            genreAdapter.setGenreCallback(new TvGenreAdapter.GenreCallback() {
                @Override
                public void genreId(GenreResponse data) {
                    Intent intent = new Intent(getContext(), TvGenreActivity.class);
                    intent.putExtra(TvGenreActivity.EXTRA_DATA,data);
                    startActivity(intent);
                }
            });

            viewModel.getTvs.observe(getViewLifecycleOwner(), newData -> {
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
        }else {
            viewLine.setVisibility(View.GONE);
            rvTvGenre.setVisibility(View.GONE);
            pbTv.setVisibility(View.GONE);
            tvCheckInternet.setVisibility(View.VISIBLE);
            rvTv.setVisibility(View.GONE);
        }

    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null){
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        return false;
    }
}