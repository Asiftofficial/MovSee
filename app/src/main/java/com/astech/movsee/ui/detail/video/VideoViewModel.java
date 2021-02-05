package com.astech.movsee.ui.detail.video;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.response.VideoResponse;
import com.astech.movsee.vo.Resource;

public class VideoViewModel extends ViewModel {
    MutableLiveData<String> catalogId = new MutableLiveData<>();
    private MovSeeRepository movSeeRepository;

    public VideoViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId.setValue(catalogId);
    }

    public LiveData<Resource<MovieDetailEntity>> getMovieVideo = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getMovieVideo(mCatalogId));

    public LiveData<Resource<TvDetailEntity>> getTvVideo = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getTvVideo(mCatalogId));

    public LiveData<Resource<VideoResponse>> getMovieVideoRemote = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getMovieVideoRemote(mCatalogId));

    public LiveData<Resource<VideoResponse>> getTvVideoRemote = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getTvVideoRemote(mCatalogId));
}
