package com.vp.detail.di

import com.vp.core.domain.FavoritesRepository
import com.vp.detail.domain.SetFavoriteMovie
import com.vp.detail.domain.SetFavoriteMovieImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class DetailModule {
    @Provides
    fun providesSetFavoriteMovie(
            favoritesRepository: FavoritesRepository
    ): SetFavoriteMovie {
        return SetFavoriteMovieImpl(
                favoritesRepository = favoritesRepository,
                dispatcher = Dispatchers.IO
        )
    }
}