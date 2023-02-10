package com.vp.favorites.di

import com.vp.favorites.presentation.FavoriteActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoritesActivityModule {
    @ContributesAndroidInjector(modules = [FavoritesViewModelsModule::class])
    abstract fun bindFavoriteActivity(): FavoriteActivity
}