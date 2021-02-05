package com.astech.movsee.ui.favorite.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.viewmodel.ViewModelFactory;

public class FavoriteMoviesFragment extends Fragment {
    private RecyclerView rvMovieFavorite;
    private ProgressBar progressBar;
    private FavoriteMovieAdapter adapter;
    private TextView tvEmptyData;


    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        rvMovieFavorite = view.findViewById(R.id.rv_favorite_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        tvEmptyData = view.findViewById(R.id.empty_data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            FavoriteMoviesViewModel viewModel = new ViewModelProvider(this, factory).get(FavoriteMoviesViewModel.class);

            adapter = new FavoriteMovieAdapter();
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movieEntities -> {
                rvMovieFavorite.setLayoutManager(new GridLayoutManager(getContext(),3));
                rvMovieFavorite.setHasFixedSize(true);
                rvMovieFavorite.setAdapter(adapter);
                if (movieEntities != null){
                    progressBar.setVisibility(View.GONE);
                    if (movieEntities.size() == 0){
                        tvEmptyData.setVisibility(View.VISIBLE);
                    }
                    adapter.submitList(movieEntities);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}