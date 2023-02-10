package com.vp.favorites.presentation

import com.vp.core.presentation.list.ListItem


data class FavoritesState(
        val movies: List<ListItem> = listOf(),
        val loading: Boolean = false
)
