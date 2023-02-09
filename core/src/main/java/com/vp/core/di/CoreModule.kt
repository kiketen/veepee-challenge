package com.vp.core.di

import com.vp.core.data.AppDatabase
import com.vp.core.data.favorites.FavoritesRepositoryImpl
import com.vp.core.data.movies.MoviesDAO
import com.vp.core.domain.FavoritesRepository
import com.vp.core.presentation.DefaultThreadScheduler
import com.vp.core.presentation.ThreadScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    fun provideMoviesDAO(appDatabase: AppDatabase): MoviesDAO {
        return appDatabase.moviesDAO()
    }

    @Singleton
    @Provides
    fun providesFavoritesRepository(
            moviesDAO: MoviesDAO
    ): FavoritesRepository {
        return FavoritesRepositoryImpl(moviesDAO)
    }

    @Singleton
    @Provides
    fun providesThreadScheduler(): ThreadScheduler {
        return DefaultThreadScheduler()
    }

}