package com.astech.movsee.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.vo.Resource;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<String> catalogId = new MutableLiveData<>();
    private MovSeeRepository movSeeRepository;

    public DetailViewModel(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public void setCatalogId(String id) {
        catalogId.setValue(id);
    }

    public LiveData<Resource<DetailResponse>> getDetailMovieRemote = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getDetailMovieRemote(mCatalogId));

    public LiveData<Resource<DetailResponse>> getDetailTvRemote = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getDetailTvRemote(mCatalogId));

    public LiveData<MovieDetailEntity> getDetailMovieLocal = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getMovieDetailLocal(mCatalogId));

    public LiveData<TvDetailEntity> getDetailTvLocal = Transformations.switchMap(catalogId,
            mCatalogId -> movSeeRepository.getTvDetailLocal(mCatalogId));


    void setMovieFavorite(){
        if (getDetailMovieLocal != null){
            MovieDetailEntity movieDetailEntity = getDetailMovieLocal.getValue();
            if (movieDetailEntity != null){
                movSeeRepository.deleteMovieFavorite(movieDetailEntity);
            }else {
                if (getDetailMovieRemote != null){
                    Resource<DetailResponse> detailResponseResource = getDetailMovieRemote.getValue();
                    if (detailResponseResource != null){
                        DetailResponse detailResponse = detailResponseResource.data;
                        if (detailResponse != null){
                            movSeeRepository.insertFavoriteMovie(detailResponse);
                        }
                    }
                }
            }
        }
    }

    void setTvFavorite(){
        if (getDetailTvLocal != null){
            TvDetailEntity tvDetailEntity = getDetailTvLocal.getValue();
            if (tvDetailEntity != null){
                movSeeRepository.deleteTvFavorite(tvDetailEntity);
            }else {
                if (getDetailTvRemote != null){
                    Resource<DetailResponse> detailResponseResource = getDetailTvRemote.getValue();
                    if (detailResponseResource != null){
                        DetailResponse detailResponse = detailResponseResource.data;
                        if (detailResponse != null){
                            movSeeRepository.insertFavoriteTv(detailResponse);
                        }
                    }
                }
            }
        }
    }

}
