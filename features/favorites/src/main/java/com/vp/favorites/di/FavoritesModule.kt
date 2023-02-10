package com.vp.favorites.di

import com.vp.core.data.movies.FavoritesDAO
import com.vp.favorites.data.FavoritesRepositoryImpl
import com.vp.favorites.domain.FavoritesRepository
import com.vp.favorites.domain.GetFavorites
import com.vp.favorites.domain.GetFavoritesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritesModule {


    @Singleton
    @Provides
    fun providesFavoritesRepository(
            favoritesDAO: FavoritesDAO
    ): FavoritesRepository {
        return FavoritesRepositoryImpl(favoritesDAO)
    }

    @Provides
    fun providesGetFavorites(
            favoritesRepository: FavoritesRepository
    ): GetFavorites {
        return GetFavoritesImpl(
                favoritesRepository = favoritesRepository
        )
    }
}