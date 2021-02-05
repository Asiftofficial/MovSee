package com.astech.movsee.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;
    private MutableLiveData<String> page = new MutableLiveData<>();

    public MoviesViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public void setPage(String no) {
        page.setValue(no);
    }

    public LiveData<Resource<List<CatalogResponse>>> getMovies = Transformations.switchMap(page,
            mPage -> movSeeRepository.getAllMovies(mPage));

    public LiveData<Resource<List<GenreResponse>>> getGenres(){
        return movSeeRepository.getGenre("movie");
    }
}
