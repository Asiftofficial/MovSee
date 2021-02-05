package com.astech.movsee.ui.favorite.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;

public class FavoriteMoviesViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;

    public FavoriteMoviesViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public LiveData<PagedList<MovieDetailEntity>> getFavoriteMovies(){
        return movSeeRepository.getFavoriteMovies();
    }
}
