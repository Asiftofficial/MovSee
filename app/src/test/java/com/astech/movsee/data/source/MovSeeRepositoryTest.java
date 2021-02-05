package com.astech.movsee.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.local.LocalDataSource;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.data.source.remote.RemoteDataSource;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.data.source.remote.response.VideoResponse;
import com.astech.movsee.utils.AppExecutors;
import com.astech.movsee.utils.DataDummy;
import com.astech.movsee.utils.LiveDataTestUtil;
import com.astech.movsee.utils.PagedListUtil;
import com.astech.movsee.vo.Resource;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovSeeRepositoryTest {

    private RemoteDataSource remoteDataSource = Mockito.mock(RemoteDataSource.class);
    private LocalDataSource localDataSource = Mockito.mock(LocalDataSource.class);
    private AppExecutors appExecutors = Mockito.mock(AppExecutors.class);

    private FakeMovSeeRepository movSeeRepository = new FakeMovSeeRepository(remoteDataSource,localDataSource,appExecutors);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private List<CatalogResponse> movieResponses = DataDummy.getRemoteMoviesDummy();
    private String movieId = movieResponses.get(0).getCatalogId();
    private VideoResponse videoResponse = DataDummy.getRemoteVideoDummy(movieId);

    private List<CatalogResponse> tvResponses = DataDummy.getRemoteTvsDummy();
    private String tvId = tvResponses.get(0).getCatalogId();

    @Test
    public void getAllMovies() {
        List<MovieEntity> dummyMovies = DataDummy.getMoviesDummy();
        MutableLiveData<List<MovieEntity>> resultMovies = new MutableLiveData<>();
        resultMovies.setValue(dummyMovies);
        when(localDataSource.getAllMovies()).thenReturn(resultMovies);

        Resource<List<MovieEntity>> movieResults = LiveDataTestUtil.getValue(movSeeRepository.getAllMovies());
        verify(localDataSource).getAllMovies();
        assertNotNull(movieResults.data);
        assertEquals(movieResults.data.size(),movieResponses.size());
    }

    @Test
    public void getAllTvs() {
        List<TvEntity> dummyTvs = DataDummy.getTvsDummy();
        MutableLiveData<List<TvEntity>> resultTvs = new MutableLiveData<>();
        resultTvs.setValue(dummyTvs);
        when(localDataSource.getAllTvs()).thenReturn(resultTvs);

        Resource<List<TvEntity>> tvResults = LiveDataTestUtil.getValue(movSeeRepository.getAllTvs());
        verify(localDataSource).getAllTvs();
        assertNotNull(tvResults.data);
        assertEquals(tvResults.data.size(),tvResponses.size());
    }

    @Test
    public void getMovieWithDetail() {
        MutableLiveData<MovieWithDetail> dummyDetail = new MutableLiveData<>();
        dummyDetail.setValue(DataDummy.generateDummyMovieWithDetail(DataDummy.getMoviesDummy().get(0),false));
        when(localDataSource.getMovieWithDetail(movieId)).thenReturn(dummyDetail);

        Resource<MovieWithDetail> movieResult = LiveDataTestUtil.getValue(movSeeRepository.getMovieWithDetail(movieId));
        verify(localDataSource).getMovieWithDetail(movieId);
        assertNotNull(movieResult.data);
        assertNotNull(movieResult.data.movieEntity.getCatalogId());
    }

    @Test
    public void getTvWithDetail() {
        MutableLiveData<TvWithDetail> dummyDetail = new MutableLiveData<>();
        dummyDetail.setValue(DataDummy.generateDummyTvWithDetail(DataDummy.getTvsDummy().get(0),false));
        when(localDataSource.getTvWithDetail(tvId)).thenReturn(dummyDetail);

        Resource<TvWithDetail> tvResult = LiveDataTestUtil.getValue(movSeeRepository.getTvWithDetail(tvId));
        verify(localDataSource).getTvWithDetail(tvId);
        assertNotNull(tvResult.data);
        assertNotNull(tvResult.data.tvEntity.getCatalogId());
    }

    @Test
    public void getMovieVideo() {
        MutableLiveData<MovieDetailEntity> dummyVideo = new MutableLiveData<>();
        dummyVideo.setValue(DataDummy.getDummyMovieVideo(movieId));
        when(localDataSource.getMovieDetailWithVideo(movieId)).thenReturn(dummyVideo);

        Resource<MovieDetailEntity> videoEntity = LiveDataTestUtil.getValue(movSeeRepository.getMovieVideo(movieId));
        verify(localDataSource).getMovieDetailWithVideo(movieId);
        assertNotNull(videoEntity.data);
        assertNotNull(videoEntity.data.videoEntity);
        assertEquals(videoEntity.data.videoEntity.getVideo(),videoResponse.getVideo());
    }

    @Test
    public void getTvVideo() {
        MutableLiveData<TvDetailEntity> dummyVideo = new MutableLiveData<>();
        dummyVideo.setValue(DataDummy.getDummyTvVideo(tvId));
        when(localDataSource.getTvDetailWithVideo(tvId)).thenReturn(dummyVideo);

        Resource<TvDetailEntity> videoEntity = LiveDataTestUtil.getValue(movSeeRepository.getTvVideo(tvId));
        verify(localDataSource).getTvDetailWithVideo(tvId);
        assertNotNull(videoEntity.data);
        assertNotNull(videoEntity.data.videoEntity);
        assertEquals(videoEntity.data.videoEntity.getVideo(),videoResponse.getVideo());
    }

    @Test
    public void getFavoriteMovies(){
        DataSource.Factory<Integer, MovieEntity> dummyFavorite = mock(DataSource.Factory.class);
        when(localDataSource.getAllFavoriteMovies()).thenReturn(dummyFavorite);
        movSeeRepository.getFavoriteMovies();

        PagedList<MovieEntity> result = PagedListUtil.mockPagedList(DataDummy.getMoviesDummy());
        verify(localDataSource).getAllFavoriteMovies();
        assertNotNull(result);
        assertEquals(result.size(),movieResponses.size());
    }

    @Test
    public void getFavoriteTvs(){
        DataSource.Factory<Integer, TvEntity> dummyFavorite = mock(DataSource.Factory.class);
        when(localDataSource.getAllFavoriteTvs()).thenReturn(dummyFavorite);
        movSeeRepository.getFavoriteTvs();

        PagedList<TvEntity> result = PagedListUtil.mockPagedList(DataDummy.getTvsDummy());
        verify(localDataSource).getAllFavoriteTvs();
        assertNotNull(result);
        assertEquals(result.size(),tvResponses.size());
    }
}