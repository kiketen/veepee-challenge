package com.vp.detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vp.daggeraddons.DaggerViewModelFactory
import com.vp.daggeraddons.ViewModelKey
import com.vp.detail.domain.SetFavoriteMovie
import com.vp.detail.domain.SetFavoriteMovieImpl
import com.vp.detail.presentation.DetailsViewModel
import com.vp.core.domain.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.Dispatchers

@Module
abstract class DetailViewModelsModule {

    @Binds
    abstract fun bindDaggerViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailViewModel(detailsViewModel: DetailsViewModel): ViewModel
}