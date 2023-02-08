package com.vp.list.viewmodel;

import com.vp.list.model.SearchResponse;
import com.vp.list.service.SearchService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import retrofit2.mock.Calls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskRule = new InstantTaskExecutorRule();

    private final SearchService searchService = mock(SearchService.class);
    private final ListViewModel listViewModel = new ListViewModel(searchService);
    private final SearchResponse searchResponse = mock(SearchResponse.class);
    private final Observer<SearchResult> mockObserver = (Observer<SearchResult>) mock(Observer.class);

    @Before
    public void init() {
        listViewModel.observeMovies().observeForever(mockObserver);
    }

    @Test
    public void shouldReturnErrorState() {
        //given
        when(searchService.search(anyString(), anyInt())).thenReturn(Calls.failure(new IOException()));

        //when
        listViewModel.searchMoviesByTitle("title", 1);

        //then
        verify(mockObserver).onChanged(SearchResult.inProgress());
        verify(mockObserver).onChanged(SearchResult.error());
        assertThat(listViewModel.observeMovies().getValue().getListState()).isEqualTo(ListState.ERROR);
    }

    @Test
    public void shouldReturnSuccessState() {
        //given
        when(searchService.search(anyString(), anyInt())).thenReturn(Calls.response(searchResponse));

        //when
        listViewModel.searchMoviesByTitle("title", 1);

        //then
        verify(mockObserver).onChanged(SearchResult.inProgress());
        verify(mockObserver).onChanged(SearchResult.success(searchResponse.getSearch(), searchResponse.getTotalResults()));
        assertThat(listViewModel.observeMovies().getValue().getListState()).isEqualTo(ListState.LOADED);
    }

}