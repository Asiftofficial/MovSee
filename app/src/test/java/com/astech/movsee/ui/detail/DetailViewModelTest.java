package com.astech.movsee.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.astech.movsee.data.source.MovSeeRepository;
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
public class DetailViewModelTest {
    private DetailViewModel detailViewModel;
    private MovieEntity movie = DataDummy.getMoviesDummy().get(0);
    private String idMovie = movie.getCatalogId();

    private TvEntity tv = DataDummy.getTvsDummy().get(0);
    private String idTv = tv.getCatalogId();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovSeeRepository movSeeRepository;

    @Mock
    private Observer<Resource<MovieWithDetail>> movieObserver;

    @Mock
    private Observer<Resource<TvWithDetail>> tvObserver;

    @Before
    public void setUp(){
        detailViewModel = new DetailViewModel(movSeeRepository);
    }

    @Test
    public void getDetailMovie() {
        detailViewModel.setCatalogId(idMovie);
        Resource<MovieWithDetail> dummyMovie = Resource.success(DataDummy.generateDummyMovieWithDetail(movie,true));
        MutableLiveData<Resource<MovieWithDetail>> movieResult = new MutableLiveData<>();
        movieResult.setValue(dummyMovie);
        when(movSeeRepository.getMovieWithDetail(idMovie)).thenReturn(movieResult);

        Resource<MovieWithDetail> result = LiveDataTestUtil.getValue(detailViewModel.getDetailMovie);
        assertNotNull(result.data);
        verify(movSeeRepository).getMovieWithDetail(idMovie);

        detailViewModel.getDetailMovie.observeForever(movieObserver);
        verify(movieObserver).onChanged(dummyMovie);
    }

    @Test
    public void getDetailTv() {
        detailViewModel.setCatalogId(idTv);
        Resource<TvWithDetail> dummyTv = Resource.success(DataDummy.generateDummyTvWithDetail(tv,true));
        MutableLiveData<Resource<TvWithDetail>> tvResult = new MutableLiveData<>();
        tvResult.setValue(dummyTv);
        when(movSeeRepository.getTvWithDetail(idTv)).thenReturn(tvResult);

        Resource<TvWithDetail> result = LiveDataTestUtil.getValue(detailViewModel.getDetailTv);
        assertNotNull(result.data);
        verify(movSeeRepository).getTvWithDetail(idTv);

        detailViewModel.getDetailTv.observeForever(tvObserver);
        verify(tvObserver).onChanged(dummyTv);
    }
}