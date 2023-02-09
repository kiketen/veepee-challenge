package com.vp.core.data.favorites

import com.vp.core.data.movies.MoviesDAO
import com.vp.core.domain.FavoritesRepository


class FavoritesRepositoryImpl(
        private val moviesDAO: MoviesDAO
) : FavoritesRepository {
    override fun setFavorite(id: String, isFavorite: Boolean) {
        moviesDAO.setFavorite(id, isFavorite)
    }
}