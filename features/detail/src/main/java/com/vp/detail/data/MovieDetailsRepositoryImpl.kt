package com.vp.detail.data

import com.vp.core.data.movies.FavoritesDAO
import com.vp.detail.domain.MovieDetail
import com.vp.detail.domain.MovieDetailsRepository
import com.vp.detail.domain.toDAO
import io.reactivex.Observable


class MovieDetailsRepositoryImpl(
        private val detailService: DetailService,
        private val favoritesDAO: FavoritesDAO
) : MovieDetailsRepository {
    override fun getMovieDetails(id: String): Observable<MovieDetail> {
        return detailService.getMovie(id).map {
            val isFavoriteMovie = favoritesDAO.get(id) != null
            it.toDomain(id = id, isFavorite = isFavoriteMovie)
        }
    }

    override fun saveFavorite(movieDetail: MovieDetail) {
        favoritesDAO.set(movieDetail.toDAO())
    }
}