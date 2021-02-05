package com.astech.movsee.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.local.LocalDataSource;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.ApiResponse;
import com.astech.movsee.data.source.remote.RemoteDataSource;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.data.source.remote.response.VideoResponse;
import com.astech.movsee.utils.AppExecutors;
import com.astech.movsee.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class FakeMovSeeRepository implements MovSeeDataSource {
    private volatile static FakeMovSeeRepository INSTANCE = null;
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private AppExecutors appExecutors;

    public FakeMovSeeRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> getAllMovies() {
        return new NetworkBoundResource<List<MovieEntity>, List<CatalogResponse>>(appExecutors){

            @Override
            protected LiveData<List<MovieEntity>> loadFromDB() {
                return localDataSource.getAllMovies();
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<CatalogResponse>>> createCall() {
                return remoteDataSource.getAllMovies();
            }

            @Override
            protected void saveCallResult(List<CatalogResponse> data) {
                ArrayList<MovieEntity> movieEntities = new ArrayList<>();
                for (CatalogResponse response : data){
                    MovieEntity movieEntity = new MovieEntity(response.getCatalogId(),
                            response.getPoster(),
                            response.getScore(),
                            false);
                    movieEntities.add(movieEntity);
                }
                localDataSource.insertMovies(movieEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<TvEntity>>> getAllTvs() {
        return new NetworkBoundResource<List<TvEntity>, List<CatalogResponse>>(appExecutors){

            @Override
            protected LiveData<List<TvEntity>> loadFromDB() {
                return localDataSource.getAllTvs();
            }

            @Override
            protected boolean shouldFetch(List<TvEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<CatalogResponse>>> createCall() {
                return remoteDataSource.getAllTvs();
            }

            @Override
            protected void saveCallResult(List<CatalogResponse> data) {
                ArrayList<TvEntity> tvEntities = new ArrayList<>();
                for (CatalogResponse response : data){
                    TvEntity tvEntity = new TvEntity(response.getCatalogId(),
                            response.getPoster(),
                            response.getScore(),
                            false);
                    tvEntities.add(tvEntity);
                }
                localDataSource.insertTvs(tvEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<PagedList<MovieEntity>> getFavoriteMovies() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getAllFavoriteMovies(),config).build();
    }

    @Override
    public LiveData<PagedList<TvEntity>> getFavoriteTvs() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPageSize(5)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getAllFavoriteTvs(),config).build();
    }

    @Override
    public LiveData<Resource<MovieWithDetail>> getMovieWithDetail(String catalogId) {
        return new NetworkBoundResource<MovieWithDetail,DetailResponse>(appExecutors){

            @Override
            protected LiveData<MovieWithDetail> loadFromDB() {
                return localDataSource.getMovieWithDetail(catalogId);
            }

            @Override
            protected boolean shouldFetch(MovieWithDetail data) {
                return (data == null) || (data.movieDetailEntity == null);
            }

            @Override
            protected LiveData<ApiResponse<DetailResponse>> createCall() {
                return remoteDataSource.getDetailMovie(catalogId);
            }

            @Override
            protected void saveCallResult(DetailResponse data) {
                MovieDetailEntity movieDetailEntity = new MovieDetailEntity(data.getCatalogId(),
                        data.getTitle(),
                        data.getOverview(),
                        data.getRelease(),
                        data.getGenre(),
                        data.getMinute());
                localDataSource.insertMovieDetail(movieDetailEntity);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvWithDetail>> getTvWithDetail(String catalogId) {
        return new NetworkBoundResource<TvWithDetail,DetailResponse>(appExecutors){

            @Override
            protected LiveData<TvWithDetail> loadFromDB() {
                return localDataSource.getTvWithDetail(catalogId);
            }

            @Override
            protected boolean shouldFetch(TvWithDetail data) {
                return (data == null) || (data.tvDetailEntity == null);
            }

            @Override
            protected LiveData<ApiResponse<DetailResponse>> createCall() {
                return remoteDataSource.getDetailTv(catalogId);
            }

            @Override
            protected void saveCallResult(DetailResponse data) {
                TvDetailEntity tvDetailEntity = new TvDetailEntity(data.getCatalogId(),
                        data.getTitle(),
                        data.getOverview(),
                        data.getRelease(),
                        data.getGenre(),
                        data.getMinute());
                localDataSource.insertTvDetail(tvDetailEntity);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieDetailEntity>> getMovieVideo(String catalogId) {
        return new NetworkBoundResource<MovieDetailEntity, VideoResponse>(appExecutors){

            @Override
            protected LiveData<MovieDetailEntity> loadFromDB() {
                return localDataSource.getMovieDetailWithVideo(catalogId);
            }

            @Override
            protected boolean shouldFetch(MovieDetailEntity data) {
                return (data == null) || (data.videoEntity == null);
            }

            @Override
            protected LiveData<ApiResponse<VideoResponse>> createCall() {
                return remoteDataSource.getVideo(catalogId,"movie");
            }

            @Override
            protected void saveCallResult(VideoResponse data) {
                localDataSource.updateMovieByVideo(data.getVideo(),catalogId);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvDetailEntity>> getTvVideo(String catalogId) {
        return new NetworkBoundResource<TvDetailEntity, VideoResponse>(appExecutors){

            @Override
            protected LiveData<TvDetailEntity> loadFromDB() {
                return localDataSource.getTvDetailWithVideo(catalogId);
            }

            @Override
            protected boolean shouldFetch(TvDetailEntity data) {
                return (data == null) || (data.videoEntity == null);
            }

            @Override
            protected LiveData<ApiResponse<VideoResponse>> createCall() {
                return remoteDataSource.getVideo(catalogId,"tv");
            }

            @Override
            protected void saveCallResult(VideoResponse data) {
                localDataSource.updateTvByVideo(data.getVideo(),catalogId);
            }
        }.asLiveData();
    }

    @Override
    public void setMovieFavorite(MovieEntity movieEntity, boolean newState) {
        appExecutors.getDiskIO().execute(() -> localDataSource.setMovieFavorite(movieEntity,newState));
    }

    @Override
    public void setTvFavorite(TvEntity tvEntity, boolean newState) {
        appExecutors.getDiskIO().execute(() -> localDataSource.setTvFavorite(tvEntity,newState));
    }
}
