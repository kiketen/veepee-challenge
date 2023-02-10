package com.vp.favorites.domain

import com.vp.core.presentation.list.ListItem
import io.reactivex.Observable


interface FavoritesRepository {
    fun get(): Observable<List<ListItem>>
}