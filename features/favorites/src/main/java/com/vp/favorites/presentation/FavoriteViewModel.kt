package com.vp.favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.core.presentation.SingleLiveEvent
import com.vp.core.presentation.ThreadScheduler
import com.vp.favorites.domain.GetFavorites
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
        private val getFavorites: GetFavorites,
        private val threadScheduler: ThreadScheduler
) : ViewModel() {

    private val _state = MutableLiveData<FavoritesState>()
    val state: LiveData<FavoritesState> get() = _state

    private val _navigator = SingleLiveEvent<FavoritesNavigation>()
    val navigator: LiveData<FavoritesNavigation> get() = _navigator

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        _state.value = FavoritesState(loading = true)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun getFavoriteMovies() {
        disposable.add(
                getFavorites()
                        .observeOn(threadScheduler.getMainThread())
                        .subscribeOn(threadScheduler.getIoThread())
                        .subscribe({
                            _state.value = _state.value?.copy(movies = it, loading = false)
                        }, {})
        )
    }

    fun onItemClick(imdbID: String) {
        _navigator.value = FavoritesNavigation.Detail(imdbID)
    }

}