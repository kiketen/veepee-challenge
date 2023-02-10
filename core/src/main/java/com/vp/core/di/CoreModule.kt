package com.vp.core.di

import com.vp.core.data.AppDatabase
import com.vp.core.data.movies.FavoritesDAO
import com.vp.core.presentation.DefaultThreadScheduler
import com.vp.core.presentation.ThreadScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    fun provideMoviesDAO(appDatabase: AppDatabase): FavoritesDAO {
        return appDatabase.favoritesDAO()
    }

    @Singleton
    @Provides
    fun providesThreadScheduler(): ThreadScheduler {
        return DefaultThreadScheduler()
    }

}