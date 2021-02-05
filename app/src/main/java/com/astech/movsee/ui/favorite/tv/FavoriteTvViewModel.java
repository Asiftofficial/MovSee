package com.astech.movsee.ui.favorite.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;

public class FavoriteTvViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;

    public FavoriteTvViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public LiveData<PagedList<TvDetailEntity>> getFavoriteTvs(){
        return movSeeRepository.getFavoriteTvs();
    }
}
