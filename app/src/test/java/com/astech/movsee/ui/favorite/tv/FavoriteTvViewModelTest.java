package com.astech.movsee.ui.favorite.tv;

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
public class FavoriteTvViewModelTest {
    private FavoriteTvViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovSeeRepository movSeeRepository;

    @Mock
    private Observer<PagedList<TvEntity>> observer;

    @Mock
    private PagedList<TvEntity> pagedList;

    @Before
    public void setUp(){
        viewModel = new FavoriteTvViewModel(movSeeRepository);
    }

    @Test
    public void getFavoriteTvs() {
        PagedList<TvEntity> dummyFavorite = pagedList;
        when(dummyFavorite.size()).thenReturn(5);
        MutableLiveData<PagedList<TvEntity>> resultTv = new MutableLiveData<>();
        resultTv.setValue(dummyFavorite);

        when(movSeeRepository.getFavoriteTvs()).thenReturn(resultTv);
        PagedList<TvEntity> tvEntities = viewModel.getFavoriteTvs().getValue();
        verify(movSeeRepository).getFavoriteTvs();
        assertNotNull(tvEntities);
        assertEquals(5,tvEntities.size());

        viewModel.getFavoriteTvs().observeForever(observer);
        verify(observer).onChanged(dummyFavorite);
    }
}