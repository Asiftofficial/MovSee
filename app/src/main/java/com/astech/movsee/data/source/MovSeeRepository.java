package com.astech.movsee.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.local.LocalDataSource;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.ApiResponse;
import com.astech.movsee.data.source.remote.RemoteDataSource;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.data.source.remote.response.VideoResponse;
import com.astech.movsee.utils.AppExecutors;
import com.astech.movsee.vo.Resource;

import java.util.List;

public class MovSeeRepository implements MovSeeDataSource {
    private volatile static MovSeeRepository INSTANCE = null;
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private AppExecutors appExecutors;

    private MovSeeRepository(RemoteDataSource remoteDataSource,LocalDataSource localDataSource, AppExecutors appExecutors){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
    }

    public static MovSeeRepository getInstance(RemoteDataSource remote,LocalDataSource local,AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (MovSeeRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new MovSeeRepository(remote,local,appExecutors);
                }
            }
        }

        return INSTANCE;
    }


    @Override
    public LiveData<Resource<List<CatalogResponse>>> getAllMovies(String page) {
        MediatorLiveData<Resource<List<CatalogResponse>>> mediator = new MediatorLiveData<>();
       LiveData<ApiResponse<List<CatalogResponse>>> result = remoteDataSource.getAllMovies(page);
       mediator.addSource(result, data -> {
           switch (data.status){
               case EMPTY:
               case SUCCESS:
                   mediator.setValue(Resource.success(data.body));
                   break;
               case ERROR:
                   mediator.setValue(Resource.error(data.message,data.body));
                   break;
           }
       });

       return mediator;
    }

    @Override
    public LiveData<Resource<List<CatalogResponse>>> getGenreMovies(String genre, String page) {
        MediatorLiveData<Resource<List<CatalogResponse>>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<List<CatalogResponse>>> result = remoteDataSource.getGenreMovies(genre,page);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public LiveData<Resource<List<CatalogResponse>>> getAllTvs(String page) {
        MediatorLiveData<Resource<List<CatalogResponse>>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<List<CatalogResponse>>> result = remoteDataSource.getAllTvs(page);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public LiveData<Resource<List<CatalogResponse>>> getGenreTvs(String genre, String page) {
        MediatorLiveData<Resource<List<CatalogResponse>>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<List<CatalogResponse>>> result = remoteDataSource.getGenreTvs(genre,page);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public LiveData<Resource<DetailResponse>> getDetailMovieRemote(String catalogId) {
        MediatorLiveData<Resource<DetailResponse>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<DetailResponse>> result = remoteDataSource.getDetailMovie(catalogId);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public LiveData<Resource<DetailResponse>> getDetailTvRemote(String catalogId) {
        MediatorLiveData<Resource<DetailResponse>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<DetailResponse>> result = remoteDataSource.getDetailTv(catalogId);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public LiveData<PagedList<MovieDetailEntity>> getFavoriteMovies() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(8)
                .setPageSize(8)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getAllFavoriteMovies(),config).build();
    }

    @Override
    public LiveData<MovieDetailEntity> getMovieDetailLocal(String catalogId) {
        return localDataSource.getMovieDetail(catalogId);
    }

    @Override
    public LiveData<PagedList<TvDetailEntity>> getFavoriteTvs() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(8)
                .setPageSize(8)
                .build();
        return new LivePagedListBuilder<>(localDataSource.getAllFavoriteTvs(),config).build();
    }

    @Override
    public LiveData<TvDetailEntity> getTvDetailLocal(String catalogId) {
        return localDataSource.getTvDetail(catalogId);
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
    public LiveData<Resource<List<GenreResponse>>> getGenre(String identity) {
        MediatorLiveData<Resource<List<GenreResponse>>> result = new MediatorLiveData<>();
        LiveData<ApiResponse<List<GenreResponse>>> data = remoteDataSource.getGenres(identity);
        result.addSource(data, remote -> {
            switch (remote.status){
                case SUCCESS:
                    result.setValue(Resource.success(remote.body));
                    break;
                case ERROR:
                    result.setValue(Resource.error(remote.message,remote.body));
                    break;
            }
        });

        return result;
    }

    @Override
    public LiveData<Resource<VideoResponse>> getMovieVideoRemote(String catalogId) {
        MediatorLiveData<Resource<VideoResponse>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<VideoResponse>> result = remoteDataSource.getVideo(catalogId,"movie");
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });
        return mediator;
    }

    @Override
    public LiveData<Resource<VideoResponse>> getTvVideoRemote(String catalogId) {
        MediatorLiveData<Resource<VideoResponse>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<VideoResponse>> result = remoteDataSource.getVideo(catalogId,"tv");
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });
        return mediator;
    }

    @Override
    public LiveData<Resource<List<CatalogResponse>>> getSearchMovies(String query,String page) {
        MediatorLiveData<Resource<List<CatalogResponse>>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<List<CatalogResponse>>> result = remoteDataSource.getSearchMovies(query,page);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public LiveData<Resource<List<CatalogResponse>>> getSearchTvs(String query,String page) {
        MediatorLiveData<Resource<List<CatalogResponse>>> mediator = new MediatorLiveData<>();
        LiveData<ApiResponse<List<CatalogResponse>>> result = remoteDataSource.getSearchTvs(query,page);
        mediator.addSource(result, data -> {
            switch (data.status){
                case EMPTY:
                case SUCCESS:
                    mediator.setValue(Resource.success(data.body));
                    break;
                case ERROR:
                    mediator.setValue(Resource.error(data.message,data.body));
                    break;
            }
        });

        return mediator;
    }

    @Override
    public void deleteMovieFavorite(MovieDetailEntity movieDetailEntity) {
        appExecutors.getDiskIO().execute(() -> localDataSource.deleteMovieFavorite(movieDetailEntity));
    }

    @Override
    public void deleteTvFavorite(TvDetailEntity tvDetailEntity) {
        appExecutors.getDiskIO().execute(() -> localDataSource.deleteTvFavorite(tvDetailEntity));
    }

    @Override
    public void insertFavoriteMovie(DetailResponse detailResponse) {
        MovieDetailEntity result = new MovieDetailEntity(detailResponse.getCatalogId(),
                detailResponse.getTitle(),
                detailResponse.getPoster(),
                detailResponse.getOverview(),
                detailResponse.getRelease(),
                detailResponse.getGenre(),
                detailResponse.getMinute(),
                detailResponse.getScore());
        appExecutors.getDiskIO().execute(() -> localDataSource.insertMovieDetail(result));
    }

    @Override
    public void insertFavoriteTv(DetailResponse detailResponse) {
        TvDetailEntity result = new TvDetailEntity(detailResponse.getCatalogId(),
                detailResponse.getTitle(),
                detailResponse.getPoster(),
                detailResponse.getOverview(),
                detailResponse.getRelease(),
                detailResponse.getGenre(),
                detailResponse.getMinute(),
                detailResponse.getScore());
        appExecutors.getDiskIO().execute(() -> localDataSource.insertTvDetail(result));
    }
}
