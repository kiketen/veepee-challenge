package com.vp.detail.di

import com.vp.core.data.movies.FavoritesDAO
import com.vp.detail.data.DetailService
import com.vp.detail.data.MovieDetailsRepositoryImpl
import com.vp.detail.domain.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DetailModule {

    @Provides
    fun providesDetailService(retrofit: Retrofit): DetailService {
        return retrofit.create(DetailService::class.java)
    }

    @Singleton
    @Provides
    fun providesMovieDetailsRepository(
            detailService: DetailService,
            favoritesDAO: FavoritesDAO
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(detailService, favoritesDAO)
    }

    @Provides
    fun providesSetFavoriteMovie(
            movieDetailsRepository: MovieDetailsRepository
    ): SetFavoriteMovie {
        return SetFavoriteMovieImpl(
                movieDetailsRepository = movieDetailsRepository
        )
    }

    @Provides
    fun providesGetMovieDetails(
            movieDetailsRepository: MovieDetailsRepository
    ): GetMovieDetails {
        return GetMovieDetailsImpl(
                movieDetailsRepository = movieDetailsRepository
        )
    }
}