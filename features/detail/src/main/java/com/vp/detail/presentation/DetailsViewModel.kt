package com.vp.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.core.presentation.ThreadScheduler
import com.vp.detail.domain.GetMovieDetails
import com.vp.detail.domain.MovieDetail
import com.vp.detail.domain.SetFavoriteMovie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
        private val getMovieDetails: GetMovieDetails,
        private val setFavoriteMovie: SetFavoriteMovie,
        private val threadScheduler: ThreadScheduler
) : ViewModel() {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    private val detailsDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        detailsDisposable.clear()
    }

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailsDisposable.clear()
        detailsDisposable.add(
                getMovieDetails(DetailActivity.queryProvider.getMovieId())
                        .observeOn(threadScheduler.getMainThread())
                        .subscribeOn(threadScheduler.getIoThread())
                        .subscribe({
                            details.postValue(it)
                            title.postValue(it.title)
                            loadingState.value = LoadingState.LOADED
                        }, {
                            details.postValue(null)
                            loadingState.value = LoadingState.ERROR
                        })
        )
    }

    fun onFavoriteClick(isChecked: Boolean) {
        details.value = details.value?.copy()
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}