package com.astech.movsee.data.source.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astech.movsee.R;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.DetailResponse;
import com.astech.movsee.data.source.remote.response.GenreResponse;
import com.astech.movsee.data.source.remote.response.VideoResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class JsonHelper {
    private Context context;

    public JsonHelper(Context context) {
        this.context = context;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> loadMovies(String page){
        MutableLiveData<ApiResponse<List<CatalogResponse>>> resultMovies = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US&page=" + page + "&without_genres=10749";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<CatalogResponse> movies = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listJson = jsonObject.getJSONArray("results");

                    if (listJson.length()>0){
                        for (int i=0;i<listJson.length();i++){
                            CatalogResponse model = new CatalogResponse();
                            model.setCatalogId(String.valueOf(listJson.getJSONObject(i).getInt("id")));
                            if(!listJson.getJSONObject(i).isNull("poster_path")){
                                model.setPoster(listJson.getJSONObject(i).getString("poster_path"));
                            }else {
                                model.setPoster("");
                            }
                            model.setScore(String.valueOf(listJson.getJSONObject(i).getDouble("vote_average")));
                            movies.add(model);
                        }

                        CatalogResponse model = new CatalogResponse();
                        model.setCatalogId("0");
                        model.setPoster("");
                        model.setScore("0.0");
                        movies.add(model);

                        resultMovies.postValue(ApiResponse.success(movies));
                    }else {
                        resultMovies.postValue(ApiResponse.empty(null));
                    }


                }catch (Exception e){
                    resultMovies.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultMovies.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return resultMovies;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> loadGenreMovies(String genre,String page){
        MutableLiveData<ApiResponse<List<CatalogResponse>>> resultMovies = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US&page=" + page + "&with_genres=" + genre;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<CatalogResponse> movies = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listJson = jsonObject.getJSONArray("results");
                    if (listJson.length()>0){
                        for (int i=0;i<listJson.length();i++){
                            CatalogResponse model = new CatalogResponse();
                            model.setCatalogId(String.valueOf(listJson.getJSONObject(i).getInt("id")));
                            if(!listJson.getJSONObject(i).isNull("poster_path")){
                                model.setPoster(listJson.getJSONObject(i).getString("poster_path"));
                            }else {
                                model.setPoster("");
                            }
                            model.setScore(String.valueOf(listJson.getJSONObject(i).getDouble("vote_average")));
                            movies.add(model);
                        }

                        CatalogResponse model = new CatalogResponse();
                        model.setCatalogId("0");
                        model.setPoster("");
                        model.setScore("0.0");
                        movies.add(model);

                        resultMovies.postValue(ApiResponse.success(movies));
                    }else {
                        resultMovies.postValue(ApiResponse.empty(null));
                    }


                }catch (Exception e){
                    resultMovies.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultMovies.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return resultMovies;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> loadTvs(String page){
        MutableLiveData<ApiResponse<List<CatalogResponse>>> resultTvs = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/discover/tv?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US&page=" + page + "&without_genres=10749";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<CatalogResponse> tvs = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listJson = jsonObject.getJSONArray("results");
                    if (listJson.length()>0){
                        for (int i=0;i<listJson.length();i++){
                            CatalogResponse model = new CatalogResponse();
                            model.setCatalogId(String.valueOf(listJson.getJSONObject(i).getInt("id")));
                            if(!listJson.getJSONObject(i).isNull("poster_path")){
                                model.setPoster(listJson.getJSONObject(i).getString("poster_path"));
                            }else {
                                model.setPoster("");
                            }
                            model.setScore(String.valueOf(listJson.getJSONObject(i).getDouble("vote_average")));
                            tvs.add(model);
                        }

                        CatalogResponse model = new CatalogResponse();
                        model.setCatalogId("0");
                        model.setPoster("");
                        model.setScore("0.0");
                        tvs.add(model);

                        resultTvs.postValue(ApiResponse.success(tvs));
                    }else {
                        resultTvs.postValue(ApiResponse.empty(null));
                    }


                }catch (Exception e){
                    resultTvs.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultTvs.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });
        return  resultTvs;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> loadGenreTvs(String genre,String page){
        MutableLiveData<ApiResponse<List<CatalogResponse>>> resultTvs = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/discover/tv?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US&page=" + page + "&with_genres=" + genre;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<CatalogResponse> tvs = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listJson = jsonObject.getJSONArray("results");
                    if (listJson.length()>0){
                        for (int i=0;i<listJson.length();i++){
                            CatalogResponse model = new CatalogResponse();
                            model.setCatalogId(String.valueOf(listJson.getJSONObject(i).getInt("id")));
                            if(!listJson.getJSONObject(i).isNull("poster_path")){
                                model.setPoster(listJson.getJSONObject(i).getString("poster_path"));
                            }else {
                                model.setPoster("");
                            }
                            model.setScore(String.valueOf(listJson.getJSONObject(i).getDouble("vote_average")));
                            tvs.add(model);
                        }

                        CatalogResponse model = new CatalogResponse();
                        model.setCatalogId("0");
                        model.setPoster("");
                        model.setScore("0.0");
                        tvs.add(model);

                        resultTvs.postValue(ApiResponse.success(tvs));
                    }else {
                        resultTvs.postValue(ApiResponse.empty(null));
                    }


                }catch (Exception e){
                    resultTvs.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultTvs.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });
        return  resultTvs;
    }

    public LiveData<ApiResponse<DetailResponse>> loadDetailMovie(String catalogId){
        MutableLiveData<ApiResponse<DetailResponse>> resultDetailMovie = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/movie/"+ catalogId +"?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    DetailResponse data = new DetailResponse();
                    data.setCatalogId(catalogId);
                    data.setTitle(jsonObject.getString("title"));
                    if(!jsonObject.isNull("poster_path")){
                        data.setPoster(jsonObject.getString("poster_path"));
                    }else {
                        data.setPoster("");
                    }
                    data.setScore(String.valueOf(jsonObject.getDouble("vote_average")));
                    data.setOverview(jsonObject.getString("overview"));
                    data.setRelease(jsonObject.getString("release_date"));
                    JSONArray listGenre = jsonObject.getJSONArray("genres");
                    String genre = "";
                    for (int i=0;i<listGenre.length();i++){
                        genre += listGenre.getJSONObject(i).getString("name");
                        genre += "\n";
                    }
                    data.setGenre(genre);
                    data.setMinute(String.valueOf(jsonObject.getInt("runtime")));
                    resultDetailMovie.postValue(ApiResponse.success(data));

                }catch (Exception e){
                    resultDetailMovie.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultDetailMovie.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return resultDetailMovie;
    }

    public LiveData<ApiResponse<DetailResponse>> loadDetailTv(String catalogId){
        MutableLiveData<ApiResponse<DetailResponse>> resultDetailTv = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/tv/"+ catalogId +"?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    DetailResponse data = new DetailResponse();
                    data.setCatalogId(catalogId);
                    data.setTitle(jsonObject.getString("name"));
                    if(!jsonObject.isNull("poster_path")){
                        data.setPoster(jsonObject.getString("poster_path"));
                    }else {
                        data.setPoster("");
                    }
                    data.setScore(String.valueOf(jsonObject.getDouble("vote_average")));
                    data.setOverview(jsonObject.getString("overview"));
                    data.setRelease(jsonObject.getString("first_air_date"));
                    JSONArray listGenre = jsonObject.getJSONArray("genres");
                    String genre = "";
                    for (int i=0;i<listGenre.length();i++){
                        genre += listGenre.getJSONObject(i).getString("name");
                        genre += "\n";
                    }
                    data.setGenre(genre);
                    data.setMinute(String.valueOf(jsonObject.getJSONArray("episode_run_time").getInt(0)));
                    resultDetailTv.postValue(ApiResponse.success(data));

                }catch (Exception e){
                    resultDetailTv.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultDetailTv.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return resultDetailTv;
    }

    public LiveData<ApiResponse<VideoResponse>> loadVideo(String catalogId, String identity){
        MutableLiveData<ApiResponse<VideoResponse>> resultVideo = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/"+ identity +"/"+ catalogId +"/videos?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listVideo = jsonObject.getJSONArray("results");
                    if (listVideo.length()>0){
                        VideoResponse model = new VideoResponse();
                        model.setCatalogId(catalogId);
                        model.setVideo(listVideo.getJSONObject(0).getString("key"));
                        resultVideo.postValue(ApiResponse.success(model));
                    }else {
                        resultVideo.postValue(ApiResponse.empty(null));
                    }
                }catch (Exception e){
                    resultVideo.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultVideo.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return resultVideo;
    }

    public LiveData<ApiResponse<List<GenreResponse>>> getGenres(String identity){
        MutableLiveData<ApiResponse<List<GenreResponse>>> result = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/genre/"+ identity +"/list?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<GenreResponse> list = new ArrayList<>();
                    String data = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("genres");
                    for (int i=0;i<jsonArray.length();i++){
                        if (jsonArray.getJSONObject(i).getString("name").equals("Romance")) continue;
                        GenreResponse genre = new GenreResponse();
                        genre.setGenreId(String.valueOf(jsonArray.getJSONObject(i).getInt("id")));
                        genre.setName(jsonArray.getJSONObject(i).getString("name"));
                        list.add(genre);
                    }
                    result.postValue(ApiResponse.success(list));
                }catch (JSONException e){
                    result.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                result.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return result;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> searchMovies(String query,String page){
        MutableLiveData<ApiResponse<List<CatalogResponse>>> resultMovies = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/search/movie?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US&query="+ query +"&page=" + page;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<CatalogResponse> movies = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listJson = jsonObject.getJSONArray("results");

                    if (listJson.length()>0){
                        for (int i=0;i<listJson.length();i++){
                            CatalogResponse model = new CatalogResponse();
                            model.setCatalogId(String.valueOf(listJson.getJSONObject(i).getInt("id")));
                            if(!listJson.getJSONObject(i).isNull("poster_path")){
                                model.setPoster(listJson.getJSONObject(i).getString("poster_path"));
                            }else {
                                model.setPoster("");
                            }
                            model.setScore(String.valueOf(listJson.getJSONObject(i).getDouble("vote_average")));
                            movies.add(model);
                        }

                        CatalogResponse model = new CatalogResponse();
                        model.setCatalogId("0");
                        model.setPoster("");
                        model.setScore("0.0");
                        movies.add(model);

                        resultMovies.postValue(ApiResponse.success(movies));
                    }else {
                        resultMovies.postValue(ApiResponse.empty(null));
                    }


                }catch (Exception e){
                    resultMovies.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultMovies.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });

        return resultMovies;
    }

    public LiveData<ApiResponse<List<CatalogResponse>>> searchTvs(String query,String page){
        MutableLiveData<ApiResponse<List<CatalogResponse>>> resultTvs = new MutableLiveData<>();
        String url = "http://api.themoviedb.org/3/search/tv?api_key=e9636c0f8cafe0a1a6e424a8b97a1a02&language=en-US&query="+ query +"&page=" + page;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<CatalogResponse> tvs = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray listJson = jsonObject.getJSONArray("results");

                    if (listJson.length()>0){
                        for (int i=0;i<listJson.length();i++){
                            CatalogResponse model = new CatalogResponse();
                            model.setCatalogId(String.valueOf(listJson.getJSONObject(i).getInt("id")));
                            if(!listJson.getJSONObject(i).isNull("poster_path")){
                                model.setPoster(listJson.getJSONObject(i).getString("poster_path"));
                            }else {
                                model.setPoster("");
                            }
                            model.setScore(String.valueOf(listJson.getJSONObject(i).getDouble("vote_average")));
                            tvs.add(model);
                        }

                        CatalogResponse model = new CatalogResponse();
                        model.setCatalogId("0");
                        model.setPoster("");
                        model.setScore("0.0");
                        tvs.add(model);

                        resultTvs.postValue(ApiResponse.success(tvs));
                    }else {
                        resultTvs.postValue(ApiResponse.empty(null));
                    }


                }catch (Exception e){
                    resultTvs.postValue(ApiResponse.empty(null));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                resultTvs.postValue(ApiResponse.error(context.getResources().getString(R.string.error_result),null));
            }
        });
        return  resultTvs;
    }

}
