package com.vp.core.domain


interface FavoritesRepository {
    fun setFavorite(id: String, isFavorite: Boolean)
}