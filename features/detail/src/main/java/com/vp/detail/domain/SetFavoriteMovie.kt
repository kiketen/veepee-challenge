package com.vp.detail.domain

import com.vp.core.domain.FavoritesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


interface SetFavoriteMovie {
    suspend operator fun invoke(id: String, isFavorite: Boolean)
}

class SetFavoriteMovieImpl(
        private val favoritesRepository: FavoritesRepository,
        private val dispatcher: CoroutineDispatcher
) : SetFavoriteMovie {
    override suspend fun invoke(id: String, isFavorite: Boolean) {
        return withContext(dispatcher) {
            favoritesRepository.setFavorite(id, isFavorite)
        }
    }
}

