package com.astech.movsee.ui.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class TvViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;
    private MutableLiveData<String> page = new MutableLiveData<>();

    public TvViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public void setPage(String no) {
        page.setValue(no);
    }

    public LiveData<Resource<List<CatalogResponse>>> getTvs = Transformations.switchMap(page,
            mPage -> movSeeRepository.getAllTvs(mPage));

    public LiveData<Resource<List<GenreResponse>>> getGenres(){
        return movSeeRepository.getGenre("tv");
    }
}
