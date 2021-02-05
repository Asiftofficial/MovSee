package com.astech.movsee.data.source.remote;

import androidx.lifecycle.LiveData;

import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.data.source.remote.response.VideoResponse;

import java.util.List;

public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;
    private JsonHelper jsonHelper;

    private RemoteDataSource(JsonHelper jsonHelper){
        this.jsonHelper = jsonHelper;
    }

    public static RemoteDataSource getInstance(JsonHelper helper){
        if (INSTANCE == null){
            INSTANCE = new RemoteDataSource(helper);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> getAllMovies(String page){
        LiveData<ApiResponse<List<CatalogResponse>>> result = jsonHelper.loadMovies(page);
        return result;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> getGenreMovies(String genre,String page){
        LiveData<ApiResponse<List<CatalogResponse>>> result = jsonHelper.loadGenreMovies(genre,page);
        return result;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> getAllTvs(String page){
        LiveData<ApiResponse<List<CatalogResponse>>> result = jsonHelper.loadTvs(page);
        return result;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> getGenreTvs(String genre,String page){
        LiveData<ApiResponse<List<CatalogResponse>>> result = jsonHelper.loadGenreTvs(genre,page);
        return result;
    }

    public LiveData<ApiResponse<DetailResponse>> getDetailMovie(String catalogId){
        LiveData<ApiResponse<DetailResponse>> result = jsonHelper.loadDetailMovie(catalogId);
        return result;
    }

    public LiveData<ApiResponse<DetailResponse>> getDetailTv(String catalogId){
        LiveData<ApiResponse<DetailResponse>> result = jsonHelper.loadDetailTv(catalogId);
        return result;
    }

    public LiveData<ApiResponse<VideoResponse>> getVideo(String catalogId, String identity){
        LiveData<ApiResponse<VideoResponse>> result = jsonHelper.loadVideo(catalogId,identity);
        return result;
    }

    public LiveData<ApiResponse<List<GenreResponse>>> getGenres(String identity){
        LiveData<ApiResponse<List<GenreResponse>>> result = jsonHelper.getGenres(identity);
        return result;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> getSearchMovies(String query,String page){
        LiveData<ApiResponse<List<CatalogResponse>>> result = jsonHelper.searchMovies(query,page);
        return result;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> getSearchTvs(String query,String page){
        LiveData<ApiResponse<List<CatalogResponse>>> result = jsonHelper.searchTvs(query,page);
        return result;
    }



}
