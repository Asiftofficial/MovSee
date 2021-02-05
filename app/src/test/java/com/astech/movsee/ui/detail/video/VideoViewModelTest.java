package com.astech.movsee.ui.detail.video;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.utils.DataDummy;
import com.astech.movsee.utils.LiveDataTestUtil;
import com.astech.movsee.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VideoViewModelTest {
    private VideoViewModel videoViewModel;
    private MovieEntity movie = DataDummy.getMoviesDummy().get(0);
    private TvEntity tv = DataDummy.getTvsDummy().get(0);
    private String idTv = tv.getCatalogId();
    private String idMovie = movie.getCatalogId();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovSeeRepository movSeeRepository;

    @Mock
    private Observer<Resource<MovieDetailEntity>> movieObserver;

    @Mock
    private Observer<Resource<TvDetailEntity>> tvObserver;

    @Before
    public void setUp(){
        videoViewModel = new VideoViewModel(movSeeRepository);
    }

    @Test
    public void getMovieVideo() {
        videoViewModel.setCatalogId(idMovie);
        Resource<MovieDetailEntity> dummyVideo = Resource.success(DataDummy.getDummyMovieVideo(idMovie));
        MutableLiveData<Resource<MovieDetailEntity>> videoResult = new MutableLiveData<>();
        videoResult.setValue(dummyVideo);

        when(movSeeRepository.getMovieVideo(idMovie)).thenReturn(videoResult);
        Resource<MovieDetailEntity> result = LiveDataTestUtil.getValue(videoViewModel.getMovieVideo);
        assertNotNull(result.data);
        assertNotNull(result.data.videoEntity);
        verify(movSeeRepository).getMovieVideo(idMovie);

        videoViewModel.getMovieVideo.observeForever(movieObserver);
        verify(movieObserver).onChanged(dummyVideo);
    }

    @Test
    public void geTvVideo() {
        videoViewModel.setCatalogId(idTv);
        Resource<TvDetailEntity> dummyVideo = Resource.success(DataDummy.getDummyTvVideo(idTv));
        MutableLiveData<Resource<TvDetailEntity>> videoResult = new MutableLiveData<>();
        videoResult.setValue(dummyVideo);

        when(movSeeRepository.getTvVideo(idTv)).thenReturn(videoResult);
        Resource<TvDetailEntity> result = LiveDataTestUtil.getValue(videoViewModel.getTvVideo);
        assertNotNull(result.data);
        assertNotNull(result.data.videoEntity);
        verify(movSeeRepository).getTvVideo(idTv);

        videoViewModel.getTvVideo.observeForever(tvObserver);
        verify(tvObserver).onChanged(dummyVideo);
    }
}