package com.astech.movsee.ui.tv;

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
public class TvViewModelTest {
    private TvViewModel tvViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MovSeeRepository movSeeRepository;

    @Mock
    private Observer<Resource<List<TvEntity>>> observer;

    @Mock
    private PagedList<TvEntity> pagedList;

    @Before
    public void setUp(){
        tvViewModel = new TvViewModel(movSeeRepository);
    }

    @Test
    public void getTvs() {
        Resource<List<TvEntity>> dummyTvs = Resource.success(DataDummy.getTvsDummy());
        MutableLiveData<Resource<List<TvEntity>>> tvResults = new MutableLiveData<>();
        tvResults.setValue(dummyTvs);

        when(movSeeRepository.getAllTvs()).thenReturn(tvResults);
        List<TvEntity> catalogEntities = tvViewModel.getTvs().getValue().data;
        verify(movSeeRepository).getAllTvs();
        assertNotNull(catalogEntities);

        tvViewModel.getTvs().observeForever(observer);
        verify(observer).onChanged(dummyTvs);
    }
}