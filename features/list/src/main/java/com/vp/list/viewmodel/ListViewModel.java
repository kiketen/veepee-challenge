package com.vp.list.viewmodel;

import com.vp.core.presentation.SingleLiveEvent;
import com.vp.core.presentation.list.ListItem;
import com.vp.list.model.SearchResponse;
import com.vp.list.service.SearchService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {

    private MutableLiveData<SearchResult> searchResultLiveData = new MutableLiveData<>();
    private SingleLiveEvent<ListNavigation> navigatorLiveData = new SingleLiveEvent<>();

    private SearchService searchService;

    private String currentTitle = "";
    private List<ListItem> aggregatedItems = new ArrayList<>();

    @Inject
    ListViewModel(@NonNull SearchService searchService) {
        this.searchService = searchService;
    }

    public LiveData<SearchResult> observeMovies() {
        return searchResultLiveData;
    }

    public LiveData<ListNavigation> observeNavigator() {
        return navigatorLiveData;
    }

    public void searchMoviesByTitle(@NonNull String title, int page) {

        if (page == 1 && !title.equals(currentTitle)) {
            aggregatedItems.clear();
            currentTitle = title;
            searchResultLiveData.setValue(SearchResult.inProgress());
        }
        searchService.search(title, page).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {

                SearchResponse result = response.body();

                if (result != null) {
                    aggregatedItems.addAll(result.getSearch());
                    searchResultLiveData.setValue(SearchResult.success(
                            result.getSearch(),
                            result.getTotalResults()
                    ));
                } else {
                    handleError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                handleError();
            }

            private void handleError() {
                aggregatedItems.clear();
                currentTitle = null;
                searchResultLiveData.setValue(SearchResult.error());
            }
        });
    }

    public void onItemClick(String imdbID) {
        navigatorLiveData.setValue(new ListNavigation.Detail(imdbID));
    }

    public void onRetryClick(String currentQuery) {
        searchMoviesByTitle(currentQuery, 1);
    }
}
