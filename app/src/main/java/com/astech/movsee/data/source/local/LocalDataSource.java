package com.astech.movsee.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.local.room.MovSeeDao;

public class LocalDataSource {
    private static LocalDataSource INSTANCE;
    private MovSeeDao movSeeDao;

    private LocalDataSource(MovSeeDao movSeeDao){
        this.movSeeDao = movSeeDao;
    }

    public static LocalDataSource getInstance(MovSeeDao movSeeDao){
        if (INSTANCE == null){
            INSTANCE = new LocalDataSource(movSeeDao);
        }

        return INSTANCE;
    }

    public DataSource.Factory<Integer, MovieDetailEntity> getAllFavoriteMovies(){
        return movSeeDao.getFavoriteMovies();
    }

    public DataSource.Factory<Integer, TvDetailEntity> getAllFavoriteTvs(){
        return movSeeDao.getFavoriteTvs();
    }

    public LiveData<MovieDetailEntity> getMovieDetail(String catalogId){
        return movSeeDao.getMovieDetailById(catalogId);
    }

    public LiveData<TvDetailEntity> getTvDetail(String catalogId){
        return movSeeDao.getTvDetailById(catalogId);
    }

    public LiveData<MovieDetailEntity> getMovieDetailWithVideo(String catalogId){
        return movSeeDao.getMovieDetailById(catalogId);
    }

    public LiveData<TvDetailEntity> getTvDetailWithVideo(String catalogId){
        return movSeeDao.getTvDetailById(catalogId);
    }

    public void insertMovieDetail(MovieDetailEntity movieDetailEntity){
        movSeeDao.insertMovies(movieDetailEntity);
    }

    public void insertTvDetail(TvDetailEntity tvDetailEntity){
        movSeeDao.insertTvs(tvDetailEntity);
    }

    public void deleteMovieFavorite(MovieDetailEntity movieDetailEntity){
        movSeeDao.deleteMovie(movieDetailEntity);
    }

    public void deleteTvFavorite(TvDetailEntity tvDetailEntity){
        movSeeDao.deleteTv(tvDetailEntity);
    }

    public void updateMovieByVideo(String video, String catalogId){
        movSeeDao.updateMovieDetailByVideo(video,catalogId);
    }

    public void updateTvByVideo(String video, String catalogId){
        movSeeDao.updateTvDetailByVideo(video,catalogId);
    }

}
