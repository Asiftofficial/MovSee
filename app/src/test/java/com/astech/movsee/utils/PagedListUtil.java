package com.astech.movsee.utils;

import androidx.paging.PagedList;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PagedListUtil {

    public static <T> PagedList<T> mockPagedList(List<T> list){
        PagedList<T> pagedList = mock(PagedList.class);

        Answer<T> answer = new Answer<T>() {
            @Override
            public T answer(InvocationOnMock invocation) throws Throwable {
                Integer index = invocation.getArgument(0);
                return list.get(index);
            }
        };

        when(pagedList.get(anyInt())).thenAnswer(answer);
        when(pagedList.size()).thenReturn(list.size());

        return pagedList;
    }
}
