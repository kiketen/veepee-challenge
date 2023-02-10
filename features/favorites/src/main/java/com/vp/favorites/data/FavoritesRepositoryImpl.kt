package com.vp.favorites.data

import com.vp.core.data.movies.FavoritesDAO
import com.vp.core.data.movies.MovieDAOModel
import com.vp.core.presentation.list.ListItem
import com.vp.favorites.domain.FavoritesRepository
import io.reactivex.Observable


class FavoritesRepositoryImpl(
        private val favoritesDAO: FavoritesDAO
) : FavoritesRepository {
    override fun get(): Observable<List<ListItem>> {
        return Observable.create {
            val favorites = favoritesDAO.getAll()
            it.onNext(favorites.toListItem())
        }
    }

    private fun List<MovieDAOModel>.toListItem() = map {
        ListItem().apply {
            imdbID = it.id
            poster = it.poster
        }
    }
}