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
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    private val detailsDisposable: CompositeDisposable = CompositeDisposable()
    private val saveFavoriteDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        detailsDisposable.clear()
        saveFavoriteDisposable.clear()
    }

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
                            loadingState.value = LoadingState.LOADED
                        }, {
                            details.postValue(null)
                            loadingState.value = LoadingState.ERROR
                        })
        )
    }

    fun onFavoriteClick(isChecked: Boolean) {
        val isFavorite = !isChecked
        details.value = details.value?.copy(isFavorite = isFavorite)
        details.value?.let {
            saveFavoriteDisposable.add(
                    setFavoriteMovie(
                            movieDetail = it,
                            isFavorite = isFavorite
                    )
                            .observeOn(threadScheduler.getMainThread())
                            .subscribeOn(threadScheduler.getIoThread())
                            .subscribe({}, {})
            )
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}