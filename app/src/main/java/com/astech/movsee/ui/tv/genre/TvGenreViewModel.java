package com.astech.movsee.ui.tv.genre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class TvGenreViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;

    public TvGenreViewModel(MovSeeRepository movSeeRepository) {
        this.movSeeRepository = movSeeRepository;
    }

    public LiveData<Resource<List<CatalogResponse>>> getGenreTvs(String genre, String page){
        return movSeeRepository.getGenreTvs(genre,page);
    }
}
