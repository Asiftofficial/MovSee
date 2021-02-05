package com.astech.movsee.ui.favorite.movies;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.astech.movsee.data.source.MovSeeRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteMoviesViewModelTest {
    private FavoriteMoviesViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovSeeRepository movSeeRepository;

    @Mock
    private Observer<PagedList<MovieEntity>> observer;

    @Mock
    private PagedList<MovieEntity> pagedList;

    @Before
    public void setUp(){
        viewModel = new FavoriteMoviesViewModel(movSeeRepository);
    }

    @Test
    public void getFavoriteMovies() {
        PagedList<MovieEntity> dummyMovies = pagedList;
        when(dummyMovies.size()).thenReturn(5);
        MutableLiveData<PagedList<MovieEntity>> resultMovie = new MutableLiveData<>();
        resultMovie.setValue(dummyMovies);

        when(movSeeRepository.getFavoriteMovies()).thenReturn(resultMovie);
        PagedList<MovieEntity> movieEntities = viewModel.getFavoriteMovies().getValue();
        verify(movSeeRepository).getFavoriteMovies();
        assertNotNull(movieEntities);
        assertEquals(5,movieEntities.size());

        viewModel.getFavoriteMovies().observeForever(observer);
        verify(observer).onChanged(pagedList);
    }
}