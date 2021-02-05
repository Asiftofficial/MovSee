package com.astech.movsee.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.data.source.remote.response.VideoResponse;
import com.astech.movsee.vo.Resource;

import java.util.List;

public interface MovSeeDataSource {

    LiveData<Resource<List<CatalogResponse>>> getAllMovies(String page);

    LiveData<Resource<List<CatalogResponse>>> getGenreMovies(String genre, String page);

    LiveData<Resource<List<CatalogResponse>>> getAllTvs(String page);

    LiveData<Resource<List<CatalogResponse>>> getGenreTvs(String genre,String page);

    LiveData<Resource<DetailResponse>> getDetailMovieRemote(String catalogId);

    LiveData<Resource<DetailResponse>> getDetailTvRemote(String catalogId);

    LiveData<PagedList<MovieDetailEntity>> getFavoriteMovies();

    LiveData<MovieDetailEntity> getMovieDetailLocal(String catalogId);

    LiveData<PagedList<TvDetailEntity>> getFavoriteTvs();

    LiveData<TvDetailEntity> getTvDetailLocal(String catalogId);

    LiveData<Resource<MovieDetailEntity>> getMovieVideo(String catalogId);

    LiveData<Resource<TvDetailEntity>> getTvVideo(String catalogId);

    LiveData<Resource<List<GenreResponse>>> getGenre(String identity);

    LiveData<Resource<VideoResponse>> getMovieVideoRemote(String catalogId);

    LiveData<Resource<VideoResponse>> getTvVideoRemote(String catalogId);

    LiveData<Resource<List<CatalogResponse>>> getSearchMovies(String query,String page);

    LiveData<Resource<List<CatalogResponse>>> getSearchTvs(String query,String page);

    void deleteMovieFavorite(MovieDetailEntity movieDetailEntity);

    void deleteTvFavorite(TvDetailEntity tvDetailEntity);

    void insertFavoriteMovie(DetailResponse detailResponse);

    void insertFavoriteTv(DetailResponse detailResponse);
}
