package com.astech.movsee.ui.search.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class SearchTvViewModel extends ViewModel {
    private MovSeeRepository movSeeRepository;

    public SearchTvViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public LiveData<Resource<List<CatalogResponse>>> getSearchTvs(String query,String page){
        return movSeeRepository.getSearchTvs(query,page);
    }
}
