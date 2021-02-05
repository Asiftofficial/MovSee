package com.astech.movsee.ui.search.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class SearchMovieViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;

    public SearchMovieViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public LiveData<Resource<List<CatalogResponse>>> getSearchMovies(String query,String page){
        return movSeeRepository.getSearchMovies(query,page);
    }
}
