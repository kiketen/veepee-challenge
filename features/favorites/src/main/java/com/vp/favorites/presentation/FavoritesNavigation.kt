package com.vp.favorites.presentation


sealed class FavoritesNavigation {
    class Detail(val id: String) : FavoritesNavigation()
}