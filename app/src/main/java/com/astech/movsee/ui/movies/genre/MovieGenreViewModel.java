package com.astech.movsee.ui.movies.genre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class MovieGenreViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;

    public MovieGenreViewModel(MovSeeRepository movSeeRepository) {
        this.movSeeRepository = movSeeRepository;
    }

    public LiveData<Resource<List<CatalogResponse>>> getGenreMovies(String genre,String page){
        return movSeeRepository.getGenreMovies(genre,page);
    }
}
