package com.astech.movsee.ui.movies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.ui.movies.genre.MovieGenreActivity;
import com.astech.movsee.viewmodel.ViewModelFactory;
import com.bumptech.glide.Glide;

import java.util.Random;


public class MoviesFragment extends Fragment {
    private MovieAdapter adapter;
    private MovieGenreAdapter genreAdapter;
    private ProgressBar pbMovies;
    private int page = 1;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        ImageView footer = view.findViewById(R.id.footer_movies);



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

            footer.setImageDrawable(getActivity().getDrawable(R.drawable.footer1));
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dunhill.co.id/"));
                    startActivity(browserIntent);
                }
            });
        }else {
            footer.setImageDrawable(getActivity().getDrawable(R.drawable.footer));
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dunhill.co.id/"));
                    startActivity(browserIntent);
                }
            });
        }

        ImageButton btnCloseFooter = view.findViewById(R.id.btn_close_movies);
        ConstraintLayout clFooter = view.findViewById(R.id.cl_footer);

        btnCloseFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clFooter.setVisibility(View.GONE);
            }
        });



        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        RecyclerView rvMoviesGenre = view.findViewById(R.id.rv_movies_genre);
        View viewLine = view.findViewById(R.id.view_line);
        pbMovies = view.findViewById(R.id.progress_bar);
        pbMovies.setVisibility(View.VISIBLE);
        TextView tvCheckInternet = view.findViewById(R.id.check_internet);

        ViewModelFactory factory = ViewModelFactory.getInstance(getContext());
        MoviesViewModel viewModel = new ViewModelProvider(this, factory).get(MoviesViewModel.class);

        adapter = new MovieAdapter();
        genreAdapter = new MovieGenreAdapter();
        rvMovies.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(adapter);

        rvMoviesGenre.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvMoviesGenre.setHasFixedSize(true);
        rvMoviesGenre.setAdapter(genreAdapter);

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

            adapter.setMovieCallback(new MovieAdapter.MovieCallback() {
                @Override
                public void pageCallback() {
                    page++;
                    viewModel.setPage(String.valueOf(page));
                }
            });

            genreAdapter.setGenreCallback(new MovieGenreAdapter.GenreCallback() {
                @Override
                public void genreId(GenreResponse data) {
                    Intent intent = new Intent(getContext(), MovieGenreActivity.class);
                    intent.putExtra(MovieGenreActivity.EXTRA_DATA,data);
                    startActivity(intent);
                }
            });


            viewModel.getMovies.observe(getViewLifecycleOwner(), newData -> {
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
            
        }else {
            viewLine.setVisibility(View.GONE);
            rvMoviesGenre.setVisibility(View.GONE);
            pbMovies.setVisibility(View.GONE);
            tvCheckInternet.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.GONE);
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