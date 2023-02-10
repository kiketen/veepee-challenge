package com.vp.favorites.domain

import com.vp.core.presentation.list.ListItem
import io.reactivex.Observable


interface GetFavorites {
    operator fun invoke(): Observable<List<ListItem>>
}

class GetFavoritesImpl(
        private val favoritesRepository: FavoritesRepository
) : GetFavorites {
    override fun invoke(): Observable<List<ListItem>> {
        return favoritesRepository.get()
    }
}

