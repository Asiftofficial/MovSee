package com.astech.movsee.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;

@Dao
public interface MovSeeDao {

    @Query("SELECT * FROM movieDetailEntities")
    DataSource.Factory<Integer, MovieDetailEntity> getFavoriteMovies();

    @Query("SELECT * FROM tvDetailEntities")
    DataSource.Factory<Integer, TvDetailEntity> getFavoriteTvs();

    @Query("SELECT * FROM movieDetailEntities WHERE catalogId = :catalogId")
    LiveData<MovieDetailEntity> getMovieDetailById(String catalogId);

    @Query("SELECT * FROM tvDetailEntities WHERE catalogId = :catalogId")
    LiveData<TvDetailEntity> getTvDetailById(String catalogId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(MovieDetailEntity movieDetailEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvs(TvDetailEntity tvDetailEntity);

    @Delete
    void deleteMovie(MovieDetailEntity movieDetailEntity);

    @Delete
    void deleteTv(TvDetailEntity tvDetailEntity);

    @Query("UPDATE movieDetailEntities SET video = :video WHERE catalogId = :catalogId")
    void updateMovieDetailByVideo(String video, String catalogId);

    @Query("UPDATE tvDetailEntities SET video = :video WHERE catalogId = :catalogId")
    void updateTvDetailByVideo(String video, String catalogId);

}
