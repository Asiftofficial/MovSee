package com.astech.movsee.ui.movies;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.utils.DataDummy;
import com.astech.movsee.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoviesViewModelTest {
    private MoviesViewModel moviesViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovSeeRepository movSeeRepository;

    @Mock
    private Observer<Resource<List<MovieEntity>>> observer;

    @Mock
    private PagedList<MovieEntity> pagedList;

    @Before
    public void setUp(){
        moviesViewModel = new MoviesViewModel(movSeeRepository);
    }

    @Test
    public void getMovies() {
        Resource<List<MovieEntity>> dummyMovies = Resource.success(DataDummy.getMoviesDummy());
        MutableLiveData<Resource<List<MovieEntity>>> movieResults = new MutableLiveData<>();
        movieResults.setValue(dummyMovies);

        when(movSeeRepository.getAllMovies()).thenReturn(movieResults);
        List<MovieEntity> catalogEntities = moviesViewModel.getMovies().getValue().data;
        verify(movSeeRepository).getAllMovies();
        assertNotNull(catalogEntities);

        moviesViewModel.getMovies().observeForever(observer);
        verify(observer).onChanged(dummyMovies);
    }
}