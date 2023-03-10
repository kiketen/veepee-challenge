package com.vp.list;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.vp.core.presentation.list.ListAdapter;
import com.vp.list.viewmodel.ListNavigation;
import com.vp.list.viewmodel.ListViewModel;
import com.vp.list.viewmodel.SearchResult;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ViewAnimator;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

import static com.vp.core.presentation.RouteConstantsKt.DEEPLINK_DETAIL;
import static com.vp.core.presentation.RouteConstantsKt.DEEPLINK_FAVORITES;
import static com.vp.core.presentation.RouteConstantsKt.DETAIL_ID;

public class ListFragment extends Fragment implements GridPagingScrollListener.LoadMoreItemsListener, ListAdapter.OnItemClickListener {

    public static final String TAG = "ListFragment";
    private static final String CURRENT_QUERY = "current_query";

    @Inject
    ViewModelProvider.Factory factory;

    private ListViewModel listViewModel;
    private GridPagingScrollListener gridPagingScrollListener;
    private ListAdapter listAdapter;
    private ViewAnimator viewAnimator;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout error;
    private Button retryButton;
    private String currentQuery = "Interview";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        listViewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        viewAnimator = view.findViewById(R.id.viewAnimator);
        progressBar = view.findViewById(R.id.progressBar);
        error = view.findViewById(R.id.error);
        retryButton = view.findViewById(R.id.retryButton);

        if (savedInstanceState != null) {
            currentQuery = savedInstanceState.getString(CURRENT_QUERY);
        }

        initBottomNavigation(view);
        initList();
        setObservers();
        setListeners();
        listViewModel.searchMoviesByTitle(currentQuery, 1);
        showProgressBar();
    }

    private void setObservers() {
        listViewModel.observeMovies().observe(getViewLifecycleOwner(), searchResult -> {
            if (searchResult != null) {
                handleResult(listAdapter, searchResult);
            }
        });
        listViewModel.observeNavigator().observe(getViewLifecycleOwner(), navigation -> {
            if (navigation instanceof ListNavigation.Detail) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DEEPLINK_DETAIL));
                intent.putExtra(DETAIL_ID, ((ListNavigation.Detail) navigation).getId());
                intent.setPackage(requireContext().getPackageName());
                startActivity(intent);
            }
        });
    }

    private void setListeners() {
        retryButton.setOnClickListener(view -> listViewModel.onRetryClick(currentQuery));
    }

    private void initBottomNavigation(@NonNull View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.favorites) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DEEPLINK_FAVORITES));
                intent.setPackage(requireContext().getPackageName());
                startActivity(intent);
            }
            return true;
        });
    }

    private void initList() {
        listAdapter = new ListAdapter();
        listAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);

        // Pagination
        gridPagingScrollListener = new GridPagingScrollListener(layoutManager);
        gridPagingScrollListener.setLoadMoreItemsListener(this);
        recyclerView.addOnScrollListener(gridPagingScrollListener);
    }

    private void showProgressBar() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(progressBar));
    }

    private void showList() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(recyclerView));
    }

    private void showError() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(error));
    }

    private void handleResult(@NonNull ListAdapter listAdapter, @NonNull SearchResult searchResult) {
        switch (searchResult.getListState()) {
            case LOADED: {
                setItemsData(listAdapter, searchResult);
                showList();
                break;
            }
            case IN_PROGRESS: {
                listAdapter.clearItems();
                showProgressBar();
                break;
            }
            default: {
                showError();
            }
        }
        gridPagingScrollListener.markLoading(false);
    }

    private void setItemsData(@NonNull ListAdapter listAdapter, @NonNull SearchResult searchResult) {
        listAdapter.setItems(searchResult.getItems());

        if (searchResult.getTotalResult() <= listAdapter.getItemCount()) {
            gridPagingScrollListener.markLastPage(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_QUERY, currentQuery);
    }

    @Override
    public void loadMoreItems(int page) {
        gridPagingScrollListener.markLoading(true);
        listViewModel.searchMoviesByTitle(currentQuery, page);
    }

    public void submitSearchQuery(@NonNull final String query) {
        currentQuery = query;
        listViewModel.searchMoviesByTitle(query, 1);
    }

    @Override
    public void onItemClick(String imdbID) {
        listViewModel.onItemClick(imdbID);
    }
}
